package com.example.demo.dto.usuario;

import java.time.LocalDate;

public record UsuarioGet(
    String name,
    String cpf,
    String cep,
    String telefone,
    String email,
    String nacionalidade,
    String estadoCivil,
    LocalDate dataNascimento
) {}
