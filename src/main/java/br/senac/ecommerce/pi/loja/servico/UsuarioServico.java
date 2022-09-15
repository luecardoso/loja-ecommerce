package br.senac.ecommerce.pi.loja.servico;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.senac.ecommerce.pi.loja.modelo.CargoModelo;
import br.senac.ecommerce.pi.loja.modelo.UsuarioModelo;
import br.senac.ecommerce.pi.loja.repositorio.CargoRepositorio;
import br.senac.ecommerce.pi.loja.repositorio.UsuarioRepositorio;

@Service
@Transactional
public class UsuarioServico {

	@Autowired
	UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	CargoRepositorio cargoRepositorio;
	
	
	public List<UsuarioModelo> listarTodosUsuarios(){
		return this.usuarioRepositorio.findAll();
	}
	
	public void salvarUsuario(UsuarioModelo usuario) {
		boolean estaAtualizandoUsuario = (usuario.getId() != null);
		if (estaAtualizandoUsuario) {
			 Optional<UsuarioModelo> usuarioExistente = usuarioRepositorio.findById(usuario.getId());
			 UsuarioModelo usuarioAtual = usuarioExistente.get();
			if(usuario.getSenha().isEmpty()) {
				usuario.setSenha(usuarioAtual.getSenha());
			}else {
				//codificarSenha(usuario);
			}
		}else {
			//codificarSenha(usuario);
		}
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
	
	public List<CargoModelo> listarCargos() {
		return  cargoRepositorio.findAll();
	}
	
//	private void codificarSenha(UsuarioModelo usuario) {
//		String senhaCodificada = codificadorSenha.encode(usuario.getSenha());
//		usuario.setSenha(senhaCodificada);
//	}
}
