package br.com.gift4us.anunciantetipo;

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
public class AnuncianteTipoController {

	@Autowired
	private Erros erros;

	@Autowired
	private Sucesso sucesso;

	@Autowired
	private AnuncianteTipoDAO anuncianteTipoDAO;

	@Autowired
	private MensagensDoSistemaDAO mensagensDoSistemaDAO;

	@Autowired
	private GerenciadorDeHistorico historico;

	@Secured({ "ROLE_CONFIGURACOES","ROLE_ADMIN"})
	@RequestMapping(value = ListaDeURLs.LISTA_DE_ANUNCIANTETIPO, method = RequestMethod.GET)
	public String lista(Model model) {
		model.addAttribute("listaDeAnuncianteTipo", anuncianteTipoDAO.listaTudo());
		return "administracao/anunciantetipo/lista";
	}

	@Secured({ "ROLE_CONFIGURACOES","ROLE_ADMIN"})
	@RequestMapping(value = ListaDeURLs.FORMULARIO_INSERCAO_DE_ANUNCIANTETIPO, method = RequestMethod.GET)
	public String carregaFormularioParaInsercao(Model model) {
		return "administracao/anunciantetipo/formulario";
	}

	@Secured({ "ROLE_CONFIGURACOES","ROLE_ADMIN"})
	@RequestMapping(value = ListaDeURLs.FORMULARIO_EDICAO_DE_ANUNCIANTETIPO + "/{id}", method = RequestMethod.GET)
	public String carregaFormularioParaEdicao(@PathVariable Long id, Model model) {
		AnuncianteTipoModel anuncianteTipo = anuncianteTipoDAO.buscaPorId(id);
		model.addAttribute("anuncianteTipo", anuncianteTipo);
		return "administracao/anunciantetipo/formulario";
	}

	@Secured({ "ROLE_CONFIGURACOES","ROLE_ADMIN"})
	@RequestMapping(value = ListaDeURLs.INSERCAO_DE_ANUNCIANTETIPO, method = RequestMethod.POST)
	public String insere(@Valid AnuncianteTipoModel anuncianteTipo, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

		if(bindingResult.hasErrors()){
			model.addAttribute("anuncianteTipo", anuncianteTipo);
			erros.setRedirectOrModel(model);
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for (ObjectError objectError : allErrors) {
				erros.adiciona(mensagensDoSistemaDAO.buscaPorError(objectError));
			}
			return "administracao/anuncianteTipo/formulario";
		}

		anuncianteTipo.setDataAlt(Calendar.getInstance());
		anuncianteTipo.setDataIncl(Calendar.getInstance());
		
		anuncianteTipoDAO.insere(anuncianteTipo);
		AnuncianteTipoModel encontrado = anuncianteTipoDAO.buscaPorId(anuncianteTipo.getId());
		historico.inserir(encontrado, "AnuncianteTipo");
		sucesso.setMensagem(redirectAttributes, mensagensDoSistemaDAO.buscaPorPropriedade("MensagemAdicionadoComSucesso").getValor());
		return "redirect:"+ListaDeURLs.LISTA_DE_ANUNCIANTETIPO;
	}

	@Secured({ "ROLE_CONFIGURACOES","ROLE_ADMIN"})
	@RequestMapping(value = ListaDeURLs.EDICAO_DE_ANUNCIANTETIPO, method = RequestMethod.POST)
	public String altera(@Valid AnuncianteTipoModel anuncianteTipo, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

		if(bindingResult.hasErrors()){
			model.addAttribute("anuncianteTipo", anuncianteTipo);
			erros.setRedirectOrModel(model);
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for (ObjectError objectError : allErrors) {
				erros.adiciona(mensagensDoSistemaDAO.buscaPorError(objectError));
			}
			return "administracao/anunciantetipo/formulario";
		}

		AnuncianteTipoModel anterior = anuncianteTipoDAO.buscaPorIdClonando(anuncianteTipo.getId());

		anuncianteTipoDAO.altera(anuncianteTipo);
		AnuncianteTipoModel atual = anuncianteTipoDAO.buscaPorIdClonando(anuncianteTipo.getId());
		historico.alterar(anterior, atual, "AnuncianteTipo");
		sucesso.setMensagem(redirectAttributes, mensagensDoSistemaDAO.buscaPorPropriedade("MensagemAlteradoComSucesso").getValor());
		return "redirect:"+ListaDeURLs.LISTA_DE_ANUNCIANTETIPO;
	}

	@Secured({ "ROLE_CONFIGURACOES","ROLE_ADMIN"})
	@RequestMapping(value = ListaDeURLs.EXCLUSAO_DE_ANUNCIANTETIPO, method = RequestMethod.POST)
	public String exclui(@Valid AnuncianteTipoModel anuncianteTipo, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

		AnuncianteTipoModel encontrado = anuncianteTipoDAO.buscaPorIdClonando(anuncianteTipo.getId());
		try {
			anuncianteTipoDAO.exclui(encontrado);
		} catch (Exception e) {
			erros.setRedirectOrModel(redirectAttributes);
			erros.adiciona("Não foi possível excluir o registro. Verificar se o registro está sendo utilizado em outras partes do sistema.");
			return "redirect:"+ListaDeURLs.LISTA_DE_ANUNCIANTETIPO;
		}
		historico.excluir(encontrado, "AnuncianteTipo");
		sucesso.setMensagem(redirectAttributes, mensagensDoSistemaDAO.buscaPorPropriedade("MensagemExcluidoComSucesso").getValor());
		return "redirect:"+ListaDeURLs.LISTA_DE_ANUNCIANTETIPO;
	}

}