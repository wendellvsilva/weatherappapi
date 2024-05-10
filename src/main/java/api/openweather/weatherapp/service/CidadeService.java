package api.openweather.weatherapp.service;

import api.openweather.weatherapp.exceptions.CidadeNotFoundException;
import api.openweather.weatherapp.exceptions.ClimaNotFoundException;
import api.openweather.weatherapp.model.Clima;
import api.openweather.weatherapp.model.dto.DadosCadastroCidade;
import api.openweather.weatherapp.model.Cidade;
import api.openweather.weatherapp.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import api.openweather.weatherapp.model.dto.DadosAtualizarCidade;
import api.openweather.weatherapp.model.dto.DadosListagemCidade;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository repository;

    public Cidade cadastrar(DadosCadastroCidade dados){
        if(dados.clima() == null) {
            throw new ClimaNotFoundException();
        }

        if(dados.cidade() == null){
            throw new CidadeNotFoundException();
        }
            Clima clima = new Clima(dados.clima());
            Cidade cidade = new Cidade(dados.cidade(), clima);
            return repository.save(cidade);

    }

    public Page<DadosListagemCidade> listar(Pageable pagina) {
        return repository.findAll(pagina).map(DadosListagemCidade::new);
    }

    public Cidade atualizar(@RequestBody @Valid DadosAtualizarCidade atualizacao) {
        Cidade cidade = repository.findById(atualizacao.id())
                .orElseThrow(() -> new IllegalArgumentException("Cidade não encontrada para o ID fornecido"));

        cidade.atualizarInformacoes(atualizacao);
        return repository.save(cidade);


    }
    public void excluir(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Cidade não encontrada para o ID fornecido");
        }
    }
}
