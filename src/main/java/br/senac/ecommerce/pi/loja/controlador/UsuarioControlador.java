package br.senac.ecommerce.pi.loja.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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

	@GetMapping("")
	public String telaInicial() {
		return "layout/layout";
	}

	@GetMapping("/usuario")
	public String mostrarUsuariosPaginacao(Model model) {
		return listarComPaginacao(1, model, null);
	}

	@GetMapping("/usuario/cadastrar")
	public String cadastrar(Model model, UsuarioModelo usuario) {
		List<CargoModelo> listarCargos = usuarioServico.listarCargos();
		model.addAttribute("usuarioModelo", usuario);
		model.addAttribute("listaCargo", listarCargos);
		return "adm/formulario-usuario";
	}

	@PostMapping("/usuario/salvar")
	public String salvarUsuario(@Valid UsuarioModelo usuario, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, Model model) {
		/* VERIFICA CAMPOS COM ERROS */
		if (bindingResult.hasErrors()) {
			model.addAttribute("usuarioModelo", usuario);
			redirectAttributes.addFlashAttribute("mensagemErro", "Campo " + bindingResult.getFieldError().getField()
					+ " com problema: \n" + bindingResult.getFieldError().getDefaultMessage());
			return "redirect:/administrador/usuario/cadastrar";
		}

		/*VERIFICA SE ESTÁ EDITANDO USUARIO*/
		boolean estaAtualizandoUsuario = (usuario.getId() != null);
		if (estaAtualizandoUsuario) {
			usuarioServico.salvarUsuario(usuario);
			model.addAttribute("usuarioModelo", usuario);
			redirectAttributes.addFlashAttribute("mensagem", "Usuário Editado com sucesso!");
			return "redirect:/administrador/usuario/cadastrar";
		}
		int emailDuplicado = usuarioServico.quantidadeEmail(usuario.getEmail());
		int cpfDuplicado = usuarioServico.quantidadeCpf(usuario.getCpf());
		/* VERIFICAR CPF DUPLICADO */
		if (cpfDuplicado >= 1) {
			redirectAttributes.addFlashAttribute("mensagemDuplicado", "CPF ja consta na base de dados");
			return "redirect:/administrador/usuario/cadastrar";
		}

		/* VERIFICAR EMAIL DUPLICADO */
		if (emailDuplicado >= 1) {
			redirectAttributes.addFlashAttribute("mensagemDuplicado", " Esse email ja consta na base de dados");
			return "redirect:/administrador/usuario/cadastrar";

		}

		/*SALVA NOVO USUÁRIO*/
		usuarioServico.salvarUsuario(usuario);
		model.addAttribute("usuarioModelo", usuario);
		redirectAttributes.addFlashAttribute("mensagem", "Usuário salvo com sucesso!");
		return "redirect:/administrador/usuario/cadastrar";
	}

	@GetMapping("/usuario/editar/{id}")
	public String editarUsuario(@PathVariable(name = "id") Long id, Model model,
			RedirectAttributes redirectAttributes) {

		UsuarioModelo listaInformacaoUsuario = usuarioServico.editarUsuario(id);
		List<CargoModelo> listaCargo = usuarioServico.listarCargos();
		model.addAttribute("usuarioModelo", listaInformacaoUsuario);
		model.addAttribute("listaCargo", listaCargo);
		return "adm/formulario-usuario";
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

//	@PostMapping("/usuario/checar_email")
//	public String checarEmailDuplicado(@Param("email") String email, @Param("id") Long id) {
//		if (id == null) {
//			return usuarioServico.emailUnico(email) ? "OK" : "Duplicado";
//		} else {		
//			return "OK";
//		}
//	}

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
		return "adm/usuario";

	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@PostMapping("/error")
	public Map<String, String> requisao(MethodArgumentNotValidException ex, RedirectAttributes attributes) {
		Map<String, String> problema = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String campoNome = ((FieldError) problema).getField();
			String mensagemErro = error.getDefaultMessage();

			problema.put(campoNome, mensagemErro);
		});

		return problema;

	}
}
