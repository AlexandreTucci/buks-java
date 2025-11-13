package com.buks.buks.controller;

import com.buks.buks.dto.PedidoDTO;
import com.buks.buks.dto.PedidoLivroDTO;
import com.buks.buks.repository.UsuarioRepository;
import com.buks.buks.service.PedidoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PedidoController.class)
@AutoConfigureMockMvc(addFilters = false)
class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PedidoService pedidoService;

    @MockitoBean
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCriarPedido() throws Exception {
        // Cria um item para compor a lista (necessário para validação)
        PedidoLivroDTO item = new PedidoLivroDTO(1, 2, null, null);

        // Construtor atualizado com a lista de itens no final
        PedidoDTO dto = new PedidoDTO(
                null,
                1,
                "Dest",
                "999",
                "00000000",
                "Comp",
                LocalDate.now(),
                List.of(item) // <--- Correção aqui
        );

        when(pedidoService.salvar(any())).thenReturn(dto);

        mockMvc.perform(post("/api/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }
}