package com.buks.buks.service;

import com.buks.buks.dto.PedidoDTO;
import com.buks.buks.dto.PedidoLivroDTO;
import com.buks.buks.model.Livro;
import com.buks.buks.model.Pedido;
import com.buks.buks.model.PedidoLivro;
import com.buks.buks.repository.LivroRepository;
import com.buks.buks.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final LivroRepository livroRepository;

    public PedidoService(PedidoRepository pedidoRepository, LivroRepository livroRepository) {
        this.pedidoRepository = pedidoRepository;
        this.livroRepository = livroRepository;
    }

    public PedidoDTO salvar(PedidoDTO dto) {
        Pedido pedido = toEntity(dto);
        // tratar itens: converter DTO -> PedidoLivro e associar ao pedido
        if (dto.getItens() != null) {
            List<PedidoLivro> itens = dto.getItens().stream().map(i -> {
                Livro livro = livroRepository.findById(i.getLivroId()).orElseThrow(() -> new RuntimeException("Livro não encontrado: " + i.getLivroId()));
                PedidoLivro pl = new PedidoLivro();
                pl.setLivro(livro);
                pl.setQuantidade(i.getQuantidade());
                pl.setPrecoUnitario(livro.getPreco());
                pl.setPedido(pedido);
                return pl;
            }).collect(Collectors.toList());
            pedido.setItens(itens);
        }

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
            // atualizar campos simples no existing (entidade gerenciada)
            existing.setUsuarioId(dto.getUsuarioId());
            existing.setNomeDestinatario(dto.getNomeDestinatario());
            existing.setTelefone(dto.getTelefone());
            existing.setCep(dto.getCep());
            existing.setComplemento(dto.getComplemento());
            existing.setDataPedido(dto.getDataPedido());

            // atualizar itens: limpar e recriar (orphanRemoval cuidará da remoção)
            if (existing.getItens() != null) {
                existing.getItens().clear();
            }

            if (dto.getItens() != null) {
                List<PedidoLivro> itens = dto.getItens().stream().map(i -> {
                    Livro livro = livroRepository.findById(i.getLivroId()).orElseThrow(() -> new RuntimeException("Livro não encontrado: " + i.getLivroId()));
                    PedidoLivro pl = new PedidoLivro();
                    pl.setLivro(livro);
                    pl.setQuantidade(i.getQuantidade());
                    pl.setPrecoUnitario(livro.getPreco());
                    pl.setPedido(existing);
                    return pl;
                }).collect(Collectors.toList());
                existing.setItens(itens);
            }

            Pedido atualizado = pedidoRepository.save(existing);
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
        Pedido pedido = new Pedido();
        pedido.setId(dto.getId());
        pedido.setUsuarioId(dto.getUsuarioId());
        pedido.setNomeDestinatario(dto.getNomeDestinatario());
        pedido.setTelefone(dto.getTelefone());
        pedido.setCep(dto.getCep());
        pedido.setComplemento(dto.getComplemento());
        pedido.setDataPedido(dto.getDataPedido());
        // itens serão setados no método salvar/atualizar
        return pedido;
    }

    private PedidoDTO toDTO(Pedido pedido) {
        PedidoDTO dto = new PedidoDTO();
        dto.setId(pedido.getId());
        dto.setUsuarioId(pedido.getUsuarioId());
        dto.setNomeDestinatario(pedido.getNomeDestinatario());
        dto.setTelefone(pedido.getTelefone());
        dto.setCep(pedido.getCep());
        dto.setComplemento(pedido.getComplemento());
        dto.setDataPedido(pedido.getDataPedido());

        List<PedidoLivroDTO> itens = new ArrayList<>();
        if (pedido.getItens() != null) {
            itens = pedido.getItens().stream().map(pl -> {
                PedidoLivroDTO pld = new PedidoLivroDTO();
                pld.setLivroId(pl.getLivro().getId());
                pld.setQuantidade(pl.getQuantidade());
                pld.setPrecoUnitario(pl.getPrecoUnitario());
                pld.setNomeLivro(pl.getLivro().getNome());
                return pld;
            }).collect(Collectors.toList());
        }
        dto.setItens(itens);

        return dto;
    }
}
