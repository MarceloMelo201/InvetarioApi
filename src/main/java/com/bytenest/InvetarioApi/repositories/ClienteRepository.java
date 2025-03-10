package com.bytenest.InvetarioApi.repositories;

import com.bytenest.InvetarioApi.models.ClienteModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClienteRepository extends JpaRepository<ClienteModel, UUID> {
}
