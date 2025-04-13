package com.bytenest.InvetarioApi.dtos;

import com.bytenest.InvetarioApi.enums.Cargos;
import jakarta.validation.constraints.NotBlank;

public record CriarUsuarioDto(

        @NotBlank
        String email,

        @NotBlank
        String senha,

        Cargos cargo

) {
}
