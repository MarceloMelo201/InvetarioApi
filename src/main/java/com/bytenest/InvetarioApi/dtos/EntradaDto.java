package com.bytenest.InvetarioApi.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record EntradaDto(

        @NotNull
        Integer quantidade,

        @NotNull
        BigDecimal valorUnitario,

        @NotNull
        BigDecimal valorTotal,

        @NotBlank
        String notaFiscal,

        @NotBlank
        String observacoes
) {
}
