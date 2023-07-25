package com.course.biblioteca.domain.login;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.course.biblioteca.domain.UserRole;
import com.course.biblioteca.domain.Usuario;
import com.course.biblioteca.repository.UsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    	Usuario usuario = usuarioRepository.findByEmail(email);
    	
    	if(usuario != null) {
    		String username = usuario.getEmail();
    		String password = usuario.getSenha();
    		List<? extends GrantedAuthority> authorities = mapRolesToAuthorities(usuario.getRole());
    		return new org.springframework.security.core.userdetails.User(username, password, authorities);
    	} else {
    		throw new UsernameNotFoundException("Usuario ou Senha Inválidos");
    	}
    }
    
    private List< ? extends GrantedAuthority> mapRolesToAuthorities(UserRole role) {
    	if(role == UserRole.ADMIN)
        	return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else
        	return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }
}



