package com.gabriel.bancojavar2.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.gabriel.bancojavar2.Cliente;
import com.gabriel.bancojavar2.dto.ClienteDTO;
import com.gabriel.bancojavar2.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ClienteControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(clienteController).build();
    }

    @Test
    public void testBuscarPorId() throws Exception {
        Cliente cliente = new Cliente("Nome", 123456789L, true, 750.0f, 1000.0f);
        when(clienteService.buscarPorId(1L)).thenReturn(Optional.of(cliente));

        mockMvc.perform(get("/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Nome"));
    }

    @Test
    public void testCriar() throws Exception {
        ClienteDTO clienteDTO = new ClienteDTO(null, "Nome", 123456789L, true, 750.0f, 1000.0f);
        Cliente cliente = new Cliente("Nome", 123456789L, true, 750.0f, 1000.0f);
        when(clienteService.salvar(any(Cliente.class))).thenReturn(cliente);

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Nome\",\"telefone\":123456789,\"correntista\":true,\"scoreCredito\":750.0,\"saldoCc\":1000.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Nome"));
    }

    @Test
    public void testListarTodos() throws Exception {
        List<Cliente> clientes = Arrays.asList(new Cliente("Nome1", 123456789L, true, 750.0f, 1000.0f),
                new Cliente("Nome2", 987654321L, false, 650.0f, 500.0f));
        when(clienteService.listarTodos()).thenReturn(clientes);

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Nome1"))
                .andExpect(jsonPath("$[1].nome").value("Nome2"));
    }

    @Test
    public void testAtualizar() throws Exception {
        Cliente cliente = new Cliente("Nome", 123456789L, true, 750.0f, 1000.0f);
        when(clienteService.buscarPorId(1L)).thenReturn(Optional.of(cliente));
        when(clienteService.atualizar(any(Cliente.class))).thenReturn(cliente);

        mockMvc.perform(put("/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Nome\",\"telefone\":123456789,\"correntista\":true,\"scoreCredito\":750.0,\"saldoCc\":1000.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Nome"));
    }

    @Test
    public void testDeletar() throws Exception {
        Cliente cliente = new Cliente("Nome", 123456789L, true, 750.0f, 1000.0f);
        when(clienteService.buscarPorId(1L)).thenReturn(Optional.of(cliente));

        mockMvc.perform(delete("/clientes/1"))
                .andExpect(status().isOk());

        verify(clienteService, times(1)).deletar(1L);
    }
}
