package com.bytenest.InvetarioApi.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record PecaQuantidadeDto(

        @NotNull
        UUID pecaId,

        @NotNull
        Integer quantidade
) {
}
