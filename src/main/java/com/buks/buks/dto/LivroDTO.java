package com.buks.buks.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set; // Adicionado para a lista de IDs de relacionamento

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

    // Novos campos
    private Integer editoraId;

    private Integer anoPublicacao;

    // Relacionamentos N:M
    private Set<Long> autoresIds;
    private Set<Integer> categoriasIds; // Usando Integer para consistência com o ID da Categoria
}