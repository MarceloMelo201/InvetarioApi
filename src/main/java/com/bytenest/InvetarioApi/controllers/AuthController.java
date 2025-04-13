package com.bytenest.InvetarioApi.controllers;

import com.bytenest.InvetarioApi.dtos.CriarUsuarioDto;
import com.bytenest.InvetarioApi.dtos.LoginUsuarioDto;
import com.bytenest.InvetarioApi.dtos.ResponseDto;
import com.bytenest.InvetarioApi.enums.Cargos;
import com.bytenest.InvetarioApi.models.UsuarioModel;
import com.bytenest.InvetarioApi.repositories.UsuarioRepository;
import com.bytenest.InvetarioApi.security.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginUsuarioDto loginUsuarioDto){

        UsuarioModel usuarioModel = this.repository
                .findByEmail(loginUsuarioDto.email()).orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
                if(passwordEncoder.matches(usuarioModel.getSenha(), loginUsuarioDto.senha())){
                    String token = this.tokenService.gerarToken(usuarioModel);
                    return ResponseEntity.ok(new ResponseDto(usuarioModel.getEmail(), token));
                }
                return ResponseEntity.badRequest().build();

    }

    @PostMapping("/register")
    public ResponseEntity registro(@RequestBody CriarUsuarioDto criarUsuarioDto){
        Optional<UsuarioModel> usuario = repository.findByEmail(criarUsuarioDto.email());

        if(usuario.isEmpty()){
            UsuarioModel usuarioModel = new UsuarioModel();
            usuarioModel.setSenha(passwordEncoder.encode(criarUsuarioDto.senha()));
            usuarioModel.setEmail(criarUsuarioDto.email());

            // Definir o cargo no modelo
            try {
                usuarioModel.setCargo(criarUsuarioDto.cargo()); // O cargo será automaticamente mapeado para o enum
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body("Cargo inválido: " + criarUsuarioDto.cargo());
            }

            this.repository.save(usuarioModel);

            String token = this.tokenService.gerarToken(usuarioModel);
            return ResponseEntity.ok(new ResponseDto(usuarioModel.getEmail(), token));
        }

        return ResponseEntity.badRequest().build();
    }

}
