package com.course.biblioteca.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.course.biblioteca.controller.AuthenticationController;

@Controller
public class HomePage {
	@GetMapping
	public ModelAndView showHomePage() {
		ModelAndView modelAndView = new ModelAndView("homePage");
		modelAndView.addObject("privileges", AuthenticationController.getSessionPrivileges());
		return modelAndView;
	}
	
	@GetMapping("/error")
	public String showErrorPage() {
		return "error";
	}
	
	@GetMapping("/index")
	public String showIndexPage() {
		return "index";
	}
}
