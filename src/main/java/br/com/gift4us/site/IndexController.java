package br.com.gift4us.site;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.gift4us.produto.ProdutoDAO;
import br.com.gift4us.produto.ProdutoModel;
import br.com.gift4us.urls.ListaDeURLs;
import br.com.gift4us.util.Util;

@Controller
public class IndexController {

	@Autowired
	private ProdutoDAO produtoDAO;
	
//	@Autowired
	//private Propriedades propriedades;
	

	@RequestMapping(value = ListaDeURLs.INDEX, method = RequestMethod.GET)
	public String index(HttpServletResponse response, Model model) {
		model.addAttribute("listaDeProduto", produtoDAO.listaTudo());
		//model.addAttribute("urlimg", propriedades.getValor("arquivo.diretorio.produto.upload"));
		
		return "site/index/index";
	}
	
	
	private List<ProdutoModel> buscaProdutosHome(){
		List<ProdutoModel> lst = new ArrayList<ProdutoModel>();
		
		return lst;
	}
	
	
}
