package com.buks.buks.controller;

import com.buks.buks.dto.PagamentoDTO;
import com.buks.buks.exception.BusinessException;
import com.buks.buks.exception.ErrorCode;
import com.buks.buks.service.PagamentoService;
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
@RequestMapping("/api/pagamentos")
@Tag(name = "Pagamento", description = "Gerenciamento de pagamentos de pedidos")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @PostMapping
    @Operation(summary = "Realizar pagamento", description = "Registra o pagamento de um pedido existente")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pagamento realizado com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado."),
            @ApiResponse(responseCode = "409", description = "Pedido já foi pago.")
    })
    public ResponseEntity<PagamentoDTO> save(@Valid @RequestBody PagamentoDTO dto) {
        PagamentoDTO salvo = pagamentoService.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping
    @Operation(summary = "Listar pagamentos")
    public List<PagamentoDTO> findAll() {
        return pagamentoService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pagamento por ID")
    public ResponseEntity<PagamentoDTO> findById(@PathVariable Integer id) {
        return pagamentoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new BusinessException(ErrorCode.PAYMENT_NOT_FOUND));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar pagamento")
    public ResponseEntity<PagamentoDTO> update(@PathVariable Integer id, @Valid @RequestBody PagamentoDTO dto) {
        return pagamentoService.atualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new BusinessException(ErrorCode.PAYMENT_NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Estornar/Deletar pagamento")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        boolean removido = pagamentoService.deletar(id);
        if (!removido) {
            throw new BusinessException(ErrorCode.PAYMENT_NOT_FOUND);
        }
        return ResponseEntity.noContent().build();
    }
}