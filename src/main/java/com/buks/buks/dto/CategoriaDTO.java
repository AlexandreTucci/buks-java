package com.buks.buks.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTO {
    private Integer id; // Alterado de Long para Integer

    @NotBlank(message = "O nome da categoria é obrigatório")
    private String nome;

    private String descricao;
}