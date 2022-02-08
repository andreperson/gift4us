package br.com.gift4us.site;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.gift4us.campanha.CampanhaDAO;
import br.com.gift4us.campanha.CampanhaModel;
import br.com.gift4us.categoria.CategoriaDAO;
import br.com.gift4us.categoria.CategoriaModel;
import br.com.gift4us.linha.LinhaDAO;
import br.com.gift4us.linha.LinhaModel;
import br.com.gift4us.produto.ProdutoDAO;
import br.com.gift4us.produto.ProdutoModel;
import br.com.gift4us.urls.ListaDeURLs;
import br.com.gift4us.util.Propriedades;
import br.com.gift4us.util.Util;

@Controller
public class IndexController {

	@Autowired
	private ProdutoDAO produtoDAO;

	@Autowired
	private CampanhaDAO campanhaDAO;

	@Autowired
	private LinhaDAO linhaDAO;
	
	@Autowired
	private CategoriaDAO categoriaDAO;

	
	@Autowired
	private Propriedades propriedades;
	

	@RequestMapping(value = ListaDeURLs.INDEX, method = RequestMethod.GET)
	public String index(Model model, HttpServletResponse response) {
		model.addAttribute("listaDeProduto", produtoDAO.listaNovidades(10));
		model.addAttribute("listaDeCategoria", categoriaDAO.listaMaisVendidos());
		model.addAttribute("urlpadrao", propriedades.getValor("arquivo.diretorio.arquivos"));
		
		List<ProdutoModel> lstProd = new ArrayList<ProdutoModel>();
		List<CampanhaModel> lstCamp = new ArrayList<CampanhaModel>();
		
		lstCamp = campanhaDAO.listaTudo();
		Integer l =0;
		Integer p =0;
		for (CampanhaModel campanha : lstCamp) {
			model.addAttribute("Campanha".concat(l.toString()), buscaCampanha(campanha.getId()));
			List<LinhaModel> lstLinha = new ArrayList<LinhaModel>();
			lstLinha = buscaLinha(campanha);
			model.addAttribute("Linha".concat(l.toString()),lstLinha);
			
			for (LinhaModel linha : lstLinha) {
				
				lstProd = new ArrayList<ProdutoModel>();
				lstProd=buscaProduto(linha);
				model.addAttribute("Produto".concat(p.toString()),lstProd);
				p+=1;
			}
			l+=1;
		}

		model.addAttribute("qtde", p);
		
		return "site/index/index";
	}
	
	
	private CampanhaModel buscaCampanha(Long campanhaid) {
		
		CampanhaModel campanha = new CampanhaModel();
		campanha = campanhaDAO.buscaPorId(campanhaid);
		
		return campanha;
	}
	
	private List<LinhaModel> buscaLinha(CampanhaModel campanha) {
		
		List<LinhaModel> lstLinha = new ArrayList<LinhaModel>();
		lstLinha = linhaDAO.buscaPorCampanha(campanha);
		
		return lstLinha;
	}
	
	private List<ProdutoModel> buscaProduto(LinhaModel linha){
		List<ProdutoModel> lstProd = new ArrayList<ProdutoModel>();
		lstProd = produtoDAO.buscaPorLinha(linha);
		return lstProd;
	}
	
}
