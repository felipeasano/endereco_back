package com.example.enderecoback.controllers;

import com.example.enderecoback.models.entities.UnidadeFederacao;
import com.example.enderecoback.repositories.UnidadeFederacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("uf")
public class UnidadeFederacaoController {

    private final UnidadeFederacaoRepository unidadeFederacaoRepository;

    @PostMapping
    public ResponseEntity<UnidadeFederacao> create(@RequestBody UnidadeFederacao unidadeFederacaoform){
        UnidadeFederacao uf = unidadeFederacaoRepository.save(unidadeFederacaoform);
        return ResponseEntity.ok(uf);
    }

    @GetMapping
    public ResponseEntity<List<UnidadeFederacao>> list(){
        List<UnidadeFederacao> ufs = unidadeFederacaoRepository.findAll();
        return ResponseEntity.ok(ufs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id){
        Optional<UnidadeFederacao> ufOpt = unidadeFederacaoRepository.findById(id);

        if(ufOpt.isPresent()){
            return ResponseEntity.ok(ufOpt.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    ResponseEntity<Void> update(@RequestBody UnidadeFederacao uf, @PathVariable Long id){
        uf.setId(id);
        unidadeFederacaoRepository.saveAndFlush(uf);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        unidadeFederacaoRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
