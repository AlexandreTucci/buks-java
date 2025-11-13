package com.buks.buks.service;

import com.buks.buks.dto.LivroDTO;
import com.buks.buks.model.Livro;
import com.buks.buks.repository.LivroRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LivroServiceTest {

    @Mock private LivroRepository livroRepository;
    @InjectMocks private LivroService livroService;

    @Test
    void deveSalvarLivro() {
        LivroDTO dto = new LivroDTO(null, "Java", "Desc", 50.0, 10);
        Livro livro = new Livro(1, "Java", "Desc", 50.0, 10);

        when(livroRepository.save(any(Livro.class))).thenReturn(livro);

        LivroDTO result = livroService.salvar(dto);
        assertEquals(1, result.getId());
    }

    @Test
    void deveBuscarPorId() {
        Livro livro = new Livro(1, "Java", "Desc", 50.0, 10);
        when(livroRepository.findById(1)).thenReturn(Optional.of(livro));

        Optional<LivroDTO> result = livroService.buscarPorId(1);
        assertTrue(result.isPresent());
    }
}