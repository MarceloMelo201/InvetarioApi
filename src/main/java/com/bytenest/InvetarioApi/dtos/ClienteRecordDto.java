package com.bytenest.InvetarioApi.dtos;

import com.bytenest.InvetarioApi.enums.EstadoEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClienteRecordDto(
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
        EstadoEnum uf
) {
}
