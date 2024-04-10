package com.example.enderecoback.controllers;

import com.example.enderecoback.models.DTO.CEPDTO;
import com.example.enderecoback.models.entities.*;
import com.example.enderecoback.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("endereco")
public class EnderecoController {

    private final EnderecoRepository enderecoRepository;

    private final BairroRepository bairroRepository;
    private final TipoLogradouroRepository tipoLogradouroRepository;
    private final LogradouroRepository logradouroRepository;
    private final UnidadeFederacaoRepository unidadeFederacaoRepository;
    private final CidadeRepository cidadeRepository;

    @PostMapping
    private ResponseEntity<Endereco> create(@RequestBody CEPDTO cepdto){

        Bairro bairro = new Bairro();
        Logradouro logradouro = new Logradouro();
        UnidadeFederacao uf = new UnidadeFederacao();
        Cidade cidade = new Cidade();


        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ViaCepEndereco> response = restTemplate.getForEntity("http://viacep.com.br/ws/" + cepdto.getCep() + "/json", ViaCepEndereco.class);

        ViaCepEndereco body = response.getBody();

        Optional<Bairro> bairroOpt = bairroRepository.findByNome(body.getBairro());
        if(bairroOpt.isPresent()){
            bairro.setNome(body.getBairro());
            bairroRepository.save(bairro);
        }

        //Optional<TipoLogradouro> tipoLogradouroOpt = tipoLogradouroRepository.findByNome(body.getLogradouro());

        Optional<Logradouro> logradouroOpt = logradouroRepository.findByNome(body.getLogradouro());
        if(logradouroOpt.isPresent()){
            logradouro.setNome(body.getLogradouro());
            logradouroRepository.save(logradouro);
        }

        Optional<UnidadeFederacao> ufOpt = unidadeFederacaoRepository.findByNome(body.getUf());
        if(ufOpt.isPresent()){
            uf.setNome(body.getUf());
            unidadeFederacaoRepository.save(uf);
        }

        Optional<Cidade> cidadeOpt = cidadeRepository.findByNome(body.getLocalidade());
        if(cidadeOpt.isPresent()){
            cidade.setNome(body.getLocalidade());
            cidade.setUnidadeFederacao(ufOpt.get());
            cidadeRepository.save(cidade);
        }

        Endereco endereco = new Endereco();
        endereco.setCep(cepdto.getCep());
        endereco.setBairro(bairroOpt.get());
        endereco.setLogradouro(logradouroOpt.get());
        endereco.setCidade(cidadeOpt.get());
        enderecoRepository.save(endereco);

        return ResponseEntity.ok(endereco);
    }

    @GetMapping
    private ResponseEntity<List<Endereco>> list(){
        List<Endereco> enderecos = enderecoRepository.findAll();
        return ResponseEntity.ok(enderecos);
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> get(@PathVariable Long id){
        Optional<Endereco> enderecoOpt = enderecoRepository.findById(id);

        if(enderecoOpt.isPresent()){
            return ResponseEntity.ok(enderecoOpt.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    private ResponseEntity<Void> update(@RequestBody Endereco endereco, @PathVariable Long id){
        endereco.setId(id);
        enderecoRepository.saveAndFlush(endereco);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> delete(@PathVariable Long id){
        enderecoRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
