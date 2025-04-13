package com.bytenest.InvetarioApi.dtos;

import com.bytenest.InvetarioApi.enums.EstadosUf;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClienteDto(
        @NotBlank
        String nomeCliente,

        @NotBlank
        @Email
        String emailCliente,

        @NotBlank
        String telefone,

        @NotBlank
        String cidade,

        @NotBlank
        String bairro,

        @NotBlank
        String rua,

        @NotNull
        EstadosUf uf
) {
}
