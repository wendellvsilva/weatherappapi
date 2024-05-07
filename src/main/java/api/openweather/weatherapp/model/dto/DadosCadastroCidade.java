package api.openweather.weatherapp.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroCidade(

        @NotBlank
        String cidade,

        @NotNull
        @Valid
        DadosCadastroClima clima
) {


}
