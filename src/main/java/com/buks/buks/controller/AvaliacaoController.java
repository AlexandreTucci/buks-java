package com.buks.buks.controller;

import com.buks.buks.dto.AvaliacaoDTO;
import com.buks.buks.exception.BusinessException;
import com.buks.buks.exception.ErrorCode;
import com.buks.buks.service.AvaliacaoService;
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
@RequestMapping("/api/avaliacoes")
@Tag(name = "Avaliação", description = "APIs de gerenciamento de avaliações de livros")
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;

    public AvaliacaoController(AvaliacaoService avaliacaoService) {
        this.avaliacaoService = avaliacaoService;
    }

    @PostMapping
    @Operation(summary = "Salvar uma avaliação", description = "Cadastra uma nova avaliação de um livro")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Avaliação salva com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados incorretos"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado.")
    })
    public ResponseEntity<AvaliacaoDTO> save(@Valid @RequestBody AvaliacaoDTO avaliacaoDTO) {
        try {
            AvaliacaoDTO salvo = avaliacaoService.salvar(avaliacaoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
        } catch (BusinessException e) {
            throw e; // já será tratado pelo GlobalExceptionHandler
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.INTERNAL_ERROR);
        }
    }

    @GetMapping
    @Operation(summary = "Listar todas as avaliações")
    public ResponseEntity<List<AvaliacaoDTO>> findAll() {
        List<AvaliacaoDTO> avaliacoes = avaliacaoService.listarTodas();

        if (avaliacoes.isEmpty()) {
            throw new BusinessException(ErrorCode.NO_REVIEWS_FOUND);
        }

        return ResponseEntity.ok(avaliacoes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar avaliação por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Avaliação encontrada com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Avaliação não encontrada.")
    })
    public ResponseEntity<AvaliacaoDTO> findById(@PathVariable Integer id) {
        return avaliacaoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new BusinessException(ErrorCode.REVIEW_NOT_FOUND));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma avaliação")
    public ResponseEntity<AvaliacaoDTO> update(@PathVariable Integer id,
                                               @Valid @RequestBody AvaliacaoDTO avaliacaoDTO) {
        return avaliacaoService.atualizar(id, avaliacaoDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new BusinessException(ErrorCode.REVIEW_NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar uma avaliação")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        boolean removido = avaliacaoService.deletar(id);

        if (!removido) {
            throw new BusinessException(ErrorCode.REVIEW_NOT_FOUND);
        }

        return ResponseEntity.noContent().build();
    }
}
