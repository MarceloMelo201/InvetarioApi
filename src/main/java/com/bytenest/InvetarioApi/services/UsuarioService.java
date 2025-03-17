package com.bytenest.InvetarioApi.services;

import com.bytenest.InvetarioApi.dtos.UsuarioRecordDto;
import com.bytenest.InvetarioApi.models.UsuarioModel;
import com.bytenest.InvetarioApi.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository UsuarioRepository) {
        this.usuarioRepository = UsuarioRepository;
    }

    @Transactional
    public ResponseEntity<?> salvarUsuario(UsuarioRecordDto usuarioRecordDto){
        try {
            var usuarioModel = new UsuarioModel();
            BeanUtils.copyProperties(usuarioRecordDto, usuarioModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRepository.save(usuarioModel));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao salvar usuário: " + e.getMessage());
        }
    }

    public ResponseEntity<List<UsuarioModel>> listarTodasAsUsuarios(){
        List<UsuarioModel> listUsuario = usuarioRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(listUsuario);
    }

    public ResponseEntity<Object> listarUsuario(Long id){
        Optional<UsuarioModel> usuario0 = usuarioRepository.findById(id);
        return usuario0.<ResponseEntity<Object>> map(UsuarioModel -> ResponseEntity.status(HttpStatus.OK).body(usuario0))
                .orElseGet(() ->ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado."));
    }

    @Transactional
    public ResponseEntity<Object> atualizarUsuario(Long id, UsuarioRecordDto usuarioRecordDto){
        try {
            Optional<UsuarioModel> usuario0 = usuarioRepository.findById(id);

            if(!usuario0.isPresent()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado.");
            }
            var usuarioModel = usuario0.get();
            BeanUtils.copyProperties(usuarioRecordDto, usuarioModel);
            return ResponseEntity.status(HttpStatus.OK).body(usuarioRepository.save(usuarioModel));

        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar usuário: " + e.getMessage());
        }
    }

    @Transactional
    public ResponseEntity<Object> deletarUsuario(Long id){
        Optional<UsuarioModel> usuario0 = usuarioRepository.findById(id);
        if(usuario0.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }
        usuarioRepository.delete(usuario0.get());
        return ResponseEntity.status(HttpStatus.OK).body("Usuário deletado com sucesso!");
    }
}
