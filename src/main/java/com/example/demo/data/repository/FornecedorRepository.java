package com.example.demo.data.repository;

import com.example.demo.data.entity.FornecedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FornecedorRepository extends JpaRepository<FornecedorEntity, Long> {

    @NativeQuery(value = """
        select concat_ws(',',
        case when exists(select 1 from fornecedor where razao_social = :razaoSocial) then 'razaoSocial' end,
        case when exists(select 1 from fornecedor where cnpj = :cnpj) then 'cnpj' end,
        case when exists(select 1 from fornecedor where telefone = :telefone) then 'telefone' end,
        case when exists(select 1 from fornecedor where email = :email) then 'email' end,
        case when exists(select 1 from fornecedor where site = :site) then 'site' end)
        """)
    String verificarDuplicidadeNative(
            @Param("razaoSocial") String razaoSocial,
            @Param("cnpj") String cnpj,
            @Param("telefone") String telefone,
            @Param("email") String email,
            @Param("site") String site);

}
