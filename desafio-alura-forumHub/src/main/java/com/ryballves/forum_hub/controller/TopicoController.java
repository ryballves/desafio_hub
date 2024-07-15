package com.ryballves.forum_hub.controller;

import com.ryballves.forum_hub.domain.model.Topico;
import com.ryballves.forum_hub.domain.dto.DadosAtualizacaoTopico;
import com.ryballves.forum_hub.domain.dto.DadosDetalhamentoTopico;
import com.ryballves.forum_hub.domain.dto.DadosListaTopico;
import com.ryballves.forum_hub.domain.dto.DadosTopico;
import com.ryballves.forum_hub.domain.repository.TopicoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository repository;

    @Transactional
    @PostMapping
    public ResponseEntity cadastrartopico(@RequestBody @Valid DadosTopico dados, UriComponentsBuilder uriBuilder ) {
       
        Optional<Topico> topicoExistente = repository.findByTituloAndNomeAutor(dados.titulo(), dados.nome_autor());
        
        if (topicoExistente.isPresent()) {
            return new ResponseEntity<> (new DadosDetalhamentoTopico(topicoExistente.get(), "Tópico já existe com o mesmo título e autor"), HttpStatus.BAD_REQUEST);
        }
        var topico = new Topico( dados);

        repository.save(topico);

        
        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        
        return ResponseEntity.created(uri).body(new DadosDetalhamentoTopico(topico, "Topico criado com sucesso!!"));
    }

    @GetMapping
    public Page<DadosListaTopico> listarTopicos(
            @PageableDefault(size = 10, sort = {"dataDeCriacao"}) Pageable paginacao) {

        return repository.findAll(paginacao).map(DadosListaTopico::new);
    }


    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoTopico> detalharTopico(@PathVariable Long id) {
        Optional<Topico> topicoOptional = repository.findById(id);
        if (topicoOptional.isPresent()) {
            var topico = topicoOptional.get();
            return ResponseEntity.ok(new DadosDetalhamentoTopico(topico, "Detalhes do tópico"));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizar(@PathVariable Long id, @RequestBody @Valid DadosAtualizacaoTopico dados) {
        Optional<Topico> topicoOptional = repository.findById(id);
        if (topicoOptional.isPresent()) {
            Topico topico = topicoOptional.get();
            topico.atualizarInformacoes(dados); 
            repository.save(topico);
            return new ResponseEntity<>("Tópico atualizado com sucesso", HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Transactional
    @DeleteMapping("/{id}") 
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        Optional<Topico> topicoOptional = repository.findById(id);
        if (topicoOptional.isPresent()) {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

