package com.example.semestreservice.controller;

import com.example.semestreservice.dto.SemestreRequest;
import com.example.semestreservice.dto.SemestreResponse;
import com.example.semestreservice.service.SemestreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.semestreservice.exception.ResourceNotFoundException;

import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/semestre-service")
@RequiredArgsConstructor
public class SemestreController {

    private final SemestreService semestreService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> crear(@Valid @RequestBody SemestreRequest request) {
        SemestreResponse creado = semestreService.crearSemestre(request);
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "✅ Semestre creado correctamente");
        response.put("semestre", creado);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> listar() {
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "✅ Lista de semestres obtenida exitosamente");
        response.put("semestres", semestreService.listarSemestres());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obtener(@PathVariable Long id) {
        SemestreResponse semestre = semestreService.obtenerSemestre(id);
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "✅ Semestre encontrado");
        response.put("semestre", semestre);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizar(@PathVariable Long id, @Valid @RequestBody SemestreRequest request) {
        SemestreResponse actualizado = semestreService.actualizarSemestre(id, request);
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "✅ Semestre actualizado correctamente");
        response.put("semestre", actualizado);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminarSemestre(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            semestreService.eliminarSemestre(id);
            response.put("mensaje", "✅ Semestre eliminado correctamente");
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException ex) {
            response.put("mensaje", "No se encontró el semestre con ID: " + id);
            response.put("error", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception ex) {
            response.put("mensaje", "❌ Ocurrió un error al eliminar el semestre");
            response.put("error", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<Map<String, Object>> listarPaginado(
            @PathVariable int page,
            @RequestParam(defaultValue = "4") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        if (size < 1) {
            return ResponseEntity.badRequest().body(Map.of(
                    "mensaje", "Tamaño de página no válido",
                    "error", "El parámetro 'size' debe ser mayor a 0"
            ));
        }

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<SemestreResponse> pageResult = semestreService.listarSemestresPaginados(pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "✅ Página de semestres obtenida correctamente");
        response.put("pagina", pageResult);

        return ResponseEntity.ok(response);
    }
}
