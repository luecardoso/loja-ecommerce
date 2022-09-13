package br.senac.ecommerce.pi.loja;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import br.senac.ecommerce.pi.loja.modelo.CargoModelo;
import br.senac.ecommerce.pi.loja.repositorio.CargoRepositorio;

//@SpringBootTest
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
class LojaApplicationTests {

	@Test
	void contextLoads() {
	}
	
	
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
}
