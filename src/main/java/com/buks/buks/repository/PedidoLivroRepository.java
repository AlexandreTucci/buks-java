package com.buks.buks.repository;

import com.buks.buks.model.PedidoLivro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface PedidoLivroRepository extends JpaRepository<PedidoLivro, Integer> {
}
