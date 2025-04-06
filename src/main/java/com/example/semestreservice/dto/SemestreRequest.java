package com.example.semestreservice.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record SemestreRequest(
        @NotBlank(message = "El nombre no puede estar vacío")
        String nombre,

        @NotNull(message = "La fecha de inicio es obligatoria")
        @FutureOrPresent(message = "La fecha de inicio debe ser presente o futura")
        LocalDate fechaInicio,

        @NotNull(message = "La fecha de fin es obligatoria")
        @Future(message = "La fecha de fin debe ser futura")
        LocalDate fechaFin,

        boolean activo
) {}
