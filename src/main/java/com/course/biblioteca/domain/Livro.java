package com.course.biblioteca.domain;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Livro {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 100, nullable = false)
	@NotBlank(message = "Campo de Título Incompleto")
	private String titulo;
	
	@Column(length = 100, nullable = false)
	@NotBlank(message = "Campo de Autor Incompleto")
	private String autor;
	
	@Column(precision = 4, scale = 0, nullable = false)
	@NotNull(message = "Campo de Ano de publicação Incompleto")
	private Integer anoPublicacao;
	
	@Column
	@NotNull(message = "Disponibilidade Pré-Definida")
	private Boolean disponivel = true;
	
	@JsonIgnore
	@OneToMany(mappedBy = "livro", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Emprestimo> emprestimos = new HashSet<>();
}
