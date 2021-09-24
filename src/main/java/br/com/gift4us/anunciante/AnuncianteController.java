package br.com.gift4us.anunciante;

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
import br.com.gift4us.historicodosistema.GerenciadorDeHistorico;
import br.com.gift4us.mensagensdosistema.MensagensDoSistemaDAO;

@Controller
public class AnuncianteController {

	@Autowired
	private Erros erros;

	@Autowired
	private Sucesso sucesso;

	@Autowired
	private AnuncianteDAO anuncianteDAO;

	@Autowired
	private MensagensDoSistemaDAO mensagensDoSistemaDAO;

	@Autowired
	private GerenciadorDeHistorico historico;

	@Secured({ "ROLE_CONFIGURACOES", "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.LISTA_DE_ANUNCIANTE, method = RequestMethod.GET)
	public String lista(Model model) {
		model.addAttribute("listaDeAnunciante", anuncianteDAO.listaTudo());
		return "administracao/anunciante/lista";
	}

	@Secured({ "ROLE_CONFIGURACOES", "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.FORMULARIO_INSERCAO_DE_ANUNCIANTE, method = RequestMethod.GET)
	public String carregaFormularioParaInsercao(Model model) {
		return "administracao/anunciante/formulario";
	}

	@Secured({ "ROLE_CONFIGURACOES", "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.FORMULARIO_EDICAO_DE_ANUNCIANTE + "/{id}", method = RequestMethod.GET)
	public String carregaFormularioParaEdicao(@PathVariable Long id, Model model) {
		AnuncianteModel anunciante = anuncianteDAO.buscaPorId(id);
		model.addAttribute("anunciante", anunciante);
		return "administracao/anunciante/formulario";
	}

	@Secured({ "ROLE_CONFIGURACOES", "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.INSERCAO_DE_ANUNCIANTE, method = RequestMethod.POST)
	public String insere(@Valid AnuncianteModel anunciante, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("anunciante", anunciante);
			erros.setRedirectOrModel(model);
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for (ObjectError objectError : allErrors) {
				erros.adiciona(mensagensDoSistemaDAO.buscaPorError(objectError));
			}
			return "administracao/anunciante/formulario";
		}

		// verifica se ja existe uma categoria com esse nome
		List<AnuncianteModel> lst = anuncianteDAO.buscaPorRazaosocialExato(anunciante.getRazaosocial());
		if (lst.size() > 0) {
			String msg = mensagensDoSistemaDAO.buscaPorPropriedade("RegistroDuplicado").getValor();

			model.addAttribute("anunciante", anunciante);
			model.addAttribute("alertademsg", msg);

			return "administracao/anunciante/formulario";
		}

		anunciante.setDataAlt(Calendar.getInstance());
		anunciante.setDataIncl(Calendar.getInstance());
		anuncianteDAO.insere(anunciante);
		AnuncianteModel encontrado = anuncianteDAO.buscaPorId(anunciante.getId());
		historico.inserir(encontrado, "Anunciante");
		sucesso.setMensagem(redirectAttributes,
				mensagensDoSistemaDAO.buscaPorPropriedade("MensagemAdicionadoComSucesso").getValor());
		return "redirect:" + ListaDeURLs.LISTA_DE_ANUNCIANTE;
	}

	@Secured({ "ROLE_CONFIGURACOES", "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.EDICAO_DE_ANUNCIANTE, method = RequestMethod.POST)
	public String altera(@Valid AnuncianteModel anunciante, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("anunciante", anunciante);
			erros.setRedirectOrModel(model);
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for (ObjectError objectError : allErrors) {
				erros.adiciona(mensagensDoSistemaDAO.buscaPorError(objectError));
			}
			return "administracao/anunciante/formulario";
		}

		// verifica se ja existe uma categoria com esse nome
		List<AnuncianteModel> lst = anuncianteDAO.buscaPorRazaosocialExato(anunciante.getRazaosocial());
		if (lst.size() > 0) {
			String msg = mensagensDoSistemaDAO.buscaPorPropriedade("RegistroDuplicado").getValor();

			model.addAttribute("anunciante", anunciante);
			model.addAttribute("alertademsg", msg);

			return "administracao/anunciante/formulario";
		}

		AnuncianteModel anterior = anuncianteDAO.buscaPorIdClonando(anunciante.getId());
		anunciante.setDataAlt(Calendar.getInstance());
		anunciante.setDataIncl(anterior.getDataIncl());
		anuncianteDAO.altera(anunciante);
		AnuncianteModel atual = anuncianteDAO.buscaPorIdClonando(anunciante.getId());
		historico.alterar(anterior, atual, "Anunciante");
		sucesso.setMensagem(redirectAttributes,
				mensagensDoSistemaDAO.buscaPorPropriedade("MensagemAlteradoComSucesso").getValor());
		return "redirect:" + ListaDeURLs.LISTA_DE_ANUNCIANTE;
	}

	@Secured({ "ROLE_CONFIGURACOES", "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.EXCLUSAO_DE_ANUNCIANTE, method = RequestMethod.POST)
	public String exclui(@Valid AnuncianteModel anunciante, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {

		AnuncianteModel encontrado = anuncianteDAO.buscaPorIdClonando(anunciante.getId());
		try {
			anuncianteDAO.exclui(encontrado);
		} catch (Exception e) {
			erros.setRedirectOrModel(redirectAttributes);
			erros.adiciona(
					"Não foi possível excluir o registro. Verificar se o registro está sendo utilizado em outras partes do sistema.");
			return "redirect:" + ListaDeURLs.LISTA_DE_ANUNCIANTE;
		}
		historico.excluir(encontrado, "Anunciante");
		sucesso.setMensagem(redirectAttributes,
				mensagensDoSistemaDAO.buscaPorPropriedade("MensagemExcluidoComSucesso").getValor());
		return "redirect:" + ListaDeURLs.LISTA_DE_ANUNCIANTE;
	}

}