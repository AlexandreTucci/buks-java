package com.buks.buks.controller;

import com.buks.buks.dto.AutorDTO;
import com.buks.buks.service.AutorService;
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
@RequestMapping("/api/autores")
@Tag(name = "Autor", description = "APIs de gerenciamento de autores")
public class AutorController {

    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @PostMapping
    @Operation(summary = "Salvar um autor", description = "Cadastra um novo autor")
    public ResponseEntity<AutorDTO> save(@Valid @RequestBody AutorDTO autorDTO) {
        AutorDTO salvo = autorService.salvar(autorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping
    @Operation(summary = "Listar todos os autores")
    public List<AutorDTO> findAll() {
        return autorService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar autor por ID")
    public ResponseEntity<AutorDTO> findById(@PathVariable Long id) {
        AutorDTO autor = autorService.buscarPorId(id);
        return ResponseEntity.ok(autor);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um autor")
    public ResponseEntity<AutorDTO> update(@PathVariable Long id,
                                           @Valid @RequestBody AutorDTO autorDTO) {
        AutorDTO atualizado = autorService.atualizar(id, autorDTO);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um autor")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        autorService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
