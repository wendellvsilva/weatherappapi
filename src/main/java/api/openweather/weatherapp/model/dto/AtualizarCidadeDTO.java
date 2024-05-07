package api.openweather.weatherapp.model.dto;

public record AtualizarCidadeDTO(
        Long id,
        String cidade,

        DadosCadastroClima dadosCadastroClima

) {
}
