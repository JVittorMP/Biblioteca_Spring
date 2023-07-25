package com.course.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.course.biblioteca.domain.Emprestimo;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Integer> {
	
}
