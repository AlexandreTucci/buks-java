package com.buks.buks.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", nullable = false)
    private Integer usuarioId;

    @Column(name = "nome_destinatario", nullable = false, length = 100)
    private String nomeDestinatario;

    private String telefone;

    private String cep;

    private String complemento;

    @Column(name = "data_pedido", nullable = false)
    private LocalDate dataPedido;
}
