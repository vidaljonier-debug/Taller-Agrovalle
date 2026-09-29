package com.agrovalle.connect.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/** Entidad mínima de oferta para permitir HU-07. */
@Entity
public class Oferta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String producto;
    private String municipio;
    private double cantidadKg;

    public Oferta() {
    }

    public Oferta(String producto, String municipio, double cantidadKg) {
        this.producto = producto;
        this.municipio = municipio;
        this.cantidadKg = cantidadKg;
    }

    public Long getId() {
        return id;
    }

    public String getProducto() {
        return producto;
    }

    public String getMunicipio() {
        return municipio;
    }

    public double getCantidadKg() {
        return cantidadKg;
    }
}
