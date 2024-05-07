package api.openweather.weatherapp.controller;

import api.openweather.weatherapp.model.dto.AtualizarCidadeDTO;
import api.openweather.weatherapp.model.dto.DadosCadastroCidade;
import api.openweather.weatherapp.model.dto.DadosListagemCidade;
import api.openweather.weatherapp.model.Cidade;

import api.openweather.weatherapp.service.CidadeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("cidades")
public class CidadeController {



    @Autowired
    private CidadeService cidadeService;

    @PostMapping
    public ResponseEntity<Cidade> cadastrar(@RequestBody DadosCadastroCidade dados){
      Cidade cidade = cidadeService.cadastrar(dados);
         return ResponseEntity.created(null).body(cidade);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemCidade>> listar(@PageableDefault(size = 10, sort = {"cidade"}) Pageable pagina){
        return ResponseEntity.ok().body(cidadeService.listar(pagina));

}

    @PutMapping
    public ResponseEntity<String> atualizar(@RequestBody @Valid AtualizarCidadeDTO atualizacao){
        cidadeService.atualizar(atualizacao);
        return ResponseEntity.ok().body("Cidade atualizada com sucesso");
    }

    @DeleteMapping("/{id}")// /{id} serve pra ser um parametro dinamico
    public ResponseEntity<Long> excluir(@PathVariable Long id) {
        cidadeService.excluir(id);
        return ResponseEntity.ok().body(id);
    }
}


