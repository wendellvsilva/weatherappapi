package api.openweather.weatherapp.service;

import api.openweather.weatherapp.model.dto.DadosCadastroCidade;
import api.openweather.weatherapp.model.Cidade;
import api.openweather.weatherapp.repository.CidadeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import api.openweather.weatherapp.model.dto.AtualizarCidadeDTO;
import api.openweather.weatherapp.model.dto.DadosCadastroCidade;
import api.openweather.weatherapp.model.dto.DadosListagemCidade;
import api.openweather.weatherapp.model.Cidade;
import api.openweather.weatherapp.repository.CidadeRepository;
import api.openweather.weatherapp.service.CidadeService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository repository;

    @Transactional
    public Cidade cadastrar(@RequestBody DadosCadastroCidade dados){
        if(dados.cidade() == null) {
            throw new IllegalArgumentException("Nome da cidade deve ser informado");
        }
        return repository.save(new Cidade(dados));

    }

    @Transactional
    public Page<DadosListagemCidade> listar(Pageable pagina) {
        return repository.findAll(pagina).map(DadosListagemCidade::new);
    }

    @Transactional
    public ResponseEntity<String> atualizar(@RequestBody @Valid AtualizarCidadeDTO atualizacao) {
        Optional<Cidade> optionalCidade = repository.findById(atualizacao.id());
        if (optionalCidade.isPresent()) {
            Cidade cidade = optionalCidade.get();
            cidade.atualizarInformacoes(atualizacao);
            return ResponseEntity.ok().body("Cidade atualizada com sucesso");
        } else {
            throw new IllegalArgumentException("Cidade não encontrada para o ID fornecido");
        }
    }

    @Transactional
    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
