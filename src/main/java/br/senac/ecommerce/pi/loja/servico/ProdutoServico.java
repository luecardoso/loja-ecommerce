package br.senac.ecommerce.pi.loja.servico;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.senac.ecommerce.pi.loja.modelo.ProdutoModelo;
import br.senac.ecommerce.pi.loja.repositorio.ProdutoRepositorio;

@Service
@Transactional
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
	
	public ProdutoModelo salvarProduto(ProdutoModelo produto) {
		if(produto.getId() == null) {
			produto.setDataCriacao(new Date());
		}
		if(produto.getQuantidade() == 0) {
			produto.setAtivo(false);
		}
		produto.setDataAtualizacao(new Date());
		return produtoRepositorio.save(produto);
	}
	
	public ProdutoModelo editarProduto(Long id) {
		return produtoRepositorio.findById(id).get();
	}
	
	public void deletarProduto(Long id) {
		 produtoRepositorio.deleteById(id);
	}
	
	public ProdutoModelo encontrarPorId(Long id) {
		return produtoRepositorio.findById(id).get();
	}
	
	public List<ProdutoModelo> listarProdutos() {
		return (List<ProdutoModelo>) produtoRepositorio.findAll();
	}
}
