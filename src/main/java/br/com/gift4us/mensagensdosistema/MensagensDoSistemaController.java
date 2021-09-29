package br.com.gift4us.mensagensdosistema;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
import org.springframework.core.io.FileSystemResource;
import javax.validation.Valid;
import org.springframework.ui.Model;
import java.util.List;
import java.lang.reflect.Method;
import java.io.File;
import br.com.gift4us.urls.ListaDeURLs;
import br.com.gift4us.util.AbrirOuBaixarArquivo;
import br.com.gift4us.util.UploadDeArquivo;
import br.com.gift4us.configuracoesdosistema.ConfiguracoesDoSistemaDAO;
import br.com.gift4us.grupo.GrupoModel;
import br.com.gift4us.mensagensdosistema.Erros;
import br.com.gift4us.mensagensdosistema.Sucesso;
import br.com.gift4us.historicodosistema.GerenciadorDeHistorico;

@Controller
public class MensagensDoSistemaController {

	@Autowired
	private Erros erros;

	@Autowired
	private Sucesso sucesso;

	@Autowired
	private MensagensDoSistemaDAO mensagensDoSistemaDAO;

	@Autowired
	private GerenciadorDeHistorico historico;

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.LISTA_DE_MENSAGENSDOSISTEMA, method = RequestMethod.GET)
	public String lista(Model model) {
		model.addAttribute("listaDeMensagensDoSistema", mensagensDoSistemaDAO.listaTudoSemCache());
		return "administracao/mensagensdosistema/lista";
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.FORMULARIO_INSERCAO_DE_MENSAGENSDOSISTEMA, method = RequestMethod.GET)
	public String carregaFormularioParaInsercao(Model model) {
		return "administracao/mensagensdosistema/formulario";
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.FORMULARIO_EDICAO_DE_MENSAGENSDOSISTEMA + "/{id}", method = RequestMethod.GET)
	public String carregaFormularioParaEdicao(@PathVariable String id, Model model) {
		MensagensDoSistemaModel mensagensDoSistema = mensagensDoSistemaDAO.buscaPorPropriedadeSemCache(id);
		model.addAttribute("mensagensDoSistema", mensagensDoSistema);
		return "administracao/mensagensdosistema/formulario";
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.INSERCAO_DE_MENSAGENSDOSISTEMA, method = RequestMethod.POST)
	public String insere(@Valid MensagensDoSistemaModel mensagensDoSistema, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("mensagensDoSistema", mensagensDoSistema);
			erros.setRedirectOrModel(model);
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for (ObjectError objectError : allErrors) {
				erros.adiciona(mensagensDoSistemaDAO.buscaPorError(objectError));
			}
			return "administracao/mensagensDoSistema/formulario";
		}

		// verifica se ja existe um cadastro com esse nome
		List<MensagensDoSistemaModel> lst = mensagensDoSistemaDAO
				.buscaPorPropriedadeExata(mensagensDoSistema);
		if (lst.size() > 0) {
			String msg = mensagensDoSistemaDAO.buscaPorPropriedade("RegistroDuplicado").getValor();

			model.addAttribute("mensagensDoSistema", mensagensDoSistema);
			model.addAttribute("alertademsg", msg);

			return "administracao/mensagensdosistema/formulario";
		}

		mensagensDoSistemaDAO.insere(mensagensDoSistema);
		MensagensDoSistemaModel encontrado = mensagensDoSistemaDAO
				.buscaPorPropriedade(mensagensDoSistema.getPropriedade());
		historico.inserir(encontrado, "Mensagens do sistema");
		sucesso.setMensagem(redirectAttributes,
				mensagensDoSistemaDAO.buscaPorPropriedade("MensagemAdicionadoComSucesso").getValor());
		return "redirect:" + ListaDeURLs.LISTA_DE_MENSAGENSDOSISTEMA;
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.EDICAO_DE_MENSAGENSDOSISTEMA, method = RequestMethod.POST)
	public String altera(@Valid MensagensDoSistemaModel mensagensDoSistema, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("mensagensDoSistema", mensagensDoSistema);
			erros.setRedirectOrModel(model);
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for (ObjectError objectError : allErrors) {
				erros.adiciona(mensagensDoSistemaDAO.buscaPorError(objectError));
			}
			return "administracao/mensagensdosistema/formulario";
		}

		// verifica se ja existe um cadastro com esse nome
		List<MensagensDoSistemaModel> lst = mensagensDoSistemaDAO
				.buscaPorPropriedadeExata(mensagensDoSistema);
		if (lst.size() > 0) {
			String msg = mensagensDoSistemaDAO.buscaPorPropriedade("RegistroDuplicado").getValor();

			model.addAttribute("mensagensDoSistema", mensagensDoSistema);
			model.addAttribute("alertademsg", msg);

			return "administracao/mensagensdosistema/formulario";
		}

		MensagensDoSistemaModel anterior = mensagensDoSistemaDAO
				.buscaPorPropriedadeClonando(mensagensDoSistema.getPropriedade());

		mensagensDoSistemaDAO.altera(mensagensDoSistema);
		MensagensDoSistemaModel atual = mensagensDoSistemaDAO
				.buscaPorPropriedadeClonando(mensagensDoSistema.getPropriedade());
		historico.alterar(anterior, atual, "Mensagens do sistema");
		sucesso.setMensagem(redirectAttributes,
				mensagensDoSistemaDAO.buscaPorPropriedade("MensagemAlteradoComSucesso").getValor());
		return "redirect:" + ListaDeURLs.LISTA_DE_MENSAGENSDOSISTEMA;
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.EXCLUSAO_DE_MENSAGENSDOSISTEMA, method = RequestMethod.POST)
	public String exclui(@Valid MensagensDoSistemaModel mensagensDoSistema, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {

		MensagensDoSistemaModel encontrado = mensagensDoSistemaDAO
				.buscaPorPropriedadeClonando(mensagensDoSistema.getPropriedade());
		try {
			mensagensDoSistemaDAO.exclui(encontrado);
		} catch (Exception e) {
			erros.setRedirectOrModel(redirectAttributes);
			erros.adiciona(
					"Não foi possível excluir o registro. Verificar se o registro está sendo utilizado em outras partes do sistema.");
			return "redirect:" + ListaDeURLs.LISTA_DE_MENSAGENSDOSISTEMA;
		}
		historico.excluir(encontrado, "Mensagens do sistema");
		sucesso.setMensagem(redirectAttributes,
				mensagensDoSistemaDAO.buscaPorPropriedade("MensagemExcluidoComSucesso").getValor());
		return "redirect:" + ListaDeURLs.LISTA_DE_MENSAGENSDOSISTEMA;
	}

}