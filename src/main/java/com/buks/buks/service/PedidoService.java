package com.buks.buks.service;

import com.buks.buks.dto.PedidoDTO;
import com.buks.buks.model.Pedido;
import com.buks.buks.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public PedidoDTO salvar(PedidoDTO dto) {
        Pedido pedido = toEntity(dto);
        Pedido salvo = pedidoRepository.save(pedido);
        return toDTO(salvo);
    }

    public List<PedidoDTO> listarTodos() {
        return pedidoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    public Optional<PedidoDTO> buscarPorId(Integer id) {
        return pedidoRepository.findById(id)
                .map(this::toDTO);
    }

    public Optional<PedidoDTO> atualizar(Integer id, PedidoDTO dto) {
        return pedidoRepository.findById(id).map(existing -> {
            Pedido pedido = toEntity(dto);
            pedido.setId(id);
            Pedido atualizado = pedidoRepository.save(pedido);
            return toDTO(atualizado);
        });
    }

    public boolean deletar(Integer id) {
        if (pedidoRepository.existsById(id)) {
            pedidoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Conversões DTO ↔ Entidade
    private Pedido toEntity(PedidoDTO dto) {
        return new Pedido(
                dto.getId(),
                dto.getUsuarioId(),
                dto.getNomeDestinatario(),
                dto.getTelefone(),
                dto.getCep(),
                dto.getComplemento(),
                dto.getDataPedido()
        );
    }

    private PedidoDTO toDTO(Pedido pedido) {
        return new PedidoDTO(
                pedido.getId(),
                pedido.getUsuarioId(),
                pedido.getNomeDestinatario(),
                pedido.getTelefone(),
                pedido.getCep(),
                pedido.getComplemento(),
                pedido.getDataPedido()
        );
    }
}
