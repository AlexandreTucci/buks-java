package com.buks.buks.controller;

import com.buks.buks.dto.PedidoDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final List<PedidoDTO> pedidos = new ArrayList<>();
    private Long nextId = 1L;

    // Criar pedido
    @PostMapping
    public ResponseEntity<PedidoDTO> criarPedido(@Valid @RequestBody PedidoDTO pedidoDTO) {
        pedidoDTO.setId(nextId++);
        pedidoDTO.setCriadoEm(LocalDate.now());
        pedidos.add(pedidoDTO);
        return ResponseEntity.ok(pedidoDTO);
    }

    // Listar todos os pedidos
    @GetMapping
    public ResponseEntity<List<PedidoDTO>> listarPedidos() {
        return ResponseEntity.ok(pedidos);
    }

    // Buscar pedido por ID
    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> buscarPorId(@PathVariable Long id) {
        return pedidos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Atualizar pedido
    @PutMapping("/{id}")
    public ResponseEntity<PedidoDTO> atualizarPedido(@PathVariable Long id, @Valid @RequestBody PedidoDTO pedidoDTO) {
        return pedidos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .map(pedidoExistente -> {
                    pedidoExistente.setNomeDestinatario(pedidoDTO.getNomeDestinatario());
                    pedidoExistente.setData(pedidoDTO.getData());
                    pedidoExistente.setComplemento(pedidoDTO.getComplemento());
                    pedidoExistente.setTelefone(pedidoDTO.getTelefone());
                    pedidoExistente.setCep(pedidoDTO.getCep());
                    pedidoExistente.setUsuarioId(pedidoDTO.getUsuarioId());
                    return ResponseEntity.ok(pedidoExistente);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Deletar pedido
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPedido(@PathVariable Long id) {
        boolean removed = pedidos.removeIf(p -> p.getId().equals(id));
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
