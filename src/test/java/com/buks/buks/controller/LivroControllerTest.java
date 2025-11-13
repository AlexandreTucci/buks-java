package com.buks.buks.controller;

import com.buks.buks.dto.LivroDTO;
import com.buks.buks.repository.UsuarioRepository;
import com.buks.buks.service.LivroService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet; // Importante

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LivroController.class)
@AutoConfigureMockMvc(addFilters = false)
class LivroControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockitoBean private LivroService livroService;
    @MockitoBean private UsuarioRepository usuarioRepository;
    @Autowired private ObjectMapper objectMapper;

    @Test
    void deveCriarLivro() throws Exception {
        // CORREÇÃO: O construtor agora exige todos os campos novos.
        // Ordem: id, nome, descricao, preco, estoque, editoraId, anoPublicacao, autoresIds, categoriasIds
        LivroDTO dto = new LivroDTO(
                null,
                "Java Completo",
                "Livro de Java",
                50.0,
                10,
                null,
                2025,
                new HashSet<>(),
                new HashSet<>()
        );

        LivroDTO salvo = new LivroDTO(
                1,
                "Java Completo",
                "Livro de Java",
                50.0,
                10,
                null,
                2025,
                new HashSet<>(),
                new HashSet<>()
        );

        when(livroService.salvar(any())).thenReturn(salvo);

        mockMvc.perform(post("/api/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }
}