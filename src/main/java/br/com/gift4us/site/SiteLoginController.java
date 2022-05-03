package br.com.gift4us.site;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import br.com.gift4us.mensagensdosistema.Erros;
import br.com.gift4us.mensagensdosistema.MensagensDoSistemaDAO;
import br.com.gift4us.mensagensdosistema.Sucesso;
import br.com.gift4us.urls.ListaDeURLs;

@Controller
public class SiteLoginController {

	@Autowired
	private Erros erros;

	@Autowired
	private MensagensDoSistemaDAO mensagem;

	@Autowired
	private Sucesso sucesso;

	@RequestMapping(method = RequestMethod.GET, value = ListaDeURLs.SITE_LOGIN)
	public String formularioDeLoginNoSite(@RequestParam(value = "error", required = false) String error, Model model) {
		
		return "site/login/login";
	}


}