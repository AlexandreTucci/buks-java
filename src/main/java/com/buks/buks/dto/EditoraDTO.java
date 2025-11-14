package com.buks.buks.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditoraDTO {

    private Integer id;

    @NotBlank(message = "O nome da editora é obrigatório.")
    private String nome;

    private String pais;
}
