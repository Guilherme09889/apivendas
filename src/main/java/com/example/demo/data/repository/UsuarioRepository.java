package com.example.demo.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.data.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;

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

}
