// src/main/java/com/buks/buks/dto/AutorDTO.java
package com.buks.buks.dto;

import jakarta.validation.constraints.NotBlank;

public class AutorDTO {
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    private String nacionalidade;

    // getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getNacionalidade() { return nacionalidade; }
    public void setNacionalidade(String nacionalidade) { this.nacionalidade = nacionalidade; }
}
