package br.com.gift4us.login;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import br.com.gift4us.mensagensdosistema.Erros;
import br.com.gift4us.mensagensdosistema.MensagensDoSistemaDAO;
import br.com.gift4us.mensagensdosistema.Sucesso;
import br.com.gift4us.urls.ListaDeURLs;
import br.com.gift4us.usuario.UsuarioAdaptador;
import br.com.gift4us.historicodosistema.GerenciadorDeHistorico;

@Controller
public class LoginController {


	@Autowired
	private Erros erros;

	@Autowired
	private MensagensDoSistemaDAO mensagem;

	@Autowired
	private Sucesso sucesso;

	@Autowired
	private UsuarioAdaptador usuarioAdaptador;


	@RequestMapping(method=RequestMethod.GET, value=ListaDeURLs.LOGIN)
	public String formularioDeLogin(@RequestParam(value="error", required=false) String error, Model model){
		if (usuarioAdaptador.obterUsuarioLogado() != null) {
			return "redirect:" + ListaDeURLs.HOME;
		}

		String ambiente = System.getenv("AMBIENTE");
		model.addAttribute("ambiente", ambiente);
		if (error != null) {
			erros.setRedirectOrModel(model);
			//erros.adiciona(mensagem.buscaPorPropriedade("ErroLoginAutenticacao").getValor());
		}
		return "administracao/login/login";
	}

	@RequestMapping(method=RequestMethod.GET, value=ListaDeURLs.LOGOUT)
	public String logout(HttpSession session, @ModelAttribute(value="sucesso") String sucesso, RedirectAttributes attr){
		session.invalidate();
		if (sucesso != null && !"".equals(sucesso)) {
			this.sucesso.setMensagem(attr, sucesso);
		}
		return "redirect:" + ListaDeURLs.LOGIN;
	}
}