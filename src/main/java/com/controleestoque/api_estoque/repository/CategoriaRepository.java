package com.controleestoque.api_estoque.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.controleestoque.api_estoque.model.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
    Optional<Categoria> findByNome(String nome);
    List<Categoria> findByNomeContaining(String parteNome);

    Optional<Categoria> findById(Long id);
}