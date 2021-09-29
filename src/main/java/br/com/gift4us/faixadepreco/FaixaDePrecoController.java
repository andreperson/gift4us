package br.com.gift4us.faixadepreco;

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
import br.com.gift4us.mensagensdosistema.Alerta;
import br.com.gift4us.mensagensdosistema.Erros;
import br.com.gift4us.mensagensdosistema.Sucesso;
import br.com.gift4us.status.StatusEnum;
import br.com.gift4us.historicodosistema.GerenciadorDeHistorico;
import br.com.gift4us.mensagensdosistema.MensagensDoSistemaDAO;
import br.com.gift4us.mensagensdosistema.MensagensDoSistemaModel;

@Controller
public class FaixaDePrecoController {

	@Autowired
	private Erros erros;

	@Autowired
	private Sucesso sucesso;

	@Autowired
	private Alerta alerta;

	@Autowired
	private FaixaDePrecoDAO faixadeprecoDAO;

	@Autowired
	private MensagensDoSistemaDAO mensagensDoSistemaDAO;

	@Autowired
	private GerenciadorDeHistorico historico;

	@Secured({ "ROLE_CONFIGURACOES", "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.LISTA_DE_FAIXADEPRECO, method = RequestMethod.GET)
	public String lista(Model model) {
		model.addAttribute("listaDeFaixaDePreco", faixadeprecoDAO.listaTudo());
		return "administracao/faixadepreco/lista";
	}

	@Secured({ "ROLE_CONFIGURACOES", "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.FORMULARIO_INSERCAO_DE_FAIXADEPRECO, method = RequestMethod.GET)
	public String carregaFormularioParaInsercao(Model model) {
		return "administracao/faixadepreco/formulario";
	}

	@Secured({ "ROLE_CONFIGURACOES", "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.FORMULARIO_EDICAO_DE_FAIXADEPRECO + "/{id}", method = RequestMethod.GET)
	public String carregaFormularioParaEdicao(@PathVariable Long id, Model model) {
		FaixaDePrecoModel faixadepreco = faixadeprecoDAO.buscaPorId(id);
		model.addAttribute("faixadepreco", faixadepreco);
		return "administracao/faixadepreco/formulario";
	}

	@Secured({ "ROLE_CONFIGURACOES", "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.INSERCAO_DE_FAIXADEPRECO, method = RequestMethod.POST)
	public String insere(@Valid FaixaDePrecoModel faixadepreco, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("faixadepreco", faixadepreco);
			erros.setRedirectOrModel(model);
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for (ObjectError objectError : allErrors) {
				erros.adiciona(mensagensDoSistemaDAO.buscaPorError(objectError));
			}
			return "administracao/faixadepreco/formulario";
		}

		// verifica se ja existe uma faixadepreco com esse nome
		List<FaixaDePrecoModel> lst = faixadeprecoDAO.buscaPorNomeExato(faixadepreco.getNome());
		if (lst.size() > 0) {
			String msg = mensagensDoSistemaDAO.buscaPorPropriedade("RegistroDuplicado").getValor();

			model.addAttribute("faixadepreco", faixadepreco);
			model.addAttribute("alertademsg", msg);

			return "administracao/faixadepreco/formulario";
		}

		faixadepreco.setDataIncl(Calendar.getInstance());
		faixadepreco.setDataAlt(Calendar.getInstance());
		faixadepreco.setStatus(StatusEnum.ATIVO);
		faixadeprecoDAO.insere(faixadepreco);
		FaixaDePrecoModel encontrado = faixadeprecoDAO.buscaPorId(faixadepreco.getId());
		historico.inserir(encontrado, "faixadepreco");
		sucesso.setMensagem(redirectAttributes,
				mensagensDoSistemaDAO.buscaPorPropriedade("MensagemAdicionadoComSucesso").getValor());
		return "redirect:" + ListaDeURLs.LISTA_DE_FAIXADEPRECO;
	}

	@Secured({ "ROLE_CONFIGURACOES", "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.EDICAO_DE_FAIXADEPRECO, method = RequestMethod.POST)
	public String altera(@Valid FaixaDePrecoModel faixadepreco, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("faixadepreco", faixadepreco);
			erros.setRedirectOrModel(model);
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for (ObjectError objectError : allErrors) {
				erros.adiciona(mensagensDoSistemaDAO.buscaPorError(objectError));
			}
			return "administracao/faixadepreco/formulario";
		}

		// verifica se ja existe uma faixadepreco com esse nome
		List<FaixaDePrecoModel> lst = faixadeprecoDAO.buscaPorNomeExato(faixadepreco.getNome());
		if (lst.size() > 0) {
			String msg = mensagensDoSistemaDAO.buscaPorPropriedade("RegistroDuplicado").getValor();

			model.addAttribute("faixadepreco", faixadepreco);
			model.addAttribute("alertademsg", msg);

			return "administracao/faixadepreco/formulario";
		}

		FaixaDePrecoModel anterior = faixadeprecoDAO.buscaPorIdClonando(faixadepreco.getId());
		faixadepreco.setDataAlt(Calendar.getInstance());
		faixadepreco.setDataIncl(anterior.getDataIncl());
		faixadepreco.setStatus(StatusEnum.ATIVO);
		faixadeprecoDAO.altera(faixadepreco);
		FaixaDePrecoModel atual = faixadeprecoDAO.buscaPorIdClonando(faixadepreco.getId());
		historico.alterar(anterior, atual, "faixadepreco");
		sucesso.setMensagem(redirectAttributes,
				mensagensDoSistemaDAO.buscaPorPropriedade("MensagemAlteradoComSucesso").getValor());
		return "redirect:" + ListaDeURLs.LISTA_DE_FAIXADEPRECO;
	}

	@Secured({ "ROLE_CONFIGURACOES", "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.EXCLUSAO_DE_FAIXADEPRECO, method = RequestMethod.POST)
	public String exclui(@Valid FaixaDePrecoModel faixadepreco, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {

		FaixaDePrecoModel encontrado = faixadeprecoDAO.buscaPorIdClonando(faixadepreco.getId());
		try {
			faixadeprecoDAO.exclui(encontrado);
		} catch (Exception e) {
			erros.setRedirectOrModel(redirectAttributes);
			erros.adiciona(
					"Não foi possível excluir o registro. Verificar se o registro está sendo utilizado em outras partes do sistema.");
			return "redirect:" + ListaDeURLs.LISTA_DE_FAIXADEPRECO;
		}
		historico.excluir(encontrado, "faixadepreco");
		sucesso.setMensagem(redirectAttributes,
				mensagensDoSistemaDAO.buscaPorPropriedade("MensagemExcluidoComSucesso").getValor());
		return "redirect:" + ListaDeURLs.LISTA_DE_FAIXADEPRECO;
	}

}