package com.buks.buks.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UsuarioDTO {

    private Integer id;

    @NotNull(message = "O nome precisa ser preenchido.")
    @NotBlank(message = "O nome precisa ser preenchido.")
    private String nome;

    @Email(message = "Email inválido")
    @NotBlank(message = "O email precisa ser preenchido.")
    private String email;

    @NotBlank(message = "A senha precisa ser preenchida.")
    private String senha;

    @NotNull(message = "A data de nascimento precisa ser preenchida.")
    @Past(message = "A data de nascimento deve ser no passado.")
    private LocalDate dataNascimento;
}
