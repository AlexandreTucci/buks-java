package com.buks.buks.service;

import com.buks.buks.dto.EditoraDTO;
import com.buks.buks.model.Editora;
import com.buks.buks.repository.EditoraRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EditoraService {

    private final EditoraRepository repository;

    public EditoraService(EditoraRepository repository) {
        this.repository = repository;
    }

    public EditoraDTO salvar(EditoraDTO dto) {
        Editora editora = toEntity(dto);
        Editora salvo = repository.save(editora);
        return toDTO(salvo);
    }

    public List<EditoraDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<EditoraDTO> buscarPorId(Integer id) {
        return repository.findById(id)
                .map(this::toDTO);
    }

    public Optional<EditoraDTO> atualizar(Integer id, EditoraDTO dto) {
        return repository.findById(id).map(existing -> {
            Editora editora = toEntity(dto);
            editora.setId(id);
            Editora atualizado = repository.save(editora);
            return toDTO(atualizado);
        });
    }

    public boolean deletar(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    // Conversões DTO ↔ Entidade
    private Editora toEntity(EditoraDTO dto) {
        return new Editora(dto.getId(), dto.getNome(), dto.getPais());
    }

    private EditoraDTO toDTO(Editora editora) {
        return new EditoraDTO(editora.getId(), editora.getNome(), editora.getPais());
    }
}
