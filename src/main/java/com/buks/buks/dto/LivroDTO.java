package com.buks.buks.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LivroDTO {

    private Integer id;

    @NotBlank(message = "O nome do livro precisa ser preenchido.")
    private String nome;

    @NotBlank(message = "A descrição precisa ser preenchida.")
    private String descricao;

    @NotNull(message = "O preço precisa ser informado.")
    @Positive(message = "O preço deve ser maior que zero.")
    private Double preco;

    private Integer estoque;
}
