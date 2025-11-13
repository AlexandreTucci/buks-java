package com.buks.buks.service;

import com.buks.buks.dto.LivroDTO;
import com.buks.buks.model.Livro;
import com.buks.buks.repository.LivroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LivroService {

    private final LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public LivroDTO salvar(LivroDTO dto) {
        Livro livro = toEntity(dto);
        Livro salvo = livroRepository.save(livro);
        return toDTO(salvo);
    }

    public List<LivroDTO> listarTodos() {
        return livroRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    public Optional<LivroDTO> buscarPorId(Integer id) {
        return livroRepository.findById(id)
                .map(this::toDTO);
    }

    public Optional<LivroDTO> atualizar(Integer id, LivroDTO dto) {
        return livroRepository.findById(id).map(existing -> {
            // atualiza a entidade gerenciada (existing)
            existing.setNome(dto.getNome());
            existing.setDescricao(dto.getDescricao());
            existing.setPreco(dto.getPreco());
            existing.setEstoque(dto.getEstoque());
            Livro atualizado = livroRepository.save(existing);
            return toDTO(atualizado);
        });
    }

    public boolean deletar(Integer id) {
        if (livroRepository.existsById(id)) {
            livroRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // --- Conversões DTO ↔ Entidade ---
    private Livro toEntity(LivroDTO dto) {
        Livro livro = new Livro();
        livro.setId(dto.getId());
        livro.setNome(dto.getNome());
        livro.setDescricao(dto.getDescricao());
        livro.setPreco(dto.getPreco());
        livro.setEstoque(dto.getEstoque());
        return livro;
    }

    private LivroDTO toDTO(Livro livro) {
        LivroDTO dto = new LivroDTO();
        dto.setId(livro.getId());
        dto.setNome(livro.getNome());
        dto.setDescricao(livro.getDescricao());
        dto.setPreco(livro.getPreco());
        dto.setEstoque(livro.getEstoque());
        return dto;
    }
}
