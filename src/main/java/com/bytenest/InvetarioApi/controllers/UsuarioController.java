package com.bytenest.InvetarioApi.controllers;

import com.bytenest.InvetarioApi.dtos.UsuarioRecordDto;
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
    public ResponseEntity<?> salvarUsuario(@RequestBody @Valid UsuarioRecordDto usuarioRecordDto) {
        return usuarioService.salvarUsuario(usuarioRecordDto);
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioModel>> listarTodasAsUsuarios(){
        return usuarioService.listarTodasOsUsuarios();
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<Object> listarUmaUsuario(@PathVariable(value = "id") Long id){
        return usuarioService.listarUsuario(id);
    }


    @PutMapping("/usuarios/{id}")
    public ResponseEntity<Object> atualizarUsuario(@PathVariable (value = "id") Long id,
                                                @RequestBody @Valid UsuarioRecordDto usuarioRecordDto) {
        return usuarioService.atualizarUsuario(id, usuarioRecordDto);
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Object> deletarUsuario(@PathVariable (value = "id") Long id){
        return usuarioService.deletarUsuario(id);
    }
}
