// src/main/java/com/buks/buks/repository/AutorRepository.java
package com.buks.buks.repository;

import com.buks.buks.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AutorRepository extends JpaRepository<Autor, Long> { }
