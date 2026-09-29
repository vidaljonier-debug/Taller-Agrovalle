package com.agrovalle.connect.dto;

import jakarta.validation.constraints.NotBlank;

/** Datos de entrada de HU-01. */
public record AgricultorRequest(
        @NotBlank String nombre,
        @NotBlank String municipio,
        @NotBlank String cedula) {
}
