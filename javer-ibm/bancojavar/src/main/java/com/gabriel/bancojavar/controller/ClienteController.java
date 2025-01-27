package com.gabriel.bancojavar.controller;

import com.gabriel.bancojavar.dto.ClienteDTO;
import com.gabriel.bancojavar.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@Tag(name = "Cliente", description = "API de clientes do banco Javer")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Operation(summary = "Lista todos os clientes", description = "Retorna a lista de clientes cadastrados")
    @GetMapping
    public List<ClienteDTO> listarTodos() {
        return clienteService.listarTodos();
    }

    @Operation(summary = "Busca cliente por ID", description = "Retorna os detalhes do cliente informado")
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscarPorId(
            @Parameter(description = "ID do cliente", example = "1") @PathVariable Long id) {
        ClienteDTO dto = clienteService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Cria um novo cliente",
            description = "Cria um cliente com os dados informados e calcula automaticamente o scoreCredito a partir do saldoCc")
    @PostMapping
    public ResponseEntity<ClienteDTO> criar(@RequestBody ClienteDTO clienteDTO) {
        // Calcula o scoreCredito automaticamente a partir do saldoCc.
        if (clienteDTO.getSaldoCc() != null) {
            clienteDTO.setScoreCredito(clienteDTO.getSaldoCc() * 0.1F);
        }
        ClienteDTO criado = clienteService.criar(clienteDTO);
        return ResponseEntity.ok(criado);
    }

    @Operation(summary = "Atualiza os dados de um cliente", description = "Atualiza os dados do cliente informado pelo ID")
    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> atualizar(
            @Parameter(description = "ID do cliente a ser atualizado", example = "1") @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Novos dados do cliente")
            @RequestBody ClienteDTO clienteDTO) {
        if (clienteDTO.getSaldoCc() != null) {
            clienteDTO.setScoreCredito(clienteDTO.getSaldoCc() * 0.1F);
        }
        ClienteDTO atualizado = clienteService.atualizar(id, clienteDTO);
        return ResponseEntity.ok(atualizado);
    }

    @Operation(summary = "Deleta um cliente", description = "Remove o cliente com o ID informado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do cliente a ser deletado", example = "1") @PathVariable Long id) {
        clienteService.deletar(id);
        return ResponseEntity.ok().build();
    }
}
