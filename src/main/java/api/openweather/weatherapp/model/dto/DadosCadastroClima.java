package api.openweather.weatherapp.model.dto;

import api.openweather.weatherapp.model.enums.SituacaoClima;
import api.openweather.weatherapp.model.enums.Turno;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record DadosCadastroClima (
        @NotNull
        SituacaoClima situacaoClima,
        @NotNull
        Turno turno,
        @NotBlank
        String data,
        @NotNull

        String umidade,
        @NotNull

        String precipitacao,
        @NotNull
        String temperatura,
        @NotNull

        String velVento,
        @NotNull
        String tempMaxima,
        @NotNull
        String tempMinima
) {}