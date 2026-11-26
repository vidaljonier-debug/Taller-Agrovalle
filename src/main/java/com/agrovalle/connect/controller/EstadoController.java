package com.agrovalle.connect.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Controlador de verificación de salud y estado del servicio en Staging. */
@RestController
@RequestMapping("/api/v1/estado")
public class EstadoController {

    private final DataSource dataSource;
    private final String perfilActivo;

    /**
     * Constructor con inyección de dependencias para validar la base de datos y el perfil.
     *
     * @param dataSource fuente de datos JDBC configurada en Spring Boot
     * @param perfilActivo perfil activo de Spring (ej. staging)
     */
    public EstadoController(
            DataSource dataSource,
            @Value("${spring.profiles.active:default}") String perfilActivo) {
        this.dataSource = dataSource;
        this.perfilActivo = perfilActivo;
    }

    /**
     * Endpoint de health-check para monitoreo del entorno en la nube.
     *
     * @return respuesta HTTP 200 OK con el estado operativo del servicio y PostgreSQL
     */
    @GetMapping
    public EstadoResponse verificarEstado() {
        String estadoBd = verificarConexionBaseDatos();
        return new EstadoResponse(
                "AgroValle Connect",
                "OK",
                perfilActivo,
                estadoBd,
                LocalDateTime.now().toString());
    }

    private String verificarConexionBaseDatos() {
        if (dataSource == null) {
            return "PostgreSQL Disconnected";
        }
        try (Connection connection = dataSource.getConnection()) {
            if (connection != null && connection.isValid(2)) {
                return "PostgreSQL Connected";
            }
        } catch (SQLException ex) {
            return "PostgreSQL Disconnected";
        }
        return "PostgreSQL Disconnected";
    }

    /**
     * Estructura JSON de respuesta del estado del servicio.
     *
     * @param aplicacion nombre de la aplicación
     * @param estado estado general del servicio
     * @param perfil perfil activo de Spring Boot
     * @param baseDatos estado de conexión con PostgreSQL
     * @param timestamp marca de tiempo de la consulta
     */
    public record EstadoResponse(
            String aplicacion,
            String estado,
            String perfil,
            String baseDatos,
            String timestamp) {
    }
}