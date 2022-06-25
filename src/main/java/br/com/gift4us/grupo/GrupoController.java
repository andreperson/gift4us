package br.com.gift4us.grupo;

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
import br.com.gift4us.anunciante.AnuncianteModel;
import br.com.gift4us.configuracoesdosistema.ConfiguracoesDoSistemaDAO;
import br.com.gift4us.mensagensdosistema.Erros;
import br.com.gift4us.mensagensdosistema.Sucesso;
import br.com.gift4us.historicodosistema.GerenciadorDeHistorico;
import br.com.gift4us.mensagensdosistema.MensagensDoSistemaDAO;

@Controller
public class GrupoController {

	@Autowired
	private Erros erros;

	@Autowired
	private Sucesso sucesso;

	@Autowired
	private GrupoDAO grupoDAO;

	@Autowired
	private MensagensDoSistemaDAO mensagensDoSistemaDAO;

	@Autowired
	private GerenciadorDeHistorico historico;

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.LISTA_DE_GRUPO, method = RequestMethod.GET)
	public String lista(Model model) {
		model.addAttribute("listaDeGrupo", grupoDAO.listaTudo());
		return "administracao/grupo/lista";
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.FORMULARIO_INSERCAO_DE_GRUPO, method = RequestMethod.GET)
	public String carregaFormularioParaInsercao(Model model) {
		return "administracao/grupo/formulario";
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.FORMULARIO_EDICAO_DE_GRUPO + "/{id}", method = RequestMethod.GET)
	public String carregaFormularioParaEdicao(@PathVariable Long id, Model model) {
		GrupoModel grupo = grupoDAO.buscaPorId(id);
		model.addAttribute("grupo", grupo);
		return "administracao/grupo/formulario";
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.INSERCAO_DE_GRUPO, method = RequestMethod.POST)
	public String insere(@Valid GrupoModel grupo, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("grupo", grupo);
			erros.setRedirectOrModel(model);
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for (ObjectError objectError : allErrors) {
				erros.adiciona(mensagensDoSistemaDAO.buscaPorError(objectError));
			}
			return "administracao/grupo/formulario";
		}

		// verifica se ja existe um cadastro com esse nome
		List<GrupoModel> lst = grupoDAO.buscaPorNomeExato(grupo.getNome());
		if (lst.size() > 0) {
			String msg = mensagensDoSistemaDAO.buscaPorPropriedade("RegistroDuplicado").getValor();

			model.addAttribute("grupo", grupo);
			model.addAttribute("alertademsg", msg);

			return "administracao/grupo/formulario";
		}

		grupoDAO.insere(grupo);
		GrupoModel encontrado = grupoDAO.buscaPorId(grupo.getId());
		historico.inserir(encontrado, "Grupo");
		sucesso.setMensagem(redirectAttributes,
				mensagensDoSistemaDAO.buscaPorPropriedade("MensagemAdicionadoComSucesso").getValor());
		return "redirect:" + ListaDeURLs.LISTA_DE_GRUPO;
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.EDICAO_DE_GRUPO, method = RequestMethod.POST)
	public String altera(@Valid GrupoModel grupo, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("grupo", grupo);
			erros.setRedirectOrModel(model);
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for (ObjectError objectError : allErrors) {
				erros.adiciona(mensagensDoSistemaDAO.buscaPorError(objectError));
			}
			return "administracao/grupo/formulario";
		}

		// verifica se ja existe um cadastro com esse nome
		List<GrupoModel> lst = grupoDAO.buscaPorNomeExato(grupo.getNome());
		if (estaDuplicando(lst,grupo.getId())) {
			String msg = mensagensDoSistemaDAO.buscaPorPropriedade("RegistroDuplicado").getValor();

			model.addAttribute("grupo", grupo);
			model.addAttribute("alertademsg", msg);

			return "administracao/grupo/formulario";
		}

		GrupoModel anterior = grupoDAO.buscaPorIdClonando(grupo.getId());

		grupoDAO.altera(grupo);
		GrupoModel atual = grupoDAO.buscaPorIdClonando(grupo.getId());
		historico.alterar(anterior, atual, "Grupo");
		sucesso.setMensagem(redirectAttributes,
				mensagensDoSistemaDAO.buscaPorPropriedade("MensagemAlteradoComSucesso").getValor());
		return "redirect:" + ListaDeURLs.LISTA_DE_GRUPO;
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.EXCLUSAO_DE_GRUPO, method = RequestMethod.POST)
	public String exclui(@Valid GrupoModel grupo, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {

		GrupoModel encontrado = grupoDAO.buscaPorIdClonando(grupo.getId());
		try {
			grupoDAO.exclui(encontrado);
		} catch (Exception e) {
			erros.setRedirectOrModel(redirectAttributes);
			erros.adiciona(
					"Não foi possível excluir o registro. Verificar se o registro está sendo utilizado em outras partes do sistema.");
			return "redirect:" + ListaDeURLs.LISTA_DE_GRUPO;
		}
		historico.excluir(encontrado, "Grupo");
		sucesso.setMensagem(redirectAttributes,
				mensagensDoSistemaDAO.buscaPorPropriedade("MensagemExcluidoComSucesso").getValor());
		return "redirect:" + ListaDeURLs.LISTA_DE_GRUPO;
	}
	
	
	private Boolean estaDuplicando(List<GrupoModel> lst, Long idalterando){
		
		Boolean retorno = false;
		
		for (GrupoModel gp : lst) {
			if (gp.getId() != idalterando) {
				retorno = true;
			}
		}
		
		return retorno;
		
	}
	

}