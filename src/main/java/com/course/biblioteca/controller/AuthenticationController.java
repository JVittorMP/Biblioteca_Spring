package com.course.biblioteca.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.course.biblioteca.domain.UserRole;
import com.course.biblioteca.domain.Usuario;
import com.course.biblioteca.service.UsuarioService;

import jakarta.validation.Valid;

@Controller
public class AuthenticationController {
	@Autowired
    private UsuarioService usuarioService;
	
	private static Boolean administrativePrivileges;
	
	@GetMapping("/session")
	public String registerSession() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();
		if(roles.contains(new SimpleGrantedAuthority("ROLE_ADMIN")))
			administrativePrivileges = true;
		else
			administrativePrivileges = false;
		return "redirect:/";
	}

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        Usuario usuario = new Usuario();
        model.addAttribute("usuario", usuario);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@ModelAttribute("usuario") @Valid Usuario usuario,
                               BindingResult result,
                               Model model){
        Usuario existing = usuarioService.findByEmail(usuario.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (result.hasErrors()) {
            model.addAttribute("usuario", usuario);
            return "register";
        }
        if(usuarioService.getAllUsuarios().isEmpty())
        	usuario.setRole(UserRole.ADMIN);
        else
        	usuario.setRole(UserRole.USER);
        usuarioService.saveUsuario(usuario);
        return "redirect:/register?success";
    }

    @GetMapping("/users")
    public String listRegisteredUsers(Model model){
        List<Usuario> usuarios = usuarioService.getAllUsuarios();
        model.addAttribute("usuarios", usuarios);
        return "users";
    }
    
    public static Boolean getSessionPrivileges() {
    	return administrativePrivileges;
    }
}
