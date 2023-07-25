package com.course.biblioteca.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
	
	private Integer id;
    
	@NotEmpty
    private String nome;
    
	@NotEmpty
	@Size(max = 20, message = "Senha Deve Conter Menos de 20 Caracteres")
    private String telefone;
    
	@Email @NotEmpty(message = "Campo de Email Obrigatório")
    private String email;
    
	@NotEmpty(message = "Campo de Senha Obrigatório")
    @Size(min = 8, max = 20, message = "Senha Deve Conter Entre 8 e 20 Caracteres")
    private String senha;
}
