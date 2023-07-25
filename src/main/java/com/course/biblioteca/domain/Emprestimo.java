package com.course.biblioteca.domain;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Emprestimo {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "emprestimo")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@FutureOrPresent(message = "Data de Empréstimo Inválida")
	private LocalDate data_emprestimo = LocalDate.now();
	
	@Column(name = "limite")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@FutureOrPresent(message = "Data Limite Inválida")
	private LocalDate data_limite = LocalDate.now().plusDays(14);
	
	@Column(name = "devolucao")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@FutureOrPresent(message = "Data de Devolução Inválida")
	private LocalDate data_devolucao;
	
	@ManyToOne
	@JoinColumn(name = "usuario", nullable = false)
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name = "livro", nullable = false)
	private Livro livro;
}
