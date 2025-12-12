package com.controleestoque.api_estoque.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_vendas")
public class Venda {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- Relacionamento N:1 (Many-to-One) ---
    // Mapeamento: Muitas vendas tem UM cliente.
    // É o lado 'N' (Muitos), que contém a chave estrangeira (FK).
    @ManyToOne(fetch = FetchType.LAZY) // LAZY: Carrega o cliente apenas quando for solicitado.
    @JoinColumn(name = "cliente_id", nullable = false) // Define a FK na tabela tb_vendas.
    private Cliente cliente;

    // --- Relacionamento 1:N (One-to-Many) ---
    // É o lado '1' do relacionamento. 'mappedBy' aponta para o campo em ItemVenda.
    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("venda")
    private List<ItemVenda> itensVenda = new ArrayList<>();

    private LocalDateTime dataVenda = LocalDateTime.now();
    private BigDecimal valorVenda;

    // Construtores, Getters e Setters
    public Venda() {}

    public Venda(Cliente cliente, List<ItemVenda> itensVenda, LocalDateTime dataVenda, BigDecimal valorVenda) {
        this.cliente = cliente;
        this.itensVenda = itensVenda;
        this.dataVenda = dataVenda;
        this.valorVenda = valorVenda;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public List<ItemVenda> getItensVenda() { return itensVenda; }
    public void setItensVenda(List<ItemVenda> itensVenda) { this.itensVenda = itensVenda; }
    public LocalDateTime getDataVenda() { return dataVenda; }
    public void setDataVenda(LocalDateTime dataVenda) { this.dataVenda = dataVenda; }
    public BigDecimal getValorVenda() { return valorVenda; }
    public void setValorVenda(BigDecimal valorVenda) { this.valorVenda = valorVenda; }
}