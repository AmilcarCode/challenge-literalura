package com.Oracle_One.LiterAluraApplication;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


// 6. Clase principal
@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {
	private final LiterAluraService literAluraService;

    public LiterAluraApplication(LiterAluraService literAluraService) {
        this.literAluraService = literAluraService;
    }


    public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) {
		literAluraService.mostrarMenu();
	}
}