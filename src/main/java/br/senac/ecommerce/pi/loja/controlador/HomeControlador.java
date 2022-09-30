package br.senac.ecommerce.pi.loja.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.senac.ecommerce.pi.loja.modelo.ProdutoModelo;
import br.senac.ecommerce.pi.loja.servico.ProdutoServico;

@Controller
@RequestMapping("/home")
//@CrossOrigin() //
public class HomeControlador {
	
	@Autowired
	ProdutoServico produtoServico;

	@GetMapping("")
	public String paginaInicial() {
		return "home";
	}
	
	@GetMapping("/produto/detalhes")
	public String paginaProduto() {
		return "produto-detalhe";
	}
	
	@GetMapping("/produto/detalhes/{id}")
	public String produtoDetalhes(@PathVariable(name = "id") Long id, Model model) {
		
		ProdutoModelo produto = produtoServico.encontrarPorId(id);
		model.addAttribute("produtoModelo", produto);
		model.addAttribute("imagemExtraProduto", produto.getImagemExtra());
		return "produto-detalhe";
	}
	
	
}
