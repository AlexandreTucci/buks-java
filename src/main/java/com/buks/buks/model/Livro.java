package com.buks.buks.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set; // Adicionado para coleções ManyToMany

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "editora_id") // Campo do DB Schema (1:N com Editora)
    private Integer editoraId;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private Double preco;

    @Column(nullable = false)
    private Integer estoque;

    @Column(name = "ano_publicacao") // Campo do DB Schema
    private Integer anoPublicacao;

    // Relacionamento N:M com Autores (via tabela livro_autor)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "livro_autor",
            joinColumns = @JoinColumn(name = "livro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private Set<Autor> autores;

    // Relacionamento N:M com Categorias (via tabela livro_categoria)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "livro_categoria",
            joinColumns = @JoinColumn(name = "livro_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private Set<Categoria> categorias;

    @OneToMany(mappedBy = "livro")
    private List<PedidoLivro> pedidos;
}