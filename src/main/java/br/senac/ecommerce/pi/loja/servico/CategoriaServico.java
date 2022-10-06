package br.senac.ecommerce.pi.loja.servico;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.senac.ecommerce.pi.loja.modelo.CategoriaModelo;
import br.senac.ecommerce.pi.loja.repositorio.CategoriaRepositorio;

@Service
@Transactional
public class CategoriaServico {

	@Autowired
	CategoriaRepositorio categoriaRepositorio;
	
	public static final int CATEGORIA_POR_PAGINA = 10;
	
	public Page<CategoriaModelo> listarPorPagina(int numPagina, String keyword) {
		Pageable pageable = PageRequest.of(numPagina - 1, CATEGORIA_POR_PAGINA);

		if (keyword != null) {
			return categoriaRepositorio.findAll(keyword, pageable);
		}
		return categoriaRepositorio.findAll(pageable);
	}
	
	public void atualizarStatusAtivo(Long id, boolean enabled) {
		categoriaRepositorio.atualizarStatusAtivado(id, enabled);
	}
	
	public CategoriaModelo salvar(CategoriaModelo categoria) {
		return categoriaRepositorio.save(categoria);
	}
	
	public CategoriaModelo editar(Long id) {
		return categoriaRepositorio.findById(id).get();
	}
	
	public void deletar(Long id) {
		categoriaRepositorio.deleteById(id);
	}
}
