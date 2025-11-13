package com.buks.buks.service;

import com.buks.buks.dto.AvaliacaoDTO;
import com.buks.buks.model.Avaliacao;
import com.buks.buks.repository.AvaliacaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AvaliacaoServiceTest {

    @Mock private AvaliacaoRepository avaliacaoRepository;
    @InjectMocks private AvaliacaoService avaliacaoService;

    @Test
    void deveSalvarAvaliacao() {
        AvaliacaoDTO dto = new AvaliacaoDTO(null, 1, 1, 5);
        Avaliacao avaliacao = new Avaliacao(1, 1, 1, 5);

        when(avaliacaoRepository.save(any(Avaliacao.class))).thenReturn(avaliacao);

        AvaliacaoDTO result = avaliacaoService.salvar(dto);
        assertEquals(5, result.getNota());
    }
}