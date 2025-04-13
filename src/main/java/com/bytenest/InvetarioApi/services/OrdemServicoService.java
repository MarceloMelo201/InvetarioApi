package com.bytenest.InvetarioApi.services;

import com.bytenest.InvetarioApi.dtos.OrdemServicoDto;
import com.bytenest.InvetarioApi.dtos.PecaQuantidadeDto;
import com.bytenest.InvetarioApi.enums.StatusOrdem;
import com.bytenest.InvetarioApi.models.*;
import com.bytenest.InvetarioApi.repositories.ClienteRepository;
import com.bytenest.InvetarioApi.repositories.FuncionarioRepository;
import com.bytenest.InvetarioApi.repositories.OrdemServicoRepository;
import com.bytenest.InvetarioApi.repositories.PecaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrdemServicoService {

    private final PecaRepository pecaRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final ClienteRepository clienteRepository;
    private final OrdemServicoRepository ordemServicoRepository;

    public OrdemServicoService(PecaRepository pecaRepository, FuncionarioRepository funcionarioRepository, ClienteRepository clienteRepository, OrdemServicoRepository ordemServicoRepository) {
        this.pecaRepository = pecaRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.clienteRepository = clienteRepository;
        this.ordemServicoRepository = ordemServicoRepository;
    }

    @Transactional
    public ResponseEntity<?> salvarOrdem(OrdemServicoDto ordemServicoDto) {
        try {
            ClienteModel cliente0 = clienteRepository.findById(ordemServicoDto.clienteId())
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado!"));

            FuncionarioModel funcionario0 = funcionarioRepository.findById(ordemServicoDto.funcionarioId())
                    .orElseThrow(() -> new RuntimeException("Funcionário não encontrado!"));

            List<PecaModel> pecas = new ArrayList<>();

            for (PecaQuantidadeDto pecaDto : ordemServicoDto.pecas()) {
                PecaModel peca = pecaRepository.findById(pecaDto.pecaId())
                        .orElseThrow(() -> new RuntimeException("Peça não encontrada!"));

                if (peca.getQuantidadeTotal() < pecaDto.quantidade()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Estoque insuficiente para a peça: " + pecaDto.pecaId());
                }
                Integer quantidadeAlterada = peca.getQuantidadeTotal() - pecaDto.quantidade();
                peca.setQuantidadeTotal(quantidadeAlterada);
                pecas.add(peca);
            }

            for (PecaModel peca : pecas) {
                pecaRepository.save(peca);
            }

            OrdemServicoModel ordemServico = getOrdemServico(ordemServicoDto, cliente0, funcionario0, pecas);
            ordemServico.setDataConclusao();

            return ResponseEntity.status(HttpStatus.CREATED).body(ordemServicoRepository.save(ordemServico));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao salvar ordem de serviço: " + e.getMessage());
        }
    }

    private OrdemServicoModel getOrdemServico(OrdemServicoDto ordemServicoDto, ClienteModel cliente0, FuncionarioModel funcionario0, List<PecaModel> pecas) {
        return OrdemServicoModel.builder()
                .codigoOrdem(ordemServicoDto.codigoOrdem())
                .cliente(cliente0)
                .observacoes(ordemServicoDto.observacoes())
                .valorTotal(ordemServicoDto.valorTotal())
                .descricaoProblema(ordemServicoDto.descricaoProblema())
                .valorPecas(ordemServicoDto.valorPecas())
                .responsavel(funcionario0)
                .pecasUtilizadas(pecas)
                .status(StatusOrdem.valueOf(ordemServicoDto.status()))
                .build()
                .setDataAbertura(ordemServicoDto.dataAbertura());
    }

    public ResponseEntity<List<OrdemServicoModel>> listarTodasAsOrdens() {
        List<OrdemServicoModel> ordemList = ordemServicoRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(ordemList);
    }

    public ResponseEntity<Object> listarOrdem(String codigoOrdem) {
        Optional<OrdemServicoModel> ordem0 = ordemServicoRepository.findByCodigoOrdem(codigoOrdem);
        return ordem0.<ResponseEntity<Object>>map(ordem -> ResponseEntity.status(HttpStatus.OK).body(ordem))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ordem não encontrada!"));
    }

    @Transactional
    public ResponseEntity<Object> atualizarOrdem(String codigoOrdem, OrdemServicoDto ordemServicoDto) {
        try {
            Optional<OrdemServicoModel> ordem0 = ordemServicoRepository.findByCodigoOrdem(codigoOrdem);

            if (!ordem0.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ordem não encontrada.");
            }

            var ordemModel = ordem0.get();
            BeanUtils.copyProperties(ordemServicoDto, ordemModel);
            return ResponseEntity.status(HttpStatus.OK).body(ordemServicoRepository.save(ordemModel));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar ordem: " + e.getMessage());
        }
    }

    @Transactional
    public ResponseEntity<Object> deletarOrdemServico(String codigoOrdem) {
        Optional<OrdemServicoModel> ordemServico = ordemServicoRepository.findByCodigoOrdem(codigoOrdem);

        if (ordemServico.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ordem de serviço não encontrada.");
        }

        ordemServicoRepository.delete(ordemServico.get());
        return ResponseEntity.status(HttpStatus.OK).body("Ordem de serviço deletada com sucesso!");
    }
}
