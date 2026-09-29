package com.agrovalle.connect.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.agrovalle.connect.model.Finca;
import com.agrovalle.connect.repository.FincaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/** Pruebas BDD automatizadas de HU-06. */
@ExtendWith(MockitoExtension.class)
class FincaServiceTest {

    @Mock
    private FincaRepository repository;

    @InjectMocks
    private FincaService service;

    @Test
    void givenFincaValidaWhenRegistrarThenGuardaFinca() {
        Finca finca = new Finca("La Esperanza", "Dagua", 1L);
        when(repository.save(finca)).thenReturn(finca);

        Finca resultado = service.registrar(finca);

        assertEquals(1L, resultado.getAgricultorId());
        verify(repository).save(finca);
    }

    @Test
    void givenAgricultorInvalidoWhenRegistrarThenLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> service.registrar(new Finca("La Esperanza", "Dagua", null)));
    }
}
