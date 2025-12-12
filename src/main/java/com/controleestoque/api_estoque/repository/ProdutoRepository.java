package com.controleestoque.api_estoque.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.controleestoque.api_estoque.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{
    Optional<Produto> findByNome(String nome);
    List<Produto> findByNomeContaining(String parteNome);

    Optional<Produto> findById(Long id);
}