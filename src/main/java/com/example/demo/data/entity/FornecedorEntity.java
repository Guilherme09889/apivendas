package com.example.demo.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Table(name = "fornecedor")
@Getter
@Setter
public class FornecedorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "razao_social", nullable = false, length = 100, unique = true)
    private String razaoSocial;

    @Column(name = "cnpj", nullable = false, length = 14, unique = true)
    private String cnpj;

    @Column(name = "telefone", nullable = false, length = 15, unique = true)
    private String telefone;

    @Column(name = "email", nullable = true, length = 100, unique = true)
    private String email;

    @Column(name = "site", length = 100, unique = true)
    private String site;

    @Column(name = "cep", length = 8, nullable = false)
    private String cep;

    @Column(name = "ativo", nullable = false)
    private boolean ativo;

    @Column(name = "country", nullable = false, length = 50)
    private String country;

    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;

    public FornecedorEntity() {}

    public FornecedorEntity(String razaoSocial,
                            String cnpj,
                            String telefone,
                            String email,
                            String site,
                            String cep,
                            boolean ativo,
                            String country,
                            LocalDate dataCadastro) {
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.telefone = telefone;
        this.email = email;
        this.site = site;
        this.cep = cep;
        this.ativo = ativo;
        this.country = country;
        this.dataCadastro = dataCadastro;
    }
}
