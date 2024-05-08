package api.openweather.weatherapp.model;

import api.openweather.weatherapp.model.dto.DadosCadastroClima;
import api.openweather.weatherapp.model.enums.SituacaoClima;
import api.openweather.weatherapp.model.enums.Turno;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Clima {

    private LocalDateTime data;

    private String umidade;
    private String precipitacao;
    private String temperatura;
    private String velVento;
    private String tempMaxima;
    private String tempMinima;

    @Enumerated(EnumType.STRING)
    private SituacaoClima situacaoClima;

    @Enumerated(EnumType.STRING)
    private Turno turno;

    public Clima(DadosCadastroClima clima) {
        this.data = LocalDateTime.parse(clima.data(),
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")); // formatação da data e hora
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
            this.data = LocalDateTime.parse(dadosClima.data(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
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