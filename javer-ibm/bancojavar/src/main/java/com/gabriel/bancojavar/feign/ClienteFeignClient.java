package com.gabriel.bancojavar.feign;

import com.gabriel.bancojavar.dto.ClienteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "storage-service", url = "${javer.storage-service.url}")
public interface ClienteFeignClient {

    @GetMapping("/clientes")
    List<ClienteDTO> listarTodos();

    @GetMapping("/clientes/{id}")
    ClienteDTO buscarPorId(@PathVariable("id") Long id);

    @PostMapping("/clientes")
    ClienteDTO criar(@RequestBody ClienteDTO clienteDTO);

    @PutMapping("/clientes/{id}")
    ClienteDTO atualizar(@PathVariable("id") Long id, @RequestBody ClienteDTO clienteDTO);

    @DeleteMapping("/clientes/{id}")
    void deletar(@PathVariable("id") Long id);
}
