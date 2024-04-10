package com.example.enderecoback.repositories;

import com.example.enderecoback.models.entities.TipoLogradouro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipoLogradouroRepository extends JpaRepository<TipoLogradouro, Long> {
    Optional<TipoLogradouro> findByNome(String logradouro);
}
