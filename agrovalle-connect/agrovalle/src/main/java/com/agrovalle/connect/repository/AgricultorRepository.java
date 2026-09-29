package com.agrovalle.connect.repository;

import com.agrovalle.connect.model.Agricultor;
import org.springframework.data.jpa.repository.JpaRepository;

/** Acceso a datos de agricultores. */
public interface AgricultorRepository extends JpaRepository<Agricultor, Long> {
}
