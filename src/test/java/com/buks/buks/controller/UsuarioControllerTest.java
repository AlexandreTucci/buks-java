package com.buks.buks.controller;

import com.buks.buks.dto.UsuarioDTO;
import com.buks.buks.repository.UsuarioRepository;
import com.buks.buks.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
@AutoConfigureMockMvc(addFilters = false) // Ignora Spring Security
class UsuarioControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockitoBean
    private UsuarioService usuarioService;
    @MockitoBean
    private UsuarioRepository usuarioRepository;
    @Autowired private ObjectMapper objectMapper;

    @Test
    void deveListarUsuarios() throws Exception {
        when(usuarioService.findAll()).thenReturn(List.of(new UsuarioDTO(1, "Nome", "teste@email.com", "senha", LocalDate.now())));

        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Nome"));
    }

    @Test
    void deveAtualizarUsuario() throws Exception {
        UsuarioDTO dto = new UsuarioDTO(1, "Nome Atualizado", "teste@email.com", "senha", LocalDate.now());
        when(usuarioService.update(any(), any())).thenReturn(Optional.of(dto));

        mockMvc.perform(put("/api/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Nome Atualizado"));
    }
}