package com.bytenest.InvetarioApi.repositories;

import com.bytenest.InvetarioApi.models.OrdemServicoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrdemServicoRepository extends JpaRepository<OrdemServicoModel, UUID> {

    Optional<OrdemServicoModel> findByCodigoOrdem(String codigoOrdem);
}
