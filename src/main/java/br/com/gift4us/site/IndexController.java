package br.com.gift4us.site;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.gift4us.anunciante.AnuncianteDAO;
import br.com.gift4us.anunciante.AnuncianteModel;
import br.com.gift4us.campanha.CampanhaDAO;
import br.com.gift4us.campanha.CampanhaModel;
import br.com.gift4us.cart.Cart;
import br.com.gift4us.categoria.CategoriaDAO;
import br.com.gift4us.configuracoesdosistema.ConfiguracoesDoSistemaDAO;
import br.com.gift4us.linha.LinhaDAO;
import br.com.gift4us.linha.LinhaModel;
import br.com.gift4us.mail.Mail;
import br.com.gift4us.mensagensdosistema.Erros;
import br.com.gift4us.mensagensdosistema.MensagensDoSistemaDAO;
import br.com.gift4us.mensagensdosistema.Sucesso;
import br.com.gift4us.orcamento.OrcamentoDAO;
import br.com.gift4us.orcamento.OrcamentoModel;
import br.com.gift4us.produto.ProdutoDAO;
import br.com.gift4us.produto.ProdutoModel;
import br.com.gift4us.urls.ListaDeURLs;
import br.com.gift4us.util.MailConfig;
import br.com.gift4us.util.OrcamentoEnum;
import br.com.gift4us.util.Propriedades;

@Controller
public class IndexController {

	@Autowired
	private Erros erros;

	@Autowired
	private Sucesso sucesso;

	@Autowired
	private OrcamentoDAO orcamentoDAO;
	
	@Autowired
	private AnuncianteDAO anuncianteDAO;

	@Autowired
	private MensagensDoSistemaDAO mensagensDoSistemaDAO;
	
	@Autowired
	private ConfiguracoesDoSistemaDAO configuracoesDAO;
	
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

		if (campanha != null) {
			if (campanha.getId() != null) {
				lstLinha = buscaLinha(campanha);
				model.addAttribute("Linha1", lstLinha);
				for (LinhaModel linha : lstLinha) {
					lstProd = new ArrayList<ProdutoModel>();
					lstProd = buscaProduto(linha);
					model.addAttribute("Produto".concat(p.toString()), lstProd);
					p += 1;
				}
			}
		}
	}

	private void montaCampanha2(Model model) {

		List<ProdutoModel> lstProd = new ArrayList<ProdutoModel>();
		CampanhaModel campanha = buscaCampanhaPorOrdem(2);
		model.addAttribute("Campanha2", campanha);
		List<LinhaModel> lstLinha = new ArrayList<LinhaModel>();
		if (campanha != null) {
			if (campanha.getId() != null) {
				lstLinha = buscaLinha(campanha);
				LinhaModel linha = escolheLinhaAleatoria(lstLinha);

				model.addAttribute("Linha2", linha);
				lstProd = new ArrayList<ProdutoModel>();
				lstProd = buscaProduto(linha);
				model.addAttribute("ProdutoCampanha2", lstProd);
			}
		}
	}

	private LinhaModel escolheLinhaAleatoria(List<LinhaModel> lstLinha) {

		Integer maximo = lstLinha.size();

		LinhaModel aleatoria = new LinhaModel();
		int segundo = LocalDateTime.now().getSecond();

		System.out.println("escolha aleatoria: " + segundo);

		if (segundo > maximo) {
			segundo = maximo;
		}

		Integer i = 0;
		for (LinhaModel linha : lstLinha) {
			i += 1;
			if (i == segundo) {
				aleatoria = linha;
				break;
			}
			aleatoria = linha;
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

	@RequestMapping(value = ListaDeURLs.CART, method = RequestMethod.GET)
	public String cart(Model model, HttpServletResponse response, HttpServletRequest request) {

		Cookie cookie = getCookie(request, "gift4us-cart");
		List<Cart> lstCart = new ArrayList<Cart>();
		List<ProdutoModel> lstProdutos = new ArrayList<ProdutoModel>();
		

		if (cookie != null) {
			if(cookie.getValue() != ""){
				lstCart = extracaoProdutosDoCookie(cookie.getValue());
				lstProdutos = produtoDAO.listaProdutosDoCarrinho(extracaoProdutosIds(lstCart));
				insereQtdes(lstProdutos, lstCart);
			}

		}
		
		model.addAttribute("lstProdutos", lstProdutos);
		model.addAttribute("urlpadrao", propriedades.getValor("arquivo.diretorio.arquivos"));
		
		return "site/index/cart";
	}
	
	@RequestMapping(value = ListaDeURLs.CHECKOUT, method = RequestMethod.GET)
	public String checkout(@Valid String nome, @Valid String email, @Valid String ddd, @Valid String celular, Model model, HttpServletResponse response, HttpServletRequest request) {

		Cookie cookie = getCookie(request, "gift4us-cart");
		List<Cart> lstCart = new ArrayList<Cart>();
		List<ProdutoModel> lstProdutos = new ArrayList<ProdutoModel>();
		

		if (cookie != null) {
			if(cookie.getValue() != ""){
				lstCart = extracaoProdutosDoCookie(cookie.getValue());
				lstProdutos = produtoDAO.listaProdutosDoCarrinho(extracaoProdutosIds(lstCart));
				insereQtdes(lstProdutos, lstCart);
			}

		}
		
		OrcamentoModel orcamento = new OrcamentoModel();
		model.addAttribute("orcamento", orcamento);
		model.addAttribute("lstProdutos", lstProdutos);
		model.addAttribute("urlpadrao", propriedades.getValor("arquivo.diretorio.arquivos"));
		
		return "site/index/checkout";
	}
	
	@RequestMapping(value = ListaDeURLs.CHECKOUT, method = RequestMethod.POST)
	public String checkout(@Valid OrcamentoModel orcamento, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes, HttpServletResponse response, HttpServletRequest request) {

		Cookie cookie = getCookie(request, "gift4us-cart");
		List<Cart> lstCart = new ArrayList<Cart>();
		List<ProdutoModel> lstProdutos = new ArrayList<ProdutoModel>();

		if (cookie != null) {
			if(cookie.getValue() != ""){
				lstCart = extracaoProdutosDoCookie(cookie.getValue());
				lstProdutos = produtoDAO.listaProdutosDoCarrinho(extracaoProdutosIds(lstCart));
				insereQtdes(lstProdutos, lstCart);
			}
		}
		
		gravaOrcamento(lstProdutos, orcamento);
		
		enviaEmailOrcamento(orcamento, lstCart, lstProdutos);	
		
		enviaEmailOrcamentoCliente(orcamento, lstProdutos);
		
		//limpa o carrinho
		
		model.addAttribute("lstProdutos", lstProdutos);
		model.addAttribute("urlpadrao", propriedades.getValor("arquivo.diretorio.arquivos"));
		
		return "site/index/checkout";
	}
	
	private void gravaOrcamento(List<ProdutoModel> lstProdutos, OrcamentoModel orcamento) {
		OrcamentoModel orcaSalva = new OrcamentoModel();
		
		for (ProdutoModel prd : lstProdutos) {
			orcaSalva = new OrcamentoModel();
			orcaSalva.setAnunciante(prd.getAnunciante());
			orcaSalva.setCelular(orcamento.getCelular());
			orcaSalva.setDataIncl(Calendar.getInstance());
			orcaSalva.setDdd(orcamento.getDdd());
			orcaSalva.setEmail(orcamento.getEmail());
			orcaSalva.setNome(orcamento.getNome());
			orcaSalva.setProduto(prd);
			orcaSalva.setQuantidade(prd.getQtdademin());
			orcaSalva.setStatus(OrcamentoEnum.NOVO.getValor());
			orcamentoDAO.insere(orcaSalva);	
		}
	}
	

	private List<ProdutoModel> insereQtdes(List<ProdutoModel> lstProdutos, List<Cart> lstCart) {

		for (ProdutoModel prd : lstProdutos) {
			for (Cart cart : lstCart) {
				if (prd.getId().equals(cart.getProdutoId())) {
					prd.setQtdademin(cart.getQtde());
				}
			}
		}

		return lstProdutos;
	}

	private List<Long> extracaoProdutosIds(List<Cart> lst) {
		List<Long> lstIds = new ArrayList<Long>();
		Long id;

		for (Cart cart : lst) {
			id = cart.getProdutoId();
			lstIds.add(id);
		}

		return lstIds;
	}

	private List<AnuncianteModel> extracaoAnuncianteIds(List<ProdutoModel> lst) {
		List<AnuncianteModel> lstAnunciante = new ArrayList<AnuncianteModel>();
		Set<AnuncianteModel> set = new HashSet<AnuncianteModel>();
		AnuncianteModel anunciante = new AnuncianteModel();

		for (ProdutoModel prd: lst) {
			anunciante = new AnuncianteModel();
			anunciante = prd.getAnunciante();
			set.add(anunciante);
		}
		
		lstAnunciante.addAll(set);

		return lstAnunciante;
	}
	
	private List<Cart> extracaoProdutosDoCookie(String cookie) {

		String[] arrayCookie = cookie.split("-");
		String[] itemCookie = null;
		Cart cart = new Cart();
		List<Cart> lstCart = new ArrayList<Cart>();

		for (String item : arrayCookie) {
			itemCookie = item.split(":");
			cart = new Cart();
			cart.setProdutoId(Long.parseLong(itemCookie[0]));
			cart.setQtde(Integer.parseInt(itemCookie[1]));
			lstCart.add(cart);
		}

		return lstCart;
	}

	private Cookie getCookie(HttpServletRequest request, String cookieName) {
		Cookie[] cookies = request.getCookies();
		Cookie cookie = null;
		if (cookies != null) {
			for (Cookie cook : cookies) {
				if (cook.getName().equals(cookieName)) {
					cookie = cook;
					break;
				}
			}
		}
		return cookie;
	}

	private Boolean enviaEmailOrcamentoCliente(OrcamentoModel orcamento, List<ProdutoModel> lstProdutos) {
		boolean enviaPara=false;
		
		String ambiente = System.getenv("AMBIENTE");

		String destinatario = orcamento.getEmail();
		String assunto = "Orcamento Solicitado | gift4us";
		
		Propriedades util = new Propriedades();
		MailConfig config = new MailConfig();
		Properties props = new Properties();
		config = config.setConfigeProperties(configuracoesDAO);
		props = util.setProps(config);
		
		String emailfrom = config.getEmailfrom();
		String emailsenha = config.getSenha();
		
		StringBuilder corpo = new StringBuilder();
		
		corpo.append(pegaCabecalho(orcamento).append(pegaInicio().append(pegaCorpo(lstProdutos, true).append(pegaRodape()))));

		if (ambiente.equals("producao")) {
			enviaPara = Mail.EnviarEmail(props, emailfrom, emailsenha, corpo, assunto, destinatario, null,
					null);	
		}
		else {
			System.out.println("email" + corpo);
		}

		return enviaPara;
	}
	
	
	private Boolean enviaEmailOrcamento(OrcamentoModel orcamento, List<Cart> lstCart, List<ProdutoModel> lstProdutos) {
		boolean enviaPara=false;
		
		String ambiente = System.getenv("AMBIENTE");

		String destinatario = "andrep.person@gmail.com";
		String assunto = "Orcamento | gift4us";
		
		Propriedades util = new Propriedades();
		MailConfig config = new MailConfig();
		Properties props = new Properties();
		config = config.setConfigeProperties(configuracoesDAO);
		props = util.setProps(config);
		
		String emailfrom = config.getEmailfrom();
		String emailsenha = config.getSenha();

		
		List<ProdutoModel> lstPrdAnunciante = new ArrayList<ProdutoModel>();
		List<AnuncianteModel> lstAnunciante = new ArrayList<AnuncianteModel>();
		lstAnunciante = extracaoAnuncianteIds(lstProdutos);
		
		StringBuilder corpo = new StringBuilder();
		
		
		for (AnuncianteModel anu : lstAnunciante) {
			lstPrdAnunciante = new ArrayList<ProdutoModel>();
			lstPrdAnunciante = produtoDAO.listaProdutosDoCarrinhoPorAnunciante(extracaoProdutosIds(lstCart), anu);
			
				corpo.append(pegaCabecalho(orcamento).append(pegaInicio().append(pegaCorpo(lstPrdAnunciante, false).append(pegaRodape()))));
				
				if (ambiente.equals("producao")) {
					enviaPara = Mail.EnviarEmail(props, emailfrom, emailsenha, corpo, assunto, destinatario, null,
							null);	
				}
				else {
					System.out.println("email" + corpo);
				}
		}
		return enviaPara;
	}
	
	private StringBuilder pegaCabecalho(OrcamentoModel orcamento) {
		StringBuilder texto = new StringBuilder();
		texto.append("<!DOCTYPE html> <html> <head> <title>Orcamento gift4us</title> <style> html, body { min-height: 100%; } body, div, form, input, p { padding: 0; margin: 0; outline: none; font-family: Roboto, Arial, sans-serif; font-size: 14px; color: #666; line-height: 22px; } h1 { font-weight: 400; } .testbox { display: flex; justify-content: center; align-items: center; height: inherit; padding: 3px; } form { width: 100%; padding: 20px; background: #fff; box-shadow: 0 2px 5px #ccc; } input { width: calc(100% - 10px); padding: 5px; border: 1px solid #ccc; border-radius: 3px; vertical-align: middle; } input:hover, textarea:hover { outline: none; border: 1px solid #095484; } th, td { width: 28%; padding: 15px 0; border-bottom: 1px solid #ccc; text-align: center; vertical-align: unset; line-height: 18px; font-weight: 400; word-break: break-all; } .first-col { width: 16%; text-align: left; } textarea:hover { outline: none; border: 1px solid #1c87c9; } table { width: 100%; } textarea { width: calc(100% - 6px); } .question { padding: 15px 0 5px; color: #095484; } .question-answer label { display: block; padding: 0 20px 10px 0; } .question-answer input { width: auto; } .btn-block { margin-top: 20px; text-align: center; } button { width: 150px; padding: 10px; border: none; -webkit-border-radius: 5px; -moz-border-radius: 5px; border-radius: 5px; background-color: #095484; font-size: 16px; color: #fff; cursor: pointer; } button:hover { background-color: #0666a3; } @media (min-width: 568px) { th, td { word-break: keep-all; } } </style> </head> <body> <div class='testbox'> <form action='/'> <h1>Orçamento gift4us</h1> <p class='question'>");
		texto.append("Solicitação de preços<br><br> ");
		texto.append("Cliente <br>" + orcamento.getNome() + " <br> " + orcamento.getEmail() + "<br>" + orcamento.getDdd() + " " + orcamento.getCelular() + "</p>");
		return texto;
	}


	private StringBuilder pegaInicio() {
		StringBuilder texto = new StringBuilder();
		texto.append("<br><br>");
		texto.append("<table class='table align-items-center table-flush'> <thead class='thead-light'> <tr style='background:#999; color:#fff'> <th scope='col'>Produto</th> <th scope='col'>Preço</th> <th scope='col'>Quantidade</th> <th scope='col'>Total</th> <th scope='col'></th> </tr> </thead>");
		return texto;
	}
	
	
	private StringBuilder pegaCorpo(List<ProdutoModel> lstProdutos, Boolean cliente) {
		Double precocomdesconto = null;
		Double precoxqtde= (double) 0;
		Double totalgeral= (double) 0;
		StringBuilder texto = new StringBuilder();
		texto.append("<tbody>");
		
		for (ProdutoModel prd : lstProdutos) {
			
			if(prd.getDesconto() != null) {
				precocomdesconto = prd.getPreco() - (prd.getPreco() * prd.getDesconto());
			}
			else {
				precocomdesconto = prd.getPreco();
			}
			
			precoxqtde = prd.getQtdademin() * precocomdesconto;
			totalgeral += precoxqtde; 

			texto.append("<tr style='background:#f7f7f7'><td class='first-col'><a href='../../site/produtos/produto/'" + prd.getId() + "'>");
			texto.append("<img src='https://gift4us.com.br/' alt='' width='40px;' class='img-thumbnail' /></a> &nbsp;&nbsp;&nbsp;");
			if(cliente) {
				texto.append("<small>" + prd.getTitulo() + " - de: " + prd.getAnunciante().getFantasia() + "</small></td>");
			}
			else {
				texto.append("<small>" + prd.getTitulo() + "</small></td>");	
			}
			texto.append("<td>" + precocomdesconto + "</td>");
			texto.append("<td>" + prd.getQtdademin() + "</td>");
			texto.append("<td>" + precoxqtde + "</td></tr>");
		}
		
	texto.append("<tr style='background:#f7f7f7; color:blue'><td>&nbsp;</td>");
		texto.append("<td></td>");
		texto.append("<td>Total: </td>");
		texto.append("<td>" + totalgeral + "</td></tr>");
		texto.append("</tbody></table>");
		
		return texto;
		
	}
	
	private StringBuilder pegaRodape() {
		
		StringBuilder texto = new StringBuilder();
		texto.append(" <br><br><div class='card-footer py-4'> <nav aria-label='...'> </nav> </div> ");
		texto.append("<footer class='footer'> <div class='row align-items-center justify-content-xl-between'> <div class='col-xl-6'> <hr>" + pegaDataAtual() + "</div>  </div> </footer>  </body>  </html>");
		return texto;
	}
	
	
	private String pegaDataAtual() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date).toString();
		
	}
	

}
