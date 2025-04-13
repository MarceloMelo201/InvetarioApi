package com.bytenest.InvetarioApi.dtos;

import jakarta.validation.constraints.NotBlank;

public record ResponseDto(

        @NotBlank
        String email,

        @NotBlank
        String token
) {
}
