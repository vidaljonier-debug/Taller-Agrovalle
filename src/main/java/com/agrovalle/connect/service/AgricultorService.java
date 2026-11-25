package com.agrovalle.connect.service;

import com.agrovalle.connect.model.Agricultor;
import com.agrovalle.connect.repository.AgricultorRepository;
import org.springframework.stereotype.Service;

/** Lógica de HU-01. */
@Service
public class AgricultorService {

    private final AgricultorRepository repository;

    public AgricultorService(AgricultorRepository repository) {
        this.repository = repository;
    }

    public Agricultor registrar(Agricultor agricultor) {
        validar(agricultor);
        return repository.save(agricultor);
    }

    private void validar(Agricultor agricultor) {
        if (agricultor == null
                || esVacio(agricultor.getNombre())
                || esVacio(agricultor.getMunicipio())
                || esVacio(agricultor.getCedula())) {
            throw new IllegalArgumentException("Los datos del agricultor son obligatorios");
        }
    }

    private boolean esVacio(String valor) {
        return valor == null || valor.isBlank();
    }
}
