package com.buks.buks.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutorDTO {

    private Long id;

    @NotBlank(message = "O nome do autor é obrigatório.")
    private String nome;

    private String nacionalidade;
}
