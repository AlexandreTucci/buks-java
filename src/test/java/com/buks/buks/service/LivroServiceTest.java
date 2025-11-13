package com.buks.buks.service;

import com.buks.buks.dto.LivroDTO;
import com.buks.buks.model.Livro;
import com.buks.buks.repository.AutorRepository;     // Novo
import com.buks.buks.repository.CategoriaRepository; // Novo
import com.buks.buks.repository.LivroRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LivroServiceTest {

    @Mock private LivroRepository livroRepository;
    @Mock private AutorRepository autorRepository;         // Mock necessário
    @Mock private CategoriaRepository categoriaRepository; // Mock necessário

    @InjectMocks private LivroService livroService;

    @Test
    void deveSalvarLivro() {
        // Atualizando construtor do DTO
        LivroDTO dto = new LivroDTO(null, "Java", "Desc", 50.0, 10, null, 2025, null, null);

        // Atualizando construtor da Entidade (a ordem dos campos no @AllArgsConstructor depende da declaração na classe)
        // id, editoraId, nome, descricao, preco, estoque, anoPublicacao, autores, categorias, pedidos
        Livro livro = new Livro(
                1,
                null,
                "Java",
                "Desc",
                50.0,
                10,
                2025,
                new HashSet<>(),
                new HashSet<>(),
                new ArrayList<>()
        );

        when(livroRepository.save(any(Livro.class))).thenReturn(livro);

        LivroDTO result = livroService.salvar(dto);
        assertEquals(1, result.getId());
    }

    @Test
    void deveBuscarPorId() {
        Livro livro = new Livro(
                1, null, "Java", "Desc", 50.0, 10, 2025,
                new HashSet<>(), new HashSet<>(), new ArrayList<>()
        );

        when(livroRepository.findById(1)).thenReturn(Optional.of(livro));

        Optional<LivroDTO> result = livroService.buscarPorId(1);
        assertTrue(result.isPresent());
    }
}