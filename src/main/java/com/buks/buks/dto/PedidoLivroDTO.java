package com.buks.buks.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoLivroDTO {

    @NotNull(message = "O ID do livro é obrigatório.")
    private Integer livroId;

    @NotNull(message = "A quantidade é obrigatória.")
    private Integer quantidade;

    // Campos adicionais para resposta
    private Double precoUnitario;
    private String nomeLivro;
}
