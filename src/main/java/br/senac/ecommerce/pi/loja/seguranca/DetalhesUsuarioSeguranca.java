package br.senac.ecommerce.pi.loja.seguranca;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.senac.ecommerce.pi.loja.modelo.CargoModelo;
import br.senac.ecommerce.pi.loja.modelo.UsuarioModelo;

public class DetalhesUsuarioSeguranca implements UserDetails{// 

	private UsuarioModelo usuario;

	public DetalhesUsuarioSeguranca(UsuarioModelo usuario) {
		this.usuario = usuario;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<CargoModelo> cargos = usuario.getCargos();
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();

		for (CargoModelo cargo : cargos) {
			authorities.add(new SimpleGrantedAuthority(cargo.getNome()));
		}

		return authorities;
	}

	@Override
	public String getPassword() { // TODO Auto-generated method stub
		return usuario.getSenha();
	}

	@Override
	public String getUsername() { // TODO Auto-generated method stub
		return usuario.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() { // TODO Auto-generated
		return true;
	}

	@Override
	public boolean isAccountNonLocked() { // TODO Auto-generated method
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() { // TODO Auto-generated
		return true;
	}

	@Override
	public boolean isEnabled() { // TODO Auto-generated method stub
		return usuario.isAtivo();
	}

	public void setNome(String nome) {
		this.usuario.setNome(nome);
	}

}
