package com.buks.buks.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    // Campos apenas para serialização (resposta) — não aceitos no request
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double precoUnitario;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String nomeLivro;
}
