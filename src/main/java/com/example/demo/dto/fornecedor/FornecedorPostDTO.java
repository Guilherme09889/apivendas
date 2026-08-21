package com.example.demo.dto.fornecedor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Locale;

public record FornecedorPostDTO(
    @NotBlank(message = "Razão social é obrigatória")
    @Size(max = 100, message = "Razão social deve ter no máximo 100 caracteres")
    String razaoSocial,

    @NotBlank(message = "CNPJ é obrigatório")
    @Pattern(regexp = "^\\d{14}$", message = "CNPJ deve ter 14 dígitos")
    String cnpj,

    @NotBlank(message = "Telefone é obrigatório")
    @Size(max = 15, message = "Telefone deve ter no máximo 15 caracteres")
    String telefone,

    @Email(message = "E-mail inválido")
    @Size(min = 1, max = 100, message = "E-mail deve ter entre 1 e 100 caracteres")
    String email,

    @Size(min = 1, max = 100, message = "Site deve ter entre 1 e 100 caracteres")
    @Pattern(regexp = "^https?://.+", message = "Site deve começar com http:// ou https://")
    String site,

    @NotBlank(message = "CEP é obrigatório")
    @Pattern(regexp = "^\\d{8}$", message = "CEP deve ter 8 dígitos")
    String cep,

    @Size(max = 50, message = "País deve ter no máximo 50 caracteres")
    String country
) {

    public FornecedorPostDTO {
        razaoSocial = normalizar(razaoSocial, true);
        cnpj        = normalizar(cnpj, false);
        telefone    = normalizar(telefone, false);
        email       = normalizar(email, true);
        site        = normalizar(site, true);
        cep         = normalizar(cep, false);
        country     = normalizar(country, false);
    }

    private static String normalizar(String valor, boolean minusculo) {
        if (valor == null) {
            return null;
        }
        String normalizado = valor.trim();
        if (normalizado.isEmpty()) {
            return normalizado;
        }
        return minusculo ? normalizado.toLowerCase(Locale.ROOT) : normalizado;
    }

}
