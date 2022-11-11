package br.senac.ecommerce.pi.loja.modelo;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "produto_imagens")
public class ProdutoImagemModelo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//@Column(nullable = false)
	private String nome;
	
	public ProdutoImagemModelo() {
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
	
	@ManyToOne
	@JoinColumn(name = "produto_id")
	private ProdutoModelo produtoimagem;
	

	public ProdutoModelo getProdutoimagem() {
		return produtoimagem;
	}

	public void setProdutoimagem(ProdutoModelo produtoimagem) {
		this.produtoimagem = produtoimagem;
	}
	
	public ProdutoImagemModelo(String nome, ProdutoModelo prod) {
		this.nome = nome;
		this.produtoimagem = prod;
	}
	
	@Transient
	public String caminhoImagemExtra() {
		return "/img/"+ produtoimagem.getId() +"/extra/"+this.nome;
	}
}
