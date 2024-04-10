package com.example.enderecoback.repositories;

import com.example.enderecoback.models.entities.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
