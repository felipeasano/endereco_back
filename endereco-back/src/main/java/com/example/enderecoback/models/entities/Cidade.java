package com.example.enderecoback.models.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Cidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ManyToOne
    @JoinColumn(name = "fk_unidadeFederacao")
    private UnidadeFederacao unidadeFederacao;
}
