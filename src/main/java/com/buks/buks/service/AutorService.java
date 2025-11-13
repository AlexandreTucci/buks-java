package com.buks.buks.service;

import com.buks.buks.dto.AutorDTO;
import com.buks.buks.exception.BusinessException;
import com.buks.buks.exception.ErrorCode;
import com.buks.buks.model.Autor;
import com.buks.buks.repository.AutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
                .orElseThrow(() -> new BusinessException(ErrorCode.AUTHOR_NOT_FOUND));
        return toDTO(autor);
    }

    public AutorDTO atualizar(Long id, AutorDTO dto) {
        Autor autorExistente = autorRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.AUTHOR_NOT_FOUND));

        autorExistente.setNome(dto.getNome());
        autorExistente.setNacionalidade(dto.getNacionalidade());

        Autor atualizado = autorRepository.save(autorExistente);
        return toDTO(atualizado);
    }

    public void deletar(Long id) {
        if (!autorRepository.existsById(id)) {
            throw new BusinessException(ErrorCode.AUTHOR_NOT_FOUND);
        }
        autorRepository.deleteById(id);
    }

    // Conversões
    private Autor toEntity(AutorDTO dto) {
        return new Autor(dto.getId(), dto.getNome(), dto.getNacionalidade());
    }

    private AutorDTO toDTO(Autor autor) {
        return new AutorDTO(autor.getId(), autor.getNome(), autor.getNacionalidade());
    }
}
