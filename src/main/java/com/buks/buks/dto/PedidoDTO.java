package com.buks.buks.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {

    private Integer id;

    @NotNull(message = "O ID do usuário é obrigatório.")
    private Integer usuarioId;

    @NotBlank(message = "O nome do destinatário é obrigatório.")
    private String nomeDestinatario;

    private String telefone;

    private String cep;

    private String complemento;

    @NotNull(message = "A data do pedido é obrigatória.")
    private LocalDate dataPedido;

    // NOVO: lista de livros do pedido
    @NotNull(message = "A lista de itens é obrigatória.")
    private List<PedidoLivroDTO> itens;
}
