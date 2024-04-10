package com.example.enderecoback.controllers;

import com.example.enderecoback.models.DTO.CEPDTO;
import com.example.enderecoback.models.entities.*;
import com.example.enderecoback.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
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

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ViaCepEndereco> response = restTemplate.getForEntity("http://viacep.com.br/ws/" + cepdto.getCep() + "/json", ViaCepEndereco.class);

        ViaCepEndereco body = response.getBody();

        Optional<Bairro> bairroOpt = bairroRepository.findByNome(body.getBairro());
        if(!bairroOpt.isPresent()){
            Bairro bairro = new Bairro();
            bairro.setNome(body.getBairro());
            bairroRepository.save(bairro);
        }

        //Optional<TipoLogradouro> tipoLogradouroOpt = tipoLogradouroRepository.findByNome(body.getLogradouro());

        Optional<Logradouro> logradouroOpt = logradouroRepository.findByNome(body.getLogradouro());
        if(!logradouroOpt.isPresent()){
            Logradouro logradouro = new Logradouro();
            logradouro.setNome(body.getLogradouro());
            logradouroRepository.save(logradouro);
        }

        Optional<UnidadeFederacao> ufOpt = unidadeFederacaoRepository.findByNome(body.getUf());
        if(!ufOpt.isPresent()){
            UnidadeFederacao uf = new UnidadeFederacao();
            uf.setNome(body.getUf());
            unidadeFederacaoRepository.save(uf);
        }

        Optional<Cidade> cidadeOpt = cidadeRepository.findByNome(body.getLocalidade());
        if(!cidadeOpt.isPresent()){
            Cidade cidade = new Cidade();
            cidade.setNome(body.getLocalidade());
            cidade.setUnidadeFederacao(ufOpt.get());
            cidadeRepository.save(cidade);
        }

        Endereco endereco = new Endereco();
        endereco.setCep(cepdto.getCep());

        Optional<Endereco> enderecoOpt = enderecoRepository.findByCep(endereco.getCep());
        if(!enderecoOpt.isPresent()){
            endereco.setBairro(bairroOpt.get());
            endereco.setLogradouro(logradouroOpt.get());
            endereco.setCidade(cidadeOpt.get());
            enderecoRepository.save(endereco);

            return ResponseEntity.ok(endereco);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @GetMapping
    private ResponseEntity<List<Endereco>> list(){
        List<Endereco> enderecos = enderecoRepository.findAll();
        return ResponseEntity.ok(enderecos);
    }

    /*@GetMapping("/{id}")
    private ResponseEntity<?> get(@PathVariable Long id){
        Optional<Endereco> enderecoOpt = enderecoRepository.findById(id);

        if(enderecoOpt.isPresent()){
            return ResponseEntity.ok(enderecoOpt.get());
        }

        return ResponseEntity.notFound().build();
    }*/

    @GetMapping("/{cep}")
    private ResponseEntity<?> get(@PathVariable String cep){
        Optional<Endereco> enderecoOpt = enderecoRepository.findByCep(cep);

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
