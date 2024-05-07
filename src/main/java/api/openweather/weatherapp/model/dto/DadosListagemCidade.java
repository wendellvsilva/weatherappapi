package api.openweather.weatherapp.model.dto;

import api.openweather.weatherapp.model.Cidade;
import api.openweather.weatherapp.model.Clima;

public record DadosListagemCidade(Long id, String cidade, Clima clima) {

    public DadosListagemCidade(Cidade cidade){
        this(cidade.getId(), cidade.getCidade(), cidade.getClima());
    }
}
