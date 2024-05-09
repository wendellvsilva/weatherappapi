package api.openweather.weatherapp.controller;

import api.openweather.weatherapp.model.Cidade;
import api.openweather.weatherapp.model.Clima;
import api.openweather.weatherapp.model.dto.DadosCadastroCidade;
import api.openweather.weatherapp.model.dto.DadosCadastroClima;
import api.openweather.weatherapp.model.enums.SituacaoClima;
import api.openweather.weatherapp.model.enums.Turno;
import api.openweather.weatherapp.repository.CidadeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = sqlProvider.populateCidades),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = sqlProvider.deleteCidades)
})
public class CidadeControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private CidadeRepository repository;



    @Test
    public void testCadastrar() throws Exception {

        DadosCadastroCidade novaCidadeJson = new DadosCadastroCidade("Porto Alegre",new DadosCadastroClima(
                SituacaoClima.CHOVENDO,
                Turno.MANHÃ,
                "06/05/2024 15:00:00",
                "2",
                "1",
                "2",
                "4",
                "6",
                "10"));
        ObjectMapper objectMapper = new ObjectMapper();
        String cidadeJson = objectMapper.writeValueAsString(novaCidadeJson);
        mockMvc.perform(MockMvcRequestBuilders.post("/cidades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cidadeJson))
                .andExpect(status().isCreated());
    }
    @Test
    public void testListar() throws Exception {

        int page = 0;
        int size = 10;
        mockMvc
                .perform(MockMvcRequestBuilders.get("/cidades/{cidades}","Recife")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))).andExpect(status().isOk())
                        .andExpect(jsonPath("$.content").exists())
                        .andExpect(jsonPath("$.content").isArray())
                        .andExpect(jsonPath("$.content[0].id").exists())
                        .andExpect(jsonPath("$.content[1].cidade").exists());
                }
    @Test
    public void testListarComErro() throws Exception {
        int page = 0;
        int size = 10;
        assertThrows(IllegalArgumentException.class, () -> {
            mockMvc.perform(MockMvcRequestBuilders.get("/cidades/{cidades}", "JOSEFATESTE")
                    .param("page", String.valueOf(page))
                    .param("size", String.valueOf(size)));
        });
    }
    @Test
    public void testExcluir() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/cidades/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}