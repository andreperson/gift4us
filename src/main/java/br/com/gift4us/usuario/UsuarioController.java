package br.com.gift4us.usuario;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.core.io.FileSystemResource;
import javax.validation.Valid;
import org.springframework.ui.Model;
import java.util.List;
import java.lang.reflect.Method;
import java.io.File;
import br.com.gift4us.urls.ListaDeURLs;
import br.com.gift4us.util.AbrirOuBaixarArquivo;
import br.com.gift4us.util.UploadDeArquivo;
import br.com.gift4us.anunciante.AnuncianteDAO;
import br.com.gift4us.anunciante.AnuncianteModel;
import br.com.gift4us.configuracoesdosistema.ConfiguracoesDoSistemaDAO;
import br.com.gift4us.mensagensdosistema.Erros;
import br.com.gift4us.mensagensdosistema.Sucesso;
import br.com.gift4us.historicodosistema.GerenciadorDeHistorico;
import br.com.gift4us.mensagensdosistema.MensagensDoSistemaDAO;

@Controller
public class UsuarioController {

	@Autowired
	private Erros erros;

	@Autowired
	private Sucesso sucesso;

	@Autowired
	private UsuarioDAO usuarioDAO;

	@Autowired
	private AnuncianteDAO anuncianteDAO;
	
	@Autowired
	private MensagensDoSistemaDAO mensagensDoSistemaDAO;

	@Autowired
	private GerenciadorDeHistorico historico;

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.LISTA_DE_USUARIO, method = RequestMethod.GET)
	public String lista(Model model) {
		model.addAttribute("listaDeUsuario", usuarioDAO.listaTudo());
		return "administracao/usuario/lista";
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.FORMULARIO_INSERCAO_DE_USUARIO, method = RequestMethod.GET)
	public String carregaFormularioParaInsercao(Model model) {
		return "administracao/usuario/formulario";
	}
	
	@Secured({ "ROLE_USUARIO_LOGADO" })
	@RequestMapping(value = ListaDeURLs.USUARIO_PERFIL + "/{id}", method = RequestMethod.GET)
	public String carregaPerfil(@PathVariable Long id, Model model) {
		UsuarioModel usuario = usuarioDAO.buscaPorId(id);
		model.addAttribute("usuario", usuario);
		return "administracao/usuario/perfil";
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.FORMULARIO_EDICAO_DE_USUARIO + "/{id}", method = RequestMethod.GET)
	public String carregaFormularioParaEdicao(@PathVariable Long id, Model model) {
		UsuarioModel usuario = usuarioDAO.buscaPorId(id);
		model.addAttribute("usuario", usuario);
		return "administracao/usuario/formulario";
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.INSERCAO_DE_USUARIO, method = RequestMethod.POST)
	public String insere(@Valid UsuarioModel usuario, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

		if(bindingResult.hasErrors()){
			model.addAttribute("usuario", usuario);
			erros.setRedirectOrModel(model);
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for (ObjectError objectError : allErrors) {
				erros.adiciona(mensagensDoSistemaDAO.buscaPorError(objectError));
			}
			return "administracao/usuario/formulario";
		}

		List<UsuarioModel> usuarioDoBanco = usuarioDAO.buscaPorLogin(usuario.getLogin());
		if (!usuarioDoBanco.isEmpty()) {
			model.addAttribute("usuario", usuario);
			erros.setRedirectOrModel(model);
			erros.adiciona("Já existe usuário cadastrado com o login informado.");
			return "administracao/usuario/formulario";
		}

		usuario.setSenha("123456");
		usuarioDAO.insere(usuario);
		UsuarioModel encontrado = usuarioDAO.buscaPorId(usuario.getId());
		historico.inserir(encontrado, "Usuário");
		sucesso.setMensagem(redirectAttributes, mensagensDoSistemaDAO.buscaPorPropriedade("MensagemAdicionadoComSucesso").getValor());
		return "redirect:"+ListaDeURLs.LISTA_DE_USUARIO;
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.EDICAO_DE_USUARIO, method = RequestMethod.POST)
	public String altera(@Valid UsuarioModel usuario, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

		if(bindingResult.hasErrors()){
			model.addAttribute("usuario", usuario);
			erros.setRedirectOrModel(model);
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for (ObjectError objectError : allErrors) {
				erros.adiciona(mensagensDoSistemaDAO.buscaPorError(objectError));
			}
			return "administracao/usuario/formulario";
		}

		List<UsuarioModel> usuarioDoBanco = usuarioDAO.buscaPorLogin(usuario.getLogin());
		if (!usuarioDoBanco.isEmpty() && !usuarioDoBanco.get(0).getId().equals(usuario.getId())) {
			model.addAttribute("usuario", usuario);
			erros.setRedirectOrModel(model);
			erros.adiciona("Já existe usuário cadastrado com o login informado.");
			return "administracao/usuario/formulario";
		}

		UsuarioModel anterior = usuarioDAO.buscaPorIdClonando(usuario.getId());

		usuario = completaInformacoesDoUsuario(usuarioDoBanco, usuario);
		usuarioDAO.altera(usuario);
		UsuarioModel atual = usuarioDAO.buscaPorIdClonando(usuario.getId());
		historico.alterar(anterior, atual, "Usuário");
		sucesso.setMensagem(redirectAttributes, mensagensDoSistemaDAO.buscaPorPropriedade("MensagemAlteradoComSucesso").getValor());
		return "redirect:"+ListaDeURLs.LISTA_DE_USUARIO;
	}
	
	private UsuarioModel completaInformacoesDoUsuario(List<UsuarioModel> usuariodobanco, UsuarioModel usuariodoform) {
		
		for (UsuarioModel ubanco : usuariodobanco) {
			usuariodoform.setAbout(ubanco.getAbout());
			usuariodoform.setDddcelular(ubanco.getDddcelular());
			usuariodoform.setCelular(ubanco.getCelular());
			usuariodoform.setDddtelefone(ubanco.getDddtelefone());
			usuariodoform.setTelefone(ubanco.getTelefone());
		}
		
		return usuariodoform;
	}
	
	@Secured({ "ROLE_USUARIO_LOGADO" })
	@RequestMapping(value = ListaDeURLs.USUARIO_PERFIL_ALTERA, method = RequestMethod.POST)
	public String perfilaltera(@Valid UsuarioModel usuario, @RequestParam("anuncianteid") Long anuncianteid,BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

		if(bindingResult.hasErrors()){
			model.addAttribute("usuario", usuario);
			erros.setRedirectOrModel(model);
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for (ObjectError objectError : allErrors) {
				erros.adiciona(mensagensDoSistemaDAO.buscaPorError(objectError));
			}
			return "administracao/usuario/perfil";
		}

		UsuarioModel anterior = usuarioDAO.buscaPorIdClonando(usuario.getId());
		usuario.setAnunciante(anterior.getAnunciante());
		usuario.setListaDeGrupo(anterior.getListaDeGrupo());
		usuarioDAO.altera(usuario);
		
		UsuarioModel atual = usuarioDAO.buscaPorIdClonando(usuario.getId());
		historico.alterar(anterior, atual, "Usuário");
		//sucesso.setMensagem(redirectAttributes, mensagensDoSistemaDAO.buscaPorPropriedade("MensagemAlteradoComSucesso").getValor());
		return "redirect:"+ListaDeURLs.HOME;
	}
	
	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.EXCLUSAO_DE_USUARIO, method = RequestMethod.POST)
	public String exclui(@Valid UsuarioModel usuario, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

		UsuarioModel encontrado = usuarioDAO.buscaPorIdClonando(usuario.getId());
		try {
			usuarioDAO.exclui(encontrado);
		} catch (Exception e) {
			erros.setRedirectOrModel(redirectAttributes);
			erros.adiciona("Não foi possível excluir o registro. Verificar se o registro está sendo utilizado em outras partes do sistema.");
			return "redirect:"+ListaDeURLs.LISTA_DE_USUARIO;
		}
		historico.excluir(encontrado, "Usuário");
		sucesso.setMensagem(redirectAttributes, mensagensDoSistemaDAO.buscaPorPropriedade("MensagemExcluidoComSucesso").getValor());
		return "redirect:"+ListaDeURLs.LISTA_DE_USUARIO;
	}

}