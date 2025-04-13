package com.bytenest.InvetarioApi.security;

import com.bytenest.InvetarioApi.models.UsuarioModel;
import com.bytenest.InvetarioApi.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UsuarioDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UsuarioModel usuarioModel = this.usuarioRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));
        return new org.springframework.security.core.userdetails.User(usuarioModel.getEmail(), usuarioModel.getSenha(), new ArrayList<>());
    }
}
