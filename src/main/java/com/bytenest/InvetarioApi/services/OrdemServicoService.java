package com.bytenest.InvetarioApi.services;

import com.bytenest.InvetarioApi.dtos.OrdemServicoRecordDto;
import com.bytenest.InvetarioApi.dtos.PecaQuantidadeDto;
import com.bytenest.InvetarioApi.enums.StatusOrdemServico;
import com.bytenest.InvetarioApi.models.ClienteModel;
import com.bytenest.InvetarioApi.models.FuncionarioModel;
import com.bytenest.InvetarioApi.models.OrdemServicoModel;
import com.bytenest.InvetarioApi.models.PecaModel;
import com.bytenest.InvetarioApi.repositories.ClienteRepository;
import com.bytenest.InvetarioApi.repositories.FuncionarioRepository;
import com.bytenest.InvetarioApi.repositories.OrdemServicoRepository;
import com.bytenest.InvetarioApi.repositories.PecaRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public ResponseEntity<?> salvarOrdem(OrdemServicoRecordDto ordemServicoRecordDto){
        try {
            ClienteModel cliente0 = clienteRepository.findById(ordemServicoRecordDto.clienteId())
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado!"));

             FuncionarioModel funcionario0 = funcionarioRepository.findById(ordemServicoRecordDto.funcionarioId())
                     .orElseThrow(() -> new RuntimeException("Funcionário não encontrado!"));

            List<PecaModel> pecas = new ArrayList<>();

            for (PecaQuantidadeDto pecaDto : ordemServicoRecordDto.pecas()) {
                PecaModel peca = pecaRepository.findById(pecaDto.pecaId())
                        .orElseThrow(() -> new RuntimeException("Peça não encontrada!"));

                if(peca.getQuantidadeTotal() < pecaDto.quantidade()){
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Estoque insuficiente para a peça: "+ pecaDto.pecaId());
                }
                Integer quantidadeAlterada = peca.getQuantidadeTotal() - pecaDto.quantidade();
                peca.setQuantidadeTotal(quantidadeAlterada);
                pecas.add(peca);
            }

            for(PecaModel peca: pecas){
                pecaRepository.save(peca);
            }

            OrdemServicoModel ordemServico = getOrdemServico(ordemServicoRecordDto, cliente0, funcionario0, pecas);
            ordemServico.setDataConclusao();

            return ResponseEntity.status(HttpStatus.CREATED).body(ordemServicoRepository.save(ordemServico));

        }
        catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao salvar ordem de serviço: " + e.getMessage());
        }
    }

    private OrdemServicoModel getOrdemServico(OrdemServicoRecordDto ordemServicoRecordDto, ClienteModel cliente0, FuncionarioModel funcionario0, List<PecaModel> pecas) {
        OrdemServicoModel ordemServico = OrdemServicoModel.builder()
                .codigoOrdem(ordemServicoRecordDto.codigoOrdem())
                .cliente(cliente0)
                .observacoes(ordemServicoRecordDto.observacoes())
                .valorTotal(ordemServicoRecordDto.valorTotal())
                .descricaoProblema(ordemServicoRecordDto.descricaoProblema())
                .valorPecas(ordemServicoRecordDto.valorPecas())
                .responsavel(funcionario0)
                .pecasUtilizadas(pecas)
                .status(StatusOrdemServico.valueOf(ordemServicoRecordDto.status()))
                .build()
                .setDataAbertura(ordemServicoRecordDto.dataAbertura());

        return ordemServico;
    }
}
