package com.controleestoque.api_estoque.repository;

import java.util.*;

import com.controleestoque.api_estoque.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {
    Optional<Venda> findById(Long id);
}