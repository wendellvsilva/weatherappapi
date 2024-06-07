package api.openweather.weatherapp.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import api.openweather.weatherapp.model.dto.DadosCadastroClima;
import api.openweather.weatherapp.model.enums.SituacaoClima;
import api.openweather.weatherapp.model.enums.Turno;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Clima {

    private LocalDate data;

    private String umidade;
    private String precipitacao;
    private String temperatura;
    private String velVento;
    private String tempMaxima;
    private String tempMinima;

    @Enumerated(EnumType.STRING)
    private SituacaoClima situacaoClima;

    private static final DateTimeFormatter FORMATACAO = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Enumerated(EnumType.STRING)
    private Turno turno;

    public Clima(DadosCadastroClima clima) {
        this.data = LocalDate.parse(clima.data(), FORMATACAO);
        this.umidade = clima.umidade();
        this.precipitacao = clima.precipitacao();
        this.temperatura = clima.temperatura();
        this.velVento = clima.velVento();
        this.tempMaxima = clima.tempMaxima();
        this.tempMinima = clima.tempMinima();
        this.situacaoClima = clima.situacaoClima();
        this.turno = clima.turno();
    }

    public void atualizarInformacoes(DadosCadastroClima dadosClima) {
        if (dadosClima.data() != null) {
            this.data = LocalDate.parse(dadosClima.data(), FORMATACAO);

        }
        if (dadosClima.umidade() != null) {
            this.umidade = dadosClima.umidade();
        }
        if (dadosClima.precipitacao() != null) {
            this.precipitacao = dadosClima.precipitacao();
        }
        if (dadosClima.temperatura() != null) {
            this.temperatura = dadosClima.temperatura();
        }
        if (dadosClima.velVento() != null) {
            this.velVento = dadosClima.velVento();
        }
        if (dadosClima.tempMaxima() != null) {
            this.tempMaxima = dadosClima.tempMaxima();
        }
        if (dadosClima.tempMinima() != null) {
            this.tempMinima = dadosClima.tempMinima();
        }
        if (dadosClima.situacaoClima() != null) {
            this.situacaoClima = dadosClima.situacaoClima();
        }
        if (dadosClima.turno() != null) {
            this.turno = dadosClima.turno();
        }
    }
}