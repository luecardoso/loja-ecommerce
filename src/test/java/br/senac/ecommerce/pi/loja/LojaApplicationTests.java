package br.senac.ecommerce.pi.loja;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import br.senac.ecommerce.pi.loja.modelo.CargoModelo;
import br.senac.ecommerce.pi.loja.modelo.UsuarioModelo;
import br.senac.ecommerce.pi.loja.repositorio.CargoRepositorio;
import br.senac.ecommerce.pi.loja.repositorio.UsuarioRepositorio;

//@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
@DataJpaTest(showSql = false)
class LojaApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Autowired
	UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	CargoRepositorio cargoRepositorio;
	
	@Test
	public void testeCriarPrimeiroCargo() {
		CargoModelo cargoAdmin = new CargoModelo("Administrador", "Administrar usuários cadastrados");
		CargoModelo cargoSalvo = cargoRepositorio.save(cargoAdmin);
		assertThat(cargoSalvo.getId()).isGreaterThan(0);
	}

	@Test
	public void testeCriarSegundoCargo() {
		CargoModelo cargoEstoquista = new CargoModelo("Estoquista", "Administrar produtos");
		CargoModelo cargoSalvo = cargoRepositorio.save(cargoEstoquista);
		assertThat(cargoSalvo.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testeCodificarSenha() {
		BCryptPasswordEncoder codificadorSenha = new BCryptPasswordEncoder();
		String senhaCrua = "12345";
		String senhaCodificada = codificadorSenha.encode(senhaCrua);
		System.out.println("Senha Normal: "+senhaCrua);
		System.out.println("Senha Codificada: "+senhaCodificada);

		boolean matches = codificadorSenha.matches(senhaCrua, senhaCodificada);

		assertThat(matches).isTrue();
	}
	
	@Test
	public void testeCriarAdministrador() {
		CargoModelo cargo = entityManager.find(CargoModelo.class, 1L);
		UsuarioModelo usuario = new UsuarioModelo();
		BCryptPasswordEncoder codificadorSenha = new BCryptPasswordEncoder();
		String senhaCrua = "123";
		String senhaCodificada = codificadorSenha.encode(senhaCrua);
//		usuario.setId(1L);
		usuario.setNome("ADMIN");
		usuario.setEmail("adm@teste.com");
		usuario.setSenha(senhaCodificada);
		usuario.setCpf("774.521.770-96");
//		usuario.setDataNascimento("2020-28-08");
		usuario.setTelefone("115550123");
		usuario.setAtivo(true);
		usuario.adicionarCargo(cargo);
		
		
		UsuarioModelo usuarioSalvo = usuarioRepositorio.save(usuario);
		boolean matches = codificadorSenha.matches(senhaCrua, senhaCodificada);

		assertThat(matches).isTrue();
		
		assertThat(usuarioSalvo.getId()).isGreaterThan(0);
		
		
	}
	
	@Test
	public void testeCriarEstoquista() {
		CargoModelo cargo = entityManager.find(CargoModelo.class, 2L);
		UsuarioModelo usuario = new UsuarioModelo();
		BCryptPasswordEncoder codificadorSenha = new BCryptPasswordEncoder();
		String senhaCrua = "123";
		String senhaCodificada = codificadorSenha.encode(senhaCrua);
//		usuario.setId(1L);
		usuario.setNome("ESTOQUISTA");
		usuario.setEmail("est@teste.com");
		usuario.setSenha(senhaCodificada);
		usuario.setCpf("079.962.630-94");
//		usuario.setDataNascimento("2020-28-08");
		usuario.setTelefone("115550123");
		usuario.setAtivo(true);
		usuario.adicionarCargo(cargo);
		
		UsuarioModelo usuarioSalvo = usuarioRepositorio.save(usuario);
		boolean matches = codificadorSenha.matches(senhaCrua, senhaCodificada);

		assertThat(matches).isTrue();
		assertThat(usuarioSalvo.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testeProcuraUsuarios() {
		String keyword = "Lucas";
		
		int numPagina = 0;
		int tamanhoPagina = 4;
		Pageable pageable = PageRequest.of(numPagina, tamanhoPagina);
		Page<UsuarioModelo> page = usuarioRepositorio.encontrarPorPagina(keyword, pageable);
		List<UsuarioModelo> listaUsuarios = page.getContent();

		listaUsuarios.forEach(usuario -> System.out.println(usuario));		
		assertThat(listaUsuarios.size()).isGreaterThan(0);
	}
}
