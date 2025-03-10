package com.bytenest.InvetarioApi.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrdemServicoRecordDto(

        @NotNull
        UUID clienteId,

        @NotNull
        UUID funcionarioId,

        @NotNull
        List<PecaQuantidadeDto> pecas,

        @NotBlank
        String codigoOrdem,

        @NotNull
        String dataAbertura,

        @NotBlank
        String descricaoProblema,

        @NotBlank
        String status,

        @NotNull
        BigDecimal valorPecas,

        @NotNull
        BigDecimal valorTotal,

        @NotBlank
        String observacoes
) {
}
