package br.com.gift4us.login;

import java.util.Properties;
import java.util.Random;

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
import br.com.gift4us.usuario.UsuarioDAO;
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

	@Autowired
	private UsuarioDAO usuarioDAO;

	@Autowired
	private GerenciadorDeHistorico historico;

	@RequestMapping(method = RequestMethod.GET, value = ListaDeURLs.LOGIN)
	public String formularioDeLogin(@RequestParam(value = "error", required = false) String error, Model model) {
		if (usuarioAdaptador.obterUsuarioLogado() != null) {
			return "redirect:" + ListaDeURLs.HOME;
		}

		String ambiente = System.getenv("AMBIENTE");
		model.addAttribute("ambiente", ambiente);
		if (error != null) {
			erros.setRedirectOrModel(model);
			// erros.adiciona(mensagem.buscaPorPropriedade("ErroLoginAutenticacao").getValor());
		}
		return "administracao/login/login";
	}

	@RequestMapping(method = RequestMethod.GET, value = ListaDeURLs.LOGOUT)
	public String logout(HttpSession session, @ModelAttribute(value = "sucesso") String sucesso,
			RedirectAttributes attr) {
		session.invalidate();
		if (sucesso != null && !"".equals(sucesso)) {
			this.sucesso.setMensagem(attr, sucesso);
		}
		return "redirect:" + ListaDeURLs.LOGIN;
	}

	@RequestMapping(method = RequestMethod.GET, value = ListaDeURLs.ESQUECI_A_SENHA)
	public String formularioDeEsquecimentodeSenha(@RequestParam(value = "error", required = false) String error,
			Model model) {

		String ambiente = System.getenv("AMBIENTE");
		model.addAttribute("ambiente", ambiente);
		if (error != null) {
			erros.setRedirectOrModel(model);
			// erros.adiciona(mensagem.buscaPorPropriedade("ErroLoginAutenticacao").getValor());
		}
		return "administracao/login/esqueciasenha";
	}

	@RequestMapping(method = RequestMethod.POST, value = ListaDeURLs.RECUPERAR_A_SENHA)
	public String formularioRecuperarSenha(@Valid String loginouemail, Model model) {
		UsuarioModel usuarioDoBanco = null;
		String msg = "";
		String ambiente = System.getenv("AMBIENTE");

		// verifica se é um email
		if (loginouemail.contains("@")) {
			usuarioDoBanco = userService.buscaPorEmail(loginouemail);
		} else {
			usuarioDoBanco = userService.buscaPorLogin(loginouemail);
		}

		String senha = novaSenha(usuarioDoBanco);

		if (usuarioDoBanco != null) {
			if (usuarioDoBanco.getEmail() != null) {
				if (enviaEmailSenha(usuarioDoBanco, senha)) {
					msg = "Senha gerada com sucesso! Verifique o e-mail cadastrado!";
				}
				else {
					msg = "Erro ao gerar senha!";
				}
				
			}
		}

		model.addAttribute("ambiente", ambiente);
		model.addAttribute("msg", msg);

		return "administracao/login/esqueciasenha";
	}

	private Boolean enviaEmailSenha(UsuarioModel usuario, String senha) {

		String destinatario = usuario.getEmail();
		String assunto = "Esqueci a Senha | gift4Us";

		String nome = usuario.getApelido();

		if (usuario.getApelido().isEmpty()) {
			nome = usuario.getNome();
		}
		
		String corpoEmail = "Prezado(a) " + nome + ", <br><br> sua nova senha é " + senha
				+ " <br><br> Equipe gift4Us";

		String mail_emailfrom = "nao-responda@gift4us.com.br";
		String mail_senha = "NaoResp123*";
		String mail_porta = "465";
		String mail_smtp = "smtp.hostinger.com";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "false");
		props.put("mail.smtp.host", mail_smtp);
		props.put("mail.MailTransport.protocol", "smtp");
		props.put("mail.smtp.port", mail_porta);

		boolean enviaPara = Mail.EnviarEmail(props, mail_emailfrom, mail_senha, corpoEmail, assunto, destinatario, null,
				null);

		return enviaPara;
	}

	private String novaSenha(UsuarioModel usuarioanterior) {

		UsuarioModel novousuario = new UsuarioModel();
		novousuario = usuarioanterior;

		String senha = gerarSenha(8);
		novousuario.setSenha(senha);
		usuarioDAO.altera(novousuario);

		//historico.alterar(usuarioanterior, novousuario, "Usuário");

		return senha;

	}

	private String gerarSenha(int i) {
		String theAlphaNumericS;
		StringBuilder builder;

		theAlphaNumericS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789";

		// create the StringBuffer
		builder = new StringBuilder(i);

		for (int m = 0; m < i; m++) {

			// generate numeric
			int myindex = (int) (theAlphaNumericS.length() * Math.random());

			// add the characters
			builder.append(theAlphaNumericS.charAt(myindex));
		}

		return builder.toString();
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value = ListaDeURLs.FALECONOSCO)
	public String formularioDeFaleConosco(@RequestParam(value = "error", required = false) String error,
			Model model) {

		String ambiente = System.getenv("AMBIENTE");
		model.addAttribute("ambiente", ambiente);
		if (error != null) {
			erros.setRedirectOrModel(model);
			// erros.adiciona(mensagem.buscaPorPropriedade("ErroLoginAutenticacao").getValor());
		}
		return "administracao/login/faleconosco";
	}

	@RequestMapping(method = RequestMethod.POST, value = ListaDeURLs.FALOUCONOSCO)
	public String formularioFalouConosco(@Valid String nome, @Valid String email, @Valid String celular, @Valid String mensagem, @Valid String contato, @Valid String periodo, Model model) {
		String msg = "";
		String ambiente = System.getenv("AMBIENTE");

		if (enviaEmailFaleConosco(nome, email, celular, mensagem, contato, periodo)) {
			msg = "Seu email foi enviado com sucesso! <br> Respondemos rapidinho, obrigado pelo contato!";
		}
		else {
			msg = "Erro ao enviar e-mail! Se for possível, por gentileza enviar um email para atendimento@gift4us.com.br";
		}
		
		model.addAttribute("msg", msg);

		return "administracao/login/faleconosco";
	}

	private Boolean enviaEmailFaleConosco(String nome, String email, String celular, String mensagem, String contato, String periodo) {

		String destinatario = "andrep.person@gmail.com";
		String assunto = "Fale Conosco | gift4Us";
		
		String corpoEmail = "Fale Conosco gift4Us" + 
				"<br> nome:" + nome + "<br> email: " + email +
				"<br> celular: " + celular + "<br> mensagem: " + mensagem + 
				"<br> melhor pedíodo: " + periodo + "<br> entrar em contato por " + contato;

		String mail_emailfrom = "nao-responda@saobernardo.sp.gov.br";
		String mail_senha = "dti*2012";
		String mail_porta = "25";
		String mail_smtp = "california.saobernardo.sp.gov.br";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "false");
		props.put("mail.smtp.host", mail_smtp);
		props.put("mail.MailTransport.protocol", "smtp");
		props.put("mail.smtp.port", mail_porta);

		boolean enviaPara = Mail.EnviarEmail(props, mail_emailfrom, mail_senha, corpoEmail, assunto, destinatario, null,
				null);

		return enviaPara;
	}
}