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
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@PostMapping("/error")
	public Map<String,String> requisao(MethodArgumentNotValidException ex){
		Map<String,String> problema = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach( (error) -> {
			String campoNome = ((FieldError) problema).getField();
			String mensagemErro = error.getDefaultMessage();
			
			problema.put(campoNome, mensagemErro);
		});
		
		return problema;
		
	}
}
