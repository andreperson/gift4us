package br.com.gift4us.configuracoesdosistema;

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
import br.com.gift4us.mensagensdosistema.Erros;
import br.com.gift4us.mensagensdosistema.Sucesso;
import br.com.gift4us.historicodosistema.GerenciadorDeHistorico;
import br.com.gift4us.mensagensdosistema.MensagensDoSistemaDAO;

@Controller
public class ConfiguracoesDoSistemaController {

	@Autowired
	private Erros erros;

	@Autowired
	private Sucesso sucesso;

	@Autowired
	private ConfiguracoesDoSistemaDAO configuracoesDoSistemaDAO;

	@Autowired
	private MensagensDoSistemaDAO mensagensDoSistemaDAO;

	@Autowired
	private GerenciadorDeHistorico historico;

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.LISTA_DE_CONFIGURACOESDOSISTEMA, method = RequestMethod.GET)
	public String lista(Model model) {
		model.addAttribute("listaDeConfiguracoesDoSistema", configuracoesDoSistemaDAO.listaTudoSemCache());
		return "administracao/configuracoesdosistema/lista";
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.FORMULARIO_INSERCAO_DE_CONFIGURACOESDOSISTEMA, method = RequestMethod.GET)
	public String carregaFormularioParaInsercao(Model model) {
		return "administracao/configuracoesdosistema/formulario";
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.FORMULARIO_EDICAO_DE_CONFIGURACOESDOSISTEMA + "/{id}", method = RequestMethod.GET)
	public String carregaFormularioParaEdicao(@PathVariable String id, Model model) {
		ConfiguracoesDoSistemaModel configuracoesDoSistema = configuracoesDoSistemaDAO.buscaPorPropriedadeSemCache(id);
		model.addAttribute("configuracoesDoSistema", configuracoesDoSistema);
		return "administracao/configuracoesdosistema/formulario";
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.INSERCAO_DE_CONFIGURACOESDOSISTEMA, method = RequestMethod.POST)
	public String insere(@Valid ConfiguracoesDoSistemaModel configuracoesDoSistema, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

		if(bindingResult.hasErrors()){
			model.addAttribute("configuracoesDoSistema", configuracoesDoSistema);
			erros.setRedirectOrModel(model);
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for (ObjectError objectError : allErrors) {
				erros.adiciona(mensagensDoSistemaDAO.buscaPorError(objectError));
			}
			return "administracao/configuracoesDoSistema/formulario";
		}

		configuracoesDoSistemaDAO.insere(configuracoesDoSistema);
		ConfiguracoesDoSistemaModel encontrado = configuracoesDoSistemaDAO.buscaPorPropriedade(configuracoesDoSistema.getPropriedade());
		historico.inserir(encontrado, "Configurações do sistema");
		sucesso.setMensagem(redirectAttributes, mensagensDoSistemaDAO.buscaPorPropriedade("MensagemAdicionadoComSucesso").getValor());
		return "redirect:"+ListaDeURLs.LISTA_DE_CONFIGURACOESDOSISTEMA;
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.EDICAO_DE_CONFIGURACOESDOSISTEMA, method = RequestMethod.POST)
	public String altera(@Valid ConfiguracoesDoSistemaModel configuracoesDoSistema, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

		if(bindingResult.hasErrors()){
			model.addAttribute("configuracoesDoSistema", configuracoesDoSistema);
			erros.setRedirectOrModel(model);
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for (ObjectError objectError : allErrors) {
				erros.adiciona(mensagensDoSistemaDAO.buscaPorError(objectError));
			}
			return "administracao/configuracoesdosistema/formulario";
		}

		ConfiguracoesDoSistemaModel anterior = configuracoesDoSistemaDAO.buscaPorPropriedadeClonando(configuracoesDoSistema.getPropriedade());

		configuracoesDoSistemaDAO.altera(configuracoesDoSistema);
		ConfiguracoesDoSistemaModel atual = configuracoesDoSistemaDAO.buscaPorPropriedadeClonando(configuracoesDoSistema.getPropriedade());
		historico.alterar(anterior, atual, "Configurações do sistema");
		sucesso.setMensagem(redirectAttributes, mensagensDoSistemaDAO.buscaPorPropriedade("MensagemAlteradoComSucesso").getValor());
		return "redirect:"+ListaDeURLs.LISTA_DE_CONFIGURACOESDOSISTEMA;
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.EXCLUSAO_DE_CONFIGURACOESDOSISTEMA, method = RequestMethod.POST)
	public String exclui(@Valid ConfiguracoesDoSistemaModel configuracoesDoSistema, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

		ConfiguracoesDoSistemaModel encontrado = configuracoesDoSistemaDAO.buscaPorPropriedadeClonando(configuracoesDoSistema.getPropriedade());
		try {
			configuracoesDoSistemaDAO.exclui(encontrado);
		} catch (Exception e) {
			erros.setRedirectOrModel(redirectAttributes);
			erros.adiciona("Não foi possível excluir o registro. Verificar se o registro está sendo utilizado em outras partes do sistema.");
			return "redirect:"+ListaDeURLs.LISTA_DE_CONFIGURACOESDOSISTEMA;
		}
		historico.excluir(encontrado, "Configurações do sistema");
		sucesso.setMensagem(redirectAttributes, mensagensDoSistemaDAO.buscaPorPropriedade("MensagemExcluidoComSucesso").getValor());
		return "redirect:"+ListaDeURLs.LISTA_DE_CONFIGURACOESDOSISTEMA;
	}

}