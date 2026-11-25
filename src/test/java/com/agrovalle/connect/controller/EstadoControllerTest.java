package com.agrovalle.connect.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/** Pruebas unitarias para el endpoint de estado de Staging. */
class EstadoControllerTest {

    @Test
    void verificarEstadoDebeRetornarHttp200YEstadoUp() {
        EstadoController controller = new EstadoController();

        ResponseEntity<Map<String, Object>> respuesta = controller.verificarEstado();

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        assertEquals("UP", respuesta.getBody().get("estado"));
        assertEquals("AgroValle Connect API", respuesta.getBody().get("servicio"));
    }
}
