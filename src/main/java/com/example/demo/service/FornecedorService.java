package com.example.demo.service;

import com.example.demo.data.entity.FornecedorEntity;
import com.example.demo.data.repository.FornecedorRepository;
import com.example.demo.dto.fornecedor.FornecedorPostDTO;
import com.example.demo.exception.ConflitoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class FornecedorService {

    private final FornecedorRepository fornecedorRepository;

    public FornecedorService(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    @Transactional
    public void postFornecedor(FornecedorPostDTO fornecedorPostDTO){

        String razaoSocial = fornecedorPostDTO.razaoSocial();
        String cnpj = fornecedorPostDTO.cnpj();
        String telefone = fornecedorPostDTO.telefone();
        String cep = fornecedorPostDTO.cep();
        String email = fornecedorPostDTO.email();
        String site = fornecedorPostDTO.site();

        String country = fornecedorPostDTO.country() == null
                ? "País não informado"
                : fornecedorPostDTO.country();

        String duplicados = fornecedorRepository
                .verificarDuplicidadeNative(razaoSocial, cnpj, telefone, email, site);

        List<String> camposDuplicados = List.of(duplicados.split(","));
        List<String> conflitos = new ArrayList<>();

        if(camposDuplicados.contains("razaoSocial")){
            conflitos.add("Razão social já cadastrada");
        }

        if(camposDuplicados.contains("cnpj")){
            conflitos.add("CNPJ já cadastrado");
        }

        if(camposDuplicados.contains("telefone")){
            conflitos.add("Telefone já cadastrado");
        }

        if(camposDuplicados.contains("email")){
            conflitos.add("E-mail já cadastrado");
        }

        if(camposDuplicados.contains("site")){
            conflitos.add("Site já cadastrado");
        }

        if(!conflitos.isEmpty()){
            throw new ConflitoException(String.join("; ", conflitos));
        }

        FornecedorEntity x = new FornecedorEntity();
        x.setRazaoSocial(razaoSocial);
        x.setCnpj(cnpj);
        x.setTelefone(telefone);
        x.setEmail(email);
        x.setSite(site);
        x.setCep(cep);
        x.setCountry(country);
        x.setAtivo(true);
        x.setDataCadastro(LocalDate.now());
        fornecedorRepository.save(x);
    }

}
