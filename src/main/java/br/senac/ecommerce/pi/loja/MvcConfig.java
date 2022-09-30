package br.senac.ecommerce.pi.loja;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer{

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		exibirDiretorio("img", registry);
	}
	
	public void exibirDiretorio(String imagem, ResourceHandlerRegistry registry) {
		String caminho = imagem;
		Path fotoProduto = Paths.get(caminho);
		
		String salvarFoto = fotoProduto.toFile().getAbsolutePath();
		
		String encontrarCaminho = imagem.replace("../","")+"/**";
		
		registry.addResourceHandler(encontrarCaminho)
		.addResourceLocations("file:/"+salvarFoto+"/");
	}
}
