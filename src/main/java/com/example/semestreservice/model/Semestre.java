package com.example.semestreservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Semestre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    @Column(nullable = false, unique = true)
    private String nombre;

    @JsonFormat(pattern = "dd-MM-yyyy")
    // Se remueve la anotación que impide fechas futuras:
    // @PastOrPresent(message = "La fecha de inicio no puede ser futura")
    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @FutureOrPresent(message = "La fecha de fin debe ser presente o futura")
    @NotNull(message = "La fecha de fin es obligatoria")
    private LocalDate fechaFin;

    @NotNull(message = "El estado 'activo' es obligatorio")
    private boolean activo;
}
