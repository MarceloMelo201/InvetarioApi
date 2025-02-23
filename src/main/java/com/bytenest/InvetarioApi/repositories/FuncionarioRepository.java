package com.bytenest.InvetarioApi.repositories;

import com.bytenest.InvetarioApi.models.FuncionarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FuncionarioRepository extends JpaRepository<FuncionarioModel, UUID> {
}
