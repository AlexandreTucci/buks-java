package com.buks.buks.controller;

import com.buks.buks.dto.LivroDTO;
import com.buks.buks.model.Livro;
import com.buks.buks.repository.LivroRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/livros")
@Tag(name = "Livro", description = "APIs de gerenciamento de livros")
public class LivroController {

    private final LivroRepository livroRepository;

    public LivroController(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    // POST /api/livros
    @PostMapping
    @Operation(summary = "Salva um livro", description = "Cadastra um novo livro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Livro salvo com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Os dados do livro estão incorretos.")
    })
    public ResponseEntity<LivroDTO> save(@Valid @RequestBody LivroDTO livroDTO) {
        Livro livro = toEntity(livroDTO);
        Livro saved = livroRepository.save(livro);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(saved));
    }

    // GET /api/livros
    @GetMapping
    @Operation(summary = "Obter a lista de livros", description = "Retorna todos os livros cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recuperado com sucesso")
    })
    public List<LivroDTO> findAll() {
        return livroRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // PUT /api/livros/{id}
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um livro", description = "Atualiza os dados de um livro existente")
    public ResponseEntity<LivroDTO> update(@PathVariable Integer id,
                                           @Valid @RequestBody LivroDTO livroDTO) {
        return livroRepository.findById(id)
                .map(l -> {
                    Livro livro = toEntity(livroDTO);
                    livro.setId(id);
                    Livro updated = livroRepository.save(livro);
                    return ResponseEntity.ok(toDTO(updated));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/livros/{id}
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um livro", description = "Remove um livro pelo ID")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (livroRepository.existsById(id)) {
            livroRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // --- conversão DTO ↔ Entidade ---
    private Livro toEntity(LivroDTO dto) {
        return new Livro(
                dto.getId(),
                dto.getNome(),
                dto.getDescricao(),
                dto.getPreco(),
                dto.getImagem()
        );
    }

    private LivroDTO toDTO(Livro livro) {
        return new LivroDTO(
                livro.getId(),
                livro.getNome(),
                livro.getDescricao(),
                livro.getPreco(),
                livro.getImagem()
        );
    }
}
