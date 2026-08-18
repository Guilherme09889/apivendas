package com.example.demo.dto.usuario;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/*
 * Cadastro: todo campo obrigatorio tem @NotBlank/@NotNull, entao os tamanhos exatos
 * usam @Pattern sem a alternativa vazia (diferente do UsuarioPatch, onde vazio
 * significa "nao alterar"). As mensagens sao as mesmas nos dois records.
 */
public record UsuarioPost(
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres") String name,

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "^\\d{11}$", message = "CPF deve ter 11 dígitos") String cpf,

    @NotBlank(message = "CEP é obrigatório")
    @Pattern(regexp = "^\\d{8}$", message = "CEP deve ter 8 dígitos") String cep,

    @NotBlank(message = "Telefone é obrigatório")
    @Size(max = 15, message = "Telefone deve ter no máximo 15 caracteres") String telefone,

    @NotBlank(message = "E-mail é obrigatório")
    @Email(message = "E-mail inválido")
    @Size(max = 50, message = "E-mail deve ter no máximo 50 caracteres") String email,

    @Size(max = 50, message = "Nacionalidade deve ter no máximo 50 caracteres") String nacionalidade,

    @Size(max = 20, message = "Estado civil deve ter no máximo 20 caracteres") String estadoCivil,

    @NotNull(message = "Data de nascimento é obrigatória")
    @Past(message = "Data de nascimento deve ser uma data passada") LocalDate dataNascimento
) {}
