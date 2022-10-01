package br.senac.ecommerce.pi.loja.repositorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.senac.ecommerce.pi.loja.modelo.ProdutoModelo;
import br.senac.ecommerce.pi.loja.modelo.UsuarioModelo;

@Repository
public interface ProdutoRepositorio extends JpaRepository<ProdutoModelo, Long>{

	@Query("UPDATE ProdutoModelo p SET p.ativo = ?2 WHERE p.id = ?1")
	@Modifying
	public void atualizarStatusAtivado(Long id, boolean enabled);
	
	@Query("SELECT p FROM ProdutoModelo p WHERE p.nome LIKE %?1% ORDER BY p.dataAtualizacao DESC")//ORDER BY p.dataAtualizacao ASC
	public Page<ProdutoModelo> findAll(String keyword, Pageable pageable);
	
//	@Query()
//	public List<ProdutoModelo> encontrarProdutos();
}
