package com.example.demo.dto.usuario;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/*
 * Atualizacao parcial: campo ausente, null ou em branco significa "nao alterar"
 * (o UsuarioService.normalizar cuida disso). Por isso nada de @NotBlank aqui,
 * e os tamanhos exatos usam @Pattern com alternativa vazia em vez de @Size(min = ...).
 * As mensagens sao as mesmas do UsuarioPost.
 */
public record UsuarioPatch(
        @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres") String name,

        @Pattern(regexp = "^$|^\\d{11}$", message = "CPF deve ter 11 dígitos") String cpf,

        @Pattern(regexp = "^$|^\\d{8}$", message = "CEP deve ter 8 dígitos") String cep,

        @Size(max = 15, message = "Telefone deve ter no máximo 15 caracteres") String telefone,

        @Email(message = "E-mail inválido")
        @Size(max = 50, message = "E-mail deve ter no máximo 50 caracteres") String email,

        @Size(max = 50, message = "Nacionalidade deve ter no máximo 50 caracteres") String nacionalidade,

        @Size(max = 20, message = "Estado civil deve ter no máximo 20 caracteres") String estadoCivil,

        @Past(message = "Data de nascimento deve ser uma data passada") LocalDate dataNascimento
){}
