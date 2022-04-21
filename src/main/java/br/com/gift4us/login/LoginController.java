package br.com.gift4us.login;

import java.util.Properties;
import java.util.Random;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import br.com.gift4us.util.MailConfig;
import br.com.gift4us.util.Propriedades;
import br.com.gift4us.anunciante.AnuncianteModel;
import br.com.gift4us.configuracoesdosistema.ConfiguracoesDoSistemaDAO;
import br.com.gift4us.enuns.TipoDeEmail;
import br.com.gift4us.historicodosistema.GerenciadorDeHistorico;
import br.com.gift4us.mail.Mail;

@Controller
public class LoginController {

	@Autowired
	private ConfiguracoesDoSistemaDAO configuracoesDAO;
	
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

		String codigo = criaCodigo(usuarioDoBanco);

		if (usuarioDoBanco != null) {
			if (usuarioDoBanco.getEmail() != null) {

				String destinatario = usuarioDoBanco.getEmail();
				String nome = usuarioDoBanco.getApelido();

				if (usuarioDoBanco.getApelido().isEmpty()) {
					nome = usuarioDoBanco.getNome();
				}
				
				StringBuilder corpo = new StringBuilder();
				String assunto = "Esqueci a senha | gift4Us";
				String urlalterasenha ="https://gift4us.com.br/admin/alterarasenha";
				corpo.append("Prezado(a) " + nome + ", <br><br> para definir uma nova senha, utilize o código " + codigo + " <br><br> através da url abaixo: <br><br>" + urlalterasenha + "<br><br>Equipe gift4Us");
				
				if (enviaEmail(ambiente, TipoDeEmail.SENHA, assunto, corpo, destinatario)) {
					msg = "Email enviado com sucesso para " + usuarioDoBanco.getEmail();
				}
				else {
					msg = "Erro ao enviar email!";
				}
				
			}
		}

		model.addAttribute("ambiente", ambiente);
		model.addAttribute("msg", msg);

		return "administracao/login/esqueciasenha";
	}

	private Boolean enviaEmail(String ambiente, TipoDeEmail tipodeemail, String assunto, StringBuilder corpo, String destinatario) {

		Boolean enviaPara = false;
		Propriedades util = new Propriedades();
		MailConfig config = new MailConfig();
		Properties props = new Properties();
		config = config.setConfigeProperties(configuracoesDAO);
		props = util.setProps(config);
		
		String emailfrom = config.getEmailfrom();
		String emailsenha = config.getSenha();

		if (ambiente.equals("producao")) {
			enviaPara = Mail.EnviarEmail(props, emailfrom, emailsenha, corpo, assunto, destinatario, null,
					null);	
		}
		else {
			System.out.println("email" + corpo);
		}
		
		
		return enviaPara;
	}

	private String criaCodigo(UsuarioModel usuarioanterior) {

		UsuarioModel novousuario = new UsuarioModel();
		novousuario = usuarioanterior;

		String codigo = gerarCodigo(8);
		novousuario.setCodigo(codigo);
		usuarioDAO.altera(novousuario);

		//historico.alterar(usuarioanterior, novousuario, "Usuário");

		return codigo;

	}
	
	private void alteraSenha(UsuarioModel usuariosenhanova) {
		usuarioDAO.altera(usuariosenhanova);
	}

	private String gerarCodigo(int i) {
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
	
	@RequestMapping(method = RequestMethod.GET, value = ListaDeURLs.ALTERAR_A_SENHA)
	public String formularioDeAlteracaodeSenha(@RequestParam(value = "error", required = false) String error,
			Model model) {

		String ambiente = System.getenv("AMBIENTE");
		model.addAttribute("ambiente", ambiente);
		if (error != null) {
			erros.setRedirectOrModel(model);
			// erros.adiciona(mensagem.buscaPorPropriedade("ErroLoginAutenticacao").getValor());
		}
		return "administracao/login/alterasenha";
	}

	@RequestMapping(method = RequestMethod.POST, value = ListaDeURLs.ALTERAR_A_SENHA)
	public String formularioAlterarSenha(@Valid String loginouemail, @Valid String codigo, @Valid String novasenha, Model model) {
		UsuarioModel usuarioDoBanco = null;
		String msg = "Erro ao alterar a senha! Por gentileza tente novamente.";
		String ambiente = System.getenv("AMBIENTE");


		// verifica se é um email
		if (loginouemail.contains("@")) {
			usuarioDoBanco = userService.buscaPorEmail(loginouemail);
		} else {
			usuarioDoBanco = userService.buscaPorLogin(loginouemail);
		}
		
		
		if (!codigo.equals(usuarioDoBanco.getCodigo())) {
			System.out.println("Código inválido");
			msg = "Código Inválido, favor verificar!";
		}
		else
		{
			
			usuarioDoBanco.setSenha(novasenha);
			alteraSenha(usuarioDoBanco);
			msg = "Senha alterada com sucesso!";

		}
		
		model.addAttribute("ambiente", ambiente);
		model.addAttribute("msg", msg);

		return "administracao/login/alterasenha";
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

		String destinatario = "andrep.person@gmail.com";
		String assunto = "Fale Conosco | gift4Us";
		StringBuilder corpo = new StringBuilder();
		corpo.append("Fale Conosco | gift4Us"); 
		corpo.append("<br> nome:" + nome + "<br> email: " + email);
		corpo.append("<br> celular: " + celular + "<br> mensagem: " + mensagem); 
		corpo.append("<br> melhor pedíodo: " + periodo + "<br> entrar em contato por " + contato);
		
		if (enviaEmail(ambiente, TipoDeEmail.FALECONOSCO, assunto, corpo, destinatario)) {
			msg = "Seu email foi enviado com sucesso! <br> Respondemos rapidinho, obrigado pelo contato!";
		}
		else {
			msg = "Erro ao enviar e-mail! Se for possível, por gentileza enviar um email para atendimento@gift4us.com.br";
		}
		
		model.addAttribute("msg", msg);

		return "administracao/login/faleconosco";
	}
	
	

}