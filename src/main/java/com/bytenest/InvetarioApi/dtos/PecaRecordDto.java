package com.bytenest.InvetarioApi.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PecaRecordDto(
        @NotBlank
        String nome,

        @NotNull
        BigDecimal valor,

        @NotBlank
        String sku,

        @NotBlank
        String descricao,

        @NotNull
        Integer quantidade
) {
}
