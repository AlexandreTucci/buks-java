package com.buks.buks.controller;

import com.buks.buks.dto.AvaliacaoDTO;
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
            @ApiResponse(responseCode = "400", description = "Dados incorretos")
    })
    public ResponseEntity<AvaliacaoDTO> save(@Valid @RequestBody AvaliacaoDTO avaliacaoDTO) {
        AvaliacaoDTO salvo = avaliacaoService.salvar(avaliacaoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping
    @Operation(summary = "Listar todas as avaliações")
    public List<AvaliacaoDTO> findAll() {
        return avaliacaoService.listarTodas();
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
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma avaliação")
    public ResponseEntity<AvaliacaoDTO> update(@PathVariable Integer id,
                                               @Valid @RequestBody AvaliacaoDTO avaliacaoDTO) {
        return avaliacaoService.atualizar(id, avaliacaoDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar uma avaliação")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        boolean removido = avaliacaoService.deletar(id);
        return removido ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
