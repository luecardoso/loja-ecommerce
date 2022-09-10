package br.senac.ecommerce.pi.loja.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import br.senac.ecommerce.pi.loja.modelo.UsuarioModelo;
import br.senac.ecommerce.pi.loja.servico.UsuarioServico;

@Controller
public class UsuarioControlador {

	@Autowired
	UsuarioServico usuarioServico;

	
	@GetMapping("/")
	public ModelAndView mostrarListagemUsuarios() {
		List<UsuarioModelo> listaUsuarios = usuarioServico.listarTodosUsuarios();
		ModelAndView mv = new ModelAndView("adm/index");
		mv.addObject("listaUsuariosControlador", listaUsuarios);
		return mv;
	}
}
