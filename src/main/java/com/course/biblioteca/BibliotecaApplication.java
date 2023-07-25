package com.course.biblioteca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BibliotecaApplication {

	public static void main(String[] args) {
		System.setProperty("server.servlet.context-path", "/biblioteca");
		SpringApplication.run(BibliotecaApplication.class, args);
	}

}
