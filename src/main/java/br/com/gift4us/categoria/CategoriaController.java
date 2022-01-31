package br.com.gift4us.categoria;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
import br.com.gift4us.util.FileUploader;
import br.com.gift4us.util.Propriedades;
import br.com.gift4us.util.UploadDeArquivo;
import br.com.gift4us.configuracoesdosistema.ConfiguracoesDoSistemaDAO;
import br.com.gift4us.mensagensdosistema.Alerta;
import br.com.gift4us.mensagensdosistema.Erros;
import br.com.gift4us.mensagensdosistema.Sucesso;
import br.com.gift4us.status.StatusEnum;
import br.com.gift4us.historicodosistema.GerenciadorDeHistorico;
import br.com.gift4us.mensagensdosistema.MensagensDoSistemaDAO;
import br.com.gift4us.mensagensdosistema.MensagensDoSistemaModel;

@Controller
public class CategoriaController {

	@Autowired
	private Erros erros;
	
	@Autowired
	private Propriedades propriedades;

	@Autowired
	FileUploader fileUploader;
	
	@Autowired
	private Sucesso sucesso;

	@Autowired
	private Alerta alerta;

	@Autowired
	private CategoriaDAO categoriaDAO;

	@Autowired
	private MensagensDoSistemaDAO mensagensDoSistemaDAO;

	@Autowired
	private GerenciadorDeHistorico historico;

	@Secured({ "ROLE_CONFIGURACOES", "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.LISTA_DE_CATEGORIA, method = RequestMethod.GET)
	public String lista(Model model) {
		model.addAttribute("listaDeCategoria", categoriaDAO.listaTudo());
		return "administracao/categoria/lista";
	}

	@Secured({ "ROLE_CONFIGURACOES", "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.FORMULARIO_INSERCAO_DE_CATEGORIA, method = RequestMethod.GET)
	public String carregaFormularioParaInsercao(Model model) {
		return "administracao/categoria/formulario";
	}

	@Secured({ "ROLE_CONFIGURACOES", "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.FORMULARIO_EDICAO_DE_CATEGORIA + "/{id}", method = RequestMethod.GET)
	public String carregaFormularioParaEdicao(@PathVariable Long id, Model model) {
		CategoriaModel categoria = categoriaDAO.buscaPorId(id);
		model.addAttribute("categoria", categoria);
		return "administracao/categoria/formulario";
	}

	@Secured({ "ROLE_CONFIGURACOES", "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.INSERCAO_DE_CATEGORIA, method = RequestMethod.POST)
	public String insere(@Valid CategoriaModel categoria, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes, MultipartFile arquivo) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("categoria", categoria);
			erros.setRedirectOrModel(model);
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for (ObjectError objectError : allErrors) {
				erros.adiciona(mensagensDoSistemaDAO.buscaPorError(objectError));
			}
			return "administracao/categoria/formulario";
		}
		
		// verifica se ja existe uma categoria com esse nome
		List<CategoriaModel> lst = categoriaDAO.buscaPorNomeExato(categoria.getNome());
		if (lst.size() > 0) {
			String msg = mensagensDoSistemaDAO.buscaPorPropriedade("RegistroDuplicado").getValor();

			model.addAttribute("categoria", categoria);
			model.addAttribute("alertademsg", msg);

			return "administracao/categoria/formulario";
		}

		categoria.setDataIncl(Calendar.getInstance());
		categoria.setDataAlt(Calendar.getInstance());
		categoria.setStatus(StatusEnum.ATIVO);
		if (arquivo != null) {
			categoria.setImagem(arquivo.getName());
		}
		Long categoriaid = categoriaDAO.saveorupdate(categoria);
		CategoriaModel encontrado = categoriaDAO.buscaPorId(categoria.getId());
		historico.inserir(encontrado, "Categoria");
		
		if(arquivo != null) {
			uploadImagem(bindingResult, categoriaid, arquivo);	
		}
		
		sucesso.setMensagem(redirectAttributes,
				mensagensDoSistemaDAO.buscaPorPropriedade("MensagemAdicionadoComSucesso").getValor());
		return "redirect:" + ListaDeURLs.LISTA_DE_CATEGORIA;
	}

	@Secured({ "ROLE_CONFIGURACOES", "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.EDICAO_DE_CATEGORIA, method = RequestMethod.POST)
	public String altera(@Valid CategoriaModel categoria, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes, MultipartFile arquivo) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("categoria", categoria);
			erros.setRedirectOrModel(model);
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for (ObjectError objectError : allErrors) {
				erros.adiciona(mensagensDoSistemaDAO.buscaPorError(objectError));
			}
			return "administracao/categoria/formulario";
		}

		// verifica se ja existe uma categoria com esse nome
		List<CategoriaModel> lst = categoriaDAO.buscaPorNomeExato(categoria.getNome());
		if (lst.size() > 0) {
			String msg = mensagensDoSistemaDAO.buscaPorPropriedade("RegistroDuplicado").getValor();

			model.addAttribute("categoria", categoria);
			model.addAttribute("alertademsg", msg);

			return "administracao/categoria/formulario";
		}

		CategoriaModel anterior = categoriaDAO.buscaPorIdClonando(categoria.getId());
		categoria.setDataAlt(Calendar.getInstance());
		categoria.setDataIncl(anterior.getDataIncl());
		categoria.setImagem(anterior.getImagem());
		categoria.setStatus(StatusEnum.ATIVO);
		if (arquivo.getSize() > 0) {
			categoria.setImagem(arquivo.getOriginalFilename());
		}
		categoriaDAO.altera(categoria);
		CategoriaModel atual = categoriaDAO.buscaPorIdClonando(categoria.getId());
		historico.alterar(anterior, atual, "Categoria");
		
		if(arquivo.getSize() > 0) {
			uploadImagem(bindingResult, categoria.getId(), arquivo);	
		}
		
		sucesso.setMensagem(redirectAttributes,
				mensagensDoSistemaDAO.buscaPorPropriedade("MensagemAlteradoComSucesso").getValor());
		return "redirect:" + ListaDeURLs.LISTA_DE_CATEGORIA;
	}

	@Secured({ "ROLE_CONFIGURACOES", "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.EXCLUSAO_DE_CATEGORIA, method = RequestMethod.POST)
	public String exclui(@Valid CategoriaModel categoria, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {

		CategoriaModel encontrado = categoriaDAO.buscaPorIdClonando(categoria.getId());
		try {
			categoriaDAO.exclui(encontrado);
		} catch (Exception e) {
			erros.setRedirectOrModel(redirectAttributes);
			erros.adiciona(
					"Não foi possível excluir o registro. Verificar se o registro está sendo utilizado em outras partes do sistema.");
			return "redirect:" + ListaDeURLs.LISTA_DE_CATEGORIA;
		}
		historico.excluir(encontrado, "Categoria");
		sucesso.setMensagem(redirectAttributes,
				mensagensDoSistemaDAO.buscaPorPropriedade("MensagemExcluidoComSucesso").getValor());
		return "redirect:" + ListaDeURLs.LISTA_DE_CATEGORIA;
	}

	private void uploadImagem(BindingResult result, Long categoriaid, MultipartFile arquivo) {
		try {
			String diretorio = propriedades.getValor("arquivo.diretorio.categoria.upload") + propriedades.getValor("arquivo.diretorio.barra") + categoriaid;
			System.out.println("antes de gravar o arquivo:" + diretorio);
			fileUploader.grava(arquivo, result, diretorio);
			System.out.println("depois de gravar o arquivo:" + diretorio);
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Erro ao gravar arquivo: " + e.getMessage());
		}
		
	}
	
}