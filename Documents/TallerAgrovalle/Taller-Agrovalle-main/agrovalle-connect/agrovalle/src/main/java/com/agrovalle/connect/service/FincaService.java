package com.agrovalle.connect.service;

import com.agrovalle.connect.model.Finca;
import com.agrovalle.connect.repository.FincaRepository;
import org.springframework.stereotype.Service;

/** Lógica de HU-06. */
@Service
public class FincaService {

    private final FincaRepository repository;

    public FincaService(FincaRepository repository) {
        this.repository = repository;
    }

    public Finca registrar(Finca finca) {
        if (finca == null
                || finca.getAgricultorId() == null
                || finca.getAgricultorId() <= 0
                || esVacio(finca.getNombre())
                || esVacio(finca.getMunicipio())) {
            throw new IllegalArgumentException("Los datos de la finca son obligatorios");
        }
        return repository.save(finca);
    }

    private boolean esVacio(String valor) {
        return valor == null || valor.isBlank();
    }
}
