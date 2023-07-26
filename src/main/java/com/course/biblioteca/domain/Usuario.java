package com.course.biblioteca.domain;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 100, nullable = false)
	@NotBlank(message = "[Nome Obrigatório]")
	private String nome;
	
	@Column(length = 20, nullable = false)
	@Size(max = 20, message = "[Telefone Deve Conter Menos de 20 Caracteres]")
	@NotBlank(message = "[Telefone Obrigatório]")
	private String telefone;
	
	@Column(length = 100, nullable = false, unique = true)
	@Email @NotBlank(message = "[Campo de Email Obrigatório]")
	@NotBlank(message = "[Email Obrigatório]")
	private String email;
	
	@Column(nullable = false)
	@Size(min = 8, message = "[Senha Deve Conter no Mínimo 8 Caracteres]")
	@NotBlank(message = "[Senha Obrigatória]")
	private String senha;
	
	@Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;
	
	@JsonIgnore
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Emprestimo> emprestimos = new HashSet<>();
}
