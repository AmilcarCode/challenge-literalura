package com.Oracle_One.LiterAluraApplication;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// 3. Repositorios (AutorRepository.java y LibroRepository.java)
// AutorRepository.java
public interface AutorRepository extends JpaRepository<Autor, Long> {
	List<Autor> findByNacimientoLessThanEqualAndFallecimientoGreaterThanEqual(Integer anio, Integer anio2);

	Optional<Autor> findByNombre(String nombre);
}
