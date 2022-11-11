package br.senac.ecommerce.pi.loja.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

@Entity
@Table(name = "produto")
public class ProdutoModelo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// @GeneratedValue(generator = "geradorId")
	private Long codigo;

	private String nome;

	@Column(length = 500)
	private String descricaoCurta;

	@Column(length = 5000)
	@Size(min = 10, max = 5000)
	private String descricaoLonga;

	private float preco;

	private Integer quantidade;

	private Date dataCriacao;

	// @Temporal(TemporalType.TIMESTAMP)
	private Date dataAtualizacao;

	private boolean ativo;
	private float avaliacao;
	private boolean destaque;

	// categoria marca

	public boolean isDestaque() {
		return destaque;
	}

	public void setDestaque(boolean destaque) {
		this.destaque = destaque;
	}

	public float getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(float avaliacao) {
		this.avaliacao = avaliacao;
	}

	// @OneToMany(mappedBy ="produtoCategoria")
	// private List<CategoriaModelo> categoria = new ArrayList<>();
	//
	// public List<CategoriaModelo> getCategoria() {
	// return categoria;
	// }
	//
	// public void setCategoria(List<CategoriaModelo> categoria) {
	// this.categoria = categoria;
	// }
	@Column(name = "imagem_principal")
	private String imagemPrincipal;

	@OneToMany(mappedBy = "produtoimagem", cascade = CascadeType.ALL)
	private Set<ProdutoImagemModelo> imagemExtra = new HashSet<>();

	@Transient
	public String getImagemPrincipal() {
		if (id == null || imagemPrincipal == null)
			return "/imagens/imagem-padrao.png";
		return "/img/" + this.id + "/" + this.imagemPrincipal;
	}

	public void setImagemPrincipal(String imagemPrincipal) {
		this.imagemPrincipal = imagemPrincipal;
	}

	public Set<ProdutoImagemModelo> getImagemExtra() {
		return imagemExtra;
	}

	public void setImagemExtra(Set<ProdutoImagemModelo> imagemExtra) {
		this.imagemExtra = imagemExtra;
	}

	public void adicionarImagemExtra(String nomeImagem) {
		this.imagemExtra.add(new ProdutoImagemModelo(nomeImagem, this));
	}

	@OneToMany
	private List<CategoriaModelo> categoria = new ArrayList<>();

	public List<CategoriaModelo> getCategoria() {
		return categoria;
	}

	public void setCategoria(List<CategoriaModelo> categoria) {
		this.categoria = categoria;
	}

	public ProdutoModelo(Long id, Long codigo, String nome, String descricaoCurta,
			@Size(min = 10, max = 5000) String descricaoLonga, float preco, Integer quantidade, Date dataCriacao,
			Date dataAtualizacao, boolean ativo, float avaliacao, boolean destaque, String imagemPrincipal,
			Set<ProdutoImagemModelo> imagemExtra, List<CategoriaModelo> categoria) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.nome = nome;
		this.descricaoCurta = descricaoCurta;
		this.descricaoLonga = descricaoLonga;
		this.preco = preco;
		this.quantidade = quantidade;
		this.dataCriacao = dataCriacao;
		this.dataAtualizacao = dataAtualizacao;
		this.ativo = ativo;
		this.avaliacao = avaliacao;
		this.destaque = destaque;
		this.imagemPrincipal = imagemPrincipal;
		this.imagemExtra = imagemExtra;
		this.categoria = categoria;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ProdutoModelo() {

	}

	public ProdutoModelo(Long id, Long codigo, String nome, String descricaoCurta, String descricaoLonga, float preco,
			Integer quantidade, Date dataCriacao, Date dataAtualizacao, boolean ativo) {

		this.id = id;
		this.codigo = codigo;
		this.nome = nome;
		this.descricaoCurta = descricaoCurta;
		this.descricaoLonga = descricaoLonga;
		this.preco = preco;
		this.quantidade = quantidade;
		this.dataCriacao = dataCriacao;
		this.dataAtualizacao = dataAtualizacao;
		this.ativo = ativo;
	}

	@Override
	public String toString() {
		return "ProdutoModelo [id=" + id + ", codigo=" + codigo + ", nome=" + nome + ", descricaoCurta="
				+ descricaoCurta + ", descricaoLonga=" + descricaoLonga + ", preco=" + preco + ", quantidade="
				+ quantidade + ", dataCriacao=" + dataCriacao + ", dataAtualizacao=" + dataAtualizacao + ", ativo="
				+ ativo + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricaoCurta() {
		return descricaoCurta;
	}

	public void setDescricaoCurta(String descricaoCurta) {
		this.descricaoCurta = descricaoCurta;
	}

	public String getDescricaoLonga() {
		return descricaoLonga;
	}

	public void setDescricaoLonga(String descricaoLonga) {
		this.descricaoLonga = descricaoLonga;
	}

	public float getPreco() {
		return preco;
	}

	public void setPreco(float preco) {
		this.preco = preco;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	private Double valorVenda;

	public Double getValorVenda() {
		return valorVenda;
	}

	public void setValorVenda(Double valorVenda) {
		this.valorVenda = valorVenda;
	}

}
