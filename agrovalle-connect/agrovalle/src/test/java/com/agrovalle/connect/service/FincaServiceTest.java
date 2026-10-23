package com.agrovalle.connect.service;

import com.agrovalle.connect.model.Finca;
import com.agrovalle.connect.repository.FincaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FincaServiceTest {

    private FincaRepository repository;
    private FincaService service;

    @BeforeEach
    void setUp() {
        repository = mock(FincaRepository.class);
        service = new FincaService(repository);
        // Para que save devuelva lo mismo que entra
        when(repository.save(any())).thenAnswer(i -> i.getArgument(0));
    }

    @Test
    void testRegistrarNullDebeFallar() {
        assertThrows(IllegalArgumentException.class, () -> service.registrar(null));
    }

    @Test
    void testAgricultorIdNullDebeFallar() {
        Finca f = new Finca();
        f.setAgricultorId(null);
        f.setNombre("La Esperanza");
        f.setMunicipio("Cali");
        assertThrows(IllegalArgumentException.class, () -> service.registrar(f));
    }

    @Test
    void testAgricultorIdCeroDebeFallar() {
        Finca f = new Finca();
        f.setAgricultorId(0L);
        f.setNombre("La Esperanza");
        f.setMunicipio("Cali");
        assertThrows(IllegalArgumentException.class, () -> service.registrar(f));
    }

    @Test
    void testNombreVacioDebeFallar() {
        Finca f = new Finca();
        f.setAgricultorId(1L);
        f.setNombre("   ");
        f.setMunicipio("Cali");
        assertThrows(IllegalArgumentException.class, () -> service.registrar(f));
    }

    @Test
    void testMunicipioVacioDebeFallar() {
        Finca f = new Finca();
        f.setAgricultorId(1L);
        f.setNombre("La Esperanza");
        f.setMunicipio(null);
        assertThrows(IllegalArgumentException.class, () -> service.registrar(f));
    }

    @Test
    void testRegistrarOkCaminoFeliz() {
        Finca f = new Finca();
        f.setAgricultorId(1L);
        f.setNombre("La Esperanza");
        f.setMunicipio("Cali");
        Finca guardada = service.registrar(f);
        assertNotNull(guardada);
        verify(repository).save(f);
    }
}
