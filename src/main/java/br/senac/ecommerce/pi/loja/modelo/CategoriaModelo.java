package br.senac.ecommerce.pi.loja.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "categoria")
public class CategoriaModelo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// @Column(length = 50)
	@NotBlank(message = "Preencha o nome")
	// @NotNull
	private String nome;
	
	private String imagem;

	private boolean ativo;
	
	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public CategoriaModelo(Long id, @NotBlank(message = "Preencha o nome") String nome) {
		this.id = id;
		this.nome = nome;
	}

	public CategoriaModelo() {
		super();
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

	public String getImagem() {
		return "/img/categoria/" +this.imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}
}
