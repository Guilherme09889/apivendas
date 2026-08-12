package com.example.demo.service;

import com.example.demo.dto.usuario.UsuarioGet;
import com.example.demo.dto.usuario.UsuarioPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.data.entity.UsuarioEntity;
import com.example.demo.data.repository.UsuarioRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    
    private final UsuarioRepository userRepo;

    public UsuarioService(UsuarioRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Transactional
    public void criarUsuario(UsuarioPost userPost){

        Boolean cpfExiste = userRepo.existsByCpfNative(userPost.cpf());
        if(cpfExiste == true){
            throw new RuntimeException("CPF já existe");
        }

        Boolean emailExiste = userRepo.existsByEmailNative(userPost.email());
        if(emailExiste == true){
            throw new RuntimeException("Email já existe");
        }

        Boolean telefoneExiste = userRepo.existsByTelNative(userPost.telefone());
        if(telefoneExiste == true){
            throw new RuntimeException("Telefone ja cadastrado");
        }

        UsuarioEntity newUser = new UsuarioEntity();
        newUser.setName(userPost.name());
        newUser.setCpf(userPost.cpf());
        newUser.setCep(userPost.cep());
        newUser.setTelefone(userPost.telefone());
        newUser.setEmail(userPost.email());
        newUser.setNacionalidade(userPost.nacionalidade());
        newUser.setEstadoCivil(userPost.estadoCivil());
        newUser.setDataNascimento(userPost.dataNascimento());
        userRepo.save(newUser);
    }

    @Transactional(readOnly = true)
    public Page<UsuarioGet> findAllNative(Pageable pageable){
        Page<UsuarioGet> users = userRepo.findAllNative(pageable);
        if(users.isEmpty()){
            throw new RuntimeException("Nenhum usuario encontrado");
        }
        return users;

    }



}
