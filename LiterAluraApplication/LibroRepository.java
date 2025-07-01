package com.Oracle_One.LiterAluraApplication;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// LibroRepository.java
public interface LibroRepository extends JpaRepository<Libro, Long> {
	List<Libro> findByIdioma(String idioma);
}
