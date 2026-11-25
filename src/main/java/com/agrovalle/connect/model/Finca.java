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

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public Long getAgricultorId() {
        return agricultorId;
    }

    public void setAgricultorId(Long agricultorId) {
        this.agricultorId = agricultorId;
    }
}