package com.controleestoque.api_estoque.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import com.controleestoque.api_estoque.model.*;

import com.controleestoque.api_estoque.repository.ClienteRepository;
import com.controleestoque.api_estoque.repository.ProdutoRepository;
import com.controleestoque.api_estoque.repository.VendaRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/vendas") // Define o caminho base para este controller
@RequiredArgsConstructor // Injeta automaticamente o VendaRepository via construtor
public class VendaController {
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final VendaRepository vendaRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public ResponseEntity<?> createVenda(@RequestBody Venda venda) {

        Cliente cliente = clienteRepository.findById(venda.getCliente().getId()).orElse(null);
        
        if(cliente == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cliente não encontrado.");
        } venda.setCliente(cliente);

        BigDecimal total = BigDecimal.ZERO;

        for(ItemVenda item : venda.getItensVenda()) {
            Produto produto = produtoRepository.findById(item.getProduto().getId()).orElse(null);

            if(produto == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Produto com ID: " + item.getProduto().getId() + " não encontrado.");
            }

            Estoque estoque = produto.getEstoque();

            if(estoque.getQuantidade() < item.getQuantidade()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Estoque insuficiente para o produto: " + produto.getNome());
            }

            estoque.setQuantidade(estoque.getQuantidade() - item.getQuantidade());
            produtoRepository.save(produto);

            item.setProduto(produto);
            item.setPrecoUnidade(produto.getPreco());
            item.setVenda(venda);

            total = total.add(item.getPrecoUnidade().multiply(BigDecimal.valueOf(item.getQuantidade())));
        }

        venda.setValorVenda(total);

        Venda novaVenda = vendaRepository.save(venda); // sava venda + itens (cascade)

        return ResponseEntity.status(HttpStatus.CREATED).body(vendaRepository.findById(novaVenda.getId()).get());
    }

    // GET /api/vendas
    @GetMapping
    public List<Venda> getAllVendas() {
        // Retorna a lista de produtos. Pode ser necessário configurar DTOs para evitar loops infinitos com JSON.
        return vendaRepository.findAll();
    }

    // GET /api/vendas/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Venda> getVendaById(@PathVariable Long id) {
        // Busca a venda pelo ID. Usa orElse para retornar 404 se não encontrar.
        return vendaRepository.findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/vendas/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenda(@PathVariable Long id) {
        // Tenta encontrar e deletar
        if (!vendaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        vendaRepository.deleteById(id);
        return ResponseEntity.noContent().build(); // Retorna o código 204 (No Content)
    }
}