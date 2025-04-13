package com.barbosa.sistema_reembolso.dto;

import jakarta.validation.constraints.NotBlank;

public record JustificativaRecusaDTO(
        @NotBlank String justificativaDespesa) {
}
