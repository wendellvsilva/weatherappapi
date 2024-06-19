package api.openweather.weatherapp.controller;

import api.openweather.weatherapp.model.dto.DadosAtualizarCidade;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import api.openweather.weatherapp.model.dto.DadosCadastroCidade;
import api.openweather.weatherapp.model.dto.DadosCadastroClima;
import api.openweather.weatherapp.model.enums.SituacaoClima;
import api.openweather.weatherapp.model.enums.Turno;

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

        @Test
        @DisplayName("Cadastramos uma cidade e fazemos testes de existência e preenchimento dos campos")
        public void deveCadastrarCidade() throws Exception {

                DadosCadastroCidade novaCidadeJson = new DadosCadastroCidade(1L,"Porto Alegre", new DadosCadastroClima(
                                SituacaoClima.CHOVENDO,
                                Turno.MANHÃ,
                                "06/05/2024",
                                "2",
                                "1",
                                "2",
                                "4",
                                "6"
                ));
                ObjectMapper objectMapper = new ObjectMapper();
                String cidadeJson = objectMapper.writeValueAsString(novaCidadeJson);
                mockMvc.perform(MockMvcRequestBuilders.post("/cidades")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(cidadeJson))
                                .andExpect(jsonPath("$.id").exists())
                                .andExpect(jsonPath("$.cidade").exists())
                                .andExpect(jsonPath("$.clima.data").exists())
                                .andExpect(jsonPath("$.clima.turno").exists())
                                .andExpect(jsonPath("$.clima.turno").isNotEmpty())
                                .andExpect(jsonPath("$.clima.umidade").exists())
                                .andExpect(jsonPath("$.clima.precipitacao").exists())
                                .andExpect(jsonPath("$.clima.velVento").exists())
                                .andExpect(jsonPath("$.clima.tempMaxima").exists())
                                .andExpect(jsonPath("$.clima.tempMinima").exists());

        }

        @Test
        @DisplayName("Criamos uma cidade e depois atualizamos seus valores pelo ID")
        public void deveAtualizarOsCampos() throws Exception {

                DadosCadastroCidade novaCidade = new DadosCadastroCidade(8L,"Porto Alegre", new DadosCadastroClima(
                        SituacaoClima.CHOVENDO,
                        Turno.TARDE,
                        "06/05/2024",
                        "25",
                        "5",
                        "0",
                        "15",
                        "10"
                ));

                ObjectMapper objectMapper = new ObjectMapper();
                String cidadeJson = objectMapper.writeValueAsString(novaCidade);


                MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/cidades")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(cidadeJson))
                        .andExpect(status().isCreated())
                        .andReturn();

                String responseJson = result.getResponse().getContentAsString();
                Long cidadeId = Long.parseLong(JsonPath.read(responseJson, "$.id").toString());
                DadosAtualizarCidade atualizacao = new DadosAtualizarCidade(cidadeId,
                        "NovaCidadeTeste",
                        new DadosCadastroClima(
                                SituacaoClima.CHOVENDO,
                                Turno.MANHÃ,
                                "06/05/2024",
                                "2",
                                "1",
                                "2",
                                "4",
                                "2"));

                String atualizacaoJson = objectMapper.writeValueAsString(atualizacao);


                mockMvc.perform(MockMvcRequestBuilders.put("/cidades/" + cidadeId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(atualizacaoJson))
                        .andExpect(status().isOk());
        }

        @Test
        @DisplayName("O teste deve utilizar o get pelo nome da Cidade")
        public void deveListarCidadePeloNome() throws Exception {

                int page = 0;
                int size = 10;
                mockMvc
                                .perform(MockMvcRequestBuilders.get("/cidades", "Recife")
                                                .param("page", String.valueOf(page))
                                                .param("size", String.valueOf(size)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.content").exists())
                                .andExpect(jsonPath("$.content").isArray())
                                .andExpect(jsonPath("$.content[0].id").exists())
                                .andExpect(jsonPath("$.content[1].cidade").exists());
        }

        @Test
        @DisplayName("Ao listar um id 4(inexistente), o content deve ter tamanho 0")
        public void listarComIdInexistente() throws Exception {
                int page = 4;
                int size = 10;
                mockMvc.perform(MockMvcRequestBuilders.get("/cidades?page=4")
                                .param("page", String.valueOf(page))
                                .param("size", String.valueOf(size)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.content").isArray())
                        .andExpect(jsonPath("$.content", hasSize(0)));
        }
        @Test
        @DisplayName("Deve excluir a cidade com ID 1 do nosso banco, que é Porto Alegre")
        public void deveExcluirCidade() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.delete("/cidades/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isNoContent());

        }
}