package com.bytenest.InvetarioApi.services;

import com.bytenest.InvetarioApi.dtos.EntradaDto;
import com.bytenest.InvetarioApi.dtos.PecaDto;
import com.bytenest.InvetarioApi.models.EntradaEstoqueModel;
import com.bytenest.InvetarioApi.models.PecaModel;
import com.bytenest.InvetarioApi.repositories.EntradaRepository;
import com.bytenest.InvetarioApi.repositories.PecaRepository;
import jakarta.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PecaService {

    private final PecaRepository pecaRepository;
    private final EntradaRepository entradaRepository;

    @Autowired
    public PecaService(PecaRepository pecaRepository, EntradaRepository entradaRepository) {
        this.pecaRepository = pecaRepository;
        this.entradaRepository = entradaRepository;
    }

    @Transactional
    public ResponseEntity<?> salvarPeca(PecaDto pecaDto, EntradaDto entradaDto){
        try {
            var pecaModel = new PecaModel();
            BeanUtils.copyProperties(pecaDto, pecaModel);

            EntradaEstoqueModel entrada = getEstoqueModel(entradaDto, pecaModel);

            pecaModel.getEntradas().add(entrada);

            pecaRepository.save(pecaModel);
            entradaRepository.save(entrada);
            return ResponseEntity.status(HttpStatus.CREATED).body(pecaModel);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao salvar peça e entrada: " + e.getMessage());
        }
    }

    private EntradaEstoqueModel getEstoqueModel(EntradaDto entradaDto, PecaModel pecaModel) {
        EntradaEstoqueModel entrada = EntradaEstoqueModel.builder()
                .dataEntrada(LocalDateTime.now())
                .produto(pecaModel)
                .quantidade(entradaDto.quantidade())
                .valorUnitario(entradaDto.valorUnitario())
                .valorTotal(entradaDto.valorTotal())
                .notaFiscal(entradaDto.notaFiscal())
                .observacoes(entradaDto.observacoes())
                .build();
        return entrada;
    }

    public ResponseEntity<List<PecaModel>> listarTodasAsPecas(){
        List<PecaModel> listPeca = pecaRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(listPeca);
    }

    public ResponseEntity<Object> listarPeca(UUID id){
        Optional<PecaModel> peca0 = pecaRepository.findById(id);
        return peca0.<ResponseEntity<Object>> map(PecaModel -> ResponseEntity.status(HttpStatus.OK).body(peca0))
                .orElseGet(() ->ResponseEntity.status(HttpStatus.NOT_FOUND).body("Peça não encontrada."));
    }

    public ResponseEntity<Object> listarPecaPorSku(String sku){
        Optional<PecaModel> peca0 = pecaRepository.findBySku(sku);
        return peca0.<ResponseEntity<Object>> map(PecaModel -> ResponseEntity.status(HttpStatus.OK).body(peca0))
                    .orElseGet(() ->ResponseEntity.status(HttpStatus.NOT_FOUND).body("Peça não encontrada."));
    }

    @Transactional
    public ResponseEntity<Object> atualizarPeca(UUID id, PecaDto pecaDto, EntradaDto entradaDto){
        try {
            Optional<PecaModel> peca0 = pecaRepository.findById(id);

            if(!peca0.isPresent()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Peça não encontrada.");
            }
            var pecaModel = peca0.get();

            EntradaEstoqueModel entrada = getEstoqueModel(entradaDto, pecaModel);

            pecaModel.setNome(pecaDto.nome());
            pecaModel.setValor(pecaDto.valor());
            pecaModel.setSku(pecaDto.sku());
            pecaModel.setDescricao(pecaDto.descricao());
            pecaModel.setQuantidadeTotal(pecaModel.getQuantidadeTotal() + entrada.getQuantidade());

            pecaModel.getEntradas().add(entrada);

            entradaRepository.save(entrada);
            pecaRepository.save(pecaModel);


            return ResponseEntity.status(HttpStatus.OK).body((pecaModel));

        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar peça: " + e.getMessage());
        }
    }

    @Transactional
    public ResponseEntity<Object> deletarPeca(UUID id){
        Optional<PecaModel> peca0 = pecaRepository.findById(id);
        if(peca0.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Peça não encontrada.");
        }
        pecaRepository.delete(peca0.get());
        return ResponseEntity.status(HttpStatus.OK).body("Peça deletada com sucesso!");
    }

}
