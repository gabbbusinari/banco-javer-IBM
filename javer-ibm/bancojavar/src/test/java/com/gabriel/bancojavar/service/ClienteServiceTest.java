package com.gabriel.bancojavar.service;

import com.gabriel.bancojavar.dto.ClienteDTO;
import com.gabriel.bancojavar.feign.ClienteFeignClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class ClienteServiceTest {

    @Mock
    private ClienteFeignClient clienteFeignClient;

    @InjectMocks
    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listarTodos() {
        List<ClienteDTO> clientes = Arrays.asList(new ClienteDTO(), new ClienteDTO());
        when(clienteFeignClient.listarTodos()).thenReturn(clientes);

        List<ClienteDTO> result = clienteService.listarTodos();
        assertEquals(2, result.size());
        verify(clienteFeignClient, times(1)).listarTodos();
    }

    @Test
    void buscarPorId() {
        ClienteDTO cliente = new ClienteDTO();
        when(clienteFeignClient.buscarPorId(1L)).thenReturn(cliente);

        ClienteDTO result = clienteService.buscarPorId(1L);
        assertEquals(cliente, result);
        verify(clienteFeignClient, times(1)).buscarPorId(1L);
    }

    @Test
    void buscarPorIdNotFound() {
        when(clienteFeignClient.buscarPorId(1L)).thenReturn(null);

        ClienteDTO result = clienteService.buscarPorId(1L);
        assertEquals(null, result);
        verify(clienteFeignClient, times(1)).buscarPorId(1L);
    }

    @Test
    void criar() {
        ClienteDTO cliente = new ClienteDTO();
        when(clienteFeignClient.criar(cliente)).thenReturn(cliente);

        ClienteDTO result = clienteService.criar(cliente);
        assertEquals(cliente, result);
        verify(clienteFeignClient, times(1)).criar(cliente);
    }

    @Test
    void atualizar() {
        ClienteDTO cliente = new ClienteDTO();
        when(clienteFeignClient.atualizar(1L, cliente)).thenReturn(cliente);

        ClienteDTO result = clienteService.atualizar(1L, cliente);
        assertEquals(cliente, result);
        verify(clienteFeignClient, times(1)).atualizar(1L, cliente);
    }

    @Test
    void deletar() {
        doNothing().when(clienteFeignClient).deletar(1L);

        clienteService.deletar(1L);
        verify(clienteFeignClient, times(1)).deletar(1L);
    }

    @Test
    void calcularScoreCredito() {
        ClienteDTO cliente = new ClienteDTO();
        cliente.setSaldoCc(1000F);
        when(clienteFeignClient.buscarPorId(1L)).thenReturn(cliente);

        Float score = clienteService.calcularScoreCredito(1L);
        assertEquals(100F, score);
    }
}

