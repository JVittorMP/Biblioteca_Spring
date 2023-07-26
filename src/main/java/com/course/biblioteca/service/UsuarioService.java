package com.course.biblioteca.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.course.biblioteca.domain.Usuario;
import com.course.biblioteca.repository.UsuarioRepository;

@Service
public class UsuarioService {
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public List<Usuario> getAllUsuarios(){
		List<Usuario> usuarios = new ArrayList<>();
		usuarioRepository.findAll().forEach(usuarios::add);
		return usuarios;
	}
	
	public Usuario getUsuario(Integer id) throws NoSuchElementException {
		return usuarioRepository.findById(id).orElseThrow();
	}

    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
	
	public void saveUsuario(Usuario usuario) {
		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		usuarioRepository.save(usuario);
	}
	
	public void updateUsuario(Usuario usuario, Integer id) throws NoSuchElementException {
		usuarioRepository.findById(id).orElseThrow();
		usuario.setId(id);
		usuarioRepository.save(usuario);
	}
	
	public void deleteUsuario(Integer id) throws IllegalArgumentException {
		usuarioRepository.deleteById(id);
	}
}
