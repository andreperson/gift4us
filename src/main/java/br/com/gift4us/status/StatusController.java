package br.com.gift4us.status;

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

import java.util.Calendar;
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
public class StatusController {

	@Autowired
	private Erros erros;

	@Autowired
	private Sucesso sucesso;

	@Autowired
	private StatusDAO statusDAO;

	@Autowired
	private MensagensDoSistemaDAO mensagensDoSistemaDAO;

	@Autowired
	private GerenciadorDeHistorico historico;

	@Secured({ "ROLE_CONFIGURACOES","ROLE_ADMIN"})
	@RequestMapping(value = ListaDeURLs.LISTA_DE_STATUS, method = RequestMethod.GET)
	public String lista(Model model) {
		model.addAttribute("listaDeStatus", statusDAO.listaTudo());
		return "administracao/status/lista";
	}

	@Secured({ "ROLE_CONFIGURACOES","ROLE_ADMIN"})
	@RequestMapping(value = ListaDeURLs.FORMULARIO_INSERCAO_DE_STATUS, method = RequestMethod.GET)
	public String carregaFormularioParaInsercao(Model model) {
		return "administracao/status/formulario";
	}

	@Secured({ "ROLE_CONFIGURACOES","ROLE_ADMIN"})
	@RequestMapping(value = ListaDeURLs.FORMULARIO_EDICAO_DE_STATUS + "/{id}", method = RequestMethod.GET)
	public String carregaFormularioParaEdicao(@PathVariable Long id, Model model) {
		StatusModel status = statusDAO.buscaPorId(id);
		model.addAttribute("status", status);
		return "administracao/status/formulario";
	}

	@Secured({ "ROLE_CONFIGURACOES","ROLE_ADMIN"})
	@RequestMapping(value = ListaDeURLs.INSERCAO_DE_STATUS, method = RequestMethod.POST)
	public String insere(@Valid StatusModel status, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

		if(bindingResult.hasErrors()){
			model.addAttribute("status", status);
			erros.setRedirectOrModel(model);
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for (ObjectError objectError : allErrors) {
				erros.adiciona(mensagensDoSistemaDAO.buscaPorError(objectError));
			}
			return "administracao/status/formulario";
		}

		status.setDataIncl(Calendar.getInstance());
		status.setDataAlt(Calendar.getInstance());
		statusDAO.insere(status);
		StatusModel encontrado = statusDAO.buscaPorId(status.getId());
		historico.inserir(encontrado, "Status");
		sucesso.setMensagem(redirectAttributes, mensagensDoSistemaDAO.buscaPorPropriedade("MensagemAdicionadoComSucesso").getValor());
		return "redirect:"+ListaDeURLs.LISTA_DE_STATUS;
	}

	@Secured({ "ROLE_CONFIGURACOES","ROLE_ADMIN"})
	@RequestMapping(value = ListaDeURLs.EDICAO_DE_STATUS, method = RequestMethod.POST)
	public String altera(@Valid StatusModel status, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

		if(bindingResult.hasErrors()){
			model.addAttribute("status", status);
			erros.setRedirectOrModel(model);
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for (ObjectError objectError : allErrors) {
				erros.adiciona(mensagensDoSistemaDAO.buscaPorError(objectError));
			}
			return "administracao/status/formulario";
		}

		StatusModel anterior = statusDAO.buscaPorIdClonando(status.getId());
		status.setDataAlt(Calendar.getInstance());
		status.setDataIncl(anterior.getDataIncl());
		statusDAO.altera(status);
		StatusModel atual = statusDAO.buscaPorIdClonando(status.getId());
		historico.alterar(anterior, atual, "Status");
		sucesso.setMensagem(redirectAttributes, mensagensDoSistemaDAO.buscaPorPropriedade("MensagemAlteradoComSucesso").getValor());
		return "redirect:"+ListaDeURLs.LISTA_DE_STATUS;
	}

	@Secured({ "ROLE_CONFIGURACOES","ROLE_ADMIN"})
	@RequestMapping(value = ListaDeURLs.EXCLUSAO_DE_STATUS, method = RequestMethod.POST)
	public String exclui(@Valid StatusModel status, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

		StatusModel encontrado = statusDAO.buscaPorIdClonando(status.getId());
		try {
			statusDAO.exclui(encontrado);
		} catch (Exception e) {
			erros.setRedirectOrModel(redirectAttributes);
			erros.adiciona("Não foi possível excluir o registro. Verificar se o registro está sendo utilizado em outras partes do sistema.");
			return "redirect:"+ListaDeURLs.LISTA_DE_STATUS;
		}
		historico.excluir(encontrado, "Status");
		sucesso.setMensagem(redirectAttributes, mensagensDoSistemaDAO.buscaPorPropriedade("MensagemExcluidoComSucesso").getValor());
		return "redirect:"+ListaDeURLs.LISTA_DE_STATUS;
	}

}