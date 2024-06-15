package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@SecurityRequirement(name = "bearer-key")
@RestController
@RequestMapping("medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder builder) {
        var medico = new Medico(dados);
        repository.save(medico);
        var uri = builder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        var dto = new DadosDetalhamentoMedico(medico);
        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping
    // era uma List de dadosListagemMedico mas sem argumentos, para a paginação deve ser uma Page com Pageable como argumento que também entra no findAll
    public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault(size=10, sort={"nome"}) Pageable paginacao) {
        // como é um Page (não mais uma List), não é necessário o stream() e nem o ToList()
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
//        repository.deleteById(id); // assim exclui totalmente, deleta no db, não é o caso pois queremos usar uma
        // exclusão lógica
    var medico = repository.getReferenceById(id);
    medico.excluir();
    return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
            var medico = repository.getReferenceById(id);
            return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));

    }
}
