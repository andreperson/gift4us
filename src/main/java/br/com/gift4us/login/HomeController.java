package br.com.gift4us.login;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.gift4us.mensagensdosistema.Erros;
import br.com.gift4us.mensagensdosistema.MensagensDoSistemaDAO;
import br.com.gift4us.mensagensdosistema.Sucesso;
import br.com.gift4us.urls.ListaDeURLs;
import br.com.gift4us.usuario.UsuarioAdaptador;
import br.com.gift4us.usuario.UsuarioDAO;
import br.com.gift4us.usuario.UsuarioModel;

@Controller
public class HomeController {

	@Autowired
	private Erros erros;

	@Autowired
	private Sucesso sucesso;

	@Autowired
	private MensagensDoSistemaDAO mensagensDoSistemaDAO;
	
	@Autowired
	private UsuarioDAO usuarioDAO;

	@Autowired
	private UsuarioAdaptador usuarioAdaptador;

	@Secured({ "ROLE_USUARIO_LOGADO" })
	@RequestMapping(value = ListaDeURLs.HOME, method = RequestMethod.GET)
	public String paginaPrincipal(HttpServletResponse response, Model model) {
		
		UsuarioModel usuario = usuarioDAO.buscaPorId(usuarioAdaptador.obterUsuarioLogado().getId());
		
		if(comparaSenhas(usuario.getSenha())) {
			return "redirect:" + ListaDeURLs.USUARIO_PERFIL + "/" + usuario.getId();
		}
		
		return "administracao/home/home";
	}
	

	@Secured({ "ROLE_USUARIO_LOGADO" })
	@RequestMapping(value = ListaDeURLs.ALTERAR_SENHA, method = RequestMethod.POST)
	public String alteraSenha(@Valid AlterarSenhaModel asm, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {
	
		UsuarioModel usuario = usuarioDAO.buscaPorId(usuarioAdaptador.obterUsuarioLogado().getId());
	
		if (!BCrypt.checkpw(asm.getSenhaAtual(), usuario.getPassword())) {
			ObjectError arg0 = new ObjectError("SenhaAtualNaoConfere", "SenhaAtualNaoConfere");
			bindingResult.addError(arg0);
		}
	
		if (bindingResult.hasErrors()) {
			erros.setRedirectOrModel(model);
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for (ObjectError objectError : allErrors) {
				erros.adiciona(mensagensDoSistemaDAO.buscaPorError(objectError));
			}
			return "administracao/home/alterarSenha";
		}
		
		usuario.setSenha(asm.getNovaSenha());
		usuarioDAO.altera(usuario);
		
		sucesso.setMensagem(redirectAttributes,
				mensagensDoSistemaDAO.buscaPorPropriedade("MensagemAlteradoComSucesso").getValor());
		return "redirect:" + ListaDeURLs.HOME;
	}
	
	@Secured({ "ROLE_REDEFINIR_SENHA" })
	@RequestMapping(value = ListaDeURLs.REDEFINIR_SENHA, method = RequestMethod.POST)
	public String redefinicaoDeSenha(Long id, RedirectAttributes redirectAttributes) {
		UsuarioModel usuario = usuarioDAO.buscaPorId(id);
		usuario.setSenha("123456");
		usuarioDAO.altera(usuario);
		sucesso.setMensagem(redirectAttributes,
				mensagensDoSistemaDAO.buscaPorPropriedade("MensagemAlteradoComSucesso").getValor());
		return "redirect:" + ListaDeURLs.LISTA_DE_USUARIO;
	}
	

	@RequestMapping(value=ListaDeURLs.ALTERAR_SENHA, method=RequestMethod.GET)
	public String carregaFormularioDeAlteracaoDeSenha(){
		return "administracao/home/alterarSenha";
	}

	private boolean comparaSenhas(String senhaatual) {
		Boolean precisamudar = false;
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();  
		if (encoder.matches("123456", senhaatual)) {
			precisamudar=true;
		}
			return precisamudar;
	}
}
