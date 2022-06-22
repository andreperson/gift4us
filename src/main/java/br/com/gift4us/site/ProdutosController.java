package br.com.gift4us.site;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.gift4us.campanha.CampanhaDAO;
import br.com.gift4us.campanha.CampanhaModel;
import br.com.gift4us.categoria.CategoriaDAO;
import br.com.gift4us.categoria.CategoriaModel;
import br.com.gift4us.linha.LinhaDAO;
import br.com.gift4us.linha.LinhaModel;
import br.com.gift4us.produto.ImagemDAO;
import br.com.gift4us.produto.ImagemModel;
import br.com.gift4us.produto.ProdutoDAO;
import br.com.gift4us.produto.ProdutoModel;
import br.com.gift4us.urls.ListaDeURLs;
import br.com.gift4us.util.Propriedades;

@Controller
public class ProdutosController {

	@Autowired
	private ProdutoDAO produtoDAO;

	@Autowired
	private CategoriaDAO categoriaDAO;

	@Autowired
	private LinhaDAO linhaDAO;
	
	@Autowired
	private ImagemDAO imagemDAO;
	
	@Autowired
	private Propriedades propriedades;
	
	@Autowired
	private CampanhaDAO campanhaDAO;

	@RequestMapping(value = ListaDeURLs.PRODUTOS_LISTACATEG + "/{id}", method = RequestMethod.GET)
	public String listacateg(@PathVariable Long id, HttpServletResponse response, Model model) {
		List<ProdutoModel> lstProds = new ArrayList<ProdutoModel>(); 
		lstProds=buscaProdutoPorCategoria(id);
		String categoria = pegaDescricaoDaCategoria(lstProds);
		model.addAttribute("listaDeProduto", lstProds);
		model.addAttribute("categoria", categoria);
		
		model.addAttribute("listaMenuCategoria", montaMenuCategoria());
		model.addAttribute("listaMenuCampanhaLinha", buscaLinha(buscaCampanhaPorOrdem(2)));
		model.addAttribute("listaMenuCampanhaEspecial", buscaLinha(buscaCampanhaPorOrdem(1)));
		model.addAttribute("urlpadrao", propriedades.getValor("arquivo.diretorio.arquivos"));
		
		return "site/produtos/listaCategoria";
	}
	
	@RequestMapping(value = ListaDeURLs.PRODUTOS_LISTALINHA + "/{id}", method = RequestMethod.GET)
	public String listalinha(@PathVariable Long id, HttpServletResponse response, Model model) {
		List<ProdutoModel> lstProds = new ArrayList<ProdutoModel>(); 
		lstProds=buscaProdutoPorLinha(id);
		String linha = pegaDescricaoDaLinha(lstProds);
		model.addAttribute("listaDeProduto", lstProds);
		model.addAttribute("linha", linha);
		
		model.addAttribute("listaMenuCategoria", montaMenuCategoria());
		model.addAttribute("listaMenuCampanhaLinha", buscaLinha(buscaCampanhaPorOrdem(2)));
		model.addAttribute("listaMenuCampanhaEspecial", buscaLinha(buscaCampanhaPorOrdem(1)));
		
		model.addAttribute("urlpadrao", propriedades.getValor("arquivo.diretorio.arquivos"));
		
		return "site/produtos/listaLinha";
	}
	
	@RequestMapping(value = ListaDeURLs.PRODUTO_DETALHE + "/{id}", method = RequestMethod.GET)
	public String produto(@PathVariable Long id, HttpServletResponse response, Model model) {
		ProdutoModel produto = produtoDAO.buscaPorId(id);
		List<ImagemModel> lstImagem = imagemDAO.buscaPorProduto(produto);
		
		model.addAttribute("lstImagem", lstImagem);
		model.addAttribute("produto", produto);
		model.addAttribute("listaMenuCategoria", montaMenuCategoria());
		model.addAttribute("listaMenuCampanhaLinha", buscaLinha(buscaCampanhaPorOrdem(2)));
		model.addAttribute("listaMenuCampanhaEspecial", buscaLinha(buscaCampanhaPorOrdem(1)));
		model.addAttribute("urlpadrao", propriedades.getValor("arquivo.diretorio.arquivos"));
		
		
		return "site/produtos/produto";
	}
	
	private List<ProdutoModel> buscaProdutoPorCategoria(Long categoria_id) {
		
		CategoriaModel categoria = new CategoriaModel();
		categoria = categoriaDAO.buscaPorId(categoria_id);

		List<ProdutoModel> lstProds = new ArrayList<ProdutoModel>(); 
		lstProds = produtoDAO.buscaPorCategoria(categoria);
		
		return lstProds; 
	}
	
private List<ProdutoModel> buscaProdutoPorLinha(Long linha_id) {
		
		LinhaModel linha = new LinhaModel();
		linha = linhaDAO.buscaPorId(linha_id);

		List<ProdutoModel> lstProds = new ArrayList<ProdutoModel>(); 
		lstProds = produtoDAO.buscaPorLinha(linha);
		
		return lstProds; 
	}
	
	
	private String pegaDescricaoDaCategoria(List<ProdutoModel> lst) {
		String categoria="";
		for (ProdutoModel prod : lst) {
			categoria = prod.getCategoria().getNome();
			break;
		}
		return categoria;
	}
	
	private String pegaDescricaoDaLinha(List<ProdutoModel> lst) {
		String linha="";
		for (ProdutoModel prod : lst) {
			linha = prod.getLinha().getNome();
			break;
		}
		return linha;
	}
	
	private List<CategoriaModel> montaMenuCategoria() {
		List<CategoriaModel> lst = new ArrayList<CategoriaModel>();
		lst = categoriaDAO.listaTudo();

		return lst;
	}
	
	private CampanhaModel buscaCampanhaPorOrdem(Integer ordem) {

		CampanhaModel campanha = new CampanhaModel();
		campanha = campanhaDAO.buscaPorOrdem(ordem);

		return campanha;
	}

	private List<LinhaModel> buscaLinha(CampanhaModel campanha) {

		List<LinhaModel> lstLinha = new ArrayList<LinhaModel>();
		lstLinha = linhaDAO.buscaPorCampanha(campanha);

		return lstLinha;
	}

	private List<ProdutoModel> buscaProduto(LinhaModel linha) {
		List<ProdutoModel> lstProd = new ArrayList<ProdutoModel>();
		lstProd = produtoDAO.buscaPorLinha(linha);
		return lstProd;
	}
	
}
