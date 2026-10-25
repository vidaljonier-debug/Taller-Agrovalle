package com.agrovalle.connect.repository;

import com.agrovalle.connect.model.Finca;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/** Acceso a datos de fincas. */
public interface FincaRepository extends JpaRepository<Finca, Long> {

    /**
     * Busca fincas por su municipio ignorando mayúsculas y minúsculas (HU-04).
     * 
     * @param municipio Nombre del municipio a buscar.
     * @return Lista de fincas encontradas en ese municipio.
     */
    List<Finca> findByMunicipioIgnoreCase(String municipio);
}
