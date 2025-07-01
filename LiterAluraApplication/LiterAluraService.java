package com.Oracle_One.LiterAluraApplication;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

// 5. Servicio principal (LiterAluraService.java)
@Service
public class LiterAluraService {
	private final APIService apiService;
	private final AutorRepository autorRepository;
	private final LibroRepository libroRepository;

    public LiterAluraService(APIService apiService, AutorRepository autorRepository, LibroRepository libroRepository) {
        this.apiService = apiService;
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
    }

    public void guardarLibro(DatosLibro datosLibro) {
		// Obtener primer autor e idioma
		DatosAutor datosAutor = datosLibro.autores().get(0);
		String idioma = datosLibro.idiomas().get(0);

		// Buscar o crear autor
		Autor autor = autorRepository.findByNombre(datosAutor.nombre())
				.orElseGet(() -> {
					Autor nuevoAutor = new Autor();
					nuevoAutor.setNombre(datosAutor.nombre());
					nuevoAutor.setNacimiento(datosAutor.nacimiento());
					nuevoAutor.setFallecimiento(datosAutor.fallecimiento());
					return autorRepository.save(nuevoAutor);
				});

		// Crear libro
		Libro libro = new Libro();
		libro.setTitulo(datosLibro.titulo());
		libro.setIdioma(idioma);
		libro.setDescargas(datosLibro.descargas());
		libro.setAutor(autor);

		libroRepository.save(libro);
		autor.getLibros().add(libro);
	}

	public void mostrarMenu() {
		Scanner scanner = new Scanner(System.in);
		int opcion;

		do {
			System.out.println("\n=== LITERALURA - MENÚ PRINCIPAL ===");
			System.out.println("1. Buscar libro por título");
			System.out.println("2. Listar todos los libros");
			System.out.println("3. Listar todos los autores");
			System.out.println("4. Buscar autores vivos en un año");
			System.out.println("5. Mostrar libros por idioma");
			System.out.println("6. Mostrar estadísticas de idiomas");
			System.out.println("0. Salir");
			System.out.print("Seleccione una opción: ");

			opcion = scanner.nextInt();
			scanner.nextLine();  // Consumir el salto de línea


			switch (opcion) {
				case 1:
					buscarLibro(scanner);
					break;
				case 2:
					listarLibros();
					break;
				case 3:
					listarAutores();
					break;
				case 4:
					buscarAutoresVivos(scanner);
					break;
				case 5:
					listarPorIdioma(scanner);
					break;
				case 6:
					mostrarEstadisticasIdiomas();
					break;
				case 0:
					System.out.println("Saliendo del sistema...");
					System.exit(0);
					return;
				default:
					System.out.println("Opción inválida. Intente nuevamente.");
			}
		} while (true);
	}

	private void buscarLibro(Scanner scanner) {
		System.out.print("Ingrese el título del libro: ");
		String titulo = scanner.nextLine();

		Optional<DatosLibro> libroOptional = apiService.buscarLibroPorTitulo(titulo);

		if (libroOptional.isPresent()) {
			DatosLibro datosLibro = libroOptional.get();
			guardarLibro(datosLibro);
			System.out.println("\nLibro registrado exitosamente:");
			System.out.println("Título: " + datosLibro.titulo());
			System.out.println("Autor: " + datosLibro.autores().get(0).nombre());
			System.out.println("Idioma: " + datosLibro.idiomas().get(0));
			System.out.println("Descargas: " + datosLibro.descargas());
		} else {
			System.out.println("No se encontró ningún libro con ese título.");
		}
	}

	private void listarLibros() {
		List<Libro> libros = libroRepository.findAll();

		if (libros.isEmpty()) {
			System.out.println("No hay libros registrados en el sistema.");
			return;
		}

		System.out.println("\n=== LISTADO DE LIBROS ===");
		libros.forEach(libro -> {
			System.out.println("Título: " + libro.getTitulo());
			System.out.println("Autor: " + libro.getAutor().getNombre());
			System.out.println("Idioma: " + libro.getIdioma());
			System.out.println("Descargas: " + libro.getDescargas());
			System.out.println("-----------------------");
		});
	}

	private void listarAutores() {
		List<Autor> autores = autorRepository.findAll();

		if (autores.isEmpty()) {
			System.out.println("No hay autores registrados en el sistema.");
			return;
		}

		System.out.println("\n=== LISTADO DE AUTORES ===");
		autores.forEach(autor -> {
			System.out.println("Nombre: " + autor.getNombre());
			System.out.println("Nacimiento: " + autor.getNacimiento());
			System.out.println("Fallecimiento: " + autor.getFallecimiento());
			System.out.println("Libros: " + autor.getLibros().size());
			System.out.println("-----------------------");
		});
	}

	private void buscarAutoresVivos(Scanner scanner) {
		try {
			System.out.print("Ingrese el año: ");
			int anio = scanner.nextInt();
			scanner.nextLine(); // limpiar buffer

			List<Autor> autores = autorRepository.findByNacimientoLessThanEqualAndFallecimientoGreaterThanEqual(anio, anio);

			if (autores.isEmpty()) {
				System.out.println("No se encontraron autores vivos en ese año.");
				return;
			}

			System.out.println("\n=== AUTORES VIVOS EN " + anio + " ===");
			autores.forEach(autor -> {
				System.out.println("Nombre: " + autor.getNombre());
				System.out.println("Vida: " + autor.getNacimiento() + " - " + autor.getFallecimiento());
				System.out.println("-----------------------");
			});

		} catch (InputMismatchException e) {
			System.out.println("Debe ingresar un año válido.");
			scanner.nextLine(); // limpiar buffer para evitar bucles
		}
	}

	private void listarPorIdioma(Scanner scanner) {
		String idioma;

		while (true) {
			System.out.print("Ingrese el idioma (código de 2 letras): ");
			idioma = scanner.nextLine().trim().toLowerCase();

			if (idioma.length() == 2 && idioma.matches("[a-z]{2}")) {
				break; // válido, salimos del while
			}

			System.out.println("Debe ingresar exactamente 2 letras para el código de idioma.");
		}

		List<Libro> libros = libroRepository.findByIdioma(idioma);

		if (libros.isEmpty()) {
			System.out.println("No se encontraron libros en ese idioma.");
			return;
		}

		System.out.println("\n=== LIBROS EN " + idioma.toUpperCase() + " ===");
		libros.forEach(libro -> {
			System.out.println("Título: " + libro.getTitulo());
			System.out.println("Autor: " + libro.getAutor().getNombre());
			System.out.println("Descargas: " + libro.getDescargas());
			System.out.println("-----------------------");
		});
	}

	private void mostrarEstadisticasIdiomas() {
		List<Libro> todosLibros = libroRepository.findAll();

		if (todosLibros.isEmpty()) {
			System.out.println("No hay libros registrados para mostrar estadísticas.");
			return;
		}

		Map<String, Long> conteoIdiomas = todosLibros.stream()
				.collect(Collectors.groupingBy(Libro::getIdioma, Collectors.counting()));

		System.out.println("\n=== ESTADÍSTICAS POR IDIOMA ===");
		conteoIdiomas.forEach((idioma, cantidad) ->
				System.out.println(idioma.toUpperCase() + ": " + cantidad + " libros"));
	}
}
