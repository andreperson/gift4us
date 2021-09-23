package br.com.gift4us.msbcvalidator;

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
public class MsbcValidatorController {

	@Autowired
	private Erros erros;

	@Autowired
	private Sucesso sucesso;

	@Autowired
	private AbrirOuBaixarArquivo arquivo;

	@Autowired
	private ConfiguracoesDoSistemaDAO configuracoesDoSistemaDAO;

	@Autowired
	private MsbcValidatorDAO msbcValidatorDAO;

	@Autowired
	private MensagensDoSistemaDAO mensagensDoSistemaDAO;

	@Autowired
	private GerenciadorDeHistorico historico;

	@Secured({ "ROLE_MSBCVALIDATOR_VER" })
	@RequestMapping(value = ListaDeURLs.LISTA_DE_MSBCVALIDATOR, method = RequestMethod.GET)
	public String lista(Model model) {
		model.addAttribute("listaDeMsbcValidator", msbcValidatorDAO.listaTudo());
		return "administracao/msbcvalidator/lista";
	}

	@Secured({ "ROLE_MSBCVALIDATOR_INSERIR" })
	@RequestMapping(value = ListaDeURLs.FORMULARIO_INSERCAO_DE_MSBCVALIDATOR, method = RequestMethod.GET)
	public String carregaFormularioParaInsercao(Model model) {
		return "administracao/msbcvalidator/formulario";
	}

	@Secured({ "ROLE_MSBCVALIDATOR_ALTERAR" })
	@RequestMapping(value = ListaDeURLs.FORMULARIO_EDICAO_DE_MSBCVALIDATOR + "/{id}", method = RequestMethod.GET)
	public String carregaFormularioParaEdicao(@PathVariable Long id, Model model) {
		MsbcValidatorModel msbcValidator = msbcValidatorDAO.buscaPorId(id);
		model.addAttribute("msbcValidator", msbcValidator);
		return "administracao/msbcvalidator/formulario";
	}

	@Secured({ "ROLE_MSBCVALIDATOR_INSERIR" })
	@RequestMapping(value = ListaDeURLs.INSERCAO_DE_MSBCVALIDATOR, method = RequestMethod.POST)
	public String insere(@Valid MsbcValidatorModel msbcValidator, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

		uploadArquivoArquivoTeste(msbcValidator, bindingResult);
		if(bindingResult.hasErrors()){
			model.addAttribute("msbcValidator", msbcValidator);
			erros.setRedirectOrModel(model);
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for (ObjectError objectError : allErrors) {
				erros.adiciona(mensagensDoSistemaDAO.buscaPorError(objectError));
			}
			return "administracao/msbcValidator/formulario";
		}

		msbcValidatorDAO.insere(msbcValidator);
		MsbcValidatorModel encontrado = msbcValidatorDAO.buscaPorId(msbcValidator.getId());
		historico.inserir(encontrado, "MsbcValidator");
		sucesso.setMensagem(redirectAttributes, mensagensDoSistemaDAO.buscaPorPropriedade("MensagemAdicionadoComSucesso").getValor());
		return "redirect:"+ListaDeURLs.LISTA_DE_MSBCVALIDATOR;
	}

	@Secured({ "ROLE_MSBCVALIDATOR_ALTERAR" })
	@RequestMapping(value = ListaDeURLs.EDICAO_DE_MSBCVALIDATOR, method = RequestMethod.POST)
	public String altera(@Valid MsbcValidatorModel msbcValidator, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

		if (msbcValidator.isValidoArquivoTesteFile()) {
			uploadArquivoArquivoTeste(msbcValidator, bindingResult);
		}

		if(bindingResult.hasErrors()){
			model.addAttribute("msbcValidator", msbcValidator);
			erros.setRedirectOrModel(model);
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for (ObjectError objectError : allErrors) {
				erros.adiciona(mensagensDoSistemaDAO.buscaPorError(objectError));
			}
			return "administracao/msbcvalidator/formulario";
		}

		MsbcValidatorModel anterior = msbcValidatorDAO.buscaPorIdClonando(msbcValidator.getId());

		msbcValidatorDAO.altera(msbcValidator);
		MsbcValidatorModel atual = msbcValidatorDAO.buscaPorIdClonando(msbcValidator.getId());
		historico.alterar(anterior, atual, "MsbcValidator");
		sucesso.setMensagem(redirectAttributes, mensagensDoSistemaDAO.buscaPorPropriedade("MensagemAlteradoComSucesso").getValor());
		return "redirect:"+ListaDeURLs.LISTA_DE_MSBCVALIDATOR;
	}

	@Secured({ "ROLE_MSBCVALIDATOR_EXCLUIR" })
	@RequestMapping(value = ListaDeURLs.EXCLUSAO_DE_MSBCVALIDATOR, method = RequestMethod.POST)
	public String exclui(@Valid MsbcValidatorModel msbcValidator, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

		MsbcValidatorModel encontrado = msbcValidatorDAO.buscaPorIdClonando(msbcValidator.getId());
		if (encontrado.isValidoArquivoTeste()) {
			new File(encontrado.getArquivoTeste()).delete();
		}

		try {
			msbcValidatorDAO.exclui(encontrado);
		} catch (Exception e) {
			erros.setRedirectOrModel(redirectAttributes);
			erros.adiciona("Não foi possível excluir o registro. Verificar se o registro está sendo utilizado em outras partes do sistema.");
			return "redirect:"+ListaDeURLs.LISTA_DE_MSBCVALIDATOR;
		}
		historico.excluir(encontrado, "MsbcValidator");
		sucesso.setMensagem(redirectAttributes, mensagensDoSistemaDAO.buscaPorPropriedade("MensagemExcluidoComSucesso").getValor());
		return "redirect:"+ListaDeURLs.LISTA_DE_MSBCVALIDATOR;
	}

	private void uploadArquivoArquivoTeste(MsbcValidatorModel model, BindingResult bindingResult) {
		String diretorio = configuracoesDoSistemaDAO.buscaPorPropriedade("DIRETORIO_DOS_ARQUIVOS").getValor();
		
		UploadDeArquivo manipulador = new UploadDeArquivo(model.getArquivoTesteFile(), diretorio);
		if (!manipulador.isValido()) {
			ObjectError erro = new ObjectError(manipulador.getErro(), "");
			bindingResult.addError(erro);
			return;
		}

		if (!manipulador.salvaComNomeNormalizado()) {
			ObjectError erro = new ObjectError(manipulador.getErro(), "");
			bindingResult.addError(erro);
			return;
		}
		
		model.setArquivoTeste(manipulador.getNomeDoArquivoSalvo());
	}
	@RequestMapping(
			value = { 
					ListaDeURLs.ARQUIVOS_DE_MSBCVALIDATOR_LOGADO + "/mostra/{id}/{propriedade}",
					ListaDeURLs.ARQUIVOS_DE_MSBCVALIDATOR_LOGADO + "/download/{id}/{propriedade}" ,
					ListaDeURLs.ARQUIVOS_DE_MSBCVALIDATOR_SITE + "/mostra/{id}/{propriedade}",
					ListaDeURLs.ARQUIVOS_DE_MSBCVALIDATOR_SITE + "/download/{id}/{propriedade}" 
			}, method = RequestMethod.GET, produces = "application/octet-stream")
	public @ResponseBody FileSystemResource arquivo(@PathVariable(value = "id") Long id,
			@PathVariable(value = "propriedade") String propriedade, HttpServletRequest request,
			HttpServletResponse response) {

		MsbcValidatorModel msbcValidator = msbcValidatorDAO.buscaPorId(id);

		try {
			Method method = msbcValidator.getClass().getMethod("get" + propriedade);
			Object arquivoCaminho = method.invoke(msbcValidator);
			File file = new File(arquivoCaminho.toString());
			if(request.getRequestURL().toString().contains("/msbcvalidator/arquivo/mostra/")){
				return arquivo.open(file);
			}else{				
				return arquivo.download(file);
			}
		} catch (Exception e) {
			return null;
		}

	}
}