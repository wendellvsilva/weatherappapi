package api.openweather.weatherapp.service;

import api.openweather.weatherapp.model.dto.DadosCadastroCidade;
import api.openweather.weatherapp.model.Cidade;
import api.openweather.weatherapp.repository.CidadeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import api.openweather.weatherapp.model.dto.AtualizarCidadeDTO;
import api.openweather.weatherapp.model.dto.DadosListagemCidade;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;


import java.util.Optional;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository repository;

    public Cidade cadastrar(@RequestBody @Valid DadosCadastroCidade dados){
        return repository.save(new Cidade(dados));

    }

    @Transactional
    public Page<DadosListagemCidade> listar(Pageable pagina) {
        return repository.findAll(pagina).map(DadosListagemCidade::new);
    }

    public ResponseEntity<String> atualizar(@RequestBody @Valid AtualizarCidadeDTO atualizacao) {
        Cidade cidade = repository.findById(atualizacao.id())
                .orElseThrow(() -> new IllegalArgumentException("Cidade não encontrada para o ID fornecido"));

        cidade.atualizarInformacoes(atualizacao);
        repository.save(cidade);

        return ResponseEntity.ok().body("Cidade atualizada com sucesso");
    }
    @Transactional
    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
