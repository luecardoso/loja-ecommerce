package br.senac.ecommerce.pi.loja.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.senac.ecommerce.pi.loja.modelo.ProdutoModelo;
import br.senac.ecommerce.pi.loja.modelo.UsuarioModelo;
import br.senac.ecommerce.pi.loja.repositorio.ProdutoRepositorio;

@Service
public class ProdutoServico {

	@Autowired
	ProdutoRepositorio produtoRepositorio;
	
	public static final int PRODUTOS_POR_PAGINA = 10;
	
	public Page<ProdutoModelo> listarPorPagina(int numPagina, String keyword) {
		Pageable pageable = PageRequest.of(numPagina - 1, PRODUTOS_POR_PAGINA);

		if (keyword != null) {
			return produtoRepositorio.findAll(keyword, pageable);
		}
		return produtoRepositorio.findAll(pageable);
	}
	
	public void atualizarStatusAtivo(Long id, boolean enabled) {
		produtoRepositorio.atualizarStatusAtivado(id, enabled);
	}
	
	
}
