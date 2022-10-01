package br.senac.ecommerce.pi.loja.controlador;

import java.io.File;
import java.io.IOException;
import java.lang.System.Logger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senac.ecommerce.pi.loja.UploadArquivo;
import br.senac.ecommerce.pi.loja.modelo.ProdutoModelo;
import br.senac.ecommerce.pi.loja.servico.ProdutoServico;

@Controller
@RequestMapping("/administrador")
public class ProdutoControlador {

	@Autowired
	ProdutoServico produtoServico;

	public static String CAMINHO_IMAGEM = System.getProperty("user.dir") + "/img/";

	@GetMapping("/produto")
	public String mostrarProdutosPaginacao(Model model) {
		return listarComPaginacao(1, model, null);
	}

	@GetMapping("/produto/pagina/{numPagina}")
	public String listarComPaginacao(@PathVariable(name = "numPagina") int numPagina, Model model,
			@Param("keyword") String keyword) {

		Page<ProdutoModelo> pagina = produtoServico.listarPorPagina(numPagina, keyword);
		List<ProdutoModelo> listarProdutos = pagina.getContent();

		long comecoConta = (numPagina - 1) * ProdutoServico.PRODUTOS_POR_PAGINA + 1;
		long finalConta = comecoConta + ProdutoServico.PRODUTOS_POR_PAGINA - 1;

		if (finalConta > pagina.getTotalElements()) {
			finalConta = pagina.getTotalElements();
		}

		model.addAttribute("paginaAtual", numPagina);
		model.addAttribute("totalPaginas", pagina.getTotalPages());
		model.addAttribute("comecoConta", comecoConta);
		model.addAttribute("finalConta", finalConta);
		model.addAttribute("totalItens", pagina.getTotalElements());
		model.addAttribute("listarProdutos", listarProdutos);
		model.addAttribute("keyword", keyword);
		return "adm/produto";

	}

	@GetMapping("/produto/cadastrar")
	public String cadastrar(Model model, ProdutoModelo produto) {
		model.addAttribute("produtoModelo", produto);
		return "adm/formulario-produto";
	}
//Funciona pra 1 imagem
//	@PostMapping("/produto/salvar")
//	public String salvarUsuario(@Valid ProdutoModelo produto, BindingResult bindingResult,
//			RedirectAttributes redirectAttributes, Model model, @RequestParam("image") MultipartFile arquivo) {
//
//		/* VERIFICA CAMPOS COM ERROS */
//		if (bindingResult.hasErrors()) {
//			model.addAttribute("produtoModelo", produto);
//			redirectAttributes.addFlashAttribute("mensagemErro", "Campo " + bindingResult.getFieldError().getField()
//					+ " com problema: \n" + bindingResult.getFieldError().getDefaultMessage());
//			return "redirect:/administrador/produto/cadastrar";
//		}
//		/* ADICIONA IMAGEM */
//		StringBuilder nomeDoArquivo = new StringBuilder();
//		try {
//			Path nomeDoCaminho = Paths.get(CAMINHO_IMAGEM, arquivo.getOriginalFilename());
//			nomeDoArquivo.append(arquivo.getOriginalFilename());
//
//			Files.write(nomeDoCaminho, arquivo.getBytes());
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
////		produto.setImagem(CAMINHO_IMAGEM +"/"+produto.getId()+"/"+nomeDoArquivo+"");
//		//produto.setImagem("/"+produto.getId()+"/"+nomeDoArquivo);
//		produto.setImagem(""+nomeDoArquivo);
//		/* SALVA PRODUTO */
//		produtoServico.salvarProduto(produto);
//		
//		model.addAttribute("produtoModelo", produto);
//		redirectAttributes.addFlashAttribute("mensagem", "Produto salvo com sucesso!");
//		return "redirect:/administrador/produto";
//	}
	
	//Funcionando com pastas
//	@PostMapping("/produto/salvar")
//	public String salvarUsuario(@Valid ProdutoModelo produto, BindingResult bindingResult,
//			RedirectAttributes redirectAttributes, Model model, @RequestParam("image") MultipartFile arquivo) throws IOException {
//
//		/* VERIFICA CAMPOS COM ERROS */
//		if (bindingResult.hasErrors()) {
//			model.addAttribute("produtoModelo", produto);
//			redirectAttributes.addFlashAttribute("mensagemErro", "Campo " + bindingResult.getFieldError().getField()
//					+ " com problema: \n" + bindingResult.getFieldError().getDefaultMessage());
//			return "redirect:/administrador/produto/cadastrar";
//		}
//		/* ADICIONA IMAGEM */
//		if(!arquivo.isEmpty()) {
//			String nomeArquivo = StringUtils.cleanPath(arquivo.getOriginalFilename());
//			produto.setImagemPrincipal(nomeArquivo);
//			//produto.setImagem(nomeArquivo);
//			
//			ProdutoModelo produtoSalvo = produtoServico.salvarProduto(produto);
//			String salvarCaminho =CAMINHO_IMAGEM + produtoSalvo.getId();
//			
//			UploadArquivo.excluirDiretorio(salvarCaminho);
//			UploadArquivo.salvarArquivo(salvarCaminho, nomeArquivo, arquivo);
//		}else {
//			/* SALVA PRODUTO */
////			if(produto.getImagemPrincipal() != null) {
////				produto.setImagemPrincipal(produto.getImagemPrincipal());
////			}
//			produtoServico.salvarProduto(produto);
//			
//			
//		}
//		
//		model.addAttribute("produtoModelo", produto);
//		redirectAttributes.addFlashAttribute("mensagem", "Produto salvo com sucesso!");
//		return "redirect:/administrador/produto";
//		
//	}
	
	//Testando com varias imagens
	@PostMapping("/produto/salvar")
	public String salvarUsuario(@Valid ProdutoModelo produto, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, Model model, 
			@RequestParam("imagemPrincipal") MultipartFile imagemPrincipal, 
			@RequestParam("imagemExtra") MultipartFile[] imagemExtra) throws IOException {

		/* VERIFICA CAMPOS COM ERROS */
//		if (bindingResult.hasErrors()) {
//			model.addAttribute("produtoModelo", produto);
//			redirectAttributes.addFlashAttribute("mensagemErro", "Campo " + bindingResult.getFieldError().getField()
//					+ " com problema: \n" + bindingResult.getFieldError().getDefaultMessage());
//			return "redirect:/administrador/produto/cadastrar";
//		}
		/* ADICIONA IMAGEM */
		adicionarImagemPrincipal(imagemPrincipal, produto);	
//		produto.setImagemPrincipal(imagemPrincipal);
		adicionarExtraImagem(imagemExtra, produto);

		/* SALVA PRODUTO */
		ProdutoModelo produtoSalvo = produtoServico.salvarProduto(produto);
		
		salvarImagens(imagemPrincipal, imagemExtra, produtoSalvo);
			
			//produtoServico.salvarProduto(produto);
			
			
		
		
		model.addAttribute("produtoModelo", produto);
		redirectAttributes.addFlashAttribute("mensagem", "Produto salvo com sucesso!");
		return "redirect:/administrador/produto";
		
	}
	
	public void salvarImagens(MultipartFile imagemPrincipal, MultipartFile[] imagemExtra,
			ProdutoModelo produtoSalvo) throws IOException {
		if(!imagemPrincipal.isEmpty()) {
			String nomeArquivo = StringUtils.cleanPath(imagemPrincipal.getOriginalFilename());
			String salvarCaminho = CAMINHO_IMAGEM + produtoSalvo.getId();
			UploadArquivo.excluirDiretorio(salvarCaminho);
			UploadArquivo.salvarArquivo(salvarCaminho, nomeArquivo, imagemPrincipal);
		}
		
		if(imagemExtra.length >0) {
			String salvarCaminho = CAMINHO_IMAGEM + produtoSalvo.getId() + "/extra";
			
			for(MultipartFile arquivoExtra : imagemExtra) {
				if(arquivoExtra.isEmpty()) continue;
				String nomeArquivo = StringUtils.cleanPath(arquivoExtra.getOriginalFilename());	
				UploadArquivo.salvarArquivo(salvarCaminho, nomeArquivo, arquivoExtra);
			}
		}
	}
		

	public void adicionarImagemPrincipal(MultipartFile imagemPrincipal, ProdutoModelo produto) {
		if(!imagemPrincipal.isEmpty()) {
			String nomeArquivo = StringUtils.cleanPath(imagemPrincipal.getOriginalFilename());
			produto.setImagemPrincipal(nomeArquivo);
		}
	}
	
	public void adicionarExtraImagem(MultipartFile[] imagemExtra, ProdutoModelo produto) {
		if(imagemExtra.length > 0) {
			for(MultipartFile arquivoExtra : imagemExtra) {
				if(!arquivoExtra.isEmpty()) {
					String nomeArquivo = StringUtils.cleanPath(arquivoExtra.getOriginalFilename());
					produto.adicionarImagemExtra(nomeArquivo);
				}
			}
		}
	}
	
//	@PostMapping("/produto/salvar")
//	public String salvarUsuario(@Valid ProdutoModelo produto, BindingResult bindingResult,
//			RedirectAttributes redirectAttributes, Model model, @RequestParam("image") MultipartFile arquivo) {
//
//		/* VERIFICA CAMPOS COM ERROS */
//		if (bindingResult.hasErrors()) {
//			model.addAttribute("produtoModelo", produto);
//			redirectAttributes.addFlashAttribute("mensagemErro", "Campo " + bindingResult.getFieldError().getField()
//					+ " com problema: \n" + bindingResult.getFieldError().getDefaultMessage());
//			return "redirect:/administrador/produto/cadastrar";
//		}
//		/* ADICIONA IMAGEM */
//		StringBuilder nomeDoArquivo = new StringBuilder();
//		try {
//			Path nomeDoCaminho = Paths.get(CAMINHO_IMAGEM, arquivo.getOriginalFilename());
//			nomeDoArquivo.append(arquivo.getOriginalFilename());
//
//			Files.write(nomeDoCaminho, arquivo.getBytes());
//			
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
////		produto.setImagem(CAMINHO_IMAGEM +"/"+produto.getId()+"/"+nomeDoArquivo+"");
//		//produto.setImagem("/"+produto.getId()+"/"+nomeDoArquivo);
//		produto.setImagem(""+nomeDoArquivo);
//		/* SALVA PRODUTO */
//		produtoServico.salvarProduto(produto);
//		
//		model.addAttribute("produtoModelo", produto);
//		redirectAttributes.addFlashAttribute("mensagem", "Produto salvo com sucesso!");
//		return "redirect:/administrador/produto";
//	}
	

	@GetMapping("/produto/{id}/ativo/{status}")
	public String atualizarStatusAtivadoUsuario(@PathVariable("id") Long id, @PathVariable("status") boolean enabled,
			RedirectAttributes redirectAttributes) {

		produtoServico.atualizarStatusAtivo(id, enabled);
		String status = enabled ? "ativado" : "desativado";
		String message = "O produto " + id + " foi " + status;
		redirectAttributes.addFlashAttribute("mensagemStatus", message);
		return "redirect:/administrador/produto";

	}
	//funciona só não edita as imagens
//	@GetMapping("/produto/editar/{id}")
//	public String editarProduto(@PathVariable(name = "id") Long id, Model model,
//			RedirectAttributes redirectAttributes) {
//
//		ProdutoModelo listaProdutoInfo = produtoServico.editarProduto(id);
//		model.addAttribute("produtoModelo", listaProdutoInfo);
//		return "adm/formulario-produto";
//	}
	
	@GetMapping("/produto/editar/{id}")
	public String editarProduto(@PathVariable(name = "id") Long id, Model model,
			RedirectAttributes redirectAttributes) {

		ProdutoModelo listaProdutoInfo = produtoServico.editarProduto(id);
		listaProdutoInfo.getImagemPrincipal();
		Integer imagensExtrasExistentes = listaProdutoInfo.getImagemExtra().size();
		
		model.addAttribute("produtoModelo", listaProdutoInfo);
		model.addAttribute("imagensExtrasExistentes", imagensExtrasExistentes);
		return "adm/formulario-produto";
	}
	
	@PostMapping("/produto/editar/{id}")
	public String editarQuantidade(@RequestParam("quantidade") Integer quantidade,
			RedirectAttributes redirectAttributes, @PathVariable(name = "id") Long id) {
		
		ProdutoModelo listaProdutoInfo = produtoServico.editarProduto(id);
		if(quantidade == null || quantidade <= 0) {
			quantidade = 0;
			listaProdutoInfo.setAtivo(false);
		}
		listaProdutoInfo.setQuantidade(quantidade);
		
		produtoServico.salvarProduto(listaProdutoInfo);
		redirectAttributes.addFlashAttribute("mensagemStatus", "Quantidade atualizada");
		return "redirect:/administrador/produto";
	}

	@GetMapping("/produto/deletar/{id}")
	public String deletarUsuario(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView("adm/formulario-produto");
		produtoServico.deletarProduto(id);
		return "redirect:/administrador/produto";
	}
	/*mostra 1 imagem apenas*/
//	@GetMapping("/produto/mostrarImagem/{imagemPrincipal}")
//	@ResponseBody
//	public byte[] mostrarImagem(@PathVariable("imagemPrincipal") String imagem) {
//		File imagemArquivo = new File(CAMINHO_IMAGEM +imagem);
//		if (imagem != null || imagem.trim().length() > 0) {
//			try {
//				return Files.readAllBytes(imagemArquivo.toPath());
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		return null;
//	}
	
	
//	@GetMapping("/produto/mostrarImagem/{imagemPrincipal}")
//	@ResponseBody
//	public byte[] mostrarImagem(@PathVariable("imagemPrincipal") String imagem) {
//		File imagemArquivo = new File(CAMINHO_IMAGEM +imagem);
//		if (imagem != null || imagem.trim().length() > 0) {
//			try {
//				return Files.readAllBytes(imagemArquivo.toPath());
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		return null;
//	}
	
	
//	@GetMapping("/produto/mostrarImagem/{imagemPrincipal}")
//	@ResponseBody
//	public byte[] mostrarImagem(@PathVariable("imagemPrincipal") String nomeImagem, ProdutoModelo produto) {
//		File imagemArquivo = new File("/img/"+produto.getId()+"/"+nomeImagem);//CAMINHO_IMAGEM+"/"+produto.getId()+"/"+ imagem
//		if (nomeImagem != null || nomeImagem.trim().length() > 0) {
//			try {
//				return Files.readAllBytes(imagemArquivo.toPath());
//			} catch (IOException e) {				
//				e.printStackTrace();
//			}
//		}
//		return null;
//	}
}
