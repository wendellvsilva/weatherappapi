package api.openweather.weatherapp.service;

import static org.junit.jupiter.api.Assertions.*;

import api.openweather.weatherapp.model.Cidade;
import api.openweather.weatherapp.model.Clima;
import api.openweather.weatherapp.model.dto.AtualizarCidadeDTO;
import api.openweather.weatherapp.model.dto.DadosCadastroCidade;
import api.openweather.weatherapp.model.dto.DadosCadastroClima;
import api.openweather.weatherapp.model.dto.DadosListagemCidade;
import api.openweather.weatherapp.model.enums.SituacaoClima;
import api.openweather.weatherapp.model.enums.Turno;
import api.openweather.weatherapp.repository.CidadeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CidadeServiceTest {

    @Mock
    private CidadeRepository cidadeRepository;

    @InjectMocks
    private CidadeService cidadeService;

    @Test
    public void testCadastrarCidadeComSucesso() {
        DadosCadastroCidade dadosCadastroCidade = new DadosCadastroCidade("CidadeTeste",new DadosCadastroClima(
                SituacaoClima.CHOVENDO,
                Turno.MANHÃ,
                "06/05/2024 15:00:00",
                "2",
                "1",
                "2",
                "4",
                "6",
                "10"));
        Cidade cidade = new Cidade(dadosCadastroCidade);

        when(cidadeRepository.save(any(Cidade.class))).thenReturn(cidade);

        Cidade cidadeSalva = cidadeService.cadastrar(dadosCadastroCidade);

        assertNotNull(cidadeSalva);
        assertEquals(cidade.getCidade(), cidadeSalva.getCidade());
        assertEquals(cidade.getClima(), cidadeSalva.getClima());
        verify(cidadeRepository, times(1)).save(any(Cidade.class));
    }

    @Test
    public void testAtualizarCidadeComSucesso() {
        AtualizarCidadeDTO atualizacao = new AtualizarCidadeDTO(1L, "CidadeTeste", new DadosCadastroClima(SituacaoClima.CHOVENDO,
                Turno.MANHÃ,
                "06/05/2024 15:00:00",
                "2",
                "1",
                "2",
                "4",
                "2",
                "10"));
        Cidade cidade = new Cidade(1L, "CidadeTeste", new Clima(new DadosCadastroClima(SituacaoClima.CHOVENDO,
                Turno.MANHÃ,
                "06/05/2024 15:00:00",
                "2",
                "6",
                "8",
                "4",
                "2",
                "10")));

        when(cidadeRepository.findById(anyLong())).thenReturn(Optional.of(cidade));
        when(cidadeRepository.save(any(Cidade.class))).thenReturn(cidade);

        ResponseEntity<String> response = cidadeService.atualizar(atualizacao);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Cidade atualizada com sucesso", response.getBody());
        verify(cidadeRepository, times(1)).findById(anyLong());
        verify(cidadeRepository, times(1)).save(any(Cidade.class));
    }

    @Test
    public void testExcluirCidadeComSucesso() {
        Long cidadeId = 1L;

        cidadeService.excluir(cidadeId);

        verify(cidadeRepository, times(1)).deleteById(cidadeId);
    }

    @Test
    public void testListarCidadesComSucesso() {
        List<Cidade> cidades = new ArrayList<>();
        cidades.add(new Cidade(1L, "Cidade 1", new Clima()));
        cidades.add(new Cidade(2L, "Cidade 2", new Clima()));
        Page<Cidade> paginaCidades = new PageImpl<>(cidades);

        when(cidadeRepository.findAll(any(Pageable.class))).thenReturn(paginaCidades);

        Page<DadosListagemCidade> paginaListagem = cidadeService.listar(Pageable.unpaged());

        assertNotNull(paginaListagem);
        assertEquals(2, paginaListagem.getTotalElements());
        verify(cidadeRepository, times(1)).findAll(any(Pageable.class));
    }

}