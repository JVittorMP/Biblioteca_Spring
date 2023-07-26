package com.course.biblioteca.view;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.course.biblioteca.controller.AuthenticationController;
import com.course.biblioteca.domain.UserRole;
import com.course.biblioteca.domain.Usuario;
import com.course.biblioteca.service.UsuarioService;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@Controller
@RequestMapping("view/usuario")
public class UsuarioView {
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private Validator validator;
	
	@GetMapping("/lista")
	public ModelAndView listarUsuarios() {
		ModelAndView modelAndView = new ModelAndView("usuario/lista-usuarios");
		List<Usuario> usuarios = usuarioService.getAllUsuarios();
		Collections.sort(usuarios, (left, right) -> left.getId() - right.getId());
		modelAndView.addObject("usuarios", usuarios);
		modelAndView.addObject("privileges", AuthenticationController.getSessionPrivileges());
		return modelAndView;
	}
	
	@GetMapping("/cadastrar")
	public ModelAndView formCadastro(Usuario usuario) {
		ModelAndView modelAndView = new ModelAndView("usuario/save-usuario");
		modelAndView.addObject("roles", List.of(UserRole.USER, UserRole.ADMIN));
		return modelAndView;
	}
	
	@GetMapping("/atualizar")
	public ModelAndView formUpdate(@RequestParam Integer id) {
		ModelAndView modelAndView = new ModelAndView("usuario/save-usuario");
		Usuario usuario = usuarioService.getUsuario(id);
		modelAndView.addObject("usuario", usuario);
		modelAndView.addObject("roles", List.of(UserRole.USER, UserRole.ADMIN));
		return modelAndView;
	}
	
	@GetMapping("/deletar")
	public String deleteUsuario(@RequestParam Integer id) {
		usuarioService.deleteUsuario(id);
		return "redirect:/view/usuario/lista";
	}
	
	@PostMapping
	public ModelAndView saveUsuario(Usuario usuario) {
		Set<ConstraintViolation<Usuario>> violations = validator.validate(usuario);
		String problemas = "";
		String mensagens = "";
		if (!violations.isEmpty()) {
			problemas = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining("\n"));
		} else {
			usuarioService.saveUsuario(usuario);
			mensagens = "Salvo com sucesso!";
		}
		ModelAndView modelAndView = new ModelAndView("usuario/save-usuario");
		modelAndView.addObject("sucesso", mensagens);
		modelAndView.addObject("error", problemas);
		return modelAndView;
	}
}
