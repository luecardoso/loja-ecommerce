package br.senac.ecommerce.pi.loja.seguranca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.senac.ecommerce.pi.loja.modelo.UsuarioModelo;
import br.senac.ecommerce.pi.loja.repositorio.UsuarioRepositorio;

//@Service("userDetailsService")
public class DetalhesUsuario implements UserDetailsService{
	
	@Autowired
	UsuarioRepositorio usuarioRepositorio;

	@Override
	//@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UsuarioModelo usuario = usuarioRepositorio.pegarUsuarioPeloEmail(email);
		
		if (usuario != null) {
			return new DetalhesUsuarioSeguranca(usuario);
		}

		throw new UsernameNotFoundException("Nao foi encontrado usuario com email: " + email);
	}

}
