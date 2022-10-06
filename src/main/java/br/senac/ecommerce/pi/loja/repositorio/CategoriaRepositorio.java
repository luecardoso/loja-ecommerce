package br.senac.ecommerce.pi.loja.repositorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.senac.ecommerce.pi.loja.modelo.CategoriaModelo;


public interface CategoriaRepositorio extends JpaRepository<CategoriaModelo, Long> {

	@Query("UPDATE CategoriaModelo p SET p.ativo = ?2 WHERE p.id = ?1")
	@Modifying
	public void atualizarStatusAtivado(Long id, boolean enabled);
	
	@Query("SELECT c FROM CategoriaModelo c WHERE c.nome LIKE %?1%")
	public Page<CategoriaModelo> findAll(String keyword, Pageable pageable);
}
