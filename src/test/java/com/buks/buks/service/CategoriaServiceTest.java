package com.buks.buks.service;

import com.buks.buks.dto.CategoriaDTO;
import com.buks.buks.model.Categoria;
import com.buks.buks.repository.CategoriaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @Mock private CategoriaRepository categoriaRepository;
    @InjectMocks private CategoriaService categoriaService;

    @Test
    void deveCriarCategoria() {
        CategoriaDTO dto = new CategoriaDTO(null, "Terror", "Livros de terror");
        Categoria categoria = new Categoria(1L, "Terror", "Livros de terror");

        when(categoriaRepository.existsByNome("Terror")).thenReturn(false);
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);

        CategoriaDTO result = categoriaService.criar(dto);
        assertEquals("Terror", result.getNome());
    }
}