package com.bytenest.InvetarioApi.controllers;

import com.bytenest.InvetarioApi.dtos.ClienteRecordDto;
import com.bytenest.InvetarioApi.dtos.OrdemServicoRecordDto;
import com.bytenest.InvetarioApi.repositories.ClienteRepository;
import com.bytenest.InvetarioApi.repositories.FuncionarioRepository;
import com.bytenest.InvetarioApi.repositories.OrdemServicoRepository;
import com.bytenest.InvetarioApi.repositories.PecaRepository;
import com.bytenest.InvetarioApi.services.OrdemServicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrdemServicoController {

    @Autowired
    PecaRepository pecaRepository;

    @Autowired
    FuncionarioRepository funcionarioRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    OrdemServicoRepository ordemServicoRepository;

    @Autowired
    OrdemServicoService servicoService;

    @PostMapping("/ordens")
    public ResponseEntity<?> salvarOrdem(@RequestBody @Valid OrdemServicoRecordDto ordemServicoRecordDto) {
        return servicoService.salvarOrdem(ordemServicoRecordDto);
    }
}
