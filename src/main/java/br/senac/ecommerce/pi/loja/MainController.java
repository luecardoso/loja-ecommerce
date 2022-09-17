package br.senac.ecommerce.pi.loja;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	@GetMapping("/administrador")
	public String teste() {
		return "layout/layout";
	}

	@GetMapping("/login")
	public String verTelaLogin() {
		return "login";
	}
}
