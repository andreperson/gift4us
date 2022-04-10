package br.com.gift4us.site;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
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
import br.com.gift4us.mail.SendGmail;
import br.com.gift4us.mensagensdosistema.Erros;
import br.com.gift4us.mensagensdosistema.MensagensDoSistemaDAO;
import br.com.gift4us.mensagensdosistema.Sucesso;
import br.com.gift4us.orcamento.OrcamentoDAO;
import br.com.gift4us.orcamento.OrcamentoEnum;
import br.com.gift4us.orcamento.OrcamentoModel;
import br.com.gift4us.produto.ProdutoDAO;
import br.com.gift4us.produto.ProdutoModel;
import br.com.gift4us.produto.ProdutoShow;
import br.com.gift4us.urls.ListaDeURLs;
import br.com.gift4us.util.MailConfig;
import br.com.gift4us.util.Propriedades;
import br.com.gift4us.util.Util;

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
		String cabecalho = cabecalhoOrcamento();
		String rodape = rodapeOrcamento();
		String corpo = cabecalho;
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
					corpo= corpo + rodape;
					if (ambiente.equals("producao")) {
						enviaPara = Mail.EnviarEmail(props, emailfrom, emailsenha, corpo, assunto, destinatario, null,
								null);	
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
				corpo += "<tr><td class='text-center'><a href='../../site/produtos/produto/'" + prd.getId() + "'>"
					+ "<img src='https://gift4us.com.br/' alt='' width='40px;' class='img-thumbnail' /></a></td>"
					+ "<td class='text-left'><small>" + prd.getTitulo() + "</small></td>"
					+ "<td class='text-left'>" + precocomdesconto + "</td>"
					+ "<td class='text-left'>" + prd.getQtdademin() + "</td>"
					+ "<td class='text-left'>" + precoxqtde + "</td></tr>";

				ultimoAnunciante = prd.getAnunciante().getId();
				enviou = false;
			}
			if(!enviou) {
				corpo+=rodape;
				if (ambiente.equals("producao")) {
					enviaPara = Mail.EnviarEmail(props, emailfrom, emailsenha, corpo, assunto, destinatario, null,
							null);	
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
	
	private String cabecalhoOrcamento() {
		String cabeca = "<html><body><div id='container' style='padding-top: 180px;'><div class='container'><div class='row'><div id='content' class='col-sm-12'><h1 class='title'>Orçamento</h1><div class='table-responsive'><table class='table table-bordered'><thead><tr><td class='text-center'>Imagem</td><td class='text-left'>Produto</td><td class='text-left'>Preço</td><td class='text-left'>Quantidade</td><td class='text-left'>Total</td></tr></thead><tbody>";
		return cabeca;
	}
	
	
	private String rodapeOrcamento() {
		String rodape = "</tbody></table></div></div></div></div></body></html>";
		return rodape;
	}
	
	
	
}
