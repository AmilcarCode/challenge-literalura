package com.Oracle_One.LiterAluraApplication;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

// 4. Servicio para consumir la API (APIService.java)
@Service
public class APIService {
	private static final String API_URL = "https://gutendex.com/books/";
	private final HttpClient client = HttpClient.newHttpClient();
	private final ObjectMapper mapper = new ObjectMapper();

	public Optional<DatosLibro> buscarLibroPorTitulo(String titulo) {
		String url = API_URL + "?search=" + titulo.replace(" ", "%20");
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.build();

		try {
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			JsonNode root = mapper.readTree(response.body());
			JsonNode results = root.path("results");

			if (results.isArray() && results.size() > 0) {
				JsonNode firstBook = results.get(0);
				DatosLibro libro = mapper.treeToValue(firstBook, DatosLibro.class);
				return Optional.of(libro);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}
}
