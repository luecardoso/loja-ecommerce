package br.senac.ecommerce.pi.loja.controlador;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senac.ecommerce.pi.loja.modelo.CargoModelo;
import br.senac.ecommerce.pi.loja.modelo.UsuarioModelo;
import br.senac.ecommerce.pi.loja.servico.UsuarioServico;

@Controller
@RequestMapping("/administrador")
public class UsuarioControlador {

	@Autowired
	UsuarioServico usuarioServico;

	
	@GetMapping("/usuario")
	public String mostrarUsuariosPaginacao(Model model) {
		return listarComPaginacao(1, model, null);
	}
	
	@GetMapping("")
	public String teste() {
		return "layout/layout";
	}
	
	@GetMapping("/usuario/cadastrar")
	public String cadastrar(Model model) {
		List<CargoModelo> listarCargos = usuarioServico.listarCargos();
		UsuarioModelo usuario = new UsuarioModelo();
		model.addAttribute("listaCargos", listarCargos);
		model.addAttribute("listaUsuarioInfo", usuario);
		
		return "adm/formulario-usuario";
	}
	
	@PostMapping("/usuario/salvar")
	public String salvarUsuario(@Valid UsuarioModelo usuario, RedirectAttributes redirectAttributes, BindingResult bindingResult) {
		if(bindingResult.hasFieldErrors()) {
			return "redirect:/administrador/usuario/cadastrar";
		}
		usuarioServico.salvarUsuario(usuario);
		redirectAttributes.addFlashAttribute("mensagem", "Usuário salvo com sucesso!");
		return "redirect:/administrador/usuario/cadastrar";
	}
	
	
	@GetMapping("/usuario/editar/{id}")
	public ModelAndView editarUsuario(@PathVariable("id") Long id) {		
		ModelAndView mv = new ModelAndView("adm/formulario-usuario");
		UsuarioModelo listaInformacaoUsuario = usuarioServico.editarUsuario(id);
		List<CargoModelo> listaCargo = usuarioServico.listarCargos();
		mv.addObject("listaUsuarioInfo", listaInformacaoUsuario);
		mv.addObject("listaCargo", listaCargo);
		return mv;
	}
	
	@GetMapping("/usuario/deletar/{id}")
	public String deletarUsuario(@PathVariable("id") Long id) {		
		ModelAndView mv = new ModelAndView("adm/formulario-usuario");
		usuarioServico.deletarUsuario(id);
		return "redirect:/administrador/usuario";
	}
	
	@GetMapping("/usuario/{id}/ativo/{status}")
	public String atualizarStatusAtivadoUsuario(@PathVariable("id") Long id, @PathVariable("status") boolean enabled,
			RedirectAttributes redirectAttributes) {

		usuarioServico.atualizarStatusAtivo(id, enabled);
		String status = enabled ? "ativado" : "desativado";
		String message = "O usuário " + id + " foi " + status;
		redirectAttributes.addFlashAttribute("mensagemStatus", message);

		return "redirect:/administrador/usuario";

	}
	
	@PostMapping("/usuarios/checar_email")
	public String checarEmailDuplicado(@Param("email") String email, @Param("id") Long id) {
		if (id == null) {
			return usuarioServico.emailUnico(email) ? "OK" : "Duplicado";
		} else {		
			return "OK";
		}
	}
	
	@GetMapping("/usuario/pagina/{numPagina}")
	public String listarComPaginacao(@PathVariable(name = "numPagina") int numPagina, Model model,
			@Param("keyword") String keyword) {

		Page<UsuarioModelo> pagina = usuarioServico.listarPorPagina(numPagina, keyword);
		List<UsuarioModelo> listarUsuarios = pagina.getContent();

		long comecoConta = (numPagina - 1) * UsuarioServico.USUARIOS_POR_PAGINA + 1;
		long finalConta = comecoConta + UsuarioServico.USUARIOS_POR_PAGINA - 1;

		if (finalConta > pagina.getTotalElements()) {
			finalConta = pagina.getTotalElements();
		}

		model.addAttribute("paginaAtual", numPagina);
		model.addAttribute("totalPaginas", pagina.getTotalPages());
		model.addAttribute("comecoConta", comecoConta);
		model.addAttribute("finalConta", finalConta);
		model.addAttribute("totalItens", pagina.getTotalElements());
		model.addAttribute("listarUsuarios", listarUsuarios);
		model.addAttribute("keyword", keyword);
		return "adm/index";

	}
}

