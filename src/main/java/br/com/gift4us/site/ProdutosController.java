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

import br.com.gift4us.categoria.CategoriaDAO;
import br.com.gift4us.categoria.CategoriaModel;
import br.com.gift4us.produto.ProdutoDAO;
import br.com.gift4us.produto.ProdutoModel;
import br.com.gift4us.urls.ListaDeURLs;
import br.com.gift4us.util.Propriedades;
import br.com.gift4us.util.Util;

@Controller
public class ProdutosController {

	@Autowired
	private ProdutoDAO produtoDAO;

	@Autowired
	private CategoriaDAO categoriaDAO;

	
	@Autowired
	private Propriedades propriedades;
	

	@RequestMapping(value = ListaDeURLs.PRODUTOS_LISTA + "/{id}", method = RequestMethod.GET)
	public String lista(@PathVariable Long id, HttpServletResponse response, Model model) {
		List<ProdutoModel> lstProds = new ArrayList<ProdutoModel>(); 
		lstProds=buscaProdutoPorCategoria(id);
		String categoria = pegaDescricaoDaCategoria(lstProds);
		model.addAttribute("listaDeProduto", lstProds);
		model.addAttribute("categoria", categoria);
		model.addAttribute("urlpadrao", propriedades.getValor("arquivo.diretorio.arquivos"));
		
		return "site/produtos/lista";
	}
	
	@RequestMapping(value = ListaDeURLs.PRODUTO_DETALHE + "/{id}", method = RequestMethod.GET)
	public String produto(@PathVariable Long id, HttpServletResponse response, Model model) {
		List<ProdutoModel> lstProds = new ArrayList<ProdutoModel>(); 
		lstProds=buscaProdutoPorCategoria(id);
		String categoria = pegaDescricaoDaCategoria(lstProds);
		model.addAttribute("listaDeProduto", lstProds);
		model.addAttribute("categoria", categoria);
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
	
	
	private String pegaDescricaoDaCategoria(List<ProdutoModel> lst) {
		String categoria="";
		for (ProdutoModel prod : lst) {
			categoria = prod.getCategoria().getNome();
			break;
		}
		
		return categoria;
		
	}
	
	
}
