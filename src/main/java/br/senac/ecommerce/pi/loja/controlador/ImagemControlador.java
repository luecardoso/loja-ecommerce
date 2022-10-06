package br.senac.ecommerce.pi.loja.controlador;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ImagemControlador {

	public static String CAMINHO_IMAGEM = System.getProperty("user.dir") + "/img/";
	
	@GetMapping("/mostrarImagem/{imagemPrincipal}")
	@ResponseBody
	public byte[] mostrarImagem(@PathVariable("imagemPrincipal") String nomeImagem) {
		File imagemArquivo = new File(CAMINHO_IMAGEM +nomeImagem);
		if (nomeImagem != null || nomeImagem.trim().length() > 0) {
			try {
				return Files.readAllBytes(imagemArquivo.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
