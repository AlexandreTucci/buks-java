package com.buks.buks.service;

import com.buks.buks.dto.AvaliacaoDTO;
import com.buks.buks.model.Avaliacao;
import com.buks.buks.repository.AvaliacaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
    }

    // --- CRUD ---

    public AvaliacaoDTO salvar(AvaliacaoDTO dto) {
        Avaliacao avaliacao = toEntity(dto);
        Avaliacao salvo = avaliacaoRepository.save(avaliacao);
        return toDTO(salvo);
    }

    public List<AvaliacaoDTO> listarTodas() {
        return avaliacaoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<AvaliacaoDTO> buscarPorId(Integer id) {
        return avaliacaoRepository.findById(id)
                .map(this::toDTO);
    }

    public Optional<AvaliacaoDTO> atualizar(Integer id, AvaliacaoDTO dto) {
        return avaliacaoRepository.findById(id).map(existing -> {
            Avaliacao avaliacao = toEntity(dto);
            avaliacao.setId(id);
            Avaliacao atualizado = avaliacaoRepository.save(avaliacao);
            return toDTO(atualizado);
        });
    }

    public boolean deletar(Integer id) {
        if (avaliacaoRepository.existsById(id)) {
            avaliacaoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // --- Conversões DTO ↔ Entidade ---
    private Avaliacao toEntity(AvaliacaoDTO dto) {
        return new Avaliacao(
                dto.getId(),
                dto.getUsuarioId(),
                dto.getLivroId(),
                dto.getNota()
        );
    }

    private AvaliacaoDTO toDTO(Avaliacao avaliacao) {
        return new AvaliacaoDTO(
                avaliacao.getId(),
                avaliacao.getUsuarioId(),
                avaliacao.getLivroId(),
                avaliacao.getNota()
        );
    }
}
