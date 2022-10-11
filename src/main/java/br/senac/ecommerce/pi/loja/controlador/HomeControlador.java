package br.senac.ecommerce.pi.loja.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.senac.ecommerce.pi.loja.modelo.CategoriaModelo;
import br.senac.ecommerce.pi.loja.modelo.ProdutoModelo;
import br.senac.ecommerce.pi.loja.servico.CategoriaServico;
import br.senac.ecommerce.pi.loja.servico.ProdutoServico;

@Controller
//@CrossOrigin( origins = "http://localhost:9090")
@RequestMapping("/home")
public class HomeControlador {
	
	@Autowired
	ProdutoServico produtoServico;
	
	@Autowired
	CategoriaServico categoriaServico;
	
	@GetMapping("teste")
	public String paginaTeste() {
		return "template";
	}

	@GetMapping()
	public String paginaInicial(Model model) {
		return listarComPaginacao(1, model, null);
	}
	
	@GetMapping("/pagina/{numPagina}")
	public String listarComPaginacao(@PathVariable(name = "numPagina") int numPagina, Model model,
			@Param("keyword") String keyword) {

		Page<ProdutoModelo> pagina = produtoServico.listarPorPagina(numPagina, keyword);
		List<ProdutoModelo> listarProdutos = pagina.getContent();

		long comecoConta = (numPagina - 1) * ProdutoServico.PRODUTOS_POR_PAGINA + 1;
		long finalConta = comecoConta + ProdutoServico.PRODUTOS_POR_PAGINA - 1;

		if (finalConta > pagina.getTotalElements()) {
			finalConta = pagina.getTotalElements();
		}
		List<CategoriaModelo> listaCategoria = categoriaServico.listaCategoria();
		List<ProdutoModelo> listaProduto = produtoServico.listarProdutos();
		model.addAttribute("paginaAtual", numPagina);
		model.addAttribute("totalPaginas", pagina.getTotalPages());
		model.addAttribute("comecoConta", comecoConta);
		model.addAttribute("finalConta", finalConta);
		model.addAttribute("totalItens", pagina.getTotalElements());
		model.addAttribute("listarProdutos", listarProdutos);
		model.addAttribute("keyword", keyword);
		model.addAttribute("listaCategoria", listaCategoria);
		model.addAttribute("listaProduto", listaProduto);
		return "/index";
	}
	
	@GetMapping("/produto/detalhes")
	public String paginaProduto() {
		return "produto-detalhe";
	}
	
	@GetMapping("/produto/detalhes/{id}")
	public String produtoDetalhes(@PathVariable(name = "id") Long id, Model model) {
		
		ProdutoModelo produto = produtoServico.encontrarPorId(id);
		List<CategoriaModelo> listaCategoria = categoriaServico.listaCategoria();
		model.addAttribute("listaCategoria", listaCategoria);
		model.addAttribute("produtoModelo", produto);
		model.addAttribute("imagemExtraProduto", produto.getImagemExtra());
		return "produto-detalhe";
	}
	
	@GetMapping("/navegar")
	public String areaNavegar(Model model) {
		List<CategoriaModelo> listaCategoria = categoriaServico.listaCategoria();
		model.addAttribute("listaCategoria", listaCategoria);
		return "area-navegar";
	}
	
	@GetMapping("/contato")
	public String contato(Model model) {
		List<CategoriaModelo> listaCategoria = categoriaServico.listaCategoria();
		model.addAttribute("listaCategoria", listaCategoria);
		return "contato";
	}
	
	@GetMapping("/entrar")
	public String lojaLogin(Model model) {
		List<CategoriaModelo> listaCategoria = categoriaServico.listaCategoria();
		model.addAttribute("listaCategoria", listaCategoria);
		return "loja-login";
	}
	
	@GetMapping("/ofertas")
	public String areaOfertas(Model model) {
		List<CategoriaModelo> listaCategoria = categoriaServico.listaCategoria();
		model.addAttribute("listaCategoria", listaCategoria);
		return "ofertas";
	}
	
	@GetMapping("/carrinho")
	public String carrinho(Model model) {
		List<CategoriaModelo> listaCategoria = categoriaServico.listaCategoria();
		model.addAttribute("listaCategoria", listaCategoria);
		return "carrinho";
	}
}
