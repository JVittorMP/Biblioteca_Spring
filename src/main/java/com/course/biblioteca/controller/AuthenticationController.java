package com.course.biblioteca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.course.biblioteca.domain.Usuario;
import com.course.biblioteca.domain.UsuarioDTO;
import com.course.biblioteca.service.UsuarioService;

import jakarta.validation.Valid;

@Controller
public class AuthenticationController {
	@Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        UsuarioDTO usuario = new UsuarioDTO();
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
        usuarioService.saveUsuario(usuario);
        return "redirect:/register?success";
    }

    @GetMapping("/users")
    public String listRegisteredUsers(Model model){
        List<UsuarioDTO> usuarios = usuarioService.findAllUsuarios();
        model.addAttribute("usuarios", usuarios);
        return "users";
    }
}
