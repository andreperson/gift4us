package br.com.gift4us.produto;

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
public class ProdutoController {

	@Autowired
	private Erros erros;

	@Autowired
	private Sucesso sucesso;

	@Autowired
	private ProdutoDAO produtoDAO;

	@Autowired
	private MensagensDoSistemaDAO mensagensDoSistemaDAO;

	@Autowired
	private GerenciadorDeHistorico historico;

	@Secured({ "ROLE_ADMIN", "ROLE_GERENCIAL", "ROLE_ANUNCIANTE" })
	@RequestMapping(value = ListaDeURLs.LISTA_DE_PRODUTO, method = RequestMethod.GET)
	public String lista(Model model) {
		model.addAttribute("listaDeProduto", produtoDAO.listaTudo());
		return "administracao/produto/lista";
	}

	@Secured({ "ROLE_ADMIN", "ROLE_GERENCIAL", "ROLE_ANUNCIANTE" })
	@RequestMapping(value = ListaDeURLs.FORMULARIO_INSERCAO_DE_PRODUTO, method = RequestMethod.GET)
	public String carregaFormularioParaInsercao(Model model) {
		return "administracao/produto/formulario";
	}

	@Secured({ "ROLE_ADMIN", "ROLE_GERENCIAL", "ROLE_ANUNCIANTE" })
	@RequestMapping(value = ListaDeURLs.FORMULARIO_EDICAO_DE_PRODUTO + "/{id}", method = RequestMethod.GET)
	public String carregaFormularioParaEdicao(@PathVariable Long id, Model model) {
		ProdutoModel produto = produtoDAO.buscaPorId(id);
		model.addAttribute("produto", produto);
		return "administracao/produto/formulario";
	}

	@Secured({ "ROLE_ADMIN", "ROLE_GERENCIAL", "ROLE_ANUNCIANTE" })
	@RequestMapping(value = ListaDeURLs.INSERCAO_DE_PRODUTO, method = RequestMethod.POST)
	public String insere(@Valid ProdutoModel produto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

		if(bindingResult.hasErrors()){
			model.addAttribute("produto", produto);
			erros.setRedirectOrModel(model);
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for (ObjectError objectError : allErrors) {
				erros.adiciona(mensagensDoSistemaDAO.buscaPorError(objectError));
			}
			return "administracao/produto/formulario";
		}

		produto.setDataIncl(Calendar.getInstance());
		produto.setDataAlt(Calendar.getInstance());
		produtoDAO.insere(produto);
		ProdutoModel encontrado = produtoDAO.buscaPorId(produto.getId());
		historico.inserir(encontrado, "Produto");
		sucesso.setMensagem(redirectAttributes, mensagensDoSistemaDAO.buscaPorPropriedade("MensagemAdicionadoComSucesso").getValor());
		return "redirect:"+ListaDeURLs.LISTA_DE_PRODUTO;
	}

	@Secured({ "ROLE_ADMIN", "ROLE_GERENCIAL", "ROLE_ANUNCIANTE" })
	@RequestMapping(value = ListaDeURLs.EDICAO_DE_PRODUTO, method = RequestMethod.POST)
	public String altera(@Valid ProdutoModel produto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

		if(bindingResult.hasErrors()){
			model.addAttribute("produto", produto);
			erros.setRedirectOrModel(model);
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for (ObjectError objectError : allErrors) {
				erros.adiciona(mensagensDoSistemaDAO.buscaPorError(objectError));
			}
			return "administracao/produto/formulario";
		}

		ProdutoModel anterior = produtoDAO.buscaPorIdClonando(produto.getId());
		produto.setDataAlt(Calendar.getInstance());
		produto.setDataIncl(anterior.getDataIncl());
		produtoDAO.altera(produto);
		ProdutoModel atual = produtoDAO.buscaPorIdClonando(produto.getId());
		historico.alterar(anterior, atual, "Produto");
		sucesso.setMensagem(redirectAttributes, mensagensDoSistemaDAO.buscaPorPropriedade("MensagemAlteradoComSucesso").getValor());
		return "redirect:"+ListaDeURLs.LISTA_DE_PRODUTO;
	}

	@Secured({ "ROLE_ADMIN", "ROLE_GERENCIAL", "ROLE_ANUNCIANTE" })
	@RequestMapping(value = ListaDeURLs.EXCLUSAO_DE_PRODUTO, method = RequestMethod.POST)
	public String exclui(@Valid ProdutoModel produto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

		ProdutoModel encontrado = produtoDAO.buscaPorIdClonando(produto.getId());
		try {
			produtoDAO.exclui(encontrado);
		} catch (Exception e) {
			erros.setRedirectOrModel(redirectAttributes);
			erros.adiciona("Não foi possível excluir o registro. Verificar se o registro está sendo utilizado em outras partes do sistema.");
			return "redirect:"+ListaDeURLs.LISTA_DE_PRODUTO;
		}
		historico.excluir(encontrado, "Produto");
		sucesso.setMensagem(redirectAttributes, mensagensDoSistemaDAO.buscaPorPropriedade("MensagemExcluidoComSucesso").getValor());
		return "redirect:"+ListaDeURLs.LISTA_DE_PRODUTO;
	}

}