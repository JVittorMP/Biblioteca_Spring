package com.course.biblioteca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.course.biblioteca.domain.Livro;

public interface LivroRepository extends JpaRepository<Livro, Integer> {
	@Query(value = "SELECT * FROM Livro WHERE Disponivel = true", nativeQuery = true)
	List<Livro> findAvailableLivros();
}
