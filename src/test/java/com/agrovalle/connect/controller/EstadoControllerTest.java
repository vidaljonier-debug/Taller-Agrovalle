package com.agrovalle.connect.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;

class EstadoControllerTest {

    @Test
    void verificarEstado_ConexionActiva_RetornaOkYPostgresConnected() throws SQLException {
        // Arrange
        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.isValid(2)).thenReturn(true);
        EstadoController controller = new EstadoController(dataSource, "staging");

        // Act
        var response = controller.verificarEstado();

        // Assert
        assertEquals("AgroValle Connect", response.aplicacion());
        assertEquals("OK", response.estado());
        assertEquals("staging", response.perfil());
        assertEquals("PostgreSQL Connected", response.baseDatos());
    }

    @Test
    void verificarEstado_ErrorConexion_RetornaPostgresDisconnected() throws SQLException {
        // Arrange
        DataSource dataSource = mock(DataSource.class);
        when(dataSource.getConnection()).thenThrow(new SQLException("Error de red"));
        EstadoController controller = new EstadoController(dataSource, "staging");

        // Act
        var response = controller.verificarEstado();

        // Assert
        assertEquals("PostgreSQL Disconnected", response.baseDatos());
    }
}