package br.senac.ecommerce.pi.loja.controlador;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import br.senac.ecommerce.pi.loja.modelo.ItensCompraModelo;
import br.senac.ecommerce.pi.loja.modelo.ProdutoModelo;
import br.senac.ecommerce.pi.loja.repositorio.ProdutoRepositorio;

@Controller
public class CarrinhoControlador {

    private List<ItensCompraModelo> itensCompra = new ArrayList<>();

    @Autowired
    private ProdutoRepositorio repositorioProduto;

    @GetMapping("/carrinho")
    public ModelAndView chamarCarrinho() {
        ModelAndView mv = new ModelAndView("carrinho");
        mv.addObject("listaItens", itensCompra);
        return mv;
    }

    @GetMapping("/alterarQuantidade/{id}/{acao}")
    public ModelAndView alterarQuantidade(@PathVariable Long id, @PathVariable Integer acaoInteger) {
        ModelAndView mv = new ModelAndView("carrinho");

        for (ItensCompraModelo it : itensCompra) {
            if (it.getProduto().getId().equals(id)) {
                if (acaoInteger.equals(1)) {
                    it.setQuantidade(it.getQuantidade() + 1);
                } else if (acaoInteger == 0) {
                    it.setQuantidade(it.getQuantidade() - 1);
                }
                break;
            }
        }

        mv.addObject("listaItens", itensCompra);
        return mv;
    }

    @GetMapping("/adicionarCarrinho/{id}")
    public ModelAndView adicionarCarrinho(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("carrinho");

        Optional<ProdutoModelo> prod = repositorioProduto.findById(id);
        ProdutoModelo produto = prod.get();

        int controle = 0;
        for (ItensCompraModelo it : itensCompra) {
            if (it.getProduto().getId().equals(produto.getId())) {
                it.setQuantidade(it.getQuantidade() + 1);
                controle = 1;
                break;
            }
        }
        if (controle == 0) {
            ItensCompraModelo item = new ItensCompraModelo();
            item.setProduto(produto);
            item.setValorUnitario(produto.getValorVenda());
            item.setQuantidade(item.getQuantidade() + 1);
            item.setValorTotal(item.getQuantidade() * item.getValorUnitario());
            itensCompra.add(item);
        }
        mv.addObject("listaItens", itensCompra);
        return mv;
    }

}
