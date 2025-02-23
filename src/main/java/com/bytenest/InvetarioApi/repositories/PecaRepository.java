package com.bytenest.InvetarioApi.repositories;

import com.bytenest.InvetarioApi.models.PecaModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PecaRepository extends JpaRepository<PecaModel, UUID> {

    Optional<PecaModel> findBySku(String sku);
}
