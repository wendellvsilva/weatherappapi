package api.openweather.weatherapp.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroCidade(
        Long id,
        @NotEmpty
        @Valid
        String cidade,
        @NotNull
        @Valid
        DadosCadastroClima clima
) {


}
