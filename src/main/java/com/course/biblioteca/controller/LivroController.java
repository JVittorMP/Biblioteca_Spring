package com.course.biblioteca.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.course.biblioteca.domain.Livro;
import com.course.biblioteca.service.LivroService;

@Controller
@RequestMapping("/API/livros")
public class LivroController {
	@Autowired
	private LivroService livroService;
	
	@GetMapping("/")
	public ResponseEntity<List<Livro>> getAllLivros(Livro livro) {
		List<Livro> livros = livroService.getAllLivros();
		return ResponseEntity.ok(livros);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Livro> getLivro(@PathVariable Integer id){
		try {
			Livro livro = livroService.getLivro(id);
		 	return ResponseEntity.ok(livro);
		}catch(NoSuchElementException E) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping("/")
	public ResponseEntity<String> addLivro(Livro livro) {
		livroService.saveLivro(livro);
		return ResponseEntity.ok("Cadastro Realizado com Sucesso!");
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> updateLivro(@RequestBody Livro livro, @PathVariable Integer id){
		try {
			livroService.updateLivro(livro, id);
			return ResponseEntity.ok("Atualização Realizada com Sucesso!");
		} catch(NoSuchElementException E) {
			return ResponseEntity.badRequest().body("Livro não Encontrado!");
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteLivro(@PathVariable Integer id){
		try {
			livroService.deleteLivro(id);
			return ResponseEntity.ok("Usuario Excluído com Sucesso!");
		} catch(IllegalArgumentException E) {
			return ResponseEntity.badRequest().body("Livro não Encontrado!");
		}
	}
}