package br.com.erika.layout.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.erika.layout.model.Produto;

@Controller
public class ProdutoController {

    List<Produto> produtos = new ArrayList<>();

    @GetMapping("/create")
    public ModelAndView home() {
        ModelAndView mv = new ModelAndView("create");
        mv.addObject("produto", new Produto());
        return mv;
    }

    @PostMapping("/create")
    public String create(Produto produto) {

        if (produto.getId() != null) {
            Produto produtoFind = produtos.stream().filter(produtoItem -> produto.getId().equals(produtoItem.getId()))
                    .findFirst().get();
            produtos.set(produtos.indexOf(produtoFind), produto);
        } else {
            Long id = produtos.size() + 1L;
            produtos.add(new Produto(id, produto.getName(), produto.getDate()));
        }

        return "redirect:/list";
    }

    @GetMapping("/list")
    public ModelAndView list() {
        ModelAndView mv = new ModelAndView("list");
        mv.addObject("produtos", produtos);
        return mv;
    }

    /**
     * @param id
     * @return
     */
    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("create");

        Produto produtoFind = produtos.stream().filter(produto -> id.equals(produto.getId())).findFirst().get();

        mv.addObject("produto", produtoFind);
        return mv;

    }

}
