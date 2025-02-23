package com.bytenest.InvetarioApi.services;

import com.bytenest.InvetarioApi.dtos.PecaRecordDto;
import com.bytenest.InvetarioApi.models.PecaModel;
import com.bytenest.InvetarioApi.repositories.PecaRepository;
import jakarta.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PecaService {

    private final PecaRepository pecaRepository;

    @Autowired
    public PecaService(PecaRepository pecaRepository) {
        this.pecaRepository = pecaRepository;
    }

    @Transactional
    public ResponseEntity<?> salvarPeca(PecaRecordDto pecaRecordDto){
        try {
            var pecaModel = new PecaModel();
            BeanUtils.copyProperties(pecaRecordDto, pecaModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(pecaRepository.save(pecaModel));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao salvar peça: " + e.getMessage());
        }
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
    public ResponseEntity<Object> atualizarPeca(UUID id, PecaRecordDto pecaRecordDto){
        try {
            Optional<PecaModel> peca0 = pecaRepository.findById(id);

            if(!peca0.isPresent()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Peça não encontrada.");
            }
            var pecaModel = peca0.get();
            BeanUtils.copyProperties(pecaRecordDto, pecaModel);
            return ResponseEntity.status(HttpStatus.OK).body(pecaRepository.save(pecaModel));

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
