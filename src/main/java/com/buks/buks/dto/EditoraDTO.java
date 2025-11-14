// src/main/java/com/buks/buks/dto/EditoraDTO.java
package com.buks.buks.dto;

import jakarta.validation.constraints.NotBlank;

public class EditoraDTO {
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    private String pais;

    // getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }
}
