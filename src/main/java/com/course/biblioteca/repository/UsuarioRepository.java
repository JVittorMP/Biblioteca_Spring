package com.course.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.course.biblioteca.domain.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	Usuario findByEmail(String email);
}
