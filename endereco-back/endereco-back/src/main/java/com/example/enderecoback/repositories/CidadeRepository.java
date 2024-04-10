package com.example.enderecoback.repositories;

import com.example.enderecoback.models.entities.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {
    Optional<Cidade> findByNome(String localidade);
}
