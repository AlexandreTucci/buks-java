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
            Livro livro = toEntity(dto);
            livro.setId(id);
            Livro atualizado = livroRepository.save(livro);
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
        return new Livro(
                dto.getId(),
                dto.getNome(),
                dto.getDescricao(),
                dto.getPreco(),
                dto.getImagem()
        );
    }

    private LivroDTO toDTO(Livro livro) {
        return new LivroDTO(
                livro.getId(),
                livro.getNome(),
                livro.getDescricao(),
                livro.getPreco(),
                livro.getImagem()
        );
    }
}
