package com.buks.buks.service;

import com.buks.buks.dto.CategoriaDTO;
import com.buks.buks.exception.BusinessException;
import com.buks.buks.exception.ErrorCode;
import com.buks.buks.model.Categoria;
import com.buks.buks.repository.CategoriaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional(readOnly = true)
    public List<CategoriaDTO> listarTodas() {
        return categoriaRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoriaDTO buscarPorId(Long id) {
        return categoriaRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new BusinessException(ErrorCode.CATEGORY_NOT_FOUND));
    }

    @Transactional
    public CategoriaDTO criar(CategoriaDTO dto) {
        if (categoriaRepository.existsByNome(dto.getNome())) {
            throw new BusinessException(ErrorCode.CATEGORY_NAME_EXISTS);
        }

        Categoria categoria = new Categoria();
        BeanUtils.copyProperties(dto, categoria);

        categoria = categoriaRepository.save(categoria);
        return toDTO(categoria);
    }

    @Transactional
    public CategoriaDTO atualizar(Long id, CategoriaDTO dto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.CATEGORY_NOT_FOUND));

        if (!categoria.getNome().equals(dto.getNome()) &&
            categoriaRepository.existsByNome(dto.getNome())) {
            throw new BusinessException(ErrorCode.CATEGORY_NAME_EXISTS);
        }

        BeanUtils.copyProperties(dto, categoria, "id");
        categoria = categoriaRepository.save(categoria);
        return toDTO(categoria);
    }

    @Transactional
    public void excluir(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new BusinessException(ErrorCode.CATEGORY_NOT_FOUND);
        }
        categoriaRepository.deleteById(id);
    }

    private CategoriaDTO toDTO(Categoria categoria) {
        CategoriaDTO dto = new CategoriaDTO();
        BeanUtils.copyProperties(categoria, dto);
        return dto;
    }
}
