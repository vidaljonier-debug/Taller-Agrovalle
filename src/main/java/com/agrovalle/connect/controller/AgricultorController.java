package com.agrovalle.connect.controller;

import com.agrovalle.connect.dto.AgricultorRequest;
import com.agrovalle.connect.model.Agricultor;
import com.agrovalle.connect.service.AgricultorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Endpoint de HU-01. */
@RestController
@RequestMapping("/api/v1/agricultores")
public class AgricultorController {

    private final AgricultorService service;

    public AgricultorController(AgricultorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Agricultor> registrar(@Valid @RequestBody AgricultorRequest request) {
        Agricultor agricultor = new Agricultor(
                request.nombre(), request.municipio(), request.cedula());
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(agricultor));
    }
}
