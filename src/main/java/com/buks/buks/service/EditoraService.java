package com.buks.buks.service;

import com.buks.buks.dto.EditoraDTO;
import com.buks.buks.exception.BusinessException;
import com.buks.buks.exception.ErrorCode;
import com.buks.buks.model.Editora;
import com.buks.buks.repository.EditoraRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EditoraService {

    private final EditoraRepository editoraRepository;

    public EditoraService(EditoraRepository editoraRepository) {
        this.editoraRepository = editoraRepository;
    }

    // CREATE
    public EditoraDTO salvar(EditoraDTO dto) {
        Editora editora = toEntity(dto);
        Editora salvo = editoraRepository.save(editora);
        return toDTO(salvo);
    }

    // READ
    public List<EditoraDTO> listarTodas() {
        return editoraRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public EditoraDTO buscarPorId(Long id) {
        Editora editora = editoraRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.EDITORA_NOT_FOUND));
        return toDTO(editora);
    }

    // UPDATE
    public EditoraDTO atualizar(Long id, EditoraDTO dto) {
        Editora existente = editoraRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.EDITORA_NOT_FOUND));

        existente.setNome(dto.getNome());
        existente.setPais(dto.getPais());
        existente.setSite(dto.getSite());

        Editora atualizado = editoraRepository.save(existente);
        return toDTO(atualizado);
    }

    // DELETE
    public void deletar(Long id) {
        if (!editoraRepository.existsById(id)) {
            throw new BusinessException(ErrorCode.EDITORA_NOT_FOUND);
        }
        editoraRepository.deleteById(id);
    }

    // ----------- DTO <-> ENTITY ------------

    private Editora toEntity(EditoraDTO dto) {
        return new Editora(
                dto.getId(),
                dto.getNome(),
                dto.getPais(),
                dto.getSite()
        );
    }

    private EditoraDTO toDTO(Editora editora) {
        return new EditoraDTO(
                editora.getId(),
                editora.getNome(),
                editora.getPais(),
                editora.getSite()
        );
    }
}
