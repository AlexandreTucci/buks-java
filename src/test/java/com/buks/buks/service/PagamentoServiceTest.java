package com.buks.buks.service;

import com.buks.buks.dto.PagamentoDTO;
import com.buks.buks.model.Pagamento;
import com.buks.buks.repository.PagamentoRepository;
import com.buks.buks.repository.PedidoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PagamentoServiceTest {

    @Mock private PagamentoRepository pagamentoRepository;
    @Mock private PedidoRepository pedidoRepository;
    @InjectMocks private PagamentoService pagamentoService;

    @Test
    void deveSalvarPagamento() {
        PagamentoDTO dto = new PagamentoDTO(null, 1, "PIX", 100.0);
        Pagamento pagamento = new Pagamento(1, 1, "PIX", 100.0, null);

        // Mocks necessários para passar pelas validações
        when(pedidoRepository.existsById(1)).thenReturn(true);
        when(pagamentoRepository.existsByPedidoId(1)).thenReturn(false);
        when(pagamentoRepository.save(any(Pagamento.class))).thenReturn(pagamento);

        PagamentoDTO result = pagamentoService.salvar(dto);
        assertEquals(1, result.getId());
    }
}