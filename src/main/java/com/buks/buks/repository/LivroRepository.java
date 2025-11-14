// src/main/java/com/buks/buks/repository/LivroRepository.java
package com.buks.buks.repository;

import com.buks.buks.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
public interface LivroRepository extends JpaRepository<Livro, Long> { }
