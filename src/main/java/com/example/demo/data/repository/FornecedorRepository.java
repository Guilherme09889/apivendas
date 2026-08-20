package com.example.demo.data.repository;

import com.example.demo.data.entity.FornecedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FornecedorRepository extends JpaRepository<FornecedorEntity, Long> {

    @NativeQuery(value = """
        select exists(
        select 1 from fornecedor
        where razao_social = :razaoSocial)
        """)
    boolean existsByRazaoSocialNative(@Param("razaoSocial") String razaoSocial);

    @NativeQuery(value = """
        select exists(
        select 1 from fornecedor
        where cnpj = :cnpj)
        """)
    boolean existsByCnpjNative(@Param("cnpj") String cnpj);

    @NativeQuery(value = """
        select exists(
        select 1 from fornecedor
        where telefone = :telefone)
        """)
    boolean existsByTelNative(@Param("telefone") String telefone);

    @NativeQuery(value = """
        select exists(
        select 1 from fornecedor
        where email = :email)
        """)
    boolean existsByEmailNative(@Param("email") String email);

    @NativeQuery(value = """
        select exists(
        select 1 from fornecedor
        where site = :site)
        """)
    boolean existsBySiteNative(@Param("site") String site);


}
