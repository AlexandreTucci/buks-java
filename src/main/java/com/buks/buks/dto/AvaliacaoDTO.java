package com.buks.buks.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvaliacaoDTO {

    private Integer id;

    @NotNull(message = "O id do usuario precisa ser preenchido.")
    private Integer usuarioId;

    @NotNull(message = "O id do livro precisa ser preenchido.")
    private Integer livroId;

    @Positive(message = "A nota deve ser maior que zero.")
    private Integer nota;
}
