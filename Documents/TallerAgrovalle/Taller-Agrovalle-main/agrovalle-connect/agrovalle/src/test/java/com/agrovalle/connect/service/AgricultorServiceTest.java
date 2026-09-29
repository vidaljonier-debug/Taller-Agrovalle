package com.agrovalle.connect.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.agrovalle.connect.model.Agricultor;
import com.agrovalle.connect.repository.AgricultorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/** Pruebas BDD automatizadas de HU-01. */
@ExtendWith(MockitoExtension.class)
class AgricultorServiceTest {

    @Mock
    private AgricultorRepository repository;

    @InjectMocks
    private AgricultorService service;

    @Test
    void givenDatosValidosWhenRegistrarThenGuardaAgricultor() {
        Agricultor agricultor = new Agricultor("Ana", "Dagua", "123");
        when(repository.save(agricultor)).thenReturn(agricultor);

        Agricultor resultado = service.registrar(agricultor);

        assertEquals("Dagua", resultado.getMunicipio());
        verify(repository).save(agricultor);
    }

    @Test
    void givenDatosInvalidosWhenRegistrarThenLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> service.registrar(new Agricultor("", "Dagua", "123")));
    }
}
