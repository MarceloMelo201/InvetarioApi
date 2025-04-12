package com.bytenest.InvetarioApi.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioRecordDto(

        @NotBlank
        String login,

        @NotBlank
        @Email
        String email,

        @NotBlank
        String senha,

        @NotNull
        Boolean estaAtivo
) {
}
