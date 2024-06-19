package api.openweather.weatherapp.service;

import static org.junit.jupiter.api.Assertions.*;


import api.openweather.weatherapp.exceptions.CidadeNotFoundException;
import api.openweather.weatherapp.exceptions.ClimaNotFoundException;
import api.openweather.weatherapp.model.Cidade;
import api.openweather.weatherapp.model.Clima;
import api.openweather.weatherapp.model.dto.DadosAtualizarCidade;
import api.openweather.weatherapp.model.dto.DadosCadastroCidade;
import api.openweather.weatherapp.model.dto.DadosCadastroClima;
import api.openweather.weatherapp.model.dto.DadosListagemCidade;
import api.openweather.weatherapp.model.enums.SituacaoClima;
import api.openweather.weatherapp.model.enums.Turno;
import api.openweather.weatherapp.repository.CidadeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.format.DateTimeFormatter;
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
    @DisplayName("Deve cadastrar com os campos corretos.")
    public void deveCadastrarComSucesso() {

        DadosCadastroCidade dadosCadastroCidade = new DadosCadastroCidade(1L,"Porto Alegre", new DadosCadastroClima(
                SituacaoClima.CHOVENDO,
                Turno.MANHÃ,
                "06/05/2024",
                "2",
                "1",
                "2",
                "4",
                "12"
        ));


        when(cidadeRepository.save(any(Cidade.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Cidade cidadeSalva = cidadeService.cadastrar(dadosCadastroCidade);
        assertNotNull(cidadeSalva);
        assertEquals("Porto Alegre", cidadeSalva.getCidade());
        assertNotNull(cidadeSalva.getClima());
        assertEquals(SituacaoClima.CHOVENDO, cidadeSalva.getClima().getSituacaoClima());
        assertEquals(Turno.MANHÃ, cidadeSalva.getClima().getTurno());

        assertEquals("1", cidadeSalva.getClima().getPrecipitacao());
        assertEquals("2", cidadeSalva.getClima().getUmidade());
        assertEquals("2", cidadeSalva.getClima().getVelVento());
        assertEquals("4", cidadeSalva.getClima().getTempMaxima());
        assertEquals("12", cidadeSalva.getClima().getTempMinima());
    }

    @Test
    @DisplayName("Cdastramos uma cidade sem nome e retornamos uma exceção")
    public void deveRetornarExcecaoDeCidadeNula() {
        DadosCadastroCidade dadosCadastroCidade = new DadosCadastroCidade(1L, null, new DadosCadastroClima(
                SituacaoClima.CHOVENDO,
                Turno.MANHÃ,
                "06/05/2024",
                "2",
                "1",
                "2",
                "4",
                "6"));
        assertThrows(CidadeNotFoundException.class, () -> cidadeService.cadastrar(dadosCadastroCidade));
    }

    @Test
    @DisplayName("Cdastramos uma clima sem valores e retornamos uma exceção")
    public void deveRetornarExcecaoDeClimaNulo() {
        DadosCadastroCidade dadosCadastroCidade = new DadosCadastroCidade(1L, "Belo Horizonte", null);
        assertThrows(ClimaNotFoundException.class, () -> cidadeService.cadastrar(dadosCadastroCidade));
    }

    @Test
    @DisplayName("Criamos uma cidade e atualizamos o valor de temperatura")
    public void deveAtualizarUmCampo() {
        DadosAtualizarCidade atualizacao = new DadosAtualizarCidade(1L, "CidadeTeste",
                new DadosCadastroClima(SituacaoClima.CHOVENDO,
                        Turno.MANHÃ,
                        "06/05/2024",
                        "2",
                        "1",
                        "2",
                        "4",
                        "2"
                ));
        Cidade cidade = new Cidade(1L, "CidadeTeste", new Clima(new DadosCadastroClima(SituacaoClima.CHOVENDO,
                Turno.MANHÃ,
                "06/05/2024",
                "2",
                "1",
                "8",
                "4",
                "2"
        )));

        when(cidadeRepository.findById(anyLong())).thenReturn(Optional.of(cidade));
        when(cidadeRepository.save(any(Cidade.class))).thenReturn(cidade);

        Cidade cidadeAtualizada = cidadeService.atualizar(1L, atualizacao);

        verify(cidadeRepository, times(1)).findById(anyLong());
        assertEquals(cidade.getCidade(), cidadeAtualizada.getCidade());
        verify(cidadeRepository, times(1)).save(any(Cidade.class));
    }

    @Test
    @DisplayName("Excluimos a cidade por Id e verificamos se o método de deletar foi chamado.")
    public void deveExcluirCidadePorId() {
        Long cidadeId = 1L;
        when(cidadeRepository.existsById(1L)).thenReturn(true);
        cidadeService.excluir(cidadeId);

        verify(cidadeRepository, times(1)).deleteById(cidadeId);
    }

    @Test
    @DisplayName("Esperamos que retorna uma exceçã quando a cidade com o ID especificado não existe")
    public void deveLancarExcecaoEmIdInexistente() {
        Long cidadeId = 1L;
        when(cidadeRepository.existsById(1L)).thenReturn(false);

        assertThrows(CidadeNotFoundException.class, () -> cidadeService.excluir(cidadeId));
    }

    @Test
    @DisplayName("Criamos duas cidades e esperamos que a quantidade de paginas seja duas.")
    public void deveListarCidades() {

        List<Cidade> cidades = new ArrayList<>();
        cidades.add(new Cidade("Porto Alegre",new Clima(new DadosCadastroClima(
                SituacaoClima.CHOVENDO,
                Turno.MANHÃ,
                "06/05/2024",
                null,
                "1",
                "2",
                "4",
                "6"
        ))));
        cidades.add(new Cidade("São Paulo",new Clima(new DadosCadastroClima(
                SituacaoClima.CHOVENDO,
                Turno.MANHÃ,
                "06/05/2024",
                null,
                "1",
                "2",
                "4",
                "6"))));
        Page<Cidade> paginaCidades = new PageImpl<>(cidades);

        when(cidadeRepository.findAll(any(Pageable.class))).thenReturn(paginaCidades);

        Page<DadosListagemCidade> paginaListagem = cidadeService.listar(Pageable.unpaged());

        assertNotNull(paginaListagem);
        assertEquals(2, paginaListagem.getTotalElements());
    }
}
