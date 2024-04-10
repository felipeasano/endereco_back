package com.example.enderecoback.controllers;

import com.example.enderecoback.models.entities.TipoLogradouro;
import com.example.enderecoback.repositories.TipoLogradouroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("tipologradouro")
public class TipoLogradouroController {

    private final TipoLogradouroRepository tipoLogradouroRepository;

    @PostMapping
    public ResponseEntity<TipoLogradouro> create(@RequestBody TipoLogradouro tipoLogradouroform){
        TipoLogradouro tipoLogradouro = tipoLogradouroRepository.save(tipoLogradouroform);
        return ResponseEntity.ok(tipoLogradouro);
    }

    @GetMapping
    public ResponseEntity<List<TipoLogradouro>> list(){
        List<TipoLogradouro> tipoLogradouros = tipoLogradouroRepository.findAll();
        return ResponseEntity.ok(tipoLogradouros);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id){
        Optional<TipoLogradouro> tipoLogradouroOpt = tipoLogradouroRepository.findById(id);

        if(tipoLogradouroOpt.isPresent()){
            return ResponseEntity.ok(tipoLogradouroOpt.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody TipoLogradouro tipoLogradouro, @PathVariable Long id){
        tipoLogradouro.setId(id);
        tipoLogradouroRepository.saveAndFlush(tipoLogradouro);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        tipoLogradouroRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
