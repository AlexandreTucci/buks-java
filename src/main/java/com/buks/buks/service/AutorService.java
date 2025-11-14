// src/main/java/com/buks/buks/service/AutorService.java
package com.buks.buks.service;

import com.buks.buks.dto.AutorDTO;
import com.buks.buks.exception.ResourceNotFoundException;
import com.buks.buks.model.Autor;
import com.buks.buks.repository.AutorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AutorService {
    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public AutorDTO create(AutorDTO dto) {
        Autor a = dtoToEntity(dto);
        a = autorRepository.save(a);
        return entityToDto(a);
    }

    public AutorDTO update(Long id, AutorDTO dto) {
        Autor a = autorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado: " + id));
        a.setNome(dto.getNome());
        a.setNacionalidade(dto.getNacionalidade());
        a = autorRepository.save(a);
        return entityToDto(a);
    }

    public AutorDTO getById(Long id) {
        return autorRepository.findById(id)
                .map(this::entityToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado: " + id));
    }

    public List<AutorDTO> listAll() {
        return autorRepository.findAll().stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public void delete(Long id) {
        if (!autorRepository.existsById(id)) throw new ResourceNotFoundException("Autor não encontrado: " + id);
        autorRepository.deleteById(id);
    }

    // mappers
    private Autor dtoToEntity(AutorDTO dto) {
        Autor a = new Autor();
        a.setNome(dto.getNome());
        a.setNacionalidade(dto.getNacionalidade());
        return a;
    }
    private AutorDTO entityToDto(Autor a) {
        AutorDTO dto = new AutorDTO();
        dto.setId(a.getId());
        dto.setNome(a.getNome());
        dto.setNacionalidade(a.getNacionalidade());
        return dto;
    }
}
