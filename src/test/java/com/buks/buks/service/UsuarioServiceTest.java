package com.buks.buks.service;

import com.buks.buks.dto.UsuarioDTO;
import com.buks.buks.model.Usuario;
import com.buks.buks.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void deveSalvarUsuario() {
        UsuarioDTO dto = new UsuarioDTO(null, "Teste", "teste@email.com", "123", LocalDate.now());
        // Construtor do Usuario: id, nome, email, senha, dataNascimento, role
        Usuario usuario = new Usuario(1, "Teste", "teste@email.com", "123", LocalDate.now(), null);

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        UsuarioDTO result = usuarioService.save(dto);

        assertNotNull(result.getId());
        assertEquals("Teste", result.getNome());
    }

    @Test
    void deveListarUsuarios() {
        when(usuarioRepository.findAll()).thenReturn(List.of(new Usuario()));
        List<UsuarioDTO> result = usuarioService.findAll();
        assertFalse(result.isEmpty());
    }
}