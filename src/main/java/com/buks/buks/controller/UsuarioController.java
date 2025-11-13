package com.buks.buks.controller;

import com.buks.buks.dto.UsuarioDTO;
import com.buks.buks.exception.BusinessException;
import com.buks.buks.exception.ErrorCode;
import com.buks.buks.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuário", description = "APIs de gerenciamento de usuários")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    @Operation(summary = "Lista todos os usuários")
    public ResponseEntity<List<UsuarioDTO>> findAll() {
        List<UsuarioDTO> usuarios = usuarioService.findAll();

        if (usuarios.isEmpty()) {
            throw new BusinessException(ErrorCode.NO_USERS_FOUND);
        }

        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um usuário por ID")
    public ResponseEntity<UsuarioDTO> findById(@PathVariable Integer id) {
        return usuarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um usuário existente")
    public ResponseEntity<UsuarioDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody UsuarioDTO usuarioDTO) {

        if (usuarioDTO == null) {
            throw new BusinessException(ErrorCode.INVALID_REQUEST);
        }

        return usuarioService.update(id, usuarioDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um usuário existente")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        boolean removed = usuarioService.delete(id);

        if (!removed) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }

        return ResponseEntity.noContent().build();
    }
}
