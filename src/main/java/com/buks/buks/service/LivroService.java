// src/main/java/com/buks/buks/service/LivroService.java
package com.buks.buks.service;

import com.buks.buks.dto.LivroDTO;
import com.buks.buks.exception.ResourceNotFoundException;
import com.buks.buks.model.Autor;
import com.buks.buks.model.Editora;
import com.buks.buks.model.Livro;
import com.buks.buks.repository.AutorRepository;
import com.buks.buks.repository.EditoraRepository;
import com.buks.buks.repository.LivroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class LivroService {
    private final LivroRepository livroRepository;
    private final EditoraRepository editoraRepository;
    private final AutorRepository autorRepository;

    public LivroService(LivroRepository livroRepository,
                        EditoraRepository editoraRepository,
                        AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.editoraRepository = editoraRepository;
        this.autorRepository = autorRepository;
    }

    public LivroDTO create(LivroDTO dto) {
        Livro l = dtoToEntity(dto);
        l = livroRepository.save(l);
        return entityToDto(l);
    }

    public LivroDTO update(Long id, LivroDTO dto) {
        Livro l = livroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado: " + id));
        l.setTitulo(dto.getTitulo());
        l.setIsbn(dto.getIsbn());
        l.setAno(dto.getAno());

        // editora
        if (dto.getEditoraId() != null) {
            Editora e = editoraRepository.findById(dto.getEditoraId())
                    .orElseThrow(() -> new ResourceNotFoundException("Editora não encontrada: " + dto.getEditoraId()));
            l.setEditora(e);
        } else {
            l.setEditora(null);
        }

        // autores
        if (dto.getAutorIds() != null) {
            Set<Autor> autores = dto.getAutorIds().stream()
                    .map(idAutor -> autorRepository.findById(idAutor)
                            .orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado: " + idAutor)))
                    .collect(Collectors.toSet());
            l.setAutores(autores);
        } else {
            l.getAutores().clear();
        }

        l = livroRepository.save(l);
        return entityToDto(l);
    }

    public LivroDTO getById(Long id) {
        return livroRepository.findById(id).map(this::entityToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado: " + id));
    }

    public List<LivroDTO> listAll() {
        return livroRepository.findAll().stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public void delete(Long id) {
        if (!livroRepository.existsById(id)) throw new ResourceNotFoundException("Livro não encontrado: " + id);
        livroRepository.deleteById(id);
    }

    // mappers
    private Livro dtoToEntity(LivroDTO dto) {
        Livro l = new Livro();
        l.setTitulo(dto.getTitulo());
        l.setIsbn(dto.getIsbn());
        l.setAno(dto.getAno());

        if (dto.getEditoraId() != null) {
            Editora e = editoraRepository.findById(dto.getEditoraId())
                    .orElseThrow(() -> new ResourceNotFoundException("Editora não encontrada: " + dto.getEditoraId()));
            l.setEditora(e);
        }

        if (dto.getAutorIds() != null) {
            Set<Autor> autores = dto.getAutorIds().stream()
                    .map(idAutor -> autorRepository.findById(idAutor)
                            .orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado: " + idAutor)))
                    .collect(Collectors.toSet());
            l.setAutores(autores);
        }
        return l;
    }

    private LivroDTO entityToDto(Livro l) {
        LivroDTO dto = new LivroDTO();
        dto.setId(l.getId());
        dto.setTitulo(l.getTitulo());
        dto.setIsbn(l.getIsbn());
        dto.setAno(l.getAno());
        if (l.getEditora() != null) {
            dto.setEditoraId(l.getEditora().getId());
            dto.setEditoraNome(l.getEditora().getNome());
        }
        if (l.getAutores() != null) {
            Set<Long> ids = l.getAutores().stream().map(Autor::getId).collect(Collectors.toSet());
            dto.setAutorIds(ids);
        }
        return dto;
    }
}
