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
public class Emprestimo {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "emprestimo")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@FutureOrPresent(message = "[Data de Empréstimo Inválida]")
	@NotNull(message = "[Data de Emprestimo Vazia]")
	private LocalDate data_emprestimo = LocalDate.now();
	
	@Column(name = "limite")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@FutureOrPresent(message = "[Data Limite Inválida]")
	@NotNull(message = "[Data Limite Vazia]")
	private LocalDate data_limite = LocalDate.now().plusDays(14);
	
	@Column(name = "devolucao")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@FutureOrPresent(message = "[Data de Devolução Inválida]")
	private LocalDate data_devolucao;
	
	@ManyToOne
	@JoinColumn(name = "usuario", nullable = false)
	@NotNull(message = "[Usuario Não Selecionado]")
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name = "livro", nullable = false)
	@NotNull(message = "[Livro Não Selecionado]")
	private Livro livro;
}
