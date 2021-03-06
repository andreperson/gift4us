package br.com.gift4us.orcamento;

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
public class OrcamentoController {

	@Autowired
	private Erros erros;

	@Autowired
	private Sucesso sucesso;

	@Autowired
	private OrcamentoDAO orcamentoDAO;

	@Autowired
	private MensagensDoSistemaDAO mensagensDoSistemaDAO;

	@Autowired
	private GerenciadorDeHistorico historico;

	@Secured({ "ROLE_ADMIN", "ROLE_GERENCIAL", "ROLE_ANUNCIANTE" })
	@RequestMapping(value = ListaDeURLs.LISTA_DE_ORCAMENTO, method = RequestMethod.GET)
	public String lista(Model model) {
		model.addAttribute("listaDeOrcamento", orcamentoDAO.listaTudo());
		return "administracao/orcamento/lista";
	}

	@Secured({ "ROLE_ADMIN", "ROLE_GERENCIAL", "ROLE_ANUNCIANTE" })
	@RequestMapping(value = ListaDeURLs.FORMULARIO_INSERCAO_DE_ORCAMENTO, method = RequestMethod.GET)
	public String carregaFormularioParaInsercao(Model model) {
		return "administracao/orcamento/formulario";
	}

	@Secured({ "ROLE_ADMIN", "ROLE_GERENCIAL", "ROLE_ANUNCIANTE" })
	@RequestMapping(value = ListaDeURLs.FORMULARIO_EDICAO_DE_ORCAMENTO + "/{id}", method = RequestMethod.GET)
	public String carregaFormularioParaEdicao(@PathVariable Long id, Model model) {
		OrcamentoModel orcamento = orcamentoDAO.buscaPorId(id);
		model.addAttribute("orcamento", orcamento);
		return "administracao/orcamento/formulario";
	}

	@Secured({ "ROLE_ADMIN", "ROLE_GERENCIAL", "ROLE_ANUNCIANTE" })
	@RequestMapping(value = ListaDeURLs.INSERCAO_DE_ORCAMENTO, method = RequestMethod.POST)
	public String insere(@Valid OrcamentoModel orcamento, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

		if(bindingResult.hasErrors()){
			model.addAttribute("orcamento", orcamento);
			erros.setRedirectOrModel(model);
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for (ObjectError objectError : allErrors) {
				erros.adiciona(mensagensDoSistemaDAO.buscaPorError(objectError));
			}
			return "administracao/orcamento/formulario";
		}
		orcamento.setDataIncl(Calendar.getInstance());
		orcamentoDAO.insere(orcamento);
		OrcamentoModel encontrado = orcamentoDAO.buscaPorId(orcamento.getId());
		historico.inserir(encontrado, "Orcamento");
		sucesso.setMensagem(redirectAttributes, mensagensDoSistemaDAO.buscaPorPropriedade("MensagemAdicionadoComSucesso").getValor());
		return "redirect:"+ListaDeURLs.LISTA_DE_ORCAMENTO;
	}

	@Secured({ "ROLE_ADMIN", "ROLE_GERENCIAL", "ROLE_ANUNCIANTE" })
	@RequestMapping(value = ListaDeURLs.EDICAO_DE_ORCAMENTO, method = RequestMethod.POST)
	public String altera(@Valid OrcamentoModel orcamento, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

		if(bindingResult.hasErrors()){
			model.addAttribute("orcamento", orcamento);
			erros.setRedirectOrModel(model);
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for (ObjectError objectError : allErrors) {
				erros.adiciona(mensagensDoSistemaDAO.buscaPorError(objectError));
			}
			return "administracao/orcamento/formulario";
		}

		OrcamentoModel anterior = orcamentoDAO.buscaPorIdClonando(orcamento.getId());
		orcamento.setDataIncl(anterior.getDataIncl());
		orcamentoDAO.altera(orcamento);
		OrcamentoModel atual = orcamentoDAO.buscaPorIdClonando(orcamento.getId());
		historico.alterar(anterior, atual, "Orcamento");
		sucesso.setMensagem(redirectAttributes, mensagensDoSistemaDAO.buscaPorPropriedade("MensagemAlteradoComSucesso").getValor());
		return "redirect:"+ListaDeURLs.LISTA_DE_ORCAMENTO;
	}

	@Secured({ "ROLE_ADMIN", "ROLE_GERENCIAL", "ROLE_ANUNCIANTE" })
	@RequestMapping(value = ListaDeURLs.EXCLUSAO_DE_ORCAMENTO, method = RequestMethod.POST)
	public String exclui(@Valid OrcamentoModel orcamento, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

		OrcamentoModel encontrado = orcamentoDAO.buscaPorIdClonando(orcamento.getId());
		try {
			orcamentoDAO.exclui(encontrado);
		} catch (Exception e) {
			erros.setRedirectOrModel(redirectAttributes);
			erros.adiciona("N??o foi poss??vel excluir o registro. Verificar se o registro est?? sendo utilizado em outras partes do sistema.");
			return "redirect:"+ListaDeURLs.LISTA_DE_ORCAMENTO;
		}
		historico.excluir(encontrado, "Orcamento");
		sucesso.setMensagem(redirectAttributes, mensagensDoSistemaDAO.buscaPorPropriedade("MensagemExcluidoComSucesso").getValor());
		return "redirect:"+ListaDeURLs.LISTA_DE_ORCAMENTO;
	}

}