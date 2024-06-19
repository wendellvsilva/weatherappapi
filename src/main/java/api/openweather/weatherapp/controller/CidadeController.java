package api.openweather.weatherapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import api.openweather.weatherapp.model.Cidade;
import api.openweather.weatherapp.model.dto.DadosAtualizarCidade;
import api.openweather.weatherapp.model.dto.DadosCadastroCidade;
import api.openweather.weatherapp.model.dto.DadosCadastroClima;
import api.openweather.weatherapp.model.dto.DadosListagemCidade;
import api.openweather.weatherapp.service.CidadeService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/cidades")
@CrossOrigin(origins = "http://localhost:3000")
public class CidadeController {

    @Autowired
    private CidadeService cidadeService;

    @PostMapping
    public ResponseEntity<DadosCadastroCidade> cadastrar(@RequestBody @Valid DadosCadastroCidade dados) {
        Cidade cidade = cidadeService.cadastrar(dados);
        DadosCadastroCidade response = new DadosCadastroCidade(
                cidade.getId(),
                cidade.getCidade(),
                new DadosCadastroClima(
                        cidade.getClima().getSituacaoClima(),
                        cidade.getClima().getTurno(),
                        cidade.getClima().getData().toString(),
                        cidade.getClima().getUmidade(),
                        cidade.getClima().getPrecipitacao(),
                        cidade.getClima().getTemperatura(),
                        cidade.getClima().getVelVento(),
                        cidade.getClima().getTempMaxima(),
                        cidade.getClima().getTempMinima()
                )
        );
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemCidade>> listar(
            @PageableDefault(size = 10, sort = { "cidade" }) Pageable pagina) {
        return ResponseEntity.ok().body(cidadeService.listar(pagina));

    }
    @GetMapping("/buscar")
    public ResponseEntity<Page<DadosListagemCidade>> buscarPorNome(
            @RequestParam(name = "nome") String nome,
            @PageableDefault(size = 10, sort = { "cidade" }) Pageable pageable) {
        Page<DadosListagemCidade> cidades = cidadeService.listarPorNome(nome, pageable);
        return ResponseEntity.ok().body(cidades);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizar(@PathVariable Long id,
            @RequestBody @Valid DadosAtualizarCidade atualizacao) {
        cidadeService.atualizar(id, atualizacao);
        return ResponseEntity.ok().body("Cidade atualizada com sucesso");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> excluir(@PathVariable Long id) {
        cidadeService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}