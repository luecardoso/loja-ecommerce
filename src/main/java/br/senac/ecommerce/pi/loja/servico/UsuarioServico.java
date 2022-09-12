package br.senac.ecommerce.pi.loja.servico;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.senac.ecommerce.pi.loja.modelo.UsuarioModelo;
import br.senac.ecommerce.pi.loja.repositorio.UsuarioRepositorio;

@Service
@Transactional
public class UsuarioServico {

	@Autowired
	UsuarioRepositorio usuarioRepositorio;
	
	public List<UsuarioModelo> listarTodosUsuarios(){
		return this.usuarioRepositorio.findAll();
	}
	
	public void salvarUsuario(UsuarioModelo usuario) {
		usuarioRepositorio.save(usuario);
	}
	
	public UsuarioModelo editarUsuario(Long id) {
		return usuarioRepositorio.findById(id).get();
	}
	
	public void deletarUsuario(Long id) {
		 usuarioRepositorio.deleteById(id);
	}

	public void atualizarStatusAtivo(Long id, boolean enabled) {
		usuarioRepositorio.atualizarStatusAtivado(id, enabled);
	}
	
	public boolean emailUnico(String email) {
		UsuarioModelo usuarioPeloEmail = usuarioRepositorio.pegarUsuarioPeloEmail(email);
		return usuarioPeloEmail == null;
	}
	
	public UsuarioModelo usuarioPorEmail(String email) {
		return usuarioRepositorio.pegarUsuarioPeloEmail(email);
	}
}
