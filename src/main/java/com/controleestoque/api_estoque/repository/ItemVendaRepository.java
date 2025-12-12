package com.controleestoque.api_estoque.repository;

import java.util.*;

import com.controleestoque.api_estoque.model.ItemVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemVendaRepository extends JpaRepository<ItemVenda, Long> {
    Optional<ItemVenda> findById(Long id);
}