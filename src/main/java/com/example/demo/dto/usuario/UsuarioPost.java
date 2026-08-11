package com.example.demo.dto.usuario;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioPost(
    @NotBlank @Size(max = 100) String name,
    @NotBlank @Size(min = 11, max = 11) String cpf,
    @NotBlank @Size(min = 8, max = 8) String cep,
    @NotBlank @Size(max = 15) String telefone,
    @NotBlank @Email @Size(max = 50) String email,
    @Size(max = 50) String nacionalidade,
    @Size(max = 20) String estadoCivil,
    @NotNull LocalDate dataNascimento
) {}
