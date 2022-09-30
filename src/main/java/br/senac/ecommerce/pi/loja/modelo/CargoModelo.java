package br.senac.ecommerce.pi.loja.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cargo")
public class CargoModelo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	private String decricao;

	public CargoModelo(Long id, String nome, String decricao) {
		this.id = id;
		this.nome = nome;
		this.decricao = decricao;
	}

	public CargoModelo() {
	}

	public CargoModelo(String nome, String decricao) {
		super();
		this.nome = nome;
		this.decricao = decricao;
	}

	@Override
	public String toString() {
		return getNome();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDecricao() {
		return decricao;
	}

	public void setDecricao(String decricao) {
		this.decricao = decricao;
	}
	
}
