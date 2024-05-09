package api.openweather.weatherapp.model.dto;

public record DadosAtualizarCidade(
        Long id,
        String cidade,
        DadosCadastroClima dadosCadastroClima

) {
}
