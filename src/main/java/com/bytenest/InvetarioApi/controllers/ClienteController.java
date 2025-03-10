package com.bytenest.InvetarioApi.controllers;

import com.bytenest.InvetarioApi.dtos.ClienteRecordDto;
import com.bytenest.InvetarioApi.models.ClienteModel;
import com.bytenest.InvetarioApi.repositories.ClienteRepository;
import com.bytenest.InvetarioApi.services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ClienteController {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ClienteService clienteService;

    @PostMapping("/clientes")
    public ResponseEntity<?> salvarCliente(@RequestBody @Valid ClienteRecordDto clienteRecordDto) {
        return clienteService.salvarCliente(clienteRecordDto);
    }

    @GetMapping("/clientes")
    public ResponseEntity<List<ClienteModel>> listarTodasOsClientes(){
        return clienteService.listarTodasOsClientes();
    }

    @GetMapping("/clientes/{id}")
    public ResponseEntity<Object> listarCliente(@PathVariable(value = "id") UUID id){
        return clienteService.listarCliente(id);
    }

    @PutMapping("/clientes/{id}")
    public ResponseEntity<Object> atualizarCliente(@PathVariable (value = "id") UUID id,
                                                       @RequestBody @Valid ClienteRecordDto clienteRecordDto) {
        return clienteService.atualizarCliente(id, clienteRecordDto);
    }

    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<Object> deletarCliente(@PathVariable (value = "id") UUID id){
        return clienteService.deletarCliente(id);
    }
}
