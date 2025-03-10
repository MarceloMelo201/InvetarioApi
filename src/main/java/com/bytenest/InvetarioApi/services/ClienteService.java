package com.bytenest.InvetarioApi.services;

import com.bytenest.InvetarioApi.dtos.ClienteRecordDto;
import com.bytenest.InvetarioApi.dtos.ClienteRecordDto;
import com.bytenest.InvetarioApi.models.ClienteModel;
import com.bytenest.InvetarioApi.models.ClienteModel;
import com.bytenest.InvetarioApi.repositories.ClienteRepository;
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
public class ClienteService {
    
    private final ClienteRepository clienteRepository;
    
    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    public ResponseEntity<?> salvarCliente(ClienteRecordDto clienteRecordDto){
        try {
            var clienteModel = new ClienteModel();
            BeanUtils.copyProperties(clienteRecordDto, clienteModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(clienteRepository.save(clienteModel));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao salvar funcionário: " + e.getMessage());
        }
    }

    public ResponseEntity<List<ClienteModel>> listarTodasOsClientes(){
        List<ClienteModel> listCliente = clienteRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(listCliente);
    }

    public ResponseEntity<Object> listarCliente(UUID id){
        Optional<ClienteModel> cliente0 = clienteRepository.findById(id);
        return cliente0.<ResponseEntity<Object>> map(ClienteModel -> ResponseEntity.status(HttpStatus.OK).body(cliente0))
                .orElseGet(() ->ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionário não encontrado."));
    }

    @Transactional
    public ResponseEntity<Object> atualizarCliente(UUID id, ClienteRecordDto clienteRecordDto){
        try {
            Optional<ClienteModel> cliente0 = clienteRepository.findById(id);

            if(!cliente0.isPresent()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionário não encontrado.");
            }
            var clienteModel = cliente0.get();
            BeanUtils.copyProperties(clienteRecordDto, clienteModel);
            return ResponseEntity.status(HttpStatus.OK).body(clienteRepository.save(clienteModel));

        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar funcinário: " + e.getMessage());
        }
    }

    @Transactional
    public ResponseEntity<Object> deletarCliente(UUID id){
        Optional<ClienteModel> cliente0 = clienteRepository.findById(id);
        if(cliente0.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionário não encontrado.");
        }
        clienteRepository.delete(cliente0.get());
        return ResponseEntity.status(HttpStatus.OK).body("Funcionário deletado com sucesso!");
    }
    
    
}
