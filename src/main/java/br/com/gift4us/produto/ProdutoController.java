package br.com.gift4us.produto;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.core.io.FileSystemResource;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import br.com.gift4us.urls.ListaDeURLs;
import br.com.gift4us.usuario.UsuarioAdaptador;
import br.com.gift4us.usuario.UsuarioDAO;
import br.com.gift4us.usuario.UsuarioModel;
import br.com.gift4us.util.FileUploader;
import br.com.gift4us.util.Propriedades;
import br.com.gift4us.anunciante.AnuncianteDAO;
import br.com.gift4us.anunciante.AnuncianteModel;
import br.com.gift4us.categoria.CategoriaModel;
import br.com.gift4us.faixadepreco.FaixaDePrecoDAO;
import br.com.gift4us.faixadepreco.FaixaDePrecoModel;
import br.com.gift4us.mensagensdosistema.Erros;
import br.com.gift4us.mensagensdosistema.Sucesso;
import br.com.gift4us.status.StatusDAO;
import br.com.gift4us.status.StatusModel;
import br.com.gift4us.subcategoria.SubCategoriaDAO;
import br.com.gift4us.subcategoria.SubCategoriaModel;
import br.com.gift4us.historicodosistema.GerenciadorDeHistorico;
import br.com.gift4us.linha.LinhaDAO;
import br.com.gift4us.linha.LinhaModel;
import br.com.gift4us.mensagensdosistema.MensagensDoSistemaDAO;

@Controller
public class ProdutoController {

	@Autowired
	private Propriedades propriedades;

	@Autowired
	private Erros erros;

	@Autowired
	private Sucesso sucesso;

	@Autowired
	private ProdutoDAO produtoDAO;

	@Autowired
	private LinhaDAO linhaDAO;
	
	@Autowired
	private ImagemDAO imagemDAO;

	@Autowired
	private AnuncianteDAO anuncianteDAO;

	@Autowired
	private SubCategoriaDAO subcategoriaDAO;

	@Autowired
	private FaixaDePrecoDAO faixadeprecoDAO;

	@Autowired
	private MensagensDoSistemaDAO mensagensDoSistemaDAO;

	@Autowired
	private GerenciadorDeHistorico historico;

	@Autowired
	private UsuarioAdaptador usuarioAdaptador;

	@Autowired
	private StatusDAO statusDAO;

	@Autowired
	FileUploader fileUploader;

	@Autowired
	private UsuarioDAO usuarioDAO;

	@Secured({ "ROLE_ADMIN", "ROLE_ANUNCIANTE_GERENCIAL", "ROLE_ANUNCIANTE" })
	@RequestMapping(value = ListaDeURLs.LISTA_DE_PRODUTO, method = RequestMethod.GET)
	public String lista(Model model) {
		model.addAttribute("listaDeProduto", produtoDAO.listaTudo());
		return "administracao/produto/lista";
	}

	@Secured({ "ROLE_ADMIN", "ROLE_ANUNCIANTE_GERENCIAL", "ROLE_ANUNCIANTE" })
	@RequestMapping(value = ListaDeURLs.FORMULARIO_INSERCAO_DE_PRODUTO, method = RequestMethod.GET)
	public String carregaFormularioParaInsercao(Model model) {
		model.addAttribute("urlpadrao", propriedades.getValor("arquivo.diretorio.arquivos"));
		return "administracao/produto/formulario";
	}

	@Secured({ "ROLE_ADMIN", "ROLE_ANUNCIANTE_GERENCIAL", "ROLE_ANUNCIANTE" })
	@RequestMapping(value = ListaDeURLs.FORMULARIO_EDICAO_DE_PRODUTO + "/{id}", method = RequestMethod.GET)
	public String carregaFormularioParaEdicao(@PathVariable Long id, Model model) {
		ProdutoModel produto = produtoDAO.buscaPorId(id);
		model.addAttribute("urlpadrao", propriedades.getValor("arquivo.diretorio.arquivos"));
		model.addAttribute("produto", produto);
		return "administracao/produto/formulario";
	}

	@Secured({ "ROLE_ADMIN", "ROLE_ANUNCIANTE_GERENCIAL", "ROLE_ANUNCIANTE" })
	@RequestMapping(value = ListaDeURLs.FORMULARIO_INSERCAO_DE_IMAGEM + "/{id}", method = RequestMethod.GET)
	public String carregaFormularioParaImagens(@PathVariable Long id, Model model) {
		ProdutoModel produto = produtoDAO.buscaPorId(id);
		List<ImagemModel> lstImagem = imagemDAO.buscaPorProduto(produto);
		model.addAttribute("produto", produto);
		model.addAttribute("lstImagem", lstImagem);
		model.addAttribute("urlpadrao", propriedades.getValor("arquivo.diretorio.arquivos"));

		return "administracao/produto/imagens";
	}

	@Secured({ "ROLE_ADMIN", "ROLE_ANUNCIANTE_GERENCIAL", "ROLE_ANUNCIANTE" })
	@Transactional
	@RequestMapping(value = ListaDeURLs.INSERCAO_DE_PRODUTO, method = RequestMethod.POST)
	public String insere(@RequestParam("subcategoriaid") Long subcategoriaid,
			@RequestParam("faixadeprecoid") Long faixadeprecoid, @Valid ProdutoModel produto,
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request,
			MultipartFile arquivo) {

		List<String> lst = validaPreenchimento(produto, subcategoriaid, faixadeprecoid);

		if (lst.size() > 0) {
			model.addAttribute("produto", produto);
			erros.setRedirectOrModel(model);
			for (String str : lst) {
				erros.adiciona(str);
			}

			return "administracao/produto/formulario";
		}

		verificaCampos(produto);

		// busca subcategoria
		StatusModel status = statusDAO.buscaIdDoStatus("Ativo");
		produto.setStatus(status);
		produto.setSubCategoria(buscaSubCategoria(subcategoriaid));
		produto.setFaixaDePreco(buscaFaixaDePreco(faixadeprecoid));
		produto.setLinha(buscaLinha(produto.getLinha().getId()));
		produto.setAnunciante(buscaAnunciantePeloUsuarioLogado());
		produto.setDataIncl(Calendar.getInstance());
		produto.setDataAlt(Calendar.getInstance());
		if (arquivo.getSize() > 0) {
			produto.setImagem(arquivo.getOriginalFilename());
		}
		Long produtoid = produtoDAO.saveorupdate(produto);
		ProdutoModel encontrado = produtoDAO.buscaPorId(produto.getId());
		historico.inserir(encontrado, "Produto");

		if (arquivo.getSize() > 0) {
			uploadImagem(bindingResult, produtoid, produto.getAnunciante().getId(), arquivo);
		}

		sucesso.setMensagem(redirectAttributes,
				mensagensDoSistemaDAO.buscaPorPropriedade("MensagemAdicionadoComSucesso").getValor());
		return "redirect:" + ListaDeURLs.FORMULARIO_INSERCAO_DE_IMAGEM + "/" + produtoid;
	}

	@Secured({ "ROLE_ADMIN", "ROLE_ANUNCIANTE_GERENCIAL", "ROLE_ANUNCIANTE" })
	@RequestMapping(value = ListaDeURLs.EDICAO_DE_PRODUTO, method = RequestMethod.POST)
	public String altera(@RequestParam("subcategoriaid") Long subcategoriaid,
			@RequestParam("faixadeprecoid") Long faixadeprecoid, @Valid ProdutoModel produto,
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes, MultipartFile arquivo) {

		List<String> lst = validaPreenchimento(produto, subcategoriaid, faixadeprecoid);

		if (lst.size() > 0) {
			model.addAttribute("produto", produto);
			erros.setRedirectOrModel(model);
			for (String str : lst) {
				erros.adiciona(str);
			}

			return "administracao/produto/formulario";
		}

		verificaCampos(produto);

		ProdutoModel anterior = produtoDAO.buscaPorIdClonando(produto.getId());
		produto.setSubCategoria(buscaSubCategoria(subcategoriaid));
		produto.setFaixaDePreco(buscaFaixaDePreco(faixadeprecoid));
		produto.setAnunciante(buscaAnunciantePeloUsuarioLogado());
		produto.setDataAlt(Calendar.getInstance());
		produto.setDataIncl(anterior.getDataIncl());
		produto.setImagem(anterior.getImagem());
		produto.setStatus(anterior.getStatus());
		if (arquivo.getSize() > 0) {
			produto.setImagem(arquivo.getOriginalFilename());
		}
		produtoDAO.altera(produto);

		if (arquivo.getSize() > 0) {
			uploadImagem(bindingResult, produto.getId(), produto.getAnunciante().getId(), arquivo);
		}

		ProdutoModel atual = produtoDAO.buscaPorIdClonando(produto.getId());
		historico.alterar(anterior, atual, "Produto");

		sucesso.setMensagem(redirectAttributes,
				mensagensDoSistemaDAO.buscaPorPropriedade("MensagemAlteradoComSucesso").getValor());
		return "redirect:" + ListaDeURLs.LISTA_DE_PRODUTO;
	}

	@Secured({ "ROLE_ADMIN", "ROLE_ANUNCIANTE_GERENCIAL", "ROLE_ANUNCIANTE" })
	@Transactional
	@RequestMapping(value = ListaDeURLs.INSERCAO_DE_IMAGEM, method = RequestMethod.POST)
	public String insereimagens(@Valid ImagemModel imagem, @RequestParam("anuncianteid") Long anuncianteid,
			@RequestParam("produtoid") Long produtoid, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes, HttpServletRequest request, MultipartFile arquivo) {

		if (arquivo.getSize() > 0) {
			uploadImagem(bindingResult, produtoid, anuncianteid, arquivo);
		}

		ProdutoModel produto = new ProdutoModel();
		produto = produtoDAO.buscaPorId(produtoid);

		imagem.setImagem(arquivo.getOriginalFilename());
		imagem.setProduto(produto);
		imagemDAO.insere(imagem);

		sucesso.setMensagem(redirectAttributes,
				mensagensDoSistemaDAO.buscaPorPropriedade("MensagemAdicionadoComSucesso").getValor());
		return "redirect:" + ListaDeURLs.FORMULARIO_INSERCAO_DE_IMAGEM + "/" + produtoid;
	}

	@Secured({ "ROLE_ADMIN", "ROLE_ANUNCIANTE_GERENCIAL", "ROLE_ANUNCIANTE" })
	@RequestMapping(value = ListaDeURLs.EXCLUSAO_DE_PRODUTO, method = RequestMethod.POST)
	public String exclui(@Valid ProdutoModel produto, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {

		ProdutoModel encontrado = produtoDAO.buscaPorIdClonando(produto.getId());
		try {
			produtoDAO.exclui(encontrado);
		} catch (Exception e) {
			erros.setRedirectOrModel(redirectAttributes);
			erros.adiciona(
					"Não foi possível excluir o registro. Verificar se o registro está sendo utilizado em outras partes do sistema.");
			return "redirect:" + ListaDeURLs.LISTA_DE_PRODUTO;
		}
		historico.excluir(encontrado, "Produto");
		sucesso.setMensagem(redirectAttributes,
				mensagensDoSistemaDAO.buscaPorPropriedade("MensagemExcluidoComSucesso").getValor());
		return "redirect:" + ListaDeURLs.LISTA_DE_PRODUTO;
	}

	@Secured({ "ROLE_ADMIN", "ROLE_ANUNCIANTE_GERENCIAL", "ROLE_ANUNCIANTE" })
	@RequestMapping(value = ListaDeURLs.EXCLUSAO_DE_IMAGEM + "/{id}", method = RequestMethod.GET)
	public String excluiimagens(@PathVariable Long id, Model model) {

		ImagemModel encontrado = imagemDAO.buscaPorId(id);

		try {
			imagemDAO.exclui(encontrado);
		} catch (Exception e) {
			erros.adiciona(
					"Não foi possível excluir o registro. Verificar se o registro está sendo utilizado em outras partes do sistema.");
			return "redirect:" + ListaDeURLs.FORMULARIO_INSERCAO_DE_IMAGEM + "/" + encontrado.getProduto().getId();
		}
		historico.excluir(encontrado, "Imagem");
		return "redirect:" + ListaDeURLs.FORMULARIO_INSERCAO_DE_IMAGEM + "/" + encontrado.getProduto().getId();
	}

	private AnuncianteModel buscaAnunciantePeloUsuarioLogado() {
		AnuncianteModel anunciante = new AnuncianteModel();
		UsuarioModel usuario = usuarioAdaptador.obterUsuarioLogado();
		usuario = usuarioDAO.buscaPorId(usuario.getId());
		anunciante = buscaAnunciante(usuario.getAnunciante().getId());
		return anunciante;
	}

	private void uploadImagem(BindingResult result, Long produtoid, Long anuncianteid, MultipartFile arquivo) {
		try {
			String diretorio = propriedades.getValor("arquivo.diretorio.produto.upload") + anuncianteid
					+ propriedades.getValor("arquivo.diretorio.barra") + produtoid;
			System.out.println("antes de gravar o arquivo:" + diretorio);
			fileUploader.grava(arquivo, result, diretorio);
			System.out.println("depois de gravar o arquivo:" + diretorio);

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Erro ao gravar arquivo: " + e.getMessage());
		}

	}

	private ProdutoModel verificaCampos(ProdutoModel produto) {

		if (produto.getEstoque() == null) {
			produto.setEstoque(0);
		}

		Double preco = Double.parseDouble("0");
		if (produto.getPreco() == null) {
			produto.setPreco(preco);
		}

		if (produto.getDesconto() == null) {
			produto.setDesconto(0);
		}

		if (produto.getQtdademin() == null) {
			produto.setQtdademin(0);
			;
		}

		return produto;
	}

	private List<String> validaPreenchimento(ProdutoModel produto, Long subcategoria, Long faixadepreco) {
		List<String> lst = new ArrayList<String>();

		if (produto.getCodigo().equals("")) {
			lst.add("Informe o código");
		}

		if (produto.getTitulo().equals("")) {
			lst.add("Informe o título");
		}

		if (produto.getBrevedescricao().equals("")) {
			lst.add("Informe a breve descrição");
		}
		if (produto.getDescricaocompleta().equals("")) {
			lst.add("Informe a descrição completa");
		}
		if (produto.getTag().equals("")) {
			lst.add("Informe a tag");
		}
		if (produto.getCategoria().getId() == null) {
			lst.add("Informe a categoria");
		}
		if (subcategoria == null) {
			lst.add("Informe a subcategoria");
		}
		if (faixadepreco == null) {
			lst.add("Informe a faixa de preço");
		}
		if (produto.getLinha().getId() == null) {
			lst.add("Informe a linha");
		}

		return lst;
	}

	private AnuncianteModel buscaAnunciante(Long id) {
		return anuncianteDAO.buscaPorId(id);
	}

	private SubCategoriaModel buscaSubCategoria(Long id) {
		return subcategoriaDAO.buscaPorId(id);
	}

	private FaixaDePrecoModel buscaFaixaDePreco(Long id) {
		return faixadeprecoDAO.buscaPorId(id);
	}
	
	private LinhaModel buscaLinha(Long id) {
		return linhaDAO.buscaPorId(id);
	}

}