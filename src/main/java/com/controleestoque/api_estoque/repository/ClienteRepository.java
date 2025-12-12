package com.controleestoque.api_estoque.repository;

import java.util.*;

import com.controleestoque.api_estoque.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByNome(String nome);
    List<Cliente> findByNomeContaining(String parteNome);

    Optional<Cliente> findById(Long id);
}