package br.com.gift4us.modelo;

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
public class ModeloController {

	@Autowired
	private Erros erros;

	@Autowired
	private Sucesso sucesso;

	@Autowired
	private AbrirOuBaixarArquivo arquivo;

	@Autowired
	private ConfiguracoesDoSistemaDAO configuracoesDoSistemaDAO;

	@Autowired
	private ModeloDAO modeloDAO;

	@Autowired
	private MensagensDoSistemaDAO mensagensDoSistemaDAO;

	@Autowired
	private GerenciadorDeHistorico historico;

	@Secured({ "ROLE_MODELO_VER" })
	@RequestMapping(value = ListaDeURLs.LISTA_DE_MODELO, method = RequestMethod.GET)
	public String lista(Model model) {
		model.addAttribute("listaDeModelo", modeloDAO.listaTudo());
		return "administracao/modelo/lista";
	}

	@Secured({ "ROLE_MODELO_INSERIR" })
	@RequestMapping(value = ListaDeURLs.FORMULARIO_INSERCAO_DE_MODELO, method = RequestMethod.GET)
	public String carregaFormularioParaInsercao(Model model) {
		return "administracao/modelo/formulario";
	}

	@Secured({ "ROLE_MODELO_ALTERAR" })
	@RequestMapping(value = ListaDeURLs.FORMULARIO_EDICAO_DE_MODELO + "/{id}", method = RequestMethod.GET)
	public String carregaFormularioParaEdicao(@PathVariable Long id, Model model) {
		ModeloModel modelo = modeloDAO.buscaPorId(id);
		model.addAttribute("modelo", modelo);
		return "administracao/modelo/formulario";
	}

	@Secured({ "ROLE_MODELO_INSERIR" })
	@RequestMapping(value = ListaDeURLs.INSERCAO_DE_MODELO, method = RequestMethod.POST)
	public String insere(@Valid ModeloModel modelo, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

		uploadArquivoImagemUnica(modelo, bindingResult);
		uploadArquivoImagemCabecalho(modelo, bindingResult);
		uploadArquivoImagemRodape(modelo, bindingResult);
		if(bindingResult.hasErrors()){
			model.addAttribute("modelo", modelo);
			erros.setRedirectOrModel(model);
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for (ObjectError objectError : allErrors) {
				erros.adiciona(mensagensDoSistemaDAO.buscaPorError(objectError));
			}
			return "administracao/modelo/formulario";
		}

		modeloDAO.insere(modelo);
		ModeloModel encontrado = modeloDAO.buscaPorId(modelo.getId());
		historico.inserir(encontrado, "Modelo");
		sucesso.setMensagem(redirectAttributes, mensagensDoSistemaDAO.buscaPorPropriedade("MensagemAdicionadoComSucesso").getValor());
		return "redirect:"+ListaDeURLs.LISTA_DE_MODELO;
	}

	@Secured({ "ROLE_MODELO_ALTERAR" })
	@RequestMapping(value = ListaDeURLs.EDICAO_DE_MODELO, method = RequestMethod.POST)
	public String altera(@Valid ModeloModel modelo, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

		if (modelo.isValidoImagemUnicaFile()) {
			uploadArquivoImagemUnica(modelo, bindingResult);
		}

		if (modelo.isValidoImagemCabecalhoFile()) {
			uploadArquivoImagemCabecalho(modelo, bindingResult);
		}

		if (modelo.isValidoImagemRodapeFile()) {
			uploadArquivoImagemRodape(modelo, bindingResult);
		}

		if(bindingResult.hasErrors()){
			model.addAttribute("modelo", modelo);
			erros.setRedirectOrModel(model);
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for (ObjectError objectError : allErrors) {
				erros.adiciona(mensagensDoSistemaDAO.buscaPorError(objectError));
			}
			return "administracao/modelo/formulario";
		}

		ModeloModel anterior = modeloDAO.buscaPorIdClonando(modelo.getId());

		modeloDAO.altera(modelo);
		ModeloModel atual = modeloDAO.buscaPorIdClonando(modelo.getId());
		historico.alterar(anterior, atual, "Modelo");
		sucesso.setMensagem(redirectAttributes, mensagensDoSistemaDAO.buscaPorPropriedade("MensagemAlteradoComSucesso").getValor());
		return "redirect:"+ListaDeURLs.LISTA_DE_MODELO;
	}

	@Secured({ "ROLE_MODELO_EXCLUIR" })
	@RequestMapping(value = ListaDeURLs.EXCLUSAO_DE_MODELO, method = RequestMethod.POST)
	public String exclui(@Valid ModeloModel modelo, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

		ModeloModel encontrado = modeloDAO.buscaPorIdClonando(modelo.getId());
		if (encontrado.isValidoImagemUnica()) {
			new File(encontrado.getImagemUnica()).delete();
		}

		if (encontrado.isValidoImagemCabecalho()) {
			new File(encontrado.getImagemCabecalho()).delete();
		}

		if (encontrado.isValidoImagemRodape()) {
			new File(encontrado.getImagemRodape()).delete();
		}

		try {
			modeloDAO.exclui(encontrado);
		} catch (Exception e) {
			erros.setRedirectOrModel(redirectAttributes);
			erros.adiciona("Não foi possível excluir o registro. Verificar se o registro está sendo utilizado em outras partes do sistema.");
			return "redirect:"+ListaDeURLs.LISTA_DE_MODELO;
		}
		historico.excluir(encontrado, "Modelo");
		sucesso.setMensagem(redirectAttributes, mensagensDoSistemaDAO.buscaPorPropriedade("MensagemExcluidoComSucesso").getValor());
		return "redirect:"+ListaDeURLs.LISTA_DE_MODELO;
	}

	private void uploadArquivoImagemUnica(ModeloModel model, BindingResult bindingResult) {
		String diretorio = configuracoesDoSistemaDAO.buscaPorPropriedade("DIRETORIO_DOS_ARQUIVOS").getValor();
		
		UploadDeArquivo manipulador = new UploadDeArquivo(model.getImagemUnicaFile(), diretorio);
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
		
		model.setImagemUnica(manipulador.getNomeDoArquivoSalvo());
	}
	private void uploadArquivoImagemCabecalho(ModeloModel model, BindingResult bindingResult) {
		String diretorio = configuracoesDoSistemaDAO.buscaPorPropriedade("DIRETORIO_DOS_ARQUIVOS").getValor();
		
		UploadDeArquivo manipulador = new UploadDeArquivo(model.getImagemCabecalhoFile(), diretorio);
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
		
		model.setImagemCabecalho(manipulador.getNomeDoArquivoSalvo());
	}
	private void uploadArquivoImagemRodape(ModeloModel model, BindingResult bindingResult) {
		String diretorio = configuracoesDoSistemaDAO.buscaPorPropriedade("DIRETORIO_DOS_ARQUIVOS").getValor();
		
		UploadDeArquivo manipulador = new UploadDeArquivo(model.getImagemRodapeFile(), diretorio);
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
		
		model.setImagemRodape(manipulador.getNomeDoArquivoSalvo());
	}
	@RequestMapping(
			value = { 
					ListaDeURLs.ARQUIVOS_DE_MODELO_LOGADO + "/mostra/{id}/{propriedade}",
					ListaDeURLs.ARQUIVOS_DE_MODELO_LOGADO + "/download/{id}/{propriedade}" ,
					ListaDeURLs.ARQUIVOS_DE_MODELO_SITE + "/mostra/{id}/{propriedade}",
					ListaDeURLs.ARQUIVOS_DE_MODELO_SITE + "/download/{id}/{propriedade}" 
			}, method = RequestMethod.GET, produces = "application/octet-stream")
	public @ResponseBody FileSystemResource arquivo(@PathVariable(value = "id") Long id,
			@PathVariable(value = "propriedade") String propriedade, HttpServletRequest request,
			HttpServletResponse response) {

		ModeloModel modelo = modeloDAO.buscaPorId(id);

		try {
			Method method = modelo.getClass().getMethod("get" + propriedade);
			Object arquivoCaminho = method.invoke(modelo);
			File file = new File(arquivoCaminho.toString());
			if(request.getRequestURL().toString().contains("/modelo/arquivo/mostra/")){
				return arquivo.open(file);
			}else{				
				return arquivo.download(file);
			}
		} catch (Exception e) {
			return null;
		}

	}
}