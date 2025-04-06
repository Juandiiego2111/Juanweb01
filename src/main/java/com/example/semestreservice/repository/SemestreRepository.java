package com.example.semestreservice.repository;

import com.example.semestreservice.model.Semestre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SemestreRepository extends JpaRepository<Semestre, Long> {
    boolean existsByNombre(String nombre); // ✅ Este método verifica si ya existe un semestre con ese nombre
}
