package com.example.demo.service;

import com.example.demo.data.entity.UsuarioEntity;
import com.example.demo.data.repository.UsuarioRepository;
import com.example.demo.dto.usuario.UsuarioGet;
import com.example.demo.dto.usuario.UsuarioPatch;
import com.example.demo.dto.usuario.UsuarioPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {


    private final UsuarioRepository userRepo;

    public UsuarioService(UsuarioRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Transactional
    public void criarUsuario(UsuarioPost userPost) {

        Boolean cpfExiste = userRepo.existsByCpfNative(userPost.cpf());
        if (cpfExiste == true) {
            throw new RuntimeException("CPF já existe");
        }

        Boolean emailExiste = userRepo.existsByEmailNative(userPost.email());
        if (emailExiste == true) {
            throw new RuntimeException("Email já existe");
        }

        Boolean telefoneExiste = userRepo.existsByTelNative(userPost.telefone());
        if (telefoneExiste == true) {
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
    public Page<UsuarioGet> findAllNative(Pageable pageable) {
        Page<UsuarioGet> users = userRepo.findAllNative(pageable);
        if (users.isEmpty()) {
            throw new RuntimeException("Nenhum usuario encontrado");
        }
        return users;
    }

    @Transactional
    public void atualizarUsuario(Long id, UsuarioPatch userPatch) {

        UsuarioGet user = userRepo.findaByIdNativeUpdate(id)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));

        String nome = userPatch.name() == null ? null : userPatch.name().trim();
        String cpf = userPatch.cpf() == null ? null : userPatch.cpf().trim();
        String cep = userPatch.cep() == null ? null : userPatch.cep().trim();
        String telefone = userPatch.telefone() == null ? null : userPatch.telefone().trim();
        String email = userPatch.email() == null ? null : userPatch.email().trim();
        String nacionalidade = userPatch.nacionalidade() == null ? null : userPatch.nacionalidade().trim();
        String estadoCivil = userPatch.estadoCivil() == null ? null : userPatch.estadoCivil().trim();

        if(nome != null){
            Boolean mesmoNome = nome.equals(user.name());
            if (mesmoNome == true){
                throw new RuntimeException("Nome igual ao atual");
            }
        }

        if(cpf != null){
            Boolean cpfExiste = userRepo.existsByCpfNative(cpf);
            Boolean cpfMudou = cpf.equals(user.cpf());
            if (cpfExiste == true && cpfMudou == false) {
                throw new RuntimeException("CPF já existe");
            }
        }

        if (cep != null){
            Boolean mesmoCep = cep.equals(user.cep());
            if (mesmoCep == true){
                throw new RuntimeException("CEP igual ao atual");
            }
        }

        if (telefone != null){
            Boolean telefoneExiste = userRepo.existsByTelNative(telefone);
            Boolean telefoneMudou = telefone.equals(user.telefone());
            if (telefoneExiste == true && telefoneMudou == false) {
                throw new RuntimeException("Telefone já existe");
            }
        }

        if (email != null){
            Boolean emailExiste = userRepo.existsByEmailNative(email);
            Boolean emailMudou = email.equals(user.email());
            if (emailExiste == true && emailMudou == false) {
                throw new RuntimeException("Email já existe");
            }
        }

        nome = nome == null ? user.name() : nome;
        cpf = cpf == null ? user.cpf() : cpf;
        cep = cep == null ? user.cep() : cep;
        telefone = telefone == null ? user.telefone() : telefone;
        email = email == null ? user.email() : email;
        nacionalidade = nacionalidade == null ? user.nacionalidade() : nacionalidade;
        estadoCivil = estadoCivil == null ? user.estadoCivil() : estadoCivil;

        userRepo.updateUsuarioNative(id, nome, cpf, cep, telefone, email, nacionalidade, estadoCivil);

    }


}
