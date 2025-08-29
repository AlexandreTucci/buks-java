package com.buks.buks.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsuarioDTO {

    private Integer id;

    @NotNull(message = "O nome precisa ser preenchido.")
    @NotBlank(message = "O nome precisa ser preenchido.")
    private String nome;

    @Email
    @NotBlank
    private String email;


}