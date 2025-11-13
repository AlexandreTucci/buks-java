package com.buks.buks.repository;

import com.buks.buks.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {
    // Método extra para verificar se já existe pagamento para um pedido (Regra 1:1)
    boolean existsByPedidoId(Integer pedidoId);
    Optional<Pagamento> findByPedidoId(Integer pedidoId);
}