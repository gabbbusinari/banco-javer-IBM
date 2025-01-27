package com.gabriel.bancojavar2.service;

import com.gabriel.bancojavar2.Cliente;
import com.gabriel.bancojavar2.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSalvar() {
        Cliente cliente = new Cliente("Nome", 123456789L, true, 750.0f, 1000.0f);
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente salvo = clienteService.salvar(cliente);

        assertNotNull(salvo);
        assertEquals("Nome", salvo.getNome());
    }

    @Test
    public void testBuscarPorId() {
        Cliente cliente = new Cliente("Nome", 123456789L, true, 750.0f, 1000.0f);
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Optional<Cliente> encontrado = clienteService.buscarPorId(1L);

        assertTrue(encontrado.isPresent());
        assertEquals("Nome", encontrado.get().getNome());
    }

    @Test
    public void testDeletar() {
        clienteService.deletar(1L);
        verify(clienteRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testListarTodos() {
        List<Cliente> clientes = Arrays.asList(new Cliente("Nome1", 123456789L, true, 750.0f, 1000.0f),
                new Cliente("Nome2", 987654321L, false, 650.0f, 500.0f));
        when(clienteRepository.findAll()).thenReturn(clientes);

        List<Cliente> resultado = clienteService.listarTodos();

        assertEquals(2, resultado.size());
        assertEquals("Nome1", resultado.get(0).getNome());
        assertEquals("Nome2", resultado.get(1).getNome());
    }

    @Test
    public void testAtualizar() {
        Cliente cliente = new Cliente("Nome", 123456789L, true, 750.0f, 1000.0f);
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente atualizado = clienteService.atualizar(cliente);

        assertNotNull(atualizado);
        assertEquals("Nome", atualizado.getNome());
    }
}
