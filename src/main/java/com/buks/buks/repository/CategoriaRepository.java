package com.buks.buks.repository;

import com.buks.buks.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> { // Alterado de Long para Integer
    Optional<Categoria> findByNome(String nome);
    boolean existsByNome(String nome);
}