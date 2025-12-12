package com.controleestoque.api_estoque.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_item_venda")
public class ItemVenda {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- Relacionamento N:1 (Many-to-One) ---
    // Mapeamento: Muitos Itens tem UMA Venda.
    // É o lado 'N' (Muitos), que contém a chave estrangeira (FK).
    @ManyToOne(fetch = FetchType.LAZY) // LAZY: Carrega a venda apenas quando for solicitada.
    @JoinColumn(name = "venda_id", nullable = false) // Define a FK na tabela tb_item_venda.
    @JsonIgnoreProperties("itensVenda")
    private Venda venda;

    // --- Relacionamento N:1 (Many-to-One) ---
    // Mapeamento: Muitas vendas tem UM cliente.
    // É o lado 'N' (Muitos), que contém a chave estrangeira (FK).
    @ManyToOne(fetch = FetchType.LAZY) // LAZY: Carrega o cliente apenas quando for solicitado.
    @JoinColumn(name = "produto_id") // Define a FK na tabela tb_item_venda.
    @JsonIgnoreProperties({"itemVenda", "fornecedores"})
    private Produto produto;

    private Integer quantidade;
    private BigDecimal precoUnidade;

    // Construtores, Getters e Setters
    public ItemVenda() {}

    public ItemVenda(Venda venda, Produto produto, Integer quantidade, BigDecimal precoUnidade) {
        this.venda = venda;
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnidade = precoUnidade;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Venda getVenda() { return venda; }
    public void setVenda(Venda venda) { this.venda = venda; }
    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }
    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
    public BigDecimal getPrecoUnidade() { return precoUnidade; }
    public void setPrecoUnidade(BigDecimal precoUnidade) { this.precoUnidade = precoUnidade; }
}