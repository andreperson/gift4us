package br.com.gift4us.linhahome;

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
import br.com.gift4us.categoria.CategoriaModel;
import br.com.gift4us.configuracoesdosistema.ConfiguracoesDoSistemaDAO;
import br.com.gift4us.mensagensdosistema.Erros;
import br.com.gift4us.mensagensdosistema.Sucesso;
import br.com.gift4us.status.StatusEnum;
import br.com.gift4us.historicodosistema.GerenciadorDeHistorico;
import br.com.gift4us.linha.LinhaDAO;
import br.com.gift4us.mensagensdosistema.MensagensDoSistemaDAO;

@Controller
public class LinhaHomeController {

	@Autowired
	private Erros erros;

	@Autowired
	private Sucesso sucesso;

	@Autowired
	private LinhaHomeDAO linhaHomeDAO;
	
	@Autowired
	private LinhaDAO linhaDAO;

	@Autowired
	private MensagensDoSistemaDAO mensagensDoSistemaDAO;

	@Autowired
	private GerenciadorDeHistorico historico;

	@Secured({ "ROLE_CONFIGURACOES", "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.LISTA_DE_LINHA_HOME, method = RequestMethod.GET)
	public String lista(Model model) {
		model.addAttribute("listaDeLinhaHome", linhaHomeDAO.listaTudo());
		model.addAttribute("listaDeLinha", linhaDAO.listaTudo());
		return "administracao/linhahome/lista";
	}

	@Secured({ "ROLE_CONFIGURACOES", "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.FORMULARIO_INSERCAO_DE_LINHA_HOME, method = RequestMethod.GET)
	public String carregaFormularioParaInsercao(Model model) {
		return "administracao/linhahome/formulario";
	}

	@Secured({ "ROLE_CONFIGURACOES", "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.FORMULARIO_EDICAO_DE_LINHA_HOME + "/{id}", method = RequestMethod.GET)
	public String carregaFormularioParaEdicao(@PathVariable Long id, Model model) {
		LinhaHomeModel linhahome = linhaHomeDAO.buscaPorId(id);
		model.addAttribute("linhahome", linhahome);
		return "administracao/linhahome/formulario";
	}

	@Secured({ "ROLE_CONFIGURACOES", "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.INSERCAO_DE_LINHA_HOME, method = RequestMethod.POST)
	public String insere(@Valid LinhaHomeModel linhahome, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("linhahome", linhahome);
			erros.setRedirectOrModel(model);
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for (ObjectError objectError : allErrors) {
				erros.adiciona(mensagensDoSistemaDAO.buscaPorError(objectError));
			}
			return "administracao/linhahome/formulario";
		}

		linhaHomeDAO.insere(linhahome);
		LinhaHomeModel encontrado = linhaHomeDAO.buscaPorId(linhahome.getId());
		historico.inserir(encontrado, "linhahome");
		sucesso.setMensagem(redirectAttributes,
				mensagensDoSistemaDAO.buscaPorPropriedade("MensagemAdicionadoComSucesso").getValor());
		return "redirect:" + ListaDeURLs.LISTA_DE_LINHA_HOME;
	}

	@Secured({ "ROLE_CONFIGURACOES", "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.EXCLUSAO_DE_LINHA_HOME, method = RequestMethod.POST)
	public String exclui(@Valid LinhaHomeModel linhahome, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {

		LinhaHomeModel encontrado = linhaHomeDAO.buscaPorIdClonando(linhahome.getId());
		try {
			linhaHomeDAO.exclui(encontrado);
		} catch (Exception e) {
			erros.setRedirectOrModel(redirectAttributes);
			erros.adiciona(
					"Não foi possível excluir o registro. Verificar se o registro está sendo utilizado em outras partes do sistema.");
			return "redirect:" + ListaDeURLs.LISTA_DE_LINHA_HOME;
		}
		historico.excluir(encontrado, "linhahome");
		sucesso.setMensagem(redirectAttributes,
				mensagensDoSistemaDAO.buscaPorPropriedade("MensagemExcluidoComSucesso").getValor());
		return "redirect:" + ListaDeURLs.LISTA_DE_LINHA_HOME;
	}

}