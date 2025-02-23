package com.bytenest.InvetarioApi.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FuncionarioRecordDto(

        @NotBlank
        String nome,

        @NotBlank
        String cpf,

        @NotBlank
        String cargo,

        @NotBlank
        @Email
        String email,

        @NotBlank
        String telefone,

        @NotNull
        Boolean estaAtivo

) {
}
