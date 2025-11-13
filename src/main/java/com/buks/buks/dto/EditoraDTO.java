package com.buks.buks.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditoraDTO {

    private Long id;

    @NotBlank(message = "O nome da editora é obrigatório.")
    private String nome;

    @NotBlank(message = "O país é obrigatório.")
    private String pais;

    @NotBlank(message = "O site é obrigatório.")
    private String site;
}
