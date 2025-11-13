package com.buks.buks.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoDTO {

    private Integer id;

    @NotNull(message = "O ID do pedido é obrigatório.")
    private Integer pedidoId;

    @NotBlank(message = "O método de pagamento é obrigatório.")
    private String metodo;

    @NotNull(message = "O valor total é obrigatório.")
    @Positive(message = "O valor deve ser maior que zero.")
    private Double valorTotal;
}