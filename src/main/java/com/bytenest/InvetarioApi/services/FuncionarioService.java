package com.bytenest.InvetarioApi.services;

import com.bytenest.InvetarioApi.dtos.FuncionarioDto;
import com.bytenest.InvetarioApi.models.FuncionarioModel;
import com.bytenest.InvetarioApi.repositories.FuncionarioRepository;
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
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;

    @Autowired
    public FuncionarioService(FuncionarioRepository FuncionarioRepository) {
        this.funcionarioRepository = FuncionarioRepository;
    }

    @Transactional
    public ResponseEntity<?> salvarFuncionario(FuncionarioDto funcionarioDto){
        try {
            var funcionarioModel = new FuncionarioModel();
            BeanUtils.copyProperties(funcionarioDto, funcionarioModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(funcionarioRepository.save(funcionarioModel));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao salvar funcionário: " + e.getMessage());
        }
    }

    public ResponseEntity<List<FuncionarioModel>> listarTodasOsFuncionarios(){
        List<FuncionarioModel> listFuncionario = funcionarioRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(listFuncionario);
    }

    public ResponseEntity<Object> listarFuncionario(UUID id){
        Optional<FuncionarioModel> funcionario0 = funcionarioRepository.findById(id);
        return funcionario0.<ResponseEntity<Object>> map(FuncionarioModel -> ResponseEntity.status(HttpStatus.OK).body(funcionario0))
                .orElseGet(() ->ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionário não encontrado."));
    }

    @Transactional
    public ResponseEntity<Object> atualizarFuncionario(UUID id, FuncionarioDto funcionarioDto){
        try {
            Optional<FuncionarioModel> funcionario0 = funcionarioRepository.findById(id);

            if(!funcionario0.isPresent()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionário não encontrado.");
            }
            var funcionarioModel = funcionario0.get();
            BeanUtils.copyProperties(funcionarioDto, funcionarioModel);
            return ResponseEntity.status(HttpStatus.OK).body(funcionarioRepository.save(funcionarioModel));

        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar funcinário: " + e.getMessage());
        }
    }

    @Transactional
    public ResponseEntity<Object> deletarFuncionario(UUID id){
        Optional<FuncionarioModel> funcionario0 = funcionarioRepository.findById(id);
        if(funcionario0.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionário não encontrado.");
        }
        funcionarioRepository.delete(funcionario0.get());
        return ResponseEntity.status(HttpStatus.OK).body("Funcionário deletado com sucesso!");
    }
}
