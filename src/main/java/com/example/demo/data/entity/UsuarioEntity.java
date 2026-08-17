package com.example.demo.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name  = "usuario")
@Getter
@Setter
public class UsuarioEntity {
        
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "cpf", nullable = false, length = 11, unique = true)
    private String cpf;

    @Column(name = "cep", nullable = false, length = 8)
    private String cep;

    @Column(name = "telefone", nullable = false, length = 15)
    private String telefone;

    @Column(name = "email", nullable = false, length = 50, unique = true)
    private String email;

    @Column(name = "nacionalidade", length = 50, nullable = false)
    private String nacionalidade = "Não informado"; //default no objeto java

    @Column(name = "estado_civil", length = 20, nullable = false)
    private String estadoCivil = "Não informado";

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "ativo", nullable = false)
    @ColumnDefault("true")
    private Boolean ativo = true;

    public UsuarioEntity(){}

    public UsuarioEntity(
        String name,
        String cpf,
        String cep,
        String telefone,
        String email,
        String nacionalidade,
        String estadoCivil,
        LocalDate dataNascimento
    ){
        this.name = name;
        this.cpf = cpf;
        this.cep = cep;
        this.telefone = telefone;
        this.email = email;
        this.nacionalidade = nacionalidade;
        this.estadoCivil = estadoCivil;
        this.dataNascimento = dataNascimento;
    }

}
