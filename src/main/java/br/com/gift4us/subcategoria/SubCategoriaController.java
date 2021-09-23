package br.com.gift4us.subcategoria;

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
public class SubCategoriaController {

	@Autowired
	private Erros erros;

	@Autowired
	private Sucesso sucesso;

	@Autowired
	private SubCategoriaDAO subCategoriaDAO;

	@Autowired
	private MensagensDoSistemaDAO mensagensDoSistemaDAO;

	@Autowired
	private GerenciadorDeHistorico historico;

	@Secured({ "ROLE_CONFIGURACOES","ROLE_ADMIN"})
	@RequestMapping(value = ListaDeURLs.LISTA_DE_SUBCATEGORIA, method = RequestMethod.GET)
	public String lista(Model model) {
		model.addAttribute("listaDeSubCategoria", subCategoriaDAO.listaTudo());
		return "administracao/subcategoria/lista";
	}

	@Secured({ "ROLE_CONFIGURACOES","ROLE_ADMIN"})
	@RequestMapping(value = ListaDeURLs.FORMULARIO_INSERCAO_DE_SUBCATEGORIA, method = RequestMethod.GET)
	public String carregaFormularioParaInsercao(Model model) {
		return "administracao/subcategoria/formulario";
	}

	@Secured({ "ROLE_CONFIGURACOES","ROLE_ADMIN"})
	@RequestMapping(value = ListaDeURLs.FORMULARIO_EDICAO_DE_SUBCATEGORIA + "/{id}", method = RequestMethod.GET)
	public String carregaFormularioParaEdicao(@PathVariable Long id, Model model) {
		SubCategoriaModel subCategoria = subCategoriaDAO.buscaPorId(id);
		model.addAttribute("subCategoria", subCategoria);
		return "administracao/subcategoria/formulario";
	}

	@Secured({ "ROLE_CONFIGURACOES","ROLE_ADMIN"})
	@RequestMapping(value = ListaDeURLs.INSERCAO_DE_SUBCATEGORIA, method = RequestMethod.POST)
	public String insere(@Valid SubCategoriaModel subCategoria, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

		if(bindingResult.hasErrors()){
			model.addAttribute("subCategoria", subCategoria);
			erros.setRedirectOrModel(model);
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for (ObjectError objectError : allErrors) {
				erros.adiciona(mensagensDoSistemaDAO.buscaPorError(objectError));
			}
			return "administracao/subCategoria/formulario";
		}
		subCategoria.setDataIncl(Calendar.getInstance());
		subCategoria.setDataAlt(Calendar.getInstance());
		subCategoriaDAO.insere(subCategoria);
		SubCategoriaModel encontrado = subCategoriaDAO.buscaPorId(subCategoria.getId());
		historico.inserir(encontrado, "SubCategoria");
		sucesso.setMensagem(redirectAttributes, mensagensDoSistemaDAO.buscaPorPropriedade("MensagemAdicionadoComSucesso").getValor());
		return "redirect:"+ListaDeURLs.LISTA_DE_SUBCATEGORIA;
	}

	@Secured({ "ROLE_CONFIGURACOES","ROLE_ADMIN"})
	@RequestMapping(value = ListaDeURLs.EDICAO_DE_SUBCATEGORIA, method = RequestMethod.POST)
	public String altera(@Valid SubCategoriaModel subCategoria, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

		if(bindingResult.hasErrors()){
			model.addAttribute("subCategoria", subCategoria);
			erros.setRedirectOrModel(model);
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for (ObjectError objectError : allErrors) {
				erros.adiciona(mensagensDoSistemaDAO.buscaPorError(objectError));
			}
			return "administracao/subcategoria/formulario";
		}

		SubCategoriaModel anterior = subCategoriaDAO.buscaPorIdClonando(subCategoria.getId());
		subCategoria.setDataAlt(Calendar.getInstance());
		subCategoria.setDataIncl(anterior.getDataIncl());
		subCategoriaDAO.altera(subCategoria);
		SubCategoriaModel atual = subCategoriaDAO.buscaPorIdClonando(subCategoria.getId());
		historico.alterar(anterior, atual, "SubCategoria");
		sucesso.setMensagem(redirectAttributes, mensagensDoSistemaDAO.buscaPorPropriedade("MensagemAlteradoComSucesso").getValor());
		return "redirect:"+ListaDeURLs.LISTA_DE_SUBCATEGORIA;
	}

	@Secured({ "ROLE_CONFIGURACOES","ROLE_ADMIN"})
	@RequestMapping(value = ListaDeURLs.EXCLUSAO_DE_SUBCATEGORIA, method = RequestMethod.POST)
	public String exclui(@Valid SubCategoriaModel subCategoria, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

		SubCategoriaModel encontrado = subCategoriaDAO.buscaPorIdClonando(subCategoria.getId());
		try {
			subCategoriaDAO.exclui(encontrado);
		} catch (Exception e) {
			erros.setRedirectOrModel(redirectAttributes);
			erros.adiciona("Não foi possível excluir o registro. Verificar se o registro está sendo utilizado em outras partes do sistema.");
			return "redirect:"+ListaDeURLs.LISTA_DE_SUBCATEGORIA;
		}
		historico.excluir(encontrado, "SubCategoria");
		sucesso.setMensagem(redirectAttributes, mensagensDoSistemaDAO.buscaPorPropriedade("MensagemExcluidoComSucesso").getValor());
		return "redirect:"+ListaDeURLs.LISTA_DE_SUBCATEGORIA;
	}

}