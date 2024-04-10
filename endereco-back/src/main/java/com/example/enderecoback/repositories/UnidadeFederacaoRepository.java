package com.example.enderecoback.repositories;

import com.example.enderecoback.models.entities.UnidadeFederacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UnidadeFederacaoRepository extends JpaRepository<UnidadeFederacao, Long> {
    Optional<UnidadeFederacao> findByNome(String uf);
}
