package api.openweather.weatherapp.service;

import static org.junit.jupiter.api.Assertions.*;

import api.openweather.weatherapp.exceptions.CidadeNotFoundException;
import api.openweather.weatherapp.exceptions.ClimaNotFoundException;
import api.openweather.weatherapp.model.Cidade;
import api.openweather.weatherapp.model.Clima;
import api.openweather.weatherapp.model.dto.DadosAtualizarCidade;
import api.openweather.weatherapp.model.dto.DadosCadastroCidade;
import api.openweather.weatherapp.model.dto.DadosCadastroClima;
import api.openweather.weatherapp.model.enums.SituacaoClima;
import api.openweather.weatherapp.model.enums.Turno;
import api.openweather.weatherapp.repository.CidadeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
        DadosCadastroCidade dadosCadastroCidade = new DadosCadastroCidade("Porto Alegre", new DadosCadastroClima(
                SituacaoClima.CHOVENDO,
                Turno.MANHÃ,
                "06/05/2024 15:00:00",
                "2",
                "1",
                "2",
                "4",
                "6",
                "10"));
        Clima clima = new Clima(new DadosCadastroClima(
                SituacaoClima.CHOVENDO,
                Turno.MANHÃ,
                "06/05/2024 15:00:00",
                null,
                "1",
                "2",
                "4",
                "6",
                "10"));
        Cidade cidade = new Cidade("Porto Alegre", clima);

        when(cidadeRepository.save(any(Cidade.class))).thenReturn(cidade);

        Cidade cidadeSalva = cidadeService.cadastrar(dadosCadastroCidade);

        assertNotNull(cidadeSalva);
        assertEquals(cidade.getCidade(), cidadeSalva.getCidade());
        assertEquals(cidade.getClima(), cidadeSalva.getClima());
        verify(cidadeRepository, times(1)).save(any(Cidade.class));
    }

    @Test
    public void testCadastrarCidadeNula() {
        DadosCadastroCidade dadosCadastroCidade = new DadosCadastroCidade(null, new DadosCadastroClima(
                SituacaoClima.CHOVENDO,
                Turno.MANHÃ,
                "06/05/2024 15:00:00",
                "2",
                "1",
                "2",
                "4",
                "6",
                "10"));
        assertThrows(CidadeNotFoundException.class, () -> cidadeService.cadastrar(dadosCadastroCidade));
    }

    @Test
    public void testCadastrarClimaNulo() {
        DadosCadastroCidade dadosCadastroCidade = new DadosCadastroCidade("Belo Horizonte", null);
        assertThrows(ClimaNotFoundException.class, () -> cidadeService.cadastrar(dadosCadastroCidade));
    }

    @Test
    public void testAtualizarCidadeComSucesso() {
        DadosAtualizarCidade atualizacao = new DadosAtualizarCidade(1L, "CidadeTeste",
                new DadosCadastroClima(SituacaoClima.CHOVENDO,
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
                "1",
                "8",
                "4",
                "2",
                "10")));

        when(cidadeRepository.findById(anyLong())).thenReturn(Optional.of(cidade));
        when(cidadeRepository.save(any(Cidade.class))).thenReturn(cidade);

        Cidade cidadeAtualizada = cidadeService.atualizar(1L, atualizacao);

        verify(cidadeRepository, times(1)).findById(anyLong());
        assertEquals(cidade.getCidade(), cidadeAtualizada.getCidade());
        verify(cidadeRepository, times(1)).save(any(Cidade.class));
    }

    @Test
    public void testExcluirCidadeComSucesso() {
        Long cidadeId = 1L;
        when(cidadeRepository.existsById(1L)).thenReturn(true);
        cidadeService.excluir(cidadeId);

        verify(cidadeRepository, times(1)).deleteById(cidadeId);
    }

    @Test
    public void testExcluirCidadeComErro() {
        Long cidadeId = 1L;
        when(cidadeRepository.existsById(1L)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> cidadeService.excluir(cidadeId));
    }

    // @Test
    // public void testListarCidadesComSucesso() {
    // List<Cidade> cidades = new ArrayList<>();
    // cidades.add(new Cidade(1L, "Cidade 1", new Clima(null)));
    // cidades.add(new Cidade(2L, "Cidade 2", new Clima(null)));
    // Page<Cidade> paginaCidades = new PageImpl<>(cidades);
    //
    // when(cidadeRepository.findAll(any(Pageable.class))).thenReturn(paginaCidades);
    //
    // Page<DadosListagemCidade> paginaListagem =
    // cidadeService.listar(Pageable.unpaged());
    //
    // assertNotNull(paginaListagem);
    // assertEquals(2, paginaListagem.getTotalElements());
    // verify(cidadeRepository, times(1)).findAll(any(Pageable.class));
    // }

}