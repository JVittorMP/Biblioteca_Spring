package com.course.biblioteca.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.course.biblioteca.domain.Emprestimo;
import com.course.biblioteca.domain.Livro;
import com.course.biblioteca.domain.Usuario;
import com.course.biblioteca.service.EmprestimoService;
import com.course.biblioteca.service.LivroService;
import com.course.biblioteca.service.UsuarioService;

@RestController
@RequestMapping("/API/emprestimos")
public class EmprestimoController {
	@Autowired
	private EmprestimoService emprestimoService;
	@Autowired
	private LivroService livroService;
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping("/")
	public ResponseEntity<List<Emprestimo>> listarTodos(){
		List<Emprestimo> emprestimos = emprestimoService.getAllEmprestimos();
		return ResponseEntity.ok(emprestimos);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Emprestimo> getEmprestimo(@PathVariable Integer id){
		try {
			Emprestimo emprestimo =  emprestimoService.getEmprestimo(id);
		 	return ResponseEntity.ok(emprestimo);
		}catch(NoSuchElementException E) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping("/{user_id}/{book_id}")
	public ResponseEntity<String> addEmprestimo(
			@RequestBody Emprestimo emprestimo,
			@PathVariable Integer user_id,
			@PathVariable Integer book_id) {
		try {
			Usuario usuario = usuarioService.getUsuario(user_id);
			Livro livro = livroService.getLivro(book_id);
			emprestimo.setLivro(livro);
			emprestimo.setUsuario(usuario);
			emprestimoService.saveEmprestimo(emprestimo);
			return ResponseEntity.ok("Emprestimo Realizado com Sucesso!");
		} catch(NoSuchElementException E) {
			return ResponseEntity.badRequest().body("Parâmetros Inválidos!");
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> updateEmprestimo(@RequestBody Emprestimo emprestimo, @PathVariable Integer id){
		try {
			emprestimoService.updateEmprestimo(emprestimo, id);
			return ResponseEntity.ok("Atualização Realizado com Sucesso!");
		} catch(NoSuchElementException E) {
			return ResponseEntity.badRequest().body("Emprestimo não Encontrado!");
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteEmprestimo(@PathVariable Integer id){
		try {
			emprestimoService.deleteEmprestimo(id);
			return ResponseEntity.ok("Emprestimo Deletado com Sucesso!");
		} catch(NoSuchElementException E) {
			return ResponseEntity.badRequest().body("Emprestimo não Encontrado!");
		}
	}
}