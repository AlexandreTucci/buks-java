// src/main/java/com/buks/buks/service/EditoraService.java
package com.buks.buks.service;

import com.buks.buks.dto.EditoraDTO;
import com.buks.buks.exception.ResourceNotFoundException;
import com.buks.buks.model.Editora;
import com.buks.buks.repository.EditoraRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EditoraService {
    private final EditoraRepository editoraRepository;

    public EditoraService(EditoraRepository editoraRepository) {
        this.editoraRepository = editoraRepository;
    }

    public EditoraDTO create(EditoraDTO dto) {
        Editora e = dtoToEntity(dto);
        e = editoraRepository.save(e);
        return entityToDto(e);
    }

    public EditoraDTO update(Long id, EditoraDTO dto) {
        Editora e = editoraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Editora não encontrada: " + id));
        e.setNome(dto.getNome());
        e.setPais(dto.getPais());
        e = editoraRepository.save(e);
        return entityToDto(e);
    }

    public EditoraDTO getById(Long id) {
        return editoraRepository.findById(id)
                .map(this::entityToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Editora não encontrada: " + id));
    }

    public List<EditoraDTO> listAll() {
        return editoraRepository.findAll().stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public void delete(Long id) {
        if (!editoraRepository.existsById(id)) throw new ResourceNotFoundException("Editora não encontrada: " + id);
        editoraRepository.deleteById(id);
    }

    // mappers simples
    private Editora dtoToEntity(EditoraDTO dto) {
        Editora e = new Editora();
        e.setNome(dto.getNome());
        e.setPais(dto.getPais());
        return e;
    }

    private EditoraDTO entityToDto(Editora e) {
        EditoraDTO dto = new EditoraDTO();
        dto.setId(e.getId());
        dto.setNome(e.getNome());
        dto.setPais(e.getPais());
        return dto;
    }
}
