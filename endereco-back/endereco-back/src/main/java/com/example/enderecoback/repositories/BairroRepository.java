package com.example.enderecoback.repositories;

import com.example.enderecoback.models.entities.Bairro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BairroRepository extends JpaRepository<Bairro, Long> {
    Optional<Bairro> findByNome(String bairro);
}
