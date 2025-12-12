package com.controleestoque.api_estoque.controller;

import java.util.*;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.controleestoque.api_estoque.model.Produto;
import com.controleestoque.api_estoque.repository.ProdutoRepository;
import com.controleestoque.api_estoque.model.Fornecedor;

import lombok.RequiredArgsConstructor;

import com.controleestoque.api_estoque.repository.CategoriaRepository;
import com.controleestoque.api_estoque.repository.FornecedorRepository;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
public class ProdutoController {
    
    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final FornecedorRepository fornecedorRepository;
    // Estoque é geralmente manipulado via produto ou separadamente.

    // GET /api/produtos
    @GetMapping
    public List<Produto> getAllProdutos() {
        // Retorna a lista de produtos. Pode ser necessário configurar DTOs para evitar loops infinitos com JSON.
        return produtoRepository.findAll();
    }

    // GET /api/produtos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Produto> getProdutoById(@PathVariable Long id) {
        // Busca o produto pelo ID. Usa orElse para retornar 404 se não encontrar.
        return produtoRepository.findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/produtos
    // Neste método, assumidos que a Categoria e os Fornecedores já existem
    // e seus IDs são passados no corpo da requisição (ProdutoDTO seria o ideal aqui).
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Produto> createProduto(@RequestBody Produto produto) {
        // 1. Gerenciamento do 1:N (Categoria)
        // A categoria deve ser buscada para garantir que existe e estar no contexto de persistência.
        if (produto.getCategoria() == null || produto.getCategoria().getId() == null) {
            return ResponseEntity.badRequest().build(); // Categoria é obrigatória
        }
        var categoria = categoriaRepository.findById(produto.getCategoria().getId())
            .orElse(null);

        if (categoria == null) {
            return ResponseEntity.badRequest().body(null);
        }
        produto.setCategoria(categoria);
        
        // 2. Gerenciamento do N:M (Fornecedores)
        // Cria um novo conjunto para guardar os fornecedores
        Set<Fornecedor> fornecedoresGerenciados = new HashSet<>();
        // Busca todos os fornecedores pelos IDs fornecidos
        if (produto.getFornecedores() != null && !produto.getFornecedores().isEmpty()) {
            // Para cada fornecedor no produto, busca o fornecedor pelo ID
            for (Fornecedor fornecedor : produto.getFornecedores()) {
                if (fornecedor != null && fornecedor.getId() != null) {
                    fornecedorRepository.findById(fornecedor.getId())
                        .ifPresent(fornecedoresGerenciados::add);
                }
            }
        }
        // Associa os fornecedores ao produto
        produto.setFornecedores(fornecedoresGerenciados);

        // 3. Gerencia o Estoque (1:1)
        if (produto.getEstoque() != null) {
            produto.getEstoque().setProduto(produto);
        }

        // 4. Salva o Produto (e o Estoque, se o CASCADE estiver configurado)
        Produto savedProduto = produtoRepository.save(produto);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduto);
    }

    // PUT /api/produtos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Produto> updateProduto(@PathVariable Long id, @RequestBody Produto produtoDetails) {
        // Tenta encontrar o produto existente
        return produtoRepository.findById(id)
        .map(produto -> {
            // Atualiza os dados do produto encontrada
            produto.setNome(produtoDetails.getNome());
            Produto updatedProduto = produtoRepository.save(produto);
            return ResponseEntity.ok(updatedProduto);
        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/produtos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        // Tenta encontrar e deletar
        if (!produtoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        produtoRepository.deleteById(id);
        return ResponseEntity.noContent().build(); // Retorna o código 204 (No Content)
    }
}