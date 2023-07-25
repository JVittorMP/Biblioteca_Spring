package com.course.biblioteca.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePage {
	@GetMapping
	public String showHomePage() {
		return "homePage";
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
