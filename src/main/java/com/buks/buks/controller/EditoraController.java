package com.buks.buks.controller;

import com.buks.buks.dto.EditoraDTO;
import com.buks.buks.service.EditoraService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/editoras")
public class EditoraController {

    private final EditoraService editoraService;

    public EditoraController(EditoraService editoraService) {
        this.editoraService = editoraService;
    }

    @PostMapping
    @Operation(summary = "Criar uma editora")
    public ResponseEntity<EditoraDTO> create(@Valid @RequestBody EditoraDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(editoraService.salvar(dto));
    }

    @GetMapping
    @Operation(summary = "Listar todas as editoras")
    public List<EditoraDTO> findAll() {
        return editoraService.listarTodas();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar editora por ID")
    public ResponseEntity<EditoraDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(editoraService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar editora")
    public ResponseEntity<EditoraDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody EditoraDTO dto) {
        return ResponseEntity.ok(editoraService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover editora")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        editoraService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
