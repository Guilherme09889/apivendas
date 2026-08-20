package com.example.demo.service;

import com.example.demo.data.entity.FornecedorEntity;
import com.example.demo.data.repository.FornecedorRepository;
import com.example.demo.dto.fornecedor.FornecedorPostDTO;
import com.example.demo.exception.ConflitoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class FornecedorService {

    private final FornecedorRepository fornecedorRepository;

    public FornecedorService(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    @Transactional
    public void postFornecedor(FornecedorPostDTO fornecedorPostDTO){

        String razaoSocial = fornecedorPostDTO.razaoSocial().trim();
        String cnpj = fornecedorPostDTO.cnpj().trim();
        String telefone = fornecedorPostDTO.telefone().trim();
        String cep = fornecedorPostDTO.cep().trim();

        String country = fornecedorPostDTO.country() == null ?
                "País não informado" :
                fornecedorPostDTO.country()
                .trim();

        String email = fornecedorPostDTO.email() == null
                ? null : fornecedorPostDTO.email().trim().toLowerCase();

        String site = fornecedorPostDTO.site() == null
                ? null : fornecedorPostDTO.site().trim();

        if(fornecedorRepository.existsByRazaoSocialNative(razaoSocial)){
            throw new ConflitoException("Razão social já cadastrada");
        }

        if(fornecedorRepository.existsByCnpjNative(cnpj)){
            throw new ConflitoException("CNPJ já cadastrado");
        }

        if(fornecedorRepository.existsByTelNative(telefone)){
            throw new ConflitoException("Telefone já cadastrado");
        }

        if(email != null && fornecedorRepository.existsByEmailNative(email)){
            throw new ConflitoException("E-mail já cadastrado");
        }

        if(site != null && fornecedorRepository.existsBySiteNative(site)){
            throw new ConflitoException("Site já cadastrado");
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
    }

}
