package com.bytenest.InvetarioApi.controllers;

import com.bytenest.InvetarioApi.dtos.CriarUsuarioDto;
import com.bytenest.InvetarioApi.models.UsuarioModel;
import com.bytenest.InvetarioApi.repositories.UsuarioRepository;
import com.bytenest.InvetarioApi.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsuarioController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    UsuarioService usuarioService;

    @PostMapping("/usuarios")
    public ResponseEntity<?> salvarUsuario(@RequestBody @Valid CriarUsuarioDto criarUsuarioDto) {
        return usuarioService.salvarUsuario(criarUsuarioDto);
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioModel>> listarTodasOsUsuarios(){
        return usuarioService.listarTodasOsUsuarios();
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<Object> listarUmUsuario(@PathVariable(value = "id") Long id){
        return usuarioService.listarUsuario(id);
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<Object> atualizarUsuario(@PathVariable (value = "id") Long id,
                                                @RequestBody @Valid CriarUsuarioDto criarUsuarioDto) {
        return usuarioService.atualizarUsuario(id, criarUsuarioDto);
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Object> deletarUsuario(@PathVariable (value = "id") Long id){
        return usuarioService.deletarUsuario(id);
    }
}
