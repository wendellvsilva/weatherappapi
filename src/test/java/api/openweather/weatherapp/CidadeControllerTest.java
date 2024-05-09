package api.openweather.weatherapp;

import api.openweather.weatherapp.controller.CidadeController;
import api.openweather.weatherapp.model.Cidade;
import api.openweather.weatherapp.model.dto.AtualizarCidadeDTO;
import api.openweather.weatherapp.model.dto.DadosCadastroCidade;
import api.openweather.weatherapp.model.dto.DadosCadastroClima;
import api.openweather.weatherapp.model.dto.DadosListagemCidade;
import api.openweather.weatherapp.model.enums.SituacaoClima;
import api.openweather.weatherapp.model.enums.Turno;
import api.openweather.weatherapp.service.CidadeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CidadeControllerTest {

    @Mock
    private CidadeService cidadeService;

    @InjectMocks
    private CidadeController cidadeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void cadastrar_deveRetornarCidadeCriada() {
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
        when(cidadeService.cadastrar(dadosCadastroCidade)).thenReturn(cidade);

        ResponseEntity<Cidade> response = cidadeController.cadastrar(dadosCadastroCidade);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(cidade, response.getBody());
    }

    @Test
    void listar_deveRetornarListaDeCidades() {
        Pageable pageable = Pageable.unpaged();
        Page<DadosListagemCidade> page = new PageImpl<>(Collections.emptyList());
        when(cidadeService.listar(pageable)).thenReturn(page);

        ResponseEntity<Page<DadosListagemCidade>> response = cidadeController.listar(pageable);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(page, response.getBody());
    }

    @Test
    void atualizar_deveRetornarSucesso() {
        AtualizarCidadeDTO atualizacao = new AtualizarCidadeDTO(1L, "CidadeTeste", null);
        ResponseEntity<String> response = cidadeController.atualizar(atualizacao);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Cidade atualizada com sucesso", response.getBody());
    }

    @Test
    void excluir_deveRetornarIdDaCidadeExcluida() {
        Long cidadeId = 1L;
        ResponseEntity<Long> response = cidadeController.excluir(cidadeId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(cidadeId, response.getBody());
    }
}