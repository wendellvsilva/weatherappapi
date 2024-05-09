package api.openweather.weatherapp.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

public record DadosCadastroCidade(

        @NotNull
        @Valid
        @Validated
        @NotBlank
        String cidade,

        @NotNull
        @NotBlank
        @Valid
        DadosCadastroClima clima
) {


}
