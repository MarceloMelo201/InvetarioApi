package com.bytenest.InvetarioApi.controllers;

import com.bytenest.InvetarioApi.dtos.EntradaRecordDto;
import com.bytenest.InvetarioApi.dtos.PecaRecordDto;
import com.bytenest.InvetarioApi.dtos.PecaEntradaWrapperDto;
import com.bytenest.InvetarioApi.models.PecaModel;
import com.bytenest.InvetarioApi.repositories.PecaRepository;
import com.bytenest.InvetarioApi.services.PecaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
public class PecaController {

    @Autowired
    PecaRepository pecaRepository;

    @Autowired
    PecaService pecaService;

    @PostMapping("/pecas")
    public ResponseEntity<?> salvarPeca(@RequestBody @Valid PecaEntradaWrapperDto pecaEntradaWrapperDto) {
        PecaRecordDto pecaRecordDto = pecaEntradaWrapperDto.getPecaRecordDto();
        EntradaRecordDto entradaRecordDto = pecaEntradaWrapperDto.getEntradaRecordDto();
        return pecaService.salvarPeca(pecaRecordDto, entradaRecordDto);
    }

    @GetMapping("/pecas")
    public ResponseEntity<List<PecaModel>> listarTodasAsPecas(){
        return pecaService.listarTodasAsPecas();
    }

    @GetMapping("/pecas/{id}")
    public ResponseEntity<Object> listarUmaPeca(@PathVariable(value = "id")UUID id){
        return pecaService.listarPeca(id);
    }

    @GetMapping("/pecas/{sku}")
    public ResponseEntity<Object> listarUmaPeca(@PathVariable(value = "sku")String sku){
        return pecaService.listarPecaPorSku(sku);
    }

    @PutMapping("/pecas/{id}")
    public ResponseEntity<Object> atualizarPeca(@PathVariable (value = "id") UUID id,
                                                @RequestBody @Valid PecaEntradaWrapperDto pecaEntradaWrapperDto) {
        PecaRecordDto pecaRecordDto = pecaEntradaWrapperDto.getPecaRecordDto();
        EntradaRecordDto entradaRecordDto = pecaEntradaWrapperDto.getEntradaRecordDto();
        return pecaService.atualizarPeca(id, pecaRecordDto, entradaRecordDto);
    }

    @DeleteMapping("/pecas/{id}")
    public ResponseEntity<Object> deletarPeca(@PathVariable (value = "id") UUID id){
        return pecaService.deletarPeca(id);
    }

}
