package com.Oracle_One.LiterAluraApplication;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

// 2. DTOs para la API (DatosLibro.java y DatosAutor.java)
// DatosLibro.java
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
		@JsonAlias("title") String titulo,
		@JsonAlias("authors") List<DatosAutor> autores,
		@JsonAlias("languages") List<String> idiomas,
		@JsonAlias("download_count") Integer descargas
) {}
