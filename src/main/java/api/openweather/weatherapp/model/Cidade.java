package api.openweather.weatherapp.model;

import api.openweather.weatherapp.model.dto.AtualizarCidadeDTO;
import api.openweather.weatherapp.model.dto.DadosCadastroCidade;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "cidades")
@Entity(name = "Cidade")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Cidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cidade;
    @Embedded
    private Clima clima;

    public Cidade(DadosCadastroCidade dados) {
        this.cidade = dados.cidade();
        this.clima = new Clima(dados.clima());
    }

    public void atualizarInformacoes(AtualizarCidadeDTO atualizacao) {
        if(atualizacao.cidade() != null){
            this.cidade = atualizacao.cidade();
        }
        if(atualizacao.dadosCadastroClima() != null) {
            this.clima.atualizarInformacoes(atualizacao.dadosCadastroClima());
        }
    }
}
