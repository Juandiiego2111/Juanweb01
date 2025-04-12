package com.example.semestreservice.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
public class ErrorResponse {

    private String mensaje;             // Mensaje principal, como "Recurso no encontrado"
    private String error;               // Detalles del error, como el mensaje técnico
    private String path;                // Ruta donde ocurrió el error
    private Map<String, String> detalles;  // Errores de validación por campo (opcional)

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime timestamp;

    public ErrorResponse(String mensaje, String error) {
        this.mensaje = mensaje;
        this.error = error;
        this.timestamp = LocalDateTime.now();
    }
}
