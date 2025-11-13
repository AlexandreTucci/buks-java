package com.buks.buks.controller;

import com.buks.buks.dto.LivroDTO;
import com.buks.buks.exception.BusinessException;
import com.buks.buks.exception.ErrorCode;
import com.buks.buks.service.LivroService;
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
@RequestMapping("/api/livros")
@Tag(name = "Livro", description = "APIs de gerenciamento de livros")
public class LivroController {

    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @PostMapping
    @Operation(summary = "Salvar um livro", description = "Cadastra um novo livro")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Livro salvo com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados incorretos")
    })
    public ResponseEntity<LivroDTO> save(@Valid @RequestBody LivroDTO livroDTO) {
        try {
            LivroDTO salvo = livroService.salvar(livroDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.INVALID_BOOK_DATA);
        }
    }

    @GetMapping
    @Operation(summary = "Listar todos os livros", description = "Retorna a lista de todos os livros cadastrados")
    public List<LivroDTO> findAll() {
        return livroService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar livro por ID", description = "Retorna os dados de um livro específico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livro encontrado com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado.")
    })
    public ResponseEntity<LivroDTO> findById(@PathVariable Integer id) {
        return livroService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new BusinessException(ErrorCode.BOOK_NOT_FOUND));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um livro", description = "Atualiza os dados de um livro existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livro atualizado com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização.")
    })
    public ResponseEntity<LivroDTO> update(@PathVariable Integer id,
                                           @Valid @RequestBody LivroDTO livroDTO) {
        return livroService.atualizar(id, livroDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new BusinessException(ErrorCode.BOOK_NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um livro", description = "Remove um livro do sistema pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Livro deletado com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado.")
    })
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        boolean removido = livroService.deletar(id);
        if (!removido) {
            throw new BusinessException(ErrorCode.BOOK_NOT_FOUND);
        }
        return ResponseEntity.noContent().build();
    }
}
