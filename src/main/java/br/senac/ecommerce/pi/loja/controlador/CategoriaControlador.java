package br.senac.ecommerce.pi.loja.controlador;

import java.io.File;
import java.io.IOException;
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

import br.senac.ecommerce.pi.loja.modelo.CategoriaModelo;
import br.senac.ecommerce.pi.loja.servico.CategoriaServico;

@Controller
@RequestMapping("/administrador")
public class CategoriaControlador {
	
	@Autowired
	CategoriaServico categoriaServico;
	public static String CAMINHO_IMAGEM = System.getProperty("user.dir") + "/img/categoria/";

	@GetMapping("/categoria")
	public String mostrarComPaginacao(Model model) {
		return listarComPaginacao(1, model, null);
	}
	
	@GetMapping("/categoria/pagina/{numPagina}")
	public String listarComPaginacao(@PathVariable(name = "numPagina") int numPagina, Model model,
			@Param("keyword") String keyword) {

		Page<CategoriaModelo> pagina = categoriaServico.listarPorPagina(numPagina, keyword);
		List<CategoriaModelo> listar = pagina.getContent();

		long comecoConta = (numPagina - 1) * CategoriaServico.CATEGORIA_POR_PAGINA + 1;
		long finalConta = comecoConta + CategoriaServico.CATEGORIA_POR_PAGINA - 1;

		if (finalConta > pagina.getTotalElements()) {
			finalConta = pagina.getTotalElements();
		}

		model.addAttribute("paginaAtual", numPagina);
		model.addAttribute("totalPaginas", pagina.getTotalPages());
		model.addAttribute("comecoConta", comecoConta);
		model.addAttribute("finalConta", finalConta);
		model.addAttribute("totalItens", pagina.getTotalElements());
		model.addAttribute("listarCategorias", listar);
		model.addAttribute("keyword", keyword);
		return "adm/categoria";

	}
	
	@GetMapping("/categoria/cadastrar")
	public String cadastrar(Model model, CategoriaModelo categoria) {
		model.addAttribute("categoriaModelo", categoria);
		return "adm/formulario-categoria";
	}
	
	@PostMapping("/categoria/salvar")
	public String salvar(@Valid CategoriaModelo categoria, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, Model model, 
			@RequestParam("imagem") MultipartFile arquivo) {

		/* VERIFICA CAMPOS COM ERROS */
//		if (bindingResult.hasErrors()) {
//			model.addAttribute("categoriaModelo", categoria);
//			redirectAttributes.addFlashAttribute("mensagemErro", "Campo " + bindingResult.getFieldError().getField()
//					+ " com problema: \n" + bindingResult.getFieldError().getDefaultMessage());
//			return "redirect:/administrador/categoria/cadastrar";
//		}
		/* ADICIONA IMAGEM */
		StringBuilder nomeDoArquivo = new StringBuilder();
		try {
			Path nomeDoCaminho = Paths.get(CAMINHO_IMAGEM, arquivo.getOriginalFilename());
			nomeDoArquivo.append(arquivo.getOriginalFilename());

			Files.write(nomeDoCaminho, arquivo.getBytes());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		categoria.setImagem(""+nomeDoArquivo);
		/* SALVA PRODUTO */
		categoriaServico.salvar(categoria);
		model.addAttribute("categoriaModelo", categoria);
		redirectAttributes.addFlashAttribute("mensagem", "Categoria salva com sucesso!");
		return "redirect:/administrador/categoria/cadastrar";
	}
	
	@GetMapping("/categoria/{id}/ativo/{status}")
	public String atualizarStatusAtivadoUsuario(@PathVariable("id") Long id, @PathVariable("status") boolean enabled,
			RedirectAttributes redirectAttributes) {

		categoriaServico.atualizarStatusAtivo(id, enabled);
		String status = enabled ? "ativado" : "desativado";
		String message = "A categoria " + id + " foi " + status;
		redirectAttributes.addFlashAttribute("mensagemStatus", message);
		return "redirect:/administrador/categoria";

	}
	
	@GetMapping("/categoria/editar/{id}")
	public ModelAndView editarProduto(@PathVariable(name = "id") Long id,
			RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView("/adm/formulario-categoria");
		CategoriaModelo listaCategoriaInfo = categoriaServico.editar(id);
		if(listaCategoriaInfo.getImagem() != null) {
			listaCategoriaInfo.setImagem(listaCategoriaInfo.getImagem());
		}
//		model.addAttribute("categoriaModelo", listaCategoriaInfo);
		mv.addObject("categoriaModelo", listaCategoriaInfo);
		return mv;
	}
	
	@GetMapping("/categoria/deletar/{id}")
	public ModelAndView deletarUsuario(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView("redirect:/administrador/categoria");
		categoriaServico.deletar(id);
		return mv;
	}
	
	@GetMapping("/categoria/mostrarImagem/{imagemPrincipal}")
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
