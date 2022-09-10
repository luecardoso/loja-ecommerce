package br.senac.ecommerce.pi.loja.controlador;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senac.ecommerce.pi.loja.modelo.UsuarioModelo;
import br.senac.ecommerce.pi.loja.servico.UsuarioServico;

@Controller
public class UsuarioControlador {

	@Autowired
	UsuarioServico usuarioServico;

	
	@GetMapping("/administrador")
	public ModelAndView mostrarListagemUsuarios() {
		List<UsuarioModelo> listaUsuarios = usuarioServico.listarTodosUsuarios();
		ModelAndView mv = new ModelAndView("adm/index");
		mv.addObject("listaUsuariosControlador", listaUsuarios);
		return mv;
	}
	
	@GetMapping("/administrador/usuario/cadastrar")
	public ModelAndView cadastrar(UsuarioModelo usuario) {
		ModelAndView mv = new ModelAndView("adm/formulario-usuario");
		mv.addObject("cadastrarUsuarioControlador", usuario);
		return mv;
	}
	
	@PostMapping("/administrador/usuario/salvar")
	public ModelAndView salvarUsuario(@Valid UsuarioModelo usuario, RedirectAttributes redirectAttributes, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return cadastrar(usuario);
		}
		usuarioServico.salvarUsuarioFormulario(usuario);
		ModelAndView mv = new ModelAndView("redirect:/administrador");
		return mv;
	}
	
	@GetMapping("/administrador/usuario/editar/{id}")
	public ModelAndView editarUsuario(@PathVariable("id") Long id) {
		// Model model, RedirectAttributes redirectAttributes
//		UsuarioModelo  usuario = usuarioServico.editarUsuarioFormulario(id);
//		model.addAttribute("usuarioInformacaoControlador", usuario);
		
		ModelAndView mv = new ModelAndView("adm/formulario-usuario");
		UsuarioModelo listaUsuarios = usuarioServico.editarUsuarioFormulario(id);
		mv.addObject("listaUsuarioInfo", listaUsuarios);
		return mv;
	}
}
