package com.buks.buks.service;

import com.buks.buks.dto.PagamentoDTO;
import com.buks.buks.exception.BusinessException;
import com.buks.buks.exception.ErrorCode;
import com.buks.buks.model.Pagamento;
import com.buks.buks.repository.PagamentoRepository;
import com.buks.buks.repository.PedidoRepository; // Integração com CRUD de pedido
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final PedidoRepository pedidoRepository;

    public PagamentoService(PagamentoRepository pagamentoRepository, PedidoRepository pedidoRepository) {
        this.pagamentoRepository = pagamentoRepository;
        this.pedidoRepository = pedidoRepository;
    }

    public PagamentoDTO salvar(PagamentoDTO dto) {
        // 1. Integração: Valida se o pedido existe
        if (!pedidoRepository.existsById(dto.getPedidoId())) {
            throw new BusinessException(ErrorCode.ORDER_NOT_FOUND);
        }

        // 2. Regra de Negócio: Verifica se o pedido já foi pago (Relação 1:1)
        if (pagamentoRepository.existsByPedidoId(dto.getPedidoId())) {
            throw new BusinessException(ErrorCode.ORDER_ALREADY_PAID);
        }

        Pagamento pagamento = toEntity(dto);
        Pagamento salvo = pagamentoRepository.save(pagamento);
        return toDTO(salvo);
    }

    public List<PagamentoDTO> listarTodos() {
        return pagamentoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<PagamentoDTO> buscarPorId(Integer id) {
        return pagamentoRepository.findById(id)
                .map(this::toDTO);
    }

    // Busca o pagamento pelo ID do pedido (útil para o front-end)
    public Optional<PagamentoDTO> buscarPorIdDoPedido(Integer pedidoId) {
        return pagamentoRepository.findByPedidoId(pedidoId)
                .map(this::toDTO);
    }

    public Optional<PagamentoDTO> atualizar(Integer id, PagamentoDTO dto) {
        return pagamentoRepository.findById(id).map(existing -> {
            // Não permitimos alterar o ID do pedido num pagamento já feito por segurança
            existing.setMetodo(dto.getMetodo());
            existing.setValorTotal(dto.getValorTotal());

            Pagamento atualizado = pagamentoRepository.save(existing);
            return toDTO(atualizado);
        });
    }

    public boolean deletar(Integer id) {
        if (pagamentoRepository.existsById(id)) {
            pagamentoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // --- Conversões ---
    private Pagamento toEntity(PagamentoDTO dto) {
        Pagamento p = new Pagamento();
        p.setId(dto.getId());
        p.setPedidoId(dto.getPedidoId());
        p.setMetodo(dto.getMetodo());
        p.setValorTotal(dto.getValorTotal());
        return p;
    }

    private PagamentoDTO toDTO(Pagamento entity) {
        return new PagamentoDTO(
                entity.getId(),
                entity.getPedidoId(),
                entity.getMetodo(),
                entity.getValorTotal()
        );
    }
}