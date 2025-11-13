package com.buks.buks.controller;

import com.buks.buks.dto.CategoriaDTO;
import com.buks.buks.repository.UsuarioRepository;
import com.buks.buks.service.CategoriaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoriaController.class)
@AutoConfigureMockMvc(addFilters = false)
class CategoriaControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockitoBean private CategoriaService categoriaService;
    @MockitoBean private UsuarioRepository usuarioRepository;
    @Autowired private ObjectMapper objectMapper;

    @Test
    void deveListarCategorias() throws Exception {
        when(categoriaService.listarTodas()).thenReturn(List.of(new CategoriaDTO(1, "Terror", "Desc")));

        mockMvc.perform(get("/api/categorias"))
                .andExpect(status().isOk());
    }

    @Test
    void deveCriarCategoria() throws Exception {
        CategoriaDTO dto = new CategoriaDTO(null, "Romance", "Desc");
        when(categoriaService.criar(any())).thenReturn(dto);

        mockMvc.perform(post("/api/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }
}