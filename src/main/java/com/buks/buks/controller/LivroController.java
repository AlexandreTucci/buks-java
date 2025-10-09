package com.buks.buks.controller;

import com.buks.buks.dto.LivroDTO;
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
        LivroDTO salvo = livroService.salvar(livroDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping
    @Operation(summary = "Listar todos os livros")
    public List<LivroDTO> findAll() {
        return livroService.listarTodos();
    }
    @GetMapping("/{id}")
    @Operation(summary = "Buscar livro por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livro encontrado com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado.")
    })
    public ResponseEntity<LivroDTO> findById(@PathVariable Integer id) {
        return livroService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um livro")
    public ResponseEntity<LivroDTO> update(@PathVariable Integer id,
                                           @Valid @RequestBody LivroDTO livroDTO) {
        return livroService.atualizar(id, livroDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um livro")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        boolean removido = livroService.deletar(id);
        return removido ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
