package br.senac.ecommerce.pi.loja.modelo;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "produto")
public class ProdutoModelo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// @GeneratedValue(generator = "geradorId")
	private Long codigo;

	private String nome;

	private String descricaoCurta;

	private String descricaoLonga;

	private float preco;

	private Integer quantidade;

	private LocalDate dataCriacao;

	private LocalDate dataAtualizacao;

	private boolean ativo;

	public ProdutoModelo() {

	}

	public ProdutoModelo(Long id, Long codigo, String nome, String descricaoCurta, String descricaoLonga, float preco,
			Integer quantidade, LocalDate dataCriacao, LocalDate dataAtualizacao, boolean ativo) {

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

	public LocalDate getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public LocalDate getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(LocalDate dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	// categoria marca imagem avalicao
}
