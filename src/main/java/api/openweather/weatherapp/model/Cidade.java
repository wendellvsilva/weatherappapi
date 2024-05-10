package api.openweather.weatherapp.model;

import api.openweather.weatherapp.model.dto.DadosAtualizarCidade;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "cidades")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Cidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cidade;
    @Embedded
    private Clima clima;

    public Cidade(String cidade, Clima clima) {
        setCidade(cidade);
        setClima(clima);
    }

    public void setCidade(String cidade) {
        if(cidade == null) throw new IllegalArgumentException("Cidade não poode ser nula");
        this.cidade = cidade;
    }

    public void setClima(Clima clima) {
        if(clima == null) throw new IllegalArgumentException("Clima não poode ser nulo");
        this.clima = clima;
    }

    public void atualizarInformacoes(DadosAtualizarCidade atualizacao) {
        if(atualizacao.cidade() != null){
            this.cidade = atualizacao.cidade();
        }
        if(atualizacao.dadosCadastroClima() != null) {
            this.clima.atualizarInformacoes(atualizacao.dadosCadastroClima());
        }
    }

}
