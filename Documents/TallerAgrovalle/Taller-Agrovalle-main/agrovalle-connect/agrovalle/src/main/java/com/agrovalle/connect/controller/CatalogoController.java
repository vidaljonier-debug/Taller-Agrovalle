package com.agrovalle.connect.controller;

import com.agrovalle.connect.model.Oferta;
import com.agrovalle.connect.service.CatalogoService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** Endpoint de HU-07. */
@RestController
@RequestMapping("/api/v1/ofertas")
public class CatalogoController {

    private final CatalogoService service;

    public CatalogoController(CatalogoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Oferta> buscarPorMunicipio(@RequestParam String municipio) {
        return service.buscarPorMunicipio(municipio);
    }
}
