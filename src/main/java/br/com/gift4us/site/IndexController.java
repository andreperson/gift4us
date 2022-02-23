package br.com.gift4us.site;

import java.time.LocalDateTime;
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

		montaCampanha1(model);
		montaCampanha2(model);
		

		return "site/index/index";
	}
	
	
	private void montaCampanha1(Model model) {
		
		List<ProdutoModel> lstProd = new ArrayList<ProdutoModel>();
		CampanhaModel campanha = buscaCampanhaPorOrdem(1);
		Integer p = 0;
		model.addAttribute("Campanha1", campanha);
		List<LinhaModel> lstLinha = new ArrayList<LinhaModel>();

		lstLinha = buscaLinha(campanha);
		model.addAttribute("Linha1", lstLinha);
		for (LinhaModel linha : lstLinha) {
			lstProd = new ArrayList<ProdutoModel>();
			lstProd = buscaProduto(linha);
			model.addAttribute("Produto".concat(p.toString()), lstProd);
			p += 1;
		}

	}
	
	
	private void montaCampanha2(Model model) {
		
		List<ProdutoModel> lstProd = new ArrayList<ProdutoModel>();
		CampanhaModel campanha = buscaCampanhaPorOrdem(2);
		Integer p = 0;
		model.addAttribute("Campanha2", campanha);
		List<LinhaModel> lstLinha = new ArrayList<LinhaModel>();
		lstLinha = buscaLinha(campanha);
		LinhaModel linha  = escolheLinhaAleatoria(lstLinha);
		
		model.addAttribute("Linha2", linha);
			lstProd = new ArrayList<ProdutoModel>();
			lstProd = buscaProduto(linha);
			model.addAttribute("ProdutoCampanha2", lstProd);
	}
	
	
	private LinhaModel escolheLinhaAleatoria(List<LinhaModel> lstLinha) {
		
		LinhaModel aleatoria = new LinhaModel();
		int diadomes = LocalDateTime.now().getDayOfMonth();
		
		for (LinhaModel linha : lstLinha) {
			if (linha.getId() == diadomes) {
				aleatoria = linha;
				break;
			}
			aleatoria=linha;
		}
		
		return aleatoria;
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
