package br.senac.ecommerce.pi.loja.controlador;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import br.senac.ecommerce.pi.loja.modelo.UsuarioModelo;
import br.senac.ecommerce.pi.loja.servico.UsuarioServico;

@RestController
@RequestMapping("/administrador")
public class UsuarioControlador {

	@Autowired
	UsuarioServico usuarioServico;

	
	@GetMapping("")
	public ModelAndView mostrarListagemUsuarios() {
		List<UsuarioModelo> listaUsuarios = usuarioServico.listarTodosUsuarios();
		ModelAndView mv = new ModelAndView("adm/index");
		mv.addObject("listaUsuariosControlador", listaUsuarios);
		return mv;
	}
	
	@GetMapping("/usuario/cadastrar")
	public ModelAndView cadastrar(UsuarioModelo usuario) {
		ModelAndView mv = new ModelAndView("adm/formulario-usuario");
		mv.addObject("listaUsuarioInfo", usuario);
		return mv;
	}
	
	@PostMapping("/usuario/salvar")
	public ModelAndView salvarUsuario(@Valid UsuarioModelo usuario, RedirectAttributes redirectAttributes, BindingResult bindingResult) {
		System.out.println("Usuario:   ******** "+usuario);
		ModelAndView mv;
		if(bindingResult.hasFieldErrors()) {
			System.out.println("Erro:  *** "+bindingResult);
			//return new ModelAndView("/administrador/usuario/cadastrar");
//			mv = new ModelAndView("/administrador/usuario/cadastrar");
//			//mv.addObject(usuario);
//			
//			List<String> msg = new ArrayList<String>();
//			for(ObjectError erro: bindingResult.getAllErrors()){
//				msg.add(erro.getDefaultMessage());
//			}
//			mv = new ModelAndView("/administrador/usuario/cadastrar");
//			mv.addObject("msg",msg);
			
			redirectAttributes.addFlashAttribute("mensagem", "Usuário não cadastrado");
			mv = new ModelAndView("redirect:/administrador/usuario/cadastrar");
			mv.addObject("listaUsuarioInfo", usuario);
			//return cadastrar(usuario);
			return mv;
			
		}
		usuarioServico.salvarUsuario(usuario);
		redirectAttributes.addFlashAttribute("mensagem", "Usuário salvo com sucesso!");
		//mv = new ModelAndView("/administrador/usuario/cadastrar");
		mv = new ModelAndView("redirect:/administrador/usuario/cadastrar");
		//mv = new ModelAndView("adm/formulario-usuario");
		//mv.addObject("listaUsuarioInfo", usuario);
		//return new ModelAndView("adm/formulario-usuario");
		//return new ModelAndView("/administrador/usuario/cadastrar");
		//return new ModelAndView("adm/formulario-usuario");
		//redirectAttributes.addFlashAttribute("mensagem", "Usuário salvo com sucesso");
		//return cadastrar(usuario);
		return mv;
	}
	
	@GetMapping("/usuario/editar/{id}")
	public ModelAndView editarUsuario(@PathVariable("id") Long id) {		
		ModelAndView mv = new ModelAndView("adm/formulario-usuario");
		UsuarioModelo listaInformacaoUsuario = usuarioServico.editarUsuario(id);
		mv.addObject("listaUsuarioInfo", listaInformacaoUsuario);
		return mv;
	}
	
	@GetMapping("/usuario/deletar/{id}")
	public ModelAndView deletarUsuario(@PathVariable("id") Long id) {		
		ModelAndView mv = new ModelAndView("adm/formulario-usuario");
		usuarioServico.deletarUsuario(id);
		return mostrarListagemUsuarios();
	}
	
	@GetMapping("/usuario/{id}/ativo/{status}")
	public String atualizarStatusAtivadoUsuario(@PathVariable("id") Long id, @PathVariable("status") boolean enabled,
			RedirectAttributes redirectAttributes) {

		usuarioServico.atualizarStatusAtivo(id, enabled);
		String status = enabled ? "ativado" : "desativado";
		String message = "O usuário " + id + " foi " + status;
		redirectAttributes.addFlashAttribute("mensagemStatus", message);

		return "redirect:/administrador";

	}
	
	@PostMapping("/usuarios/checar_email")
	public String checarEmailDuplicado(@Param("email") String email, @Param("id") Long id) {
		if (id == null) {
			return usuarioServico.emailUnico(email) ? "OK" : "Duplicado";
		} else {		
			return "OK";
		}
	}
}

