package com.gabriel.bancojavar.service;

import com.gabriel.bancojavar.dto.ClienteDTO;
import com.gabriel.bancojavar.feign.ClienteFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteFeignClient clienteFeignClient;

    public ClienteService(ClienteFeignClient clienteFeignClient) {
        this.clienteFeignClient = clienteFeignClient;
    }

    public List<ClienteDTO> listarTodos() {
        return clienteFeignClient.listarTodos();
    }

    public ClienteDTO buscarPorId(Long id) {
        return clienteFeignClient.buscarPorId(id);
    }

    public ClienteDTO criar(ClienteDTO clienteDTO) {
        return clienteFeignClient.criar(clienteDTO);
    }

    public ClienteDTO atualizar(Long id, ClienteDTO clienteDTO) {
        return clienteFeignClient.atualizar(id, clienteDTO);
    }

    public void deletar(Long id) {
        clienteFeignClient.deletar(id);
    }

    // Cálculo do score de crédito: saldo_cc * 0.1
    public Float calcularScoreCredito(Long id) {
        ClienteDTO cliente = clienteFeignClient.buscarPorId(id);
        if (cliente != null && cliente.getSaldoCc() != null) {
            return cliente.getSaldoCc() * 0.1F;
        }
        return 0F;
    }
}
