package br.com.gift4us.atividade;

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
public class AtividadeController {

	@Autowired
	private Erros erros;

	@Autowired
	private Sucesso sucesso;

	@Autowired
	private AtividadeDAO atividadeDAO;

	@Autowired
	private MensagensDoSistemaDAO mensagensDoSistemaDAO;

	@Autowired
	private GerenciadorDeHistorico historico;

	@Secured({ "ROLE_ATIVIDADE_VER" })
	@RequestMapping(value = ListaDeURLs.LISTA_DE_ATIVIDADE, method = RequestMethod.GET)
	public String lista(Model model) {
		model.addAttribute("listaDeAtividade", atividadeDAO.listaTudo());
		return "administracao/atividade/lista";
	}

	@Secured({ "ROLE_ATIVIDADE_INSERIR" })
	@RequestMapping(value = ListaDeURLs.FORMULARIO_INSERCAO_DE_ATIVIDADE, method = RequestMethod.GET)
	public String carregaFormularioParaInsercao(Model model) {
		return "administracao/atividade/formulario";
	}

	@Secured({ "ROLE_ATIVIDADE_ALTERAR" })
	@RequestMapping(value = ListaDeURLs.FORMULARIO_EDICAO_DE_ATIVIDADE + "/{id}", method = RequestMethod.GET)
	public String carregaFormularioParaEdicao(@PathVariable Long id, Model model) {
		AtividadeModel atividade = atividadeDAO.buscaPorId(id);
		model.addAttribute("atividade", atividade);
		return "administracao/atividade/formulario";
	}

	@Secured({ "ROLE_ATIVIDADE_INSERIR" })
	@RequestMapping(value = ListaDeURLs.INSERCAO_DE_ATIVIDADE, method = RequestMethod.POST)
	public String insere(@Valid AtividadeModel atividade, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

		if(bindingResult.hasErrors()){
			model.addAttribute("atividade", atividade);
			erros.setRedirectOrModel(model);
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for (ObjectError objectError : allErrors) {
				erros.adiciona(mensagensDoSistemaDAO.buscaPorError(objectError));
			}
			return "administracao/atividade/formulario";
		}

		atividadeDAO.insere(atividade);
		AtividadeModel encontrado = atividadeDAO.buscaPorId(atividade.getId());
		historico.inserir(encontrado, "Atividade");
		sucesso.setMensagem(redirectAttributes, mensagensDoSistemaDAO.buscaPorPropriedade("MensagemAdicionadoComSucesso").getValor());
		return "redirect:"+ListaDeURLs.LISTA_DE_ATIVIDADE;
	}

	@Secured({ "ROLE_ATIVIDADE_ALTERAR" })
	@RequestMapping(value = ListaDeURLs.EDICAO_DE_ATIVIDADE, method = RequestMethod.POST)
	public String altera(@Valid AtividadeModel atividade, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

		if(bindingResult.hasErrors()){
			model.addAttribute("atividade", atividade);
			erros.setRedirectOrModel(model);
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for (ObjectError objectError : allErrors) {
				erros.adiciona(mensagensDoSistemaDAO.buscaPorError(objectError));
			}
			return "administracao/atividade/formulario";
		}

		AtividadeModel anterior = atividadeDAO.buscaPorIdClonando(atividade.getId());

		atividadeDAO.altera(atividade);
		AtividadeModel atual = atividadeDAO.buscaPorIdClonando(atividade.getId());
		historico.alterar(anterior, atual, "Atividade");
		sucesso.setMensagem(redirectAttributes, mensagensDoSistemaDAO.buscaPorPropriedade("MensagemAlteradoComSucesso").getValor());
		return "redirect:"+ListaDeURLs.LISTA_DE_ATIVIDADE;
	}

	@Secured({ "ROLE_ATIVIDADE_EXCLUIR" })
	@RequestMapping(value = ListaDeURLs.EXCLUSAO_DE_ATIVIDADE, method = RequestMethod.POST)
	public String exclui(@Valid AtividadeModel atividade, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

		AtividadeModel encontrado = atividadeDAO.buscaPorIdClonando(atividade.getId());
		try {
			atividadeDAO.exclui(encontrado);
		} catch (Exception e) {
			erros.setRedirectOrModel(redirectAttributes);
			erros.adiciona("N??o foi poss??vel excluir o registro. Verificar se o registro est?? sendo utilizado em outras partes do sistema.");
			return "redirect:"+ListaDeURLs.LISTA_DE_ATIVIDADE;
		}
		historico.excluir(encontrado, "Atividade");
		sucesso.setMensagem(redirectAttributes, mensagensDoSistemaDAO.buscaPorPropriedade("MensagemExcluidoComSucesso").getValor());
		return "redirect:"+ListaDeURLs.LISTA_DE_ATIVIDADE;
	}

}