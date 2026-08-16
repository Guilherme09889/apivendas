package com.example.demo.data.repository;

import com.example.demo.data.entity.UsuarioEntity;
import com.example.demo.dto.usuario.UsuarioGet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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
    select exists(
        select 1 from usuario
        where id = :id and ativo = true)
    """)
    boolean existsByIdNative(@Param("id") long id);

    @NativeQuery(value = """
    select exists(
        select 1 from usuario
        where id = :id and ativo = false)
    """)
    boolean existsByIdNegativoNative(@Param("id") long id);

    @NativeQuery(value = """
        select u.name,
        u.cpf,
        u.cep,
        u.telefone,
        u.email,
        u.nacionalidade,
        u.estado_civil as estadoCivil,
        u.data_nascimento as dataNascimento,
        u.ativo
            from usuario u 
                where u.ativo = true
        """,
    countQuery = """
        select count(*) from usuario u 
                where u.ativo = true
    """
    )
    Page<UsuarioGet> findAllNative(Pageable pageable);

    @NativeQuery(value = """
        select u.name as name,
        u.cpf as cpf,
        u.cep as cep,
        u.telefone as telefone,
        u.email as email,
        u.nacionalidade as nacionalidade,
        u.estado_civil as estadoCivil,
        u.data_nascimento as dataNascimento
            from usuario u 
                where u.id = :id and u.ativo = true
        """)
     Optional<UsuarioGet> findaByIdNativeUpdate(@Param("id") Long id);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @NativeQuery(value = """
        update usuario u
                set name = :name,
                    cpf = :cpf,
                    cep = :cep,
                    telefone = :telefone,
                    email = :email,
                    nacionalidade = :nacionalidade,
                    estado_civil = :estadoCivil
                where u.id = :id and u.ativo = true
        """)
    void updateUsuarioNative(@Param("id") Long id,
                             @Param("name") String name,
                             @Param("cpf") String cpf,
                             @Param("cep") String cep,
                             @Param("telefone") String telefone,
                             @Param("email") String email,
                             @Param("nacionalidade") String nacionalidade,
                             @Param("estadoCivil") String estadoCivil);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @NativeQuery(value = """
        update usuario
        set ativo = false
        where id = :id and ativo = true
    """)
    int desativarUsuarioNativo(@Param("id") Long id);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @NativeQuery(value = """
        update usuario
            set ativo = true
            where id = :id and ativo = false
    """)
    int ativarUsuarioNativo(@Param("id") Long id);

}