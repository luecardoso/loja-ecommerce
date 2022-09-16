package br.senac.ecommerce.pi.loja.repositorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.senac.ecommerce.pi.loja.modelo.UsuarioModelo;

@Repository
public interface UsuarioRepositorio extends JpaRepository<UsuarioModelo, Long> {

	@Query("UPDATE UsuarioModelo u SET u.ativo = ?2 WHERE u.id = ?1")
	@Modifying
	public void atualizarStatusAtivado(Long id, boolean enabled);
	
	
	@Query("SELECT u FROM UsuarioModelo u WHERE u.email = :email")
	public UsuarioModelo pegarUsuarioPeloEmail(@Param("email") String email);
	
	@Query("SELECT u FROM UsuarioModelo u WHERE u.nome LIKE %?1% OR u.email LIKE %?1%")
	public Page<UsuarioModelo> encontrarPorPagina(String keyword, Pageable pageable);
}
