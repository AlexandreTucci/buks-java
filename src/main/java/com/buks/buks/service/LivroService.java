package com.buks.buks.service;

import com.buks.buks.dto.LivroDTO;
import com.buks.buks.model.Autor;
import com.buks.buks.model.Categoria;
import com.buks.buks.model.Livro;
import com.buks.buks.repository.AutorRepository;
import com.buks.buks.repository.CategoriaRepository;
import com.buks.buks.repository.LivroRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository; // Novo
    private final CategoriaRepository categoriaRepository; // Novo

    public LivroService(LivroRepository livroRepository, AutorRepository autorRepository, CategoriaRepository categoriaRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
        this.categoriaRepository = categoriaRepository;
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
            // Novos campos
            existing.setEditoraId(dto.getEditoraId());
            existing.setAnoPublicacao(dto.getAnoPublicacao());

            // Atualiza relacionamentos N:M
            updateRelationships(existing, dto);

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

    // Método auxiliar para converter DTO -> Entity
    private Livro toEntity(LivroDTO dto) {
        Livro livro = new Livro();
        livro.setId(dto.getId());
        livro.setNome(dto.getNome());
        livro.setDescricao(dto.getDescricao());
        livro.setPreco(dto.getPreco());
        livro.setEstoque(dto.getEstoque());
        // Novos campos
        livro.setEditoraId(dto.getEditoraId());
        livro.setAnoPublicacao(dto.getAnoPublicacao());

        // Relacionamentos N:M
        updateRelationships(livro, dto);

        return livro;
    }

    // Método auxiliar para atualizar os relacionamentos N:M
    private void updateRelationships(Livro livro, LivroDTO dto) {
        // Autores
        if (dto.getAutoresIds() != null && !dto.getAutoresIds().isEmpty()) {
            Set<Autor> autores = new HashSet<>(autorRepository.findAllById(dto.getAutoresIds()));
            livro.setAutores(autores);
        } else {
            livro.setAutores(new HashSet<>());
        }

        // Categorias
        if (dto.getCategoriasIds() != null && !dto.getCategoriasIds().isEmpty()) {
            // CORRIGIDO: Passa diretamente o Set<Integer> para findAllById
            Set<Categoria> categorias = new HashSet<>(categoriaRepository.findAllById(dto.getCategoriasIds()));
            livro.setCategorias(categorias);
        } else {
            livro.setCategorias(new HashSet<>());
        }
    }

    // Método auxiliar para converter Entity -> DTO
    private LivroDTO toDTO(Livro livro) {
        LivroDTO dto = new LivroDTO();
        dto.setId(livro.getId());
        dto.setNome(livro.getNome());
        dto.setDescricao(livro.getDescricao());
        dto.setPreco(livro.getPreco());
        dto.setEstoque(livro.getEstoque());
        // Novos campos
        dto.setEditoraId(livro.getEditoraId());
        dto.setAnoPublicacao(livro.getAnoPublicacao());

        // Relacionamentos N:M (apenas IDs para o DTO)
        if (livro.getAutores() != null) {
            dto.setAutoresIds(livro.getAutores().stream().map(Autor::getId).collect(Collectors.toSet()));
        }
        if (livro.getCategorias() != null) {
            dto.setCategoriasIds(livro.getCategorias().stream().map(Categoria::getId).collect(Collectors.toSet()));
        }

        return dto;
    }
}