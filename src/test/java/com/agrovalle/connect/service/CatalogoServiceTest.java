package com.agrovalle.connect.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.agrovalle.connect.model.Oferta;
import com.agrovalle.connect.repository.OfertaRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/** Pruebas BDD automatizadas de HU-07. */
@ExtendWith(MockitoExtension.class)
class CatalogoServiceTest {

    @Mock
    private OfertaRepository repository;

    @InjectMocks
    private CatalogoService service;

    @Test
    void givenOfertasEnVariosMunicipiosWhenBuscarDaguaThenDevuelveDagua() {
        Oferta oferta = new Oferta("Mango", "Dagua", 100);
        when(repository.findByMunicipioIgnoreCase("Dagua")).thenReturn(List.of(oferta));

        List<Oferta> resultado = service.buscarPorMunicipio("Dagua");

        assertEquals(1, resultado.size());
        assertEquals("Dagua", resultado.get(0).getMunicipio());
        verify(repository).findByMunicipioIgnoreCase("Dagua");
    }

    @Test
    void givenMunicipioVacioWhenBuscarThenLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> service.buscarPorMunicipio(" "));
    }
}
