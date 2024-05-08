package api.openweather.weatherapp;

import api.openweather.weatherapp.model.Cidade;
import api.openweather.weatherapp.model.Clima;
import api.openweather.weatherapp.model.dto.DadosListagemCidade;
import api.openweather.weatherapp.repository.CidadeRepository;
import api.openweather.weatherapp.service.CidadeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CidadeServiceTest {

    @Mock
    private CidadeRepository cidadeRepository;

    @InjectMocks
    private CidadeService cidadeService;

    @Test
    public void testCadastrarCidades() {
        List<Cidade> cidades = new ArrayList<>();
        cidades.add(new Cidade(1L, "Porto Alegre", new Clima()));

    }

    @Test
    public void testListarCidadesComSucesso() {
        List<Cidade> cidades = new ArrayList<>();
        cidades.add(new Cidade(1L, "Cidade 1", new Clima()));
        cidades.add(new Cidade(1L, "Cidade 2", new Clima()));
        Page<Cidade> paginaCidades = new PageImpl<>(cidades);

        when(cidadeRepository.findAll(any(Pageable.class))).thenReturn(paginaCidades);

        Page<DadosListagemCidade> paginaListagem = cidadeService.listar(Pageable.unpaged());

        assertNotNull(paginaListagem);
        assertEquals(2, paginaListagem.getTotalElements());
        verify(cidadeRepository, times(1)).findAll(any(Pageable.class));
    }

}