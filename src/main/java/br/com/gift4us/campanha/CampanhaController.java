package br.com.gift4us.campanha;

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
import br.com.gift4us.mensagensdosistema.MensagensDoSistemaDAO;

@Controller
public class CampanhaController {

	@Autowired
	private Erros erros;

	@Autowired
	private Sucesso sucesso;

	@Autowired
	private CampanhaDAO campanhaDAO;

	@Autowired
	private MensagensDoSistemaDAO mensagensDoSistemaDAO;

	@Autowired
	private GerenciadorDeHistorico historico;

	@Secured({ "ROLE_CONFIGURACOES", "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.LISTA_DE_CAMPANHA, method = RequestMethod.GET)
	public String lista(Model model) {
		model.addAttribute("listaDeCampanha", campanhaDAO.listaTudo());
		return "administracao/campanha/lista";
	}

	@Secured({ "ROLE_CONFIGURACOES", "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.FORMULARIO_INSERCAO_DE_CAMPANHA, method = RequestMethod.GET)
	public String carregaFormularioParaInsercao(Model model) {
		return "administracao/campanha/formulario";
	}

	@Secured({ "ROLE_CONFIGURACOES", "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.FORMULARIO_EDICAO_DE_CAMPANHA + "/{id}", method = RequestMethod.GET)
	public String carregaFormularioParaEdicao(@PathVariable Long id, Model model) {
		CampanhaModel campanha = campanhaDAO.buscaPorId(id);
		model.addAttribute("campanha", campanha);
		return "administracao/campanha/formulario";
	}

	@Secured({ "ROLE_CONFIGURACOES", "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.INSERCAO_DE_CAMPANHA, method = RequestMethod.POST)
	public String insere(@Valid CampanhaModel campanha, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("campanha", campanha);
			erros.setRedirectOrModel(model);
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for (ObjectError objectError : allErrors) {
				erros.adiciona(mensagensDoSistemaDAO.buscaPorError(objectError));
			}
			return "administracao/campanha/formulario";
		}

		// verifica se ja existe uma campanha com esse nome
		List<CampanhaModel> lst = campanhaDAO.buscaPorNomeExato(campanha.getNome());
		if (lst.size() > 0) {
			String msg = mensagensDoSistemaDAO.buscaPorPropriedade("RegistroDuplicado").getValor();

			model.addAttribute("campanha", campanha);
			model.addAttribute("alertademsg", msg);

			return "administracao/campanha/formulario";
		}

		campanha.setDataIncl(Calendar.getInstance());
		campanha.setDataAlt(Calendar.getInstance());
		campanha.setStatus(StatusEnum.ATIVO);
		campanhaDAO.insere(campanha);
		CampanhaModel encontrado = campanhaDAO.buscaPorId(campanha.getId());
		historico.inserir(encontrado, "campanha");
		sucesso.setMensagem(redirectAttributes,
				mensagensDoSistemaDAO.buscaPorPropriedade("MensagemAdicionadoComSucesso").getValor());
		return "redirect:" + ListaDeURLs.LISTA_DE_CAMPANHA;
	}

	@Secured({ "ROLE_CONFIGURACOES", "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.EDICAO_DE_CAMPANHA, method = RequestMethod.POST)
	public String altera(@Valid CampanhaModel campanha, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("campanha", campanha);
			erros.setRedirectOrModel(model);
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for (ObjectError objectError : allErrors) {
				erros.adiciona(mensagensDoSistemaDAO.buscaPorError(objectError));
			}
			return "administracao/campanha/formulario";
		}

		// verifica se ja existe uma campanha com esse nome
		List<CampanhaModel> lst = campanhaDAO.buscaPorNomeExato(campanha.getNome());
		if (lst.size() > 0) {
			String msg = mensagensDoSistemaDAO.buscaPorPropriedade("RegistroDuplicado").getValor();

			model.addAttribute("campanha", campanha);
			model.addAttribute("alertademsg", msg);

			return "administracao/campanha/formulario";
		}

		CampanhaModel anterior = campanhaDAO.buscaPorIdClonando(campanha.getId());
		campanha.setDataAlt(Calendar.getInstance());
		campanha.setDataIncl(anterior.getDataIncl());
		campanha.setStatus(StatusEnum.ATIVO);

		campanhaDAO.altera(campanha);
		CampanhaModel atual = campanhaDAO.buscaPorIdClonando(campanha.getId());
		historico.alterar(anterior, atual, "campanha");
		sucesso.setMensagem(redirectAttributes,
				mensagensDoSistemaDAO.buscaPorPropriedade("MensagemAlteradoComSucesso").getValor());
		return "redirect:" + ListaDeURLs.LISTA_DE_CAMPANHA;
	}

	@Secured({ "ROLE_CONFIGURACOES", "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.EXCLUSAO_DE_CAMPANHA, method = RequestMethod.POST)
	public String exclui(@Valid CampanhaModel campanha, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {

		CampanhaModel encontrado = campanhaDAO.buscaPorIdClonando(campanha.getId());
		try {
			campanhaDAO.exclui(encontrado);
		} catch (Exception e) {
			erros.setRedirectOrModel(redirectAttributes);
			erros.adiciona(
					"Não foi possível excluir o registro. Verificar se o registro está sendo utilizado em outras partes do sistema.");
			return "redirect:" + ListaDeURLs.LISTA_DE_CAMPANHA;
		}
		historico.excluir(encontrado, "campanha");
		sucesso.setMensagem(redirectAttributes,
				mensagensDoSistemaDAO.buscaPorPropriedade("MensagemExcluidoComSucesso").getValor());
		return "redirect:" + ListaDeURLs.LISTA_DE_CAMPANHA;
	}

}