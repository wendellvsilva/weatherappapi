package api.openweather.weatherapp.model.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroCidade(
        Long id,
        @NotEmpty
        
        String cidade,
        @NotNull
       
        DadosCadastroClima clima
) {


}
