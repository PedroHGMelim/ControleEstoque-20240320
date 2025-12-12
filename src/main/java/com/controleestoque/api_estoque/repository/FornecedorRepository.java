package com.controleestoque.api_estoque.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.controleestoque.api_estoque.model.Fornecedor;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long>{
    Optional<Fornecedor> findByNome(String nome);
    List<Fornecedor> findByNomeContaining(String parteNome);

    Optional<Fornecedor> findById(Long id);
}