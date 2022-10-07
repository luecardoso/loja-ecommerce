package br.senac.ecommerce.pi.loja;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.senac.ecommerce.pi.loja.modelo.UsuarioModelo;

@Controller
// @CrossOrigin(value = "http://localhost:9090")
public class MainController {

	@GetMapping("/")
	public String teste() {
		return "redirect:/home";
	}

	@GetMapping("/login")
	public String verTelaLogin(UsuarioModelo usuario) {
		if (usuario.getCargos() == null) {
			return "redirect:/home";
		}
		return "login";
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@PostMapping("/error")
	public Map<String, String> requisao(MethodArgumentNotValidException ex) {
		Map<String, String> problema = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String campoNome = ((FieldError) problema).getField();
			String mensagemErro = error.getDefaultMessage();

			problema.put(campoNome, mensagemErro);
		});

		return problema;

	}
}
