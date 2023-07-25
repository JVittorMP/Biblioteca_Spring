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

import com.course.biblioteca.domain.Usuario;
import com.course.biblioteca.service.UsuarioService;

@RestController
@RequestMapping("/API/usuarios")
public class UsuarioController {
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping("/")
	public ResponseEntity<List<Usuario>> listarTodos(){
		List<Usuario> usuarios = usuarioService.getAllUsuarios();
		return ResponseEntity.ok(usuarios);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> getUsuario(@PathVariable Integer id){
		try {
			Usuario usuario = usuarioService.getUsuario(id);
		 	return ResponseEntity.ok(usuario);
		}catch(NoSuchElementException E) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping("/")
	public ResponseEntity<String> addUsuario(@RequestBody Usuario usuario){
		usuarioService.saveUsuario(usuario);
		return ResponseEntity.ok("Cadastro Realizado com Sucesso!");
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> updateUsuario(@RequestBody Usuario usuario, @PathVariable Integer id){
		try {
			usuarioService.updateUsuario(usuario, id);
			return ResponseEntity.ok("Atualização Realizada com Sucesso!");
		} catch(NoSuchElementException E) {
			return ResponseEntity.badRequest().body("Usuario não Encontrado!");
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUsuario(@PathVariable Integer id){
		try {
			usuarioService.deleteUsuario(id);
			return ResponseEntity.ok("Usuario Excluído com Sucesso!");
		} catch(IllegalArgumentException E) {
			return ResponseEntity.badRequest().body("Usuario não Encontrado!");
		}
	}
}