package br.com.gift4us.site;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.gift4us.anunciante.AnuncianteDAO;
import br.com.gift4us.anunciante.AnuncianteModel;
import br.com.gift4us.campanha.CampanhaDAO;
import br.com.gift4us.campanha.CampanhaModel;
import br.com.gift4us.cart.Cart;
import br.com.gift4us.categoria.CategoriaDAO;
import br.com.gift4us.categoria.CategoriaModel;
import br.com.gift4us.configuracoesdosistema.ConfiguracoesDoSistemaDAO;
import br.com.gift4us.enuns.TipoDeEmail;
import br.com.gift4us.linha.LinhaDAO;
import br.com.gift4us.linha.LinhaModel;
import br.com.gift4us.mail.Mail;
import br.com.gift4us.mensagensdosistema.Erros;
import br.com.gift4us.mensagensdosistema.MensagensDoSistemaDAO;
import br.com.gift4us.mensagensdosistema.Sucesso;
import br.com.gift4us.orcamento.OrcamentoDAO;
import br.com.gift4us.orcamento.OrcamentoEnum;
import br.com.gift4us.orcamento.OrcamentoModel;
import br.com.gift4us.produto.ProdutoDAO;
import br.com.gift4us.produto.ProdutoModel;
import br.com.gift4us.urls.ListaDeURLs;
import br.com.gift4us.util.MailConfig;
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
				lstProdutos = produtoDAO.listaProdutosDoCarrinho(extracaoIds(lstCart));
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
				lstProdutos = produtoDAO.listaProdutosDoCarrinho(extracaoIds(lstCart));
				insereQtdes(lstProdutos, lstCart);
			}

		}
		
		//enviaEmailOrcamento();
		
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
				lstProdutos = produtoDAO.listaProdutosDoCarrinho(extracaoIds(lstCart));
				insereQtdes(lstProdutos, lstCart);
			}
		}
		
		gravaOrcamento(lstProdutos, orcamento);
		
		enviaEmailOrcamento(lstProdutos);	
		
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

	private List<Long> extracaoIds(List<Cart> lst) {
		List<Long> lstIds = new ArrayList<Long>();
		Long id;

		for (Cart cart : lst) {
			id = cart.getProdutoId();
			lstIds.add(id);
		}

		return lstIds;
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

	private Boolean enviaEmailOrcamento(List<ProdutoModel> lstProdutos) {
		boolean enviaPara=false;
		
		
		String cabecalho = pegaCabecalho();
		String rodape = "</table><p>&nbsp;</p><hr>";
		String corpo = "";
		String ambiente = System.getenv("AMBIENTE");

		String destinatario = "andrep.person@gmail.com";
		String assunto = "Orçamento | gift4Us";
		
		MailConfig config = new MailConfig();
		Properties props = new Properties();
		config = config.setConfigeProperties(configuracoesDAO);
		props = setProps(config);
		
		String emailfrom = config.getEmailfrom();
		String emailsenha = config.getSenha();
		
			System.out.println("vai enviar email");
			
			Long ultimoAnunciante =0l;
			Boolean enviou = false;
			Double precocomdesconto = null;
			Double precoxqtde= null;
			for (ProdutoModel prd : lstProdutos) {
				if (prd.getAnunciante().getId() != ultimoAnunciante && (ultimoAnunciante != 0)) {
					//manda email
					corpo = cabecalho + corpo + rodape;
					if (ambiente.equals("producao")) {
						enviaPara = Mail.EnviarEmail(props, emailfrom, emailsenha, corpo, assunto, destinatario, null,
								null);	
					}
					else {
						System.out.println("email" + corpo);
					}
					corpo=cabecalho;
					enviou = true;
				}
				
				if(prd.getDesconto() != null) {
					precocomdesconto = prd.getPreco() - (prd.getPreco() * prd.getDesconto());
				}
				else {
					precocomdesconto = prd.getPreco();
				}
				
				precoxqtde = prd.getQtdademin() * precocomdesconto;
				corpo += "<tr><td class='first-col'><a href='../../site/produtos/produto/'" + prd.getId() + "'>"
						+ "<img src='https://gift4us.com.br/' alt='' width='40px;' class='img-thumbnail' /></a> &nbsp;&nbsp;&nbsp;"
						+ "<small>" + prd.getTitulo() + "</small></td>"
						+ "<td>" + precocomdesconto + "</td>"
						+ "<td>" + prd.getQtdademin() + "</td>"
						+ "<td>" + precoxqtde + "</td></tr>";

				ultimoAnunciante = prd.getAnunciante().getId();
				enviou = false;
			}
			if(!enviou) {
				corpo = cabecalho + corpo + rodape;
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
	
	private Properties setProps(MailConfig config ) {
		Properties props = new Properties();
		props.put("mail.smtp.auth", config.getAuth());
		props.put("mail.smtp.ssl.trust", config.getTrust());
		props.put("mail.smtp.host", config.getSmtp());
		props.put("mail.MailTransport.protocol", config.getProtocol());
		props.put("mail.smtp.port", config.getPort());
		
		return props;
	}
	
	
	private String pegaCabecalho() {
	
		return "<!DOCTYPE html> <html> <head> <title>Orcamento gift4us</title> <style> html, body { min-height: 100%; } body, div, form, input, p { padding: 0; margin: 0; outline: none; font-family: Roboto, Arial, sans-serif; font-size: 14px; color: #666; line-height: 22px; } h1 { font-weight: 400; } .testbox { display: flex; justify-content: center; align-items: center; height: inherit; padding: 3px; } form { width: 100%; padding: 20px; background: #fff; box-shadow: 0 2px 5px #ccc; } input { width: calc(100% - 10px); padding: 5px; border: 1px solid #ccc; border-radius: 3px; vertical-align: middle; } input:hover, textarea:hover { outline: none; border: 1px solid #095484; } th, td { width: 28%; padding: 15px 0; border-bottom: 1px solid #ccc; text-align: center; vertical-align: unset; line-height: 18px; font-weight: 400; word-break: break-all; } .first-col { width: 16%; text-align: left; } textarea:hover { outline: none; border: 1px solid #1c87c9; } table { width: 100%; } textarea { width: calc(100% - 6px); } .question { padding: 15px 0 5px; color: #095484; } .question-answer label { display: block; padding: 0 20px 10px 0; } .question-answer input { width: auto; } .btn-block { margin-top: 20px; text-align: center; } button { width: 150px; padding: 10px; border: none; -webkit-border-radius: 5px; -moz-border-radius: 5px; border-radius: 5px; background-color: #095484; font-size: 16px; color: #fff; cursor: pointer; } button:hover { background-color: #0666a3; } @media (min-width: 568px) { th, td { word-break: keep-all; } } </style> </head> <body> <div class='testbox'> <form action='/'> <h1>Orçamento gift4us</h1> <p class='question'>Solicitação de preços do Cliente </p>";
		
	}


	private StringBuilder pegaInicio() {
		StringBuilder texto = new StringBuilder();
		texto.append("<table><tr><th class='first-col'></th><th>Produto</th><th>Preço</th><th>Quantidade</th><th>Total</th></tr>");
		return texto;
	}

	private StringBuilder pegaCorpo() {
		StringBuilder texto = new StringBuilder();
		texto.append("<table class='table align-items-center table-flush'> <thead class='thead-light'> <tr> <th scope='col'>Produto</th> <th scope='col'>Preço</th> <th scope='col'>Quantidade</th> <th scope='col'>Total</th> <th scope='col'></th> </tr> </thead> <tbody> <tr> <th scope='row'> <div class='media align-items-center'> <a href='#' class='avatar rounded-circle mr-3'> <img alt='Image placeholder' src='../assets/img/theme/bootstrap.jpg'> </a> <div class='media-body'> <span class='mb-0 text-sm'>Argon Design System</span> </div> </div> </th> <td> $2,500 USD </td> <td> <span class='badge badge-dot mr-4'> <i class='bg-warning'></i> pending </span> </td> <td> <div class='avatar-group'> ddd </div> </td> <td class='text-right'> <div class='dropdown'> <a class='btn btn-sm btn-icon-only text-light' href='#' role='button' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'> <i class='fas fa-ellipsis-v'></i> </a> <div class='dropdown-menu dropdown-menu-right dropdown-menu-arrow'> <a class='dropdown-item' href='#'>Action</a> <a class='dropdown-item' href='#'>Another action</a> <a class='dropdown-item' href='#'>Something else here</a> </div> </div> </td> </tr> <tr> <th scope='row'> <div class='media align-items-center'> <a href='#' class='avatar rounded-circle mr-3'> <img alt='Image placeholder' src='../assets/img/theme/angular.jpg'> </a> <div class='media-body'> <span class='mb-0 text-sm'>Angular Now UI Kit PRO</span> </div> </div> </th> <td> $1,800 USD </td> <td> <span class='badge badge-dot'> <i class='bg-success'></i> completed </span> </td> <td> <div class='avatar-group'> mmm </div> </td> <td class='text-right'> <div class='dropdown'> <a class='btn btn-sm btn-icon-only text-light' href='#' role='button' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'> <i class='fas fa-ellipsis-v'></i> </a> <div class='dropdown-menu dropdown-menu-right dropdown-menu-arrow'> <a class='dropdown-item' href='#'>Action</a> <a class='dropdown-item' href='#'>Another action</a> <a class='dropdown-item' href='#'>Something else here</a> </div> </div> </td> </tr> <tr> <th scope='row'> <div class='media align-items-center'> <a href='#' class='avatar rounded-circle mr-3'> <img alt='Image placeholder' src='../assets/img/theme/sketch.jpg'> </a> <div class='media-body'> <span class='mb-0 text-sm'>Black Dashboard</span> </div> </div> </th> <td> $3,150 USD </td> <td> <span class='badge badge-dot mr-4'> <i class='bg-danger'></i> delayed </span> </td> <td> <div class='avatar-group'> xxx </div> </td> <td class='text-right'> <div class='dropdown'> <a class='btn btn-sm btn-icon-only text-light' href='#' role='button' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'> <i class='fas fa-ellipsis-v'></i> </a> <div class='dropdown-menu dropdown-menu-right dropdown-menu-arrow'> <a class='dropdown-item' href='#'>Action</a> <a class='dropdown-item' href='#'>Another action</a> <a class='dropdown-item' href='#'>Something else here</a> </div> </div> </td> </tr> </tbody> </table>");
		return texto;
		
	}

	private StringBuilder pegaRodape() {
		
		StringBuilder texto = new StringBuilder();
		texto.append("</div> <div class='card-footer py-4'> <nav aria-label='...'> </nav> </div> </div> </div> </div> <footer class='footer'> <div class='row align-items-center justify-content-xl-between'> <div class='col-xl-6'> <div class='copyright text-center text-xl-left text-muted'> &copy; 2018 <a href='https://www.creative-tim.com' class='font-weight-bold ml-1' target='_blank'>Creative Tim</a> </div> </div> <div class='col-xl-6'> <ul class='nav nav-footer justify-content-center justify-content-xl-end'> <li class='nav-item'> <a href='https://www.creative-tim.com' class='nav-link' target='_blank'>Creative Tim</a> </li> <li class='nav-item'> <a href='https://www.creative-tim.com/presentation' class='nav-link' target='_blank'>About Us</a> </li> <li class='nav-item'> <a href='http://blog.creative-tim.com' class='nav-link' target='_blank'>Blog</a> </li> <li class='nav-item'> <a href='https://github.com/creativetimofficial/argon-dashboard/blob/master/LICENSE.md' class='nav-link' target='_blank'>MIT License</a> </li> </ul> </div> </div> </footer> </div> </div>  </body>  </html>");
		return texto;
	}

}
