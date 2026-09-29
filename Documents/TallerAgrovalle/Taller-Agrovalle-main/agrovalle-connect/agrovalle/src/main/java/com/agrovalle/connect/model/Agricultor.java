package com.agrovalle.connect.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/** Entidad de agricultor para HU-01. */
@Entity
public class Agricultor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String municipio;
    private String cedula;

    public Agricultor() {
    }

    public Agricultor(String nombre, String municipio, String cedula) {
        this.nombre = nombre;
        this.municipio = municipio;
        this.cedula = cedula;
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

    public String getCedula() {
        return cedula;
    }
}
