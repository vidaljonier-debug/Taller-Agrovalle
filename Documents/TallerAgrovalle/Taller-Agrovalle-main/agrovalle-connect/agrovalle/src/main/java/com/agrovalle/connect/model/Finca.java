package com.agrovalle.connect.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/** Entidad de finca para HU-06. */
@Entity
public class Finca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String municipio;
    private Long agricultorId;

    public Finca() {
    }

    public Finca(String nombre, String municipio, Long agricultorId) {
        this.nombre = nombre;
        this.municipio = municipio;
        this.agricultorId = agricultorId;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getMunicipio() {
        return municipio;
    }

    public Long getAgricultorId() {
        return agricultorId;
    }
}
