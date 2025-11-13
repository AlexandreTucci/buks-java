package com.buks.buks.service;

import com.buks.buks.dto.AutorDTO;
import com.buks.buks.exception.BusinessException;
import com.buks.buks.exception.ErrorCode;
import com.buks.buks.model.Autor;
import com.buks.buks.repository.AutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public AutorDTO salvar(AutorDTO dto) {
        Autor autor = toEntity(dto);
        Autor salvo = autorRepository.save(autor);
        return toDTO(salvo);
    }

    public List<AutorDTO> listarTodos() {
        return autorRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public AutorDTO buscarPorId(Long id) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.AUTOR_NOT_FOUND));
        return toDTO(autor);
    }

    public AutorDTO atualizar(Long id, AutorDTO dto) {
        Autor existente = autorRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.AUTOR_NOT_FOUND));

        existente.setNome(dto.getNome());
        existente.setNacionalidade(dto.getNacionalidade());

        Autor atualizado = autorRepository.save(existente);
        return toDTO(atualizado);
    }

    public void deletar(Long id) {
        if (!autorRepository.existsById(id)) {
            throw new BusinessException(ErrorCode.AUTOR_NOT_FOUND);
        }
        autorRepository.deleteById(id);
    }

    // Conversões DTO ↔ Entidade
    private Autor toEntity(AutorDTO dto) {
        Autor autor = new Autor();
        autor.setId(dto.getId());
        autor.setNome(dto.getNome());
        autor.setNacionalidade(dto.getNacionalidade());
        return autor;
    }

    private AutorDTO toDTO(Autor autor) {
        return new AutorDTO(autor.getId(), autor.getNome(), autor.getNacionalidade());
    }
}
