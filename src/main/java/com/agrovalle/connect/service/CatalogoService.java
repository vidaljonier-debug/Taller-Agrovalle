package com.agrovalle.connect.service;

import com.agrovalle.connect.model.Oferta;
import com.agrovalle.connect.repository.OfertaRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/** Lógica de HU-07. */
@Service
public class CatalogoService {

    private final OfertaRepository repository;

    public CatalogoService(OfertaRepository repository) {
        this.repository = repository;
    }

    public List<Oferta> buscarPorMunicipio(String municipio) {
        if (municipio == null || municipio.isBlank()) {
            throw new IllegalArgumentException("El municipio es obligatorio");
        }
        return repository.findByMunicipioIgnoreCase(municipio.trim());
    }
}
