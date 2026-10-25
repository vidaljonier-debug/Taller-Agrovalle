package com.agrovalle.connect.service;

import com.agrovalle.connect.model.Finca;
import com.agrovalle.connect.repository.FincaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

/** Lógica de negocio para Fincas (HU-04 y HU-06). */
@Service
public class FincaService {

    private final FincaRepository repository;

    /**
     * Constructor para inyección de dependencias.
     * @param repository repositorio de fincas
     */
    public FincaService(FincaRepository repository) {
        this.repository = repository;
    }

    /**
     * Registra una nueva finca validando datos obligatorios (HU-06).
     * @param finca entidad a registrar
     * @return finca guardada
     */
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

    /**
     * Busca fincas por municipio validando que el parámetro no esté vacío (HU-04).
     * @param municipio nombre del municipio a buscar
     * @return lista de fincas encontradas
     */
    public List<Finca> buscarPorMunicipio(String municipio) {
        if (esVacio(municipio)) {
            throw new IllegalArgumentException("El municipio de búsqueda no puede estar vacío");
        }
        return repository.findByMunicipioIgnoreCase(municipio.trim());
    }

    /**
     * Verifica si un valor es nulo o vacío.
     * @param valor texto a validar
     * @return true si es vacío
     */
    private boolean esVacio(String valor) {
        return valor == null || valor.isBlank();
    }
}