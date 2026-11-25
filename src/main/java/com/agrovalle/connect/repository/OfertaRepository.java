package com.agrovalle.connect.repository;

import com.agrovalle.connect.model.Oferta;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/** Acceso a datos para la búsqueda por municipio. */
public interface OfertaRepository extends JpaRepository<Oferta, Long> {

    List<Oferta> findByMunicipioIgnoreCase(String municipio);
}
