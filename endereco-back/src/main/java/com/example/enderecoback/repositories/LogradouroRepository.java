package com.example.enderecoback.repositories;

import com.example.enderecoback.models.entities.Logradouro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LogradouroRepository extends JpaRepository<Logradouro, Long> {
    Optional<Logradouro> findByNome(String logradouro);
}
