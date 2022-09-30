package br.senac.ecommerce.pi.loja;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

public class UploadArquivo {
	public static void salvarArquivo(String caminho, String arquivo, MultipartFile multi) throws IOException {
		Path salvarArquivo = Paths.get(caminho);
		
		if(!Files.exists(salvarArquivo)) {
			Files.createDirectories(salvarArquivo);
		}
			
		try(InputStream input = multi.getInputStream()){
			 Path arq = salvarArquivo.resolve(arquivo);
			 Files.copy(input,arq, StandardCopyOption.REPLACE_EXISTING);
		}catch(IOException ex) {
			throw new IOException("Não foi possível salvar o arquivo "+arquivo, ex);
		}
	}
	
	public static void excluirDiretorio(String caminho) {
		Path diretorio = Paths.get(caminho);
		
		try {
			Files.list(diretorio).forEach(file -> {
				if(!Files.isDirectory(file)) {
					try {
						Files.delete(file);
					}catch(IOException ex) {
						System.out.println("Não foi possível deletar o arquivo: "+file);
					}
				}
			});
		}catch(IOException ex){
			System.out.println("Não foi possível listar o diretório: "+diretorio);
		}
	}
}
