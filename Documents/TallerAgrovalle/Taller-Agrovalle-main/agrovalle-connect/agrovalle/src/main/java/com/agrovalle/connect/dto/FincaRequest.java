package com.agrovalle.connect.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/** Datos de entrada de HU-06. */
public record FincaRequest(
        @NotBlank String nombre,
        @NotBlank String municipio,
        @NotNull Long agricultorId) {
}
