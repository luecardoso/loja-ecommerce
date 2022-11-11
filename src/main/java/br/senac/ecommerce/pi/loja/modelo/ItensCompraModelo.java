package br.senac.ecommerce.pi.loja.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "itens_compra")
public class ItensCompraModelo implements Serializable {
    public ItensCompraModelo() {
        super();
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private ProdutoModelo produto;

    @ManyToOne
    private CompraModelo compra;

    private Integer quantidade;

    private Double valorUnitario;

    private Double valorTotal;

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProdutoModelo getProduto() {
        return produto;
    }

    public void setProduto(ProdutoModelo produto) {
        this.produto = produto;
    }

    public CompraModelo getCompra() {
        return compra;
    }

    public void setCompra(CompraModelo compra) {
        this.compra = compra;
    }

    public Integer getQuantidade() {
        if(quantidade ==null){
            quantidade=0;
        }
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }
}    
