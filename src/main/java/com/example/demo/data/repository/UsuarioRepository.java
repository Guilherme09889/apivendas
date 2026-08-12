package com.example.demo.data.repository;

import com.example.demo.dto.usuario.UsuarioGet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.data.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    @NativeQuery(value = """

            select exists(
        select 1 from usuario
        where cpf = :cpf)
        """)
    boolean existsByCpfNative(@Param("cpf") String cpf);

    @NativeQuery(value = """
        select exists(
        select 1 from usuario
        where email = :email)
        """)
    boolean existsByEmailNative(@Param("email") String email);

    @NativeQuery(value = """
        select exists(
        select 1 from usuario
        where telefone = :telefone)
        """)
    boolean existsByTelNative(@Param("telefone") String telefone);


    @NativeQuery(value = """
        select u.name,
        u.cpf,
        u.cep,
        u.telefone,
        u.email,
        u.nacionalidade,
        u.estado_civil as estadoCivil,
        u.data_nascimento as dataNascimento
            from usuario u 
                where u.ativo = true
        """)
    List<UsuarioGet> findAllNative();

}