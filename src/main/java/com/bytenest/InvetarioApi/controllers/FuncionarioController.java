package com.bytenest.InvetarioApi.controllers;

import com.bytenest.InvetarioApi.dtos.FuncionarioDto;
import com.bytenest.InvetarioApi.models.FuncionarioModel;
import com.bytenest.InvetarioApi.repositories.FuncionarioRepository;
import com.bytenest.InvetarioApi.services.FuncionarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class FuncionarioController {

    @Autowired
    FuncionarioRepository funcionarioRepository;

    @Autowired
    FuncionarioService funcionarioService;

    @PostMapping("/funcionarios")
    public ResponseEntity<?> salvarFuncionario(@RequestBody @Valid FuncionarioDto funcionarioDto) {
        return funcionarioService.salvarFuncionario(funcionarioDto);
    }

    @GetMapping("/funcionarios")
    public ResponseEntity<List<FuncionarioModel>> listarTodasOsFuncionarios(){
        return funcionarioService.listarTodasOsFuncionarios();
    }

    @GetMapping("/funcionarios/{id}")
    public ResponseEntity<Object> listarFuncionario(@PathVariable(value = "id") UUID id){
        return funcionarioService.listarFuncionario(id);
    }

    @PutMapping("/funcionarios/{id}")
    public ResponseEntity<Object> atualizarFuncionario(@PathVariable (value = "id") UUID id,
                                                   @RequestBody @Valid FuncionarioDto funcionarioDto) {
        return funcionarioService.atualizarFuncionario(id, funcionarioDto);
    }

    @DeleteMapping("/funcionarios/{id}")
    public ResponseEntity<Object> deletarFuncionario(@PathVariable (value = "id") UUID id){
        return funcionarioService.deletarFuncionario(id);
    }
}
