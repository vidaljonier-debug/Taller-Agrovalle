package com.agrovalle.connect.repository;

import com.agrovalle.connect.model.Finca;
import org.springframework.data.jpa.repository.JpaRepository;

/** Acceso a datos de fincas. */
public interface FincaRepository extends JpaRepository<Finca, Long> {
}
