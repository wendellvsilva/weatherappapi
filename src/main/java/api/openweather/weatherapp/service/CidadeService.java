package api.openweather.weatherapp.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import api.openweather.weatherapp.exceptions.CidadeNotFoundException;
import api.openweather.weatherapp.exceptions.ClimaNotFoundException;
import api.openweather.weatherapp.model.Cidade;
import api.openweather.weatherapp.model.Clima;
import api.openweather.weatherapp.model.dto.DadosAtualizarCidade;
import api.openweather.weatherapp.model.dto.DadosCadastroCidade;
import api.openweather.weatherapp.model.dto.DadosListagemCidade;
import api.openweather.weatherapp.repository.CidadeRepository;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository repository;

    public Cidade cadastrar(DadosCadastroCidade dados) {
        verificaSeClimaestaNulo(dados);
        verificaSeCidadeestaNula(dados);
        Clima clima = new Clima(dados.clima());
        Cidade cidade = new Cidade(dados.cidade(), clima);
        return repository.save(cidade);
    }

    private static void verificaSeCidadeestaNula(DadosCadastroCidade dados) {
        if (dados.cidade() == null) {
            throw new CidadeNotFoundException("Cidade não pode ser nula");
        }
    }

    private static void verificaSeClimaestaNulo(DadosCadastroCidade dados) {
        if (dados.clima() == null) {
            throw new ClimaNotFoundException("Clima não pode ser nulo");
        }
    }
    public Page<DadosListagemCidade> listarPorNome(String nome, Pageable pageable) {
        Page<Cidade> cidades = repository.findByCidade(nome, pageable);
        return cidades.map(DadosListagemCidade::new);
    }

    public Page<DadosListagemCidade> listar(Pageable pagina) {
        return repository.findAll(pagina).map(DadosListagemCidade::new);
    }

    public Cidade atualizar(Long id, DadosAtualizarCidade atualizacao) {
        Cidade cidade = repository.findById(id)
                .orElseThrow(() -> new CidadeNotFoundException("Cidade não encontrada para o ID fornecido"));

        cidade.atualizarInformacoes(atualizacao);

        return repository.save(cidade);
    }

    public void excluir(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new CidadeNotFoundException("Cidade não encontrada para o ID fornecido");
        }
    }
}