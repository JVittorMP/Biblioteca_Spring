package com.course.biblioteca.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.course.biblioteca.domain.UserRole;
import com.course.biblioteca.domain.Usuario;
import com.course.biblioteca.domain.UsuarioDTO;
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
	
	public void saveUsuario(UsuarioDTO usuarioDTO) {
        Usuario Usuario = new Usuario();
        Usuario.setNome(usuarioDTO.getNome());
        Usuario.setEmail(usuarioDTO.getEmail());
        Usuario.setTelefone(usuarioDTO.getTelefone());
        Usuario.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario.setRole(UserRole.USER);
        usuarioRepository.save(Usuario);
    }
	
	public void updateUsuario(Usuario usuario, Integer id) throws NoSuchElementException {
		usuarioRepository.findById(id).orElseThrow();
		usuario.setId(id);
		usuarioRepository.save(usuario);
	}
	
	public void deleteUsuario(Integer id) throws IllegalArgumentException {
		usuarioRepository.deleteById(id);
	}
	
	public List<UsuarioDTO> findAllUsuarios() {
        List<Usuario> Usuarios = usuarioRepository.findAll();
        return Usuarios.stream().map((Usuario) -> convertEntityToDTO(Usuario)).collect(Collectors.toList());
    }
    
    private UsuarioDTO convertEntityToDTO(Usuario usuario){
        UsuarioDTO UsuarioDTO = new UsuarioDTO();
        UsuarioDTO.setNome(usuario.getNome());
        UsuarioDTO.setEmail(usuario.getEmail());
        UsuarioDTO.setTelefone(usuario.getTelefone());
        return UsuarioDTO;
    }
}
