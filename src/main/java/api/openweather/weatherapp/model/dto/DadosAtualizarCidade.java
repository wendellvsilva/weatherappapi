package api.openweather.weatherapp.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record DadosAtualizarCidade(

        Long id,
        @NotBlank
        String cidade,
        @Valid
        DadosCadastroClima dadosCadastroClima

) {
}
