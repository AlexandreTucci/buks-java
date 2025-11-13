package com.buks.buks.controller;

import com.buks.buks.dto.PagamentoDTO;
import com.buks.buks.repository.UsuarioRepository;
import com.buks.buks.service.PagamentoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PagamentoController.class)
@AutoConfigureMockMvc(addFilters = false)
class PagamentoControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockitoBean private PagamentoService pagamentoService;
    @MockitoBean private UsuarioRepository usuarioRepository;
    @Autowired private ObjectMapper objectMapper;

    @Test
    void deveRealizarPagamento() throws Exception {
        PagamentoDTO dto = new PagamentoDTO(null, 1, "PIX", 50.0);
        when(pagamentoService.salvar(any())).thenReturn(dto);

        mockMvc.perform(post("/api/pagamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }
}