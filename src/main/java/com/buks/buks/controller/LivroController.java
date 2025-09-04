package com.buks.buks.controller;

import com.buks.buks.dto.LivroDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/livros")
@Tag(name = "Livro", description = "APIs de gerenciamento de livros")
public class LivroController {

    private final List<LivroDTO> livros = new ArrayList<>();
    private int nextId = 1;

    @PostMapping
    @Operation(summary = "Salva um livro", description = "Cadastra um novo livro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Livro salvo com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Os dados do livro estão incorretos."),
    })
    public ResponseEntity<LivroDTO> save(@Valid @RequestBody LivroDTO livroDTO) {
        livroDTO.setId(nextId++);
        livros.add(livroDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(livroDTO);
    }

    @GetMapping
    @Operation(summary = "Obter a lista de livros", description = "Retorna todos os livros cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recuperado com sucesso"),
    })
    public List<LivroDTO> findAll() {
        return livros;
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um livro", description = "Atualiza os dados de um livro existente")
    public ResponseEntity<LivroDTO> update(@PathVariable("id") Integer id,
                                           @Valid @RequestBody LivroDTO livroDTO) {
        for (int i = 0; i < livros.size(); i++) {
            if (livros.get(i).getId().equals(id)) {
                livroDTO.setId(id);
                livros.set(i, livroDTO);
                return ResponseEntity.ok(livroDTO);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um livro", description = "Remove um livro pelo ID")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        boolean removed = livros.removeIf(l -> l.getId().equals(id));
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
