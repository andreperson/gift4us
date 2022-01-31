package br.com.gift4us.linha;

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
public class LinhaController {

	@Autowired
	private Erros erros;

	@Autowired
	private Sucesso sucesso;

	@Autowired
	private LinhaDAO linhaDAO;

	@Autowired
	private MensagensDoSistemaDAO mensagensDoSistemaDAO;

	@Autowired
	private GerenciadorDeHistorico historico;

	@Secured({ "ROLE_CONFIGURACOES", "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.LISTA_DE_LINHA, method = RequestMethod.GET)
	public String lista(Model model) {
		model.addAttribute("listaDeLinha", linhaDAO.listaTudo());
		return "administracao/linha/lista";
	}

	@Secured({ "ROLE_CONFIGURACOES", "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.FORMULARIO_INSERCAO_DE_LINHA, method = RequestMethod.GET)
	public String carregaFormularioParaInsercao(Model model) {
		return "administracao/linha/formulario";
	}

	@Secured({ "ROLE_CONFIGURACOES", "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.FORMULARIO_EDICAO_DE_LINHA + "/{id}", method = RequestMethod.GET)
	public String carregaFormularioParaEdicao(@PathVariable Long id, Model model) {
		LinhaModel linha = linhaDAO.buscaPorId(id);
		model.addAttribute("linha", linha);
		return "administracao/linha/formulario";
	}

	@Secured({ "ROLE_CONFIGURACOES", "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.INSERCAO_DE_LINHA, method = RequestMethod.POST)
	public String insere(@Valid LinhaModel linha, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("linha", linha);
			erros.setRedirectOrModel(model);
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for (ObjectError objectError : allErrors) {
				erros.adiciona(mensagensDoSistemaDAO.buscaPorError(objectError));
			}
			return "administracao/linha/formulario";
		}

		// verifica se ja existe uma linha com esse nome
		List<LinhaModel> lst = linhaDAO.buscaPorNomeExato(linha.getNome());
		if (lst.size() > 0) {
			String msg = mensagensDoSistemaDAO.buscaPorPropriedade("RegistroDuplicado").getValor();

			model.addAttribute("linha", linha);
			model.addAttribute("alertademsg", msg);

			return "administracao/linha/formulario";
		}

		linha.setDataIncl(Calendar.getInstance());
		linha.setDataAlt(Calendar.getInstance());
		linha.setStatus(StatusEnum.ATIVO);
		linhaDAO.insere(linha);
		LinhaModel encontrado = linhaDAO.buscaPorId(linha.getId());
		historico.inserir(encontrado, "linha");
		sucesso.setMensagem(redirectAttributes,
				mensagensDoSistemaDAO.buscaPorPropriedade("MensagemAdicionadoComSucesso").getValor());
		return "redirect:" + ListaDeURLs.LISTA_DE_LINHA;
	}

	@Secured({ "ROLE_CONFIGURACOES", "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.EDICAO_DE_LINHA, method = RequestMethod.POST)
	public String altera(@Valid LinhaModel linha, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("linha", linha);
			erros.setRedirectOrModel(model);
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for (ObjectError objectError : allErrors) {
				erros.adiciona(mensagensDoSistemaDAO.buscaPorError(objectError));
			}
			return "administracao/linha/formulario";
		}

		// verifica se ja existe uma linha com esse nome
		List<LinhaModel> lst = linhaDAO.buscaPorNomeExato(linha.getNome());
		if (lst.size() > 0) {
			String msg = mensagensDoSistemaDAO.buscaPorPropriedade("RegistroDuplicado").getValor();

			model.addAttribute("linha", linha);
			model.addAttribute("alertademsg", msg);

			return "administracao/linha/formulario";
		}

		LinhaModel anterior = linhaDAO.buscaPorIdClonando(linha.getId());
		linha.setDataAlt(Calendar.getInstance());
		linha.setDataIncl(anterior.getDataIncl());
		linha.setStatus(StatusEnum.ATIVO);

		linhaDAO.altera(linha);
		LinhaModel atual = linhaDAO.buscaPorIdClonando(linha.getId());
		historico.alterar(anterior, atual, "linha");
		sucesso.setMensagem(redirectAttributes,
				mensagensDoSistemaDAO.buscaPorPropriedade("MensagemAlteradoComSucesso").getValor());
		return "redirect:" + ListaDeURLs.LISTA_DE_LINHA;
	}

	@Secured({ "ROLE_CONFIGURACOES", "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.EXCLUSAO_DE_LINHA, method = RequestMethod.POST)
	public String exclui(@Valid LinhaModel linha, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {

		LinhaModel encontrado = linhaDAO.buscaPorIdClonando(linha.getId());
		try {
			linhaDAO.exclui(encontrado);
		} catch (Exception e) {
			erros.setRedirectOrModel(redirectAttributes);
			erros.adiciona(
					"Não foi possível excluir o registro. Verificar se o registro está sendo utilizado em outras partes do sistema.");
			return "redirect:" + ListaDeURLs.LISTA_DE_LINHA;
		}
		historico.excluir(encontrado, "linha");
		sucesso.setMensagem(redirectAttributes,
				mensagensDoSistemaDAO.buscaPorPropriedade("MensagemExcluidoComSucesso").getValor());
		return "redirect:" + ListaDeURLs.LISTA_DE_LINHA;
	}

}