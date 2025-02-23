package com.bytenest.InvetarioApi.repositories;

import com.bytenest.InvetarioApi.models.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, UUID> {
}
