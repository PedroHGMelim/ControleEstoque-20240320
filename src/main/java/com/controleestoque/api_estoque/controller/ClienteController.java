package com.controleestoque.api_estoque.controller;

import java.util.List;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.controleestoque.api_estoque.model.Cliente;
import com.controleestoque.api_estoque.repository.ClienteRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/clientes") // Define o caminho base para este controller
@RequiredArgsConstructor // Injeta automaticamente o ClienteRepository via construtor
public class ClienteController {
    private final ClienteRepository clienteRepository;

    // GET /api/clientes
    @GetMapping
    public List<Cliente> getAllClientes() {
        // Retorna todas os clientes do banco de dados
        return clienteRepository.findAll();
    }

    // GET /api/clientes/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id) {
        // Busca o cliente pelo ID. Usa orElse para retornar 404 se não encontrar.
        return clienteRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/clientes
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Retorna código 201 (Created)
    public Cliente createCliente(@RequestBody Cliente cliente) {
        // Salva um novo cliente no banco de dados
        return clienteRepository.save(cliente);
    }

    // PUT /api/clientes/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(
        @PathVariable Long id, @RequestBody Cliente clienteDetails) {
        // Tenta encontrar a categoria existente
        return clienteRepository.findById(id)
                .map(cliente -> {
                    // Atualiza os dados do cliente encontrado
                    cliente.setNome(clienteDetails.getNome());
                    cliente.setEmail(clienteDetails.getEmail());
                    cliente.setTelefone(clienteDetails.getTelefone());
                    Cliente updateCliente = clienteRepository.save(cliente);
                    return ResponseEntity.ok(updateCliente);
                }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/clientes/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        // Tenta encontrar e deletar
        if (!clienteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        clienteRepository.deleteById(id);
        return ResponseEntity.noContent().build(); // Retorna código 204 (No Content)
    }
}