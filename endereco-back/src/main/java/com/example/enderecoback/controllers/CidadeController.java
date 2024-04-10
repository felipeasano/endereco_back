package com.example.enderecoback.controllers;

import com.example.enderecoback.models.entities.Cidade;
import com.example.enderecoback.repositories.CidadeRepository;
import com.example.enderecoback.repositories.UnidadeFederacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("cidade")
public class CidadeController {

    private final CidadeRepository cidadeRepository;
    private final UnidadeFederacaoRepository unidadeFederacaoRepository;

    @PostMapping
    public ResponseEntity<Cidade> create(@RequestBody Cidade cidadeform){
        Cidade cidade = cidadeRepository.save(cidadeform);
        return ResponseEntity.ok(cidade);
    }

    @GetMapping
    public ResponseEntity<List<Cidade>> list(){
        List<Cidade> cidades = cidadeRepository.findAll();
        return ResponseEntity.ok(cidades);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id){

        Optional<Cidade> cidadeOpt = cidadeRepository.findById(id);

        if(cidadeOpt.isPresent()){
            return ResponseEntity.ok(cidadeOpt.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody Cidade cidade, @PathVariable Long id){
        cidade.setId(id);
        cidadeRepository.saveAndFlush(cidade);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        cidadeRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
