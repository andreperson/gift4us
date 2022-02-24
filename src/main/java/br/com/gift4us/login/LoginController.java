package br.com.gift4us.login;

import java.util.Properties;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
import br.com.gift4us.usuario.UsuarioModel;
import br.com.gift4us.anunciante.AnuncianteModel;
import br.com.gift4us.historicodosistema.GerenciadorDeHistorico;
import br.com.gift4us.mail.Mail;

@Controller
public class LoginController {

	@Autowired
    private CustomUserService userService;
	
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
	
	@RequestMapping(method=RequestMethod.GET, value=ListaDeURLs.ESQUECI_A_SENHA)
	public String formularioDeEsquecimentodeSenha(@RequestParam(value="error", required=false) String error, Model model){

		String ambiente = System.getenv("AMBIENTE");
		model.addAttribute("ambiente", ambiente);
		if (error != null) {
			erros.setRedirectOrModel(model);
			//erros.adiciona(mensagem.buscaPorPropriedade("ErroLoginAutenticacao").getValor());
		}
		return "administracao/login/esqueciasenha";
	}
	
	
	@RequestMapping(method=RequestMethod.POST, value=ListaDeURLs.RECUPERAR_A_SENHA)
	public String formulariorRecuperarSenha(@Valid String loginouemail, Model model){
		UsuarioModel usuarioDoBanco = null;
		String msg = "";
		String ambiente = System.getenv("AMBIENTE");
		
		//verifica se é um email
		if (loginouemail.contains("@")) {
			usuarioDoBanco = userService.buscaPorEmail(loginouemail);
		}
		else {
			usuarioDoBanco = userService.buscaPorLogin(loginouemail);			
		}
		
		
		if(usuarioDoBanco != null) {
			if(usuarioDoBanco.getEmail() != null) {
				enviaEmail(usuarioDoBanco);
			}
		}
		
		model.addAttribute("ambiente", ambiente);
		model.addAttribute("msg", msg);
		
		return "administracao/login/esqueciasenha";
	}
	
	private void enviaEmail(UsuarioModel usuario) {

		String destinatario = usuario.getEmail();
		String assunto = "Esqueci a Senha";
		
		String nome = usuario.getApelido();
		
		if (usuario.getApelido().isEmpty()) {
			nome = usuario.getNome();
		}

		String requerente = usuario.getNome();
		
		//String corpoEmail = configuracoesDao.buscarPeloNomeDaPropriedade("corpo.email.confirmacao").getValor().replace("{{nome_interessado}}", requerente).replace("{{numero_termo}}", termo.getNumero());

		String corpoEmail = "Prezado(a) " + nome + ",<br> sua senha é: " + usuario.getSenha();
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "false");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.MailTransport.protocol", "smtp");
		props.put("mail.smtp.port", "587");
		
		String emailFrom = "andrep.person@gmail.com";
		String emailSenha = "Cach0rr016*";
		destinatario = "andrep.person@gmail.com";
		
		Mail mail = new Mail();
		
		boolean enviaPara = mail.EnviarEmail();

		if (!enviaPara) {
			//model.addAttribute("erro", "Ocorreu um erro ao enviar o e-mail de confirmação para o destinatário!");
		}
	}
	
	
}