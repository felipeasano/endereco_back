package com.example.enderecoback.models.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Logradouro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    //@ManyToOne
    //@JoinColumn(name = "fk_tipoLogradouro")
    //private TipoLogradouro tipoLogradouro;
}
