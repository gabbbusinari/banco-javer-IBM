package com.gabriel.bancojavar2.controller;

import com.gabriel.bancojavar2.Cliente;
import com.gabriel.bancojavar2.dto.ClienteDTO;
import com.gabriel.bancojavar2.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clientes")
@Tag(name = "Cliente", description = "APIs para gerenciamento de clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // Converter entidade para DTO
    private ClienteDTO converterParaDTO(Cliente cliente) {
        return new ClienteDTO(cliente.getId(), cliente.getNome(),
                cliente.getTelefone(), cliente.getCorrentista(),
                cliente.getScoreCredito(), cliente.getSaldoCc());
    }

    @Operation(summary = "Listar todos os clientes", description = "Retorna a lista de clientes cadastrados")
    @GetMapping
    public List<ClienteDTO> listarTodos() {
        return clienteService.listarTodos().stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Buscar cliente por ID", description = "Retorna os detalhes de um cliente pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscarPorId(@PathVariable Long id) {
        return clienteService.buscarPorId(id)
                .map(cliente -> ResponseEntity.ok(converterParaDTO(cliente)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Criar um novo cliente", description = "Cria um novo cliente com os dados fornecidos")
    @PostMapping
    public ResponseEntity<ClienteDTO> criar(@RequestBody ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente(clienteDTO.getNome(), clienteDTO.getTelefone(),
                clienteDTO.getCorrentista(), clienteDTO.getScoreCredito(),
                clienteDTO.getSaldoCc());
        Cliente salvo = clienteService.salvar(cliente);
        return ResponseEntity.ok(converterParaDTO(salvo));
    }

    @Operation(summary = "Atualizar um cliente", description = "Atualiza os dados de um cliente existente")
    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> atualizar(@PathVariable Long id, @RequestBody ClienteDTO clienteDTO) {
        return clienteService.buscarPorId(id)
                .map(existing -> {
                    existing.setNome(clienteDTO.getNome());
                    existing.setTelefone(clienteDTO.getTelefone());
                    existing.setCorrentista(clienteDTO.getCorrentista());
                    existing.setScoreCredito(clienteDTO.getScoreCredito());
                    existing.setSaldoCc(clienteDTO.getSaldoCc());

                    Cliente atualizado = clienteService.atualizar(existing);
                    return ResponseEntity.ok(converterParaDTO(atualizado));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Deletar um cliente", description = "Remove um cliente pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (clienteService.buscarPorId(id).isPresent()){
            clienteService.deletar(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
