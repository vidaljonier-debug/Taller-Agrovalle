package com.agrovalle.connect.controller;

import com.agrovalle.connect.model.Finca;
import com.agrovalle.connect.service.FincaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Controlador REST para la gestión de Fincas (HU-04 y HU-06). */
@RestController
@RequestMapping("/api/fincas")
public class FincaController {

    private final FincaService fincaService;

    /**
     * Constructor para inyección de dependencias.
     * @param fincaService servicio de fincas
     */
    public FincaController(FincaService fincaService) {
        this.fincaService = fincaService;
    }

    /**
     * Endpoint para buscar fincas por municipio (HU-04).
     * Ruta: GET /api/fincas/municipio/{municipio}
     * 
     * @param municipio nombre del municipio a consultar
     * @return lista de fincas encontradas
     */
    @GetMapping("/municipio/{municipio}")
    public ResponseEntity<List<Finca>> buscarPorMunicipio(@PathVariable String municipio) {
        List<Finca> fincas = fincaService.buscarPorMunicipio(municipio);
        return ResponseEntity.ok(fincas);
    }
}