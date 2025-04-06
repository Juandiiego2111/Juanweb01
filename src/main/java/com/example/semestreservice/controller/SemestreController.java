package com.example.semestreservice.controller;

import com.example.semestreservice.dto.SemestreRequest;
import com.example.semestreservice.dto.SemestreResponse;
import com.example.semestreservice.service.SemestreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/semestres")
@RequiredArgsConstructor
public class SemestreController {
    private final SemestreService semestreService;

    @PostMapping
    public ResponseEntity<String> crearSemestre(@Valid @RequestBody SemestreRequest request) {
        try {
            semestreService.crearSemestre(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("✅ El semestre fue creado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("❌ No se pudo crear el semestre. Error: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> listarSemestres() {
        try {
            List<SemestreResponse> lista = semestreService.listarSemestres();
            return ResponseEntity.ok().body(lista.isEmpty()
                    ? "⚠️ No hay semestres registrados."
                    : lista);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("❌ No se pudieron listar los semestres. Error: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerSemestre(@PathVariable Long id) {
        try {
            SemestreResponse semestre = semestreService.obtenerSemestre(id);
            return ResponseEntity.ok().body(semestre);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("❌ No se pudo obtener el semestre con ID " + id + ". Error: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarSemestre(
            @PathVariable Long id,
            @Valid @RequestBody SemestreRequest request) {
        try {
            SemestreResponse actualizado = semestreService.actualizarSemestre(id, request);
            return ResponseEntity.ok("✅ El semestre fue actualizado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("❌ No se pudo actualizar el semestre con ID " + id + ". Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarSemestre(@PathVariable Long id) {
        try {
            semestreService.eliminarSemestre(id);
            return ResponseEntity.ok("✅ El semestre fue eliminado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("❌ No se pudo eliminar el semestre con ID " + id + ". Error: " + e.getMessage());
        }
    }
}
