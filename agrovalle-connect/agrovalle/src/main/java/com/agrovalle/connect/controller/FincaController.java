package com.agrovalle.connect.controller;

import com.agrovalle.connect.dto.FincaRequest;
import com.agrovalle.connect.model.Finca;
import com.agrovalle.connect.service.FincaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Endpoint de HU-06. */
@RestController
@RequestMapping("/api/v1/fincas")
public class FincaController {

    private final FincaService service;

    public FincaController(FincaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Finca> registrar(@Valid @RequestBody FincaRequest request) {
        Finca finca = new Finca(request.nombre(), request.municipio(), request.agricultorId());
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(finca));
    }
}
