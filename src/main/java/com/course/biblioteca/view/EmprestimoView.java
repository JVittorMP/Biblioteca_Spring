package com.course.biblioteca.view;

import java.time.LocalDate;
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
import com.course.biblioteca.domain.Emprestimo;
import com.course.biblioteca.service.EmprestimoService;
import com.course.biblioteca.service.LivroService;
import com.course.biblioteca.service.UsuarioService;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@Controller
@RequestMapping("view/emprestimo")
public class EmprestimoView {
	@Autowired
	private EmprestimoService emprestimoService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private LivroService livroService;
	
	@Autowired
	private Validator validator;
	
	@GetMapping("/lista")
	public ModelAndView listarEmprestimos() {
		ModelAndView modelAndView = new ModelAndView("emprestimo/lista-emprestimos");
		List<Emprestimo> emprestimos = emprestimoService.getAllEmprestimos();
		Collections.sort(emprestimos, (left, right) -> left.getId() - right.getId());
		modelAndView.addObject("emprestimos", emprestimos);
		modelAndView.addObject("privileges", AuthenticationController.getSessionPrivileges());
		return modelAndView;
	}
	
	@GetMapping("/cadastrar")
	public ModelAndView formCadastro(Emprestimo emprestimo) {
		ModelAndView modelAndView = new ModelAndView("emprestimo/save-emprestimo");
		modelAndView.addObject("userDropBox", usuarioService.getAllUsuarios());
		modelAndView.addObject("bookDropBox", livroService.getAvailableLivros());
		return modelAndView;
	}
	
	@GetMapping("/atualizar")
	public ModelAndView formUpdate(@RequestParam Integer id) {
		ModelAndView modelAndView = new ModelAndView("emprestimo/save-emprestimo");
		Emprestimo emprestimo = emprestimoService.getEmprestimo(id);
		emprestimo.setData_devolucao(LocalDate.now());
		modelAndView.addObject("emprestimo", emprestimo);
		return modelAndView;
	}
	
	@GetMapping("/deletar")
	public String deleteEmprestimo(@RequestParam Integer id) {
		emprestimoService.deleteEmprestimo(id);
		return "redirect:/view/emprestimo/lista";
	}
	
	@GetMapping("/devolver")
	public String makeAvailable(@RequestParam Integer id) {
		Emprestimo emprestimo = emprestimoService.getEmprestimo(id);
		emprestimo.setData_devolucao(LocalDate.now());
		emprestimo.getLivro().setDisponivel(true);
		emprestimoService.saveEmprestimo(emprestimo);
		return "redirect:/view/emprestimo/lista";
	}
	
	@PostMapping
	public ModelAndView saveEmprestimo(Emprestimo emprestimo) {
		Set<ConstraintViolation<Emprestimo>> violations = validator.validate(emprestimo);
		String problemas = "";
		String mensagens = "";
		if (!violations.isEmpty()) {
			problemas = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining("\n"));
		} else {
			emprestimo.getLivro().setDisponivel(false);
			livroService.saveLivro(emprestimo.getLivro());
			emprestimoService.saveEmprestimo(emprestimo);
			mensagens = "Salvo com sucesso!";
		}
		ModelAndView modelAndView = new ModelAndView("emprestimo/save-emprestimo");
		modelAndView.addObject("sucesso", mensagens);
		modelAndView.addObject("error", problemas);
		return modelAndView;
	}
}
