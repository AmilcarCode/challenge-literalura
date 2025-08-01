package com.Oracle_One.LiterAluraApplication;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// DatosAutor.java
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutor(
		@JsonAlias("name") String nombre,
		@JsonAlias("birth_year") Integer nacimiento,
		@JsonAlias("death_year") Integer fallecimiento
) {}
