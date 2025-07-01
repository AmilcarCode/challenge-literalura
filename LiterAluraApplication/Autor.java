package com.Oracle_One.LiterAluraApplication;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


// Autor.java
@Entity
public class Autor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	private Integer nacimiento;
	private Integer fallecimiento;

	@OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Libro> libros = new ArrayList<>();

	// Constructores, getters y setters

	public Autor() {}

	public List<Libro> getLibros() {
		return libros;
	}

	public void setLibros(List<Libro> libros) {
		this.libros = libros;
	}

	public Integer getNacimiento() {
		return nacimiento;
	}

	public void setNacimiento(Integer nacimiento) {
		this.nacimiento = nacimiento;
	}

	public Integer getFallecimiento() {
		return fallecimiento;
	}

	public void setFallecimiento(Integer fallecimiento) {
		this.fallecimiento = fallecimiento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
