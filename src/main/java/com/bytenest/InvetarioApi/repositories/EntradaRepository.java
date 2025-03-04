package com.bytenest.InvetarioApi.repositories;

import com.bytenest.InvetarioApi.models.EntradaEstoqueModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EntradaRepository extends JpaRepository<EntradaEstoqueModel, UUID> {
}
