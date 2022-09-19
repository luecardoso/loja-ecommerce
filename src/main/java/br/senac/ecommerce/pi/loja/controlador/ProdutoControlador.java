package br.senac.ecommerce.pi.loja.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.senac.ecommerce.pi.loja.modelo.ProdutoModelo;
import br.senac.ecommerce.pi.loja.modelo.UsuarioModelo;
import br.senac.ecommerce.pi.loja.servico.ProdutoServico;
import br.senac.ecommerce.pi.loja.servico.UsuarioServico;

@Controller
@RequestMapping("/administrador")
public class ProdutoControlador {

	@Autowired
	ProdutoServico produtoServico;
	
	@GetMapping("/produto")
	public String mostrarUsuariosPaginacao(Model model) {
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
}
