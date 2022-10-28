package br.senac.ecommerce.pi.loja.controlador;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senac.ecommerce.pi.loja.modelo.CategoriaModelo;
import br.senac.ecommerce.pi.loja.modelo.MarcaModelo;
import br.senac.ecommerce.pi.loja.servico.CategoriaServico;
import br.senac.ecommerce.pi.loja.servico.MarcaServico;

@Controller
@RequestMapping("/administrador")
public class MarcaControlador {

    @Autowired
    MarcaServico marcaServico;
    public static String CAMINHO_IMAGEM = System.getProperty("user.dir") + "/img/marca/";

    @GetMapping("/marca")
    public String mostrarComPaginacao(Model model) {
        return listarComPaginacao(1, model, null);
    }

    @GetMapping("/marca/pagina/{numPagina}")
    public String listarComPaginacao(@PathVariable(name = "numPagina") int numPagina, Model model,
            @Param("keyword") String keyword) {

        Page<MarcaModelo> pagina = marcaServico.listarPorPagina(numPagina, keyword);
        List<MarcaModelo> listar = pagina.getContent();

        long comecoConta = (numPagina - 1) * MarcaServico.MARCA_POR_PAGINA + 1;
        long finalConta = comecoConta + MarcaServico.MARCA_POR_PAGINA - 1;

        if (finalConta > pagina.getTotalElements()) {
            finalConta = pagina.getTotalElements();
        }

        List<MarcaModelo> listaMarca = marcaServico.listaMarca();
        model.addAttribute("listaMarca", listaMarca);
        model.addAttribute("paginaAtual", numPagina);
        model.addAttribute("totalPaginas", pagina.getTotalPages());
        model.addAttribute("comecoConta", comecoConta);
        model.addAttribute("finalConta", finalConta);
        model.addAttribute("totalItens", pagina.getTotalElements());
        model.addAttribute("listarMarcas", listar);
        model.addAttribute("keyword", keyword);
        return "adm/marca";

    }

    @GetMapping("/marca/cadastrar")
    public String cadastrar(Model model, MarcaModelo marca) {
        model.addAttribute("marcaModelo", marca);
        return "adm/formulario-marca";
    }

    /**
     * @param marca
     * @param bindingResult
     * @param redirectAttributes
     * @param model
     * @param arquivo
     * @return
     */
    @PostMapping("/marca/salvar")
    public String salvar(@Valid CategoriaModelo marca, BindingResult bindingResult,
            RedirectAttributes redirectAttributes, Model model,
            @RequestParam("imagem") MultipartFile arquivo) {

        /* VERIFICA CAMPOS COM ERROS */
        // if (bindingResult.hasErrors()) {
        // model.addAttribute("categoriaModelo", categoria);
        // redirectAttributes.addFlashAttribute("mensagemErro", "Campo " +
        // bindingResult.getFieldError().getField()
        // + " com problema: \n" + bindingResult.getFieldError().getDefaultMessage());
        // return "redirect:/administrador/categoria/cadastrar";
        // }
        /* ADICIONA IMAGEM */
        StringBuilder nomeDoArquivo = new StringBuilder();
        try {
            Path nomeDoCaminho = Paths.get(CAMINHO_IMAGEM, arquivo.getOriginalFilename());
            nomeDoArquivo.append(arquivo.getOriginalFilename());

            Files.write(nomeDoCaminho, arquivo.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }

        marca.setImagem("" + nomeDoArquivo);
        /* SALVA PRODUTO */
        marcaServico.salvar(marca);
        model.addAttribute("marcaModelo", marca);
        redirectAttributes.addFlashAttribute("mensagem", "Marca salva com sucesso!");
        return "redirect:/administrador/marca/cadastrar";
    }

    @GetMapping("/marca/{id}/ativo/{status}")
    public String atualizarStatusAtivadoUsuario(@PathVariable("id") Long id, @PathVariable("status") boolean enabled,
            RedirectAttributes redirectAttributes) {

        marcaServico.atualizarStatusAtivo(id, enabled);
        String status = enabled ? "ativado" : "desativado";
        String message = "A marca " + id + " foi " + status;
        redirectAttributes.addFlashAttribute("mensagemStatus", message);
        return "redirect:/administrador/marca";

    }

    /**
     * @param id
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/marca/editar/{id}")
    public ModelAndView editarProduto(@PathVariable(name = "id") Long id,
            RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView("/adm/formulario-marca");
        final MarcaModelo listaMarcaInfo = marcaServico.editar(id);
        if (listaMarcaInfo.getImagem() != null) {
            listaMarcaInfo.setImagem(listaMarcaInfo.getImagem());
        }
        // model.addAttribute("marcaModelo", listaMarcaInfo);
        mv.addObject("marcaModelo", listaMarcaInfo);
        return mv;
    }

    @GetMapping("/marca/deletar/{id}")
    public ModelAndView deletarUsuario(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("redirect:/administrador/marca");
        marcaServico.deletar(id);
        return mv;
    }

    @GetMapping("/marca/mostrarImagem/{imagemPrincipal}")
    @ResponseBody
    public byte[] mostrarImagem(@PathVariable("imagemPrincipal") String nomeImagem) {
        File imagemArquivo = new File(CAMINHO_IMAGEM + nomeImagem);
        if (nomeImagem != null || nomeImagem.trim().length() > 0) {
            try {
                return Files.readAllBytes(imagemArquivo.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}