package br.com.gift4us.util;

import br.com.gift4us.configuracoesdosistema.ConfiguracoesDoSistemaDAO;
public class MailConfig {
		private String emailfrom;
	private String senha;
	private String port;
	private String smtp;
	private String auth;
	private String trust;
	private String protocol;
	
	public MailConfig setConfigeProperties(ConfiguracoesDoSistemaDAO configuracoesDAO) {
		MailConfig config = new MailConfig();
		config.setEmailfrom(configuracoesDAO.buscarPeloNomeDaPropriedade("mailfrom").getValor());
		config.setSenha(configuracoesDAO.buscarPeloNomeDaPropriedade("mailsenha").getValor());
		config.setPort(configuracoesDAO.buscarPeloNomeDaPropriedade("mailporta").getValor());
		config.setSmtp(configuracoesDAO.buscarPeloNomeDaPropriedade("mailsmtp").getValor());
		config.setAuth(configuracoesDAO.buscarPeloNomeDaPropriedade("mailauth").getValor());
		config.setTrust(configuracoesDAO.buscarPeloNomeDaPropriedade("mailtrust").getValor());
		config.setProtocol(configuracoesDAO.buscarPeloNomeDaPropriedade("mailprotocol").getValor());
		
		return config;
	}
	
	public String getEmailfrom() {
		return emailfrom;
	}

	public void setEmailfrom(String emailfrom) {
		this.emailfrom = emailfrom;
	}

	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getSmtp() {
		return smtp;
	}

	public void setSmtp(String smtp) {
		this.smtp = smtp;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public String getTrust() {
		return trust;
	}

	public void setTrust(String trust) {
		this.trust = trust;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
}
