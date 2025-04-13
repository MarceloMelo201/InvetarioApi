package com.bytenest.InvetarioApi.controllers;

import com.bytenest.InvetarioApi.dtos.OrdemServicoDto;
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
    public ResponseEntity<?> criarOrdem(@RequestBody OrdemServicoDto ordemServicoDto) {
        return ordemServicoService.salvarOrdem(ordemServicoDto);
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
    public ResponseEntity<?> atualizarOrdem(@PathVariable String codigoOrdem, @RequestBody OrdemServicoDto ordemServicoDto) {
        return ordemServicoService.atualizarOrdem(codigoOrdem, ordemServicoDto);
    }

    @DeleteMapping("/{codigoOrdem}")
    public ResponseEntity<Object> deletarOrdemServico(@PathVariable String codigoOrdem) {
        return ordemServicoService.deletarOrdemServico(codigoOrdem);
    }
}
