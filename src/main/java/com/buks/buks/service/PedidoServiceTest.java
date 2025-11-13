package com.buks.buks.service;

import com.buks.buks.dto.PedidoDTO;
import com.buks.buks.model.Pedido;
import com.buks.buks.repository.PedidoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock private PedidoRepository pedidoRepository;
    @InjectMocks private PedidoService pedidoService;

    @Test
    void deveSalvarPedido() {
        PedidoDTO dto = new PedidoDTO(null, 1, "Destinatario", "123", "00000", "Casa", LocalDate.now());
        Pedido pedido = new Pedido(1, 1, "Destinatario", "123", "00000", "Casa", LocalDate.now());

        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);

        PedidoDTO result = pedidoService.salvar(dto);
        assertNotNull(result.getId());
    }
}