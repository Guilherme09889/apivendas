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

    private static final String NAO_INFORMADO = "Não informado";

    private final UsuarioRepository userRepo;

    public UsuarioService(UsuarioRepository userRepo) {
        this.userRepo = userRepo;
    }

    // null ou string em branco viram null; caso contrario devolve o valor sem espacos nas pontas
    private String normalizar(String valor) {
        return (valor == null || valor.isBlank()) ? null : valor.trim();
    }

    // mesma coisa, mas cai no padrao em vez de null
    private String normalizar(String valor, String padrao) {
        String normalizado = normalizar(valor);
        return normalizado == null ? padrao : normalizado;
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
        newUser.setNacionalidade(normalizar(userPost.nacionalidade(), NAO_INFORMADO));
        newUser.setEstadoCivil(normalizar(userPost.estadoCivil(), NAO_INFORMADO));
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

        String nome = normalizar(userPatch.name());
        String cpf = normalizar(userPatch.cpf());
        String cep = normalizar(userPatch.cep());
        String telefone = normalizar(userPatch.telefone());
        String email = normalizar(userPatch.email());
        String nacionalidade = normalizar(userPatch.nacionalidade());
        String estadoCivil = normalizar(userPatch.estadoCivil());

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

    @Transactional
    public void deletarUsuario(Long id){

        int linhasAfetadas = userRepo.desativarUsuarioNativo(id);
        if (linhasAfetadas == 0) {
            throw new RuntimeException("Usuario não encontrado");
        }
    }

    @Transactional
    public void recuperarContaDesativada(Long id){
        boolean usuarioExiste = userRepo.existsByIdNegativoNative(id);
        if(usuarioExiste == false){
            throw new RuntimeException("Usuario não encontrado");
        }
        userRepo.ativarUsuarioNativo(id);
    }

    @Transactional(readOnly = true)
    public UsuarioGet fifByIdNative(Long id){
        UsuarioGet user = userRepo.findaByIdNative(id)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
        return user;
    }

}
