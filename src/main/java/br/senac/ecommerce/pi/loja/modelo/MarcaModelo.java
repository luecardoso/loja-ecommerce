package br.senac.ecommerce.pi.loja.modelo;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "marca")
public class MarcaModelo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// @Column(length = 50)
	@NotBlank(message = "Preencha o nome")
	// @NotNull
	private String nome;
	
	private String imagem;

	private boolean ativo;
	
//	@ManyToOne
//	@JoinColumn(name = "produto_id")
//	private ProdutoModelo produtoMarca;
//	
//	public ProdutoModelo getProduto() {
//		return produtoMarca;
//	}
//
//	public void setProduto(ProdutoModelo produto) {
//		this.produtoMarca = produto;
//	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public MarcaModelo(Long id, @NotBlank(message = "Preencha o nome") String nome) {
		this.id = id;
		this.nome = nome;
	}

	public MarcaModelo() {
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
		return "/img/marca/" +this.imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	@Override
	public String toString() {
		return  nome;
	}
	
}
