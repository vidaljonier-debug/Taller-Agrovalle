package com.agrovalle.connect.controller;

import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Controlador de verificación de salud y estado del servicio en Staging. */
@RestController
@RequestMapping("/api/v1/estado")
public class EstadoController {

    /**
     * Endpoint de health-check para monitoreo del entorno en la nube.
     *
     * @return respuesta HTTP 200 OK con el estado operativo del servicio
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> verificarEstado() {
        return ResponseEntity.ok(Map.of(
                "estado", "UP",
                "servicio", "AgroValle Connect API",
                "timestamp", LocalDateTime.now().toString()));
    }
}
