package com.bytenest.InvetarioApi.controllers;

import com.bytenest.InvetarioApi.dtos.OrdemServicoRecordDto;
import com.bytenest.InvetarioApi.services.OrdemServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ordens")
public class OrdemServicoController {

    private final OrdemServicoService ordemServicoService;

    @Autowired
    public OrdemServicoController(OrdemServicoService ordemServicoService) {
        this.ordemServicoService = ordemServicoService;
    }

    @PostMapping
    public ResponseEntity<?> criarOrdem(@RequestBody OrdemServicoRecordDto ordemServicoRecordDto) {
        return ordemServicoService.salvarOrdem(ordemServicoRecordDto);
    }

    @GetMapping
    public ResponseEntity<?> listarOrdens() {
        return ordemServicoService.listarTodasAsOrdens();
    }

    @GetMapping("/{codigoOrdem}")
    public ResponseEntity<?> obterOrdem(@PathVariable String codigoOrdem) {
        return ordemServicoService.listarOrdem(codigoOrdem);
    }

    @PutMapping("/{codigoOrdem}")
    public ResponseEntity<?> atualizarOrdem(@PathVariable String codigoOrdem, @RequestBody OrdemServicoRecordDto ordemServicoRecordDto) {
        return ordemServicoService.atualizarOrdem(codigoOrdem, ordemServicoRecordDto);
    }
}
