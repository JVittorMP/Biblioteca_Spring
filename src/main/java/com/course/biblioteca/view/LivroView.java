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

import com.course.biblioteca.domain.Livro;
import com.course.biblioteca.service.LivroService;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@Controller
@RequestMapping("view/livro")
public class LivroView {
	@Autowired
	private LivroService livroService;
	
	@Autowired
	private Validator validator;
	
	@GetMapping("/lista")
	public ModelAndView listarLivros() {
		ModelAndView modelAndView = new ModelAndView("livro/lista-livros");
		List<Livro> livros = livroService.getAllLivros();
		Collections.sort(livros, (left, right) -> left.getId() - right.getId());
		modelAndView.addObject("livros", livros);
		return modelAndView;
	}
	
	@GetMapping("/cadastrar")
	public ModelAndView formCadastro(Livro livro) {
		ModelAndView modelAndView = new ModelAndView("livro/save-livro");
		return modelAndView;
	}
	
	@GetMapping("/atualizar")
	public ModelAndView formUpdate(@RequestParam Integer id) {
		ModelAndView modelAndView = new ModelAndView("livro/save-livro");
		Livro livro = livroService.getLivro(id);
		modelAndView.addObject("livro", livro);
		return modelAndView;
	}
	
	@GetMapping("/deletar")
	public String deleteLivro(@RequestParam Integer id) {
		livroService.deleteLivro(id);
		return "redirect:/view/livro/lista";
	}
	
	@PostMapping
	public ModelAndView saveLivro(Livro livro) {
		Set<ConstraintViolation<Livro>> violations = validator.validate(livro);
		String problemas = "";
		String mensagens = "";
		if (!violations.isEmpty()) {
			problemas = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining("\n"));
		} else {
			livroService.saveLivro(livro);
			mensagens = "Salvo com sucesso!";
		}
		ModelAndView modelAndView = new ModelAndView("livro/save-livro");
		modelAndView.addObject("sucesso", mensagens);
		modelAndView.addObject("error", problemas);
		return modelAndView;
	}
}
