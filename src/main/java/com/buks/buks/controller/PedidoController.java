package com.buks.buks.controller;

import com.buks.buks.dto.PedidoDTO;
import com.buks.buks.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@Tag(name = "Pedido", description = "APIs de gerenciamento de pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    @Operation(summary = "Salvar um pedido")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados incorretos.")
    })
    public ResponseEntity<PedidoDTO> save(@Valid @RequestBody PedidoDTO dto) {
        PedidoDTO salvo = pedidoService.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping
    @Operation(summary = "Listar todos os pedidos")
    public List<PedidoDTO> findAll() {
        return pedidoService.listarTodos();
    }


    @GetMapping("/{id}")
    @Operation(summary = "Buscar pedido por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido encontrado com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado.")
    })
    public ResponseEntity<PedidoDTO> findById(@PathVariable Long id) {
        return pedidoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um pedido existente")
    public ResponseEntity<PedidoDTO> update(@PathVariable Long id, @Valid @RequestBody PedidoDTO dto) {
        return pedidoService.atualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir um pedido pelo ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean removido = pedidoService.deletar(id);
        return removido ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
