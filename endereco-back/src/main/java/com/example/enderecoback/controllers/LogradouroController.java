package com.example.enderecoback.controllers;


import com.example.enderecoback.models.entities.Logradouro;
import com.example.enderecoback.repositories.LogradouroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("logradouro")
public class LogradouroController {

    private final LogradouroRepository logradouroRepository;

    @PostMapping
    public ResponseEntity<Logradouro> create(@RequestBody Logradouro logradouroform){
        Logradouro logradouro = logradouroRepository.save(logradouroform);
        return ResponseEntity.ok(logradouro);
    }

    @GetMapping
    public ResponseEntity<List<Logradouro>> list(){
        List<Logradouro> logradouros = logradouroRepository.findAll();
        return ResponseEntity.ok(logradouros);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id){
        Optional<Logradouro> logradouroOpt = logradouroRepository.findById(id);

        if(logradouroOpt.isPresent()){
            return ResponseEntity.ok(logradouroOpt.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody Logradouro logradouro, @PathVariable Long id){
        logradouro.setId(id);
        logradouroRepository.saveAndFlush(logradouro);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        logradouroRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
