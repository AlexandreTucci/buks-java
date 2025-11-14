// src/main/java/com/buks/buks/dto/LivroDTO.java
package com.buks.buks.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.Set;

public class LivroDTO {
    private Long id;

    @NotBlank(message = "Título é obrigatório")
    private String titulo;

    private String isbn;

    private Integer ano;

    // id da editora (relacionamento simples)
    private Long editoraId;
    private String editoraNome;

    // ids dos autores
    private Set<Long> autorIds;

    // getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public Integer getAno() { return ano; }
    public void setAno(Integer ano) { this.ano = ano; }
    public Long getEditoraId() { return editoraId; }
    public void setEditoraId(Long editoraId) { this.editoraId = editoraId; }
    public String getEditoraNome() { return editoraNome; }
    public void setEditoraNome(String editoraNome) { this.editoraNome = editoraNome; }
    public Set<Long> getAutorIds() { return autorIds; }
    public void setAutorIds(Set<Long> autorIds) { this.autorIds = autorIds; }
}
