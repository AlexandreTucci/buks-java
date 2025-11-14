package com.buks.buks.controller;

import com.buks.buks.dto.EditoraDTO;
import com.buks.buks.service.EditoraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/editoras")
@Tag(name = "Editora", description = "APIs de gerenciamento de editoras")
public class EditoraController {

    private final EditoraService service;

    public EditoraController(EditoraService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Cadastrar uma nova editora")
    public ResponseEntity<EditoraDTO> salvar(@Valid @RequestBody EditoraDTO dto) {
        EditoraDTO salvo = service.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping
    @Operation(summary = "Listar todas as editoras")
    public List<EditoraDTO> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar editora por ID")
    public ResponseEntity<EditoraDTO> buscar(@PathVariable Integer id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma editora")
    public ResponseEntity<EditoraDTO> atualizar(@PathVariable Integer id, @Valid @RequestBody EditoraDTO dto) {
        return service.atualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar uma editora")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        boolean removido = service.deletar(id);
        return removido ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
