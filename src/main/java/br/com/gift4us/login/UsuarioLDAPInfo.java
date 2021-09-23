package br.com.gift4us.login;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class UsuarioLDAPInfo {

	private final String LDAP_SERVER = "dti-dc-01";
	private final String LDAP_SERVER_PORT = "3268";
	private final String LDAP_BIND_DN = "devweb";
	private final String LDAP_BIND_PASSWORD = "#D3w@_Dti0308";
	private String matricula;
	private String nomeCompleto;
	private Calendar ultimoLogon;
	private Calendar criacao;
	private Calendar alteracao;
	private String nomeNoDominio;
	private String nomeCurto;
	private String email;

	public String getMatricula() {
		return matricula;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public Calendar getUltimoLogon() {
		return ultimoLogon;
	}

	public Calendar getCriacao() {
		return criacao;
	}

	public Calendar getAlteracao() {
		return alteracao;
	}

	public String getNomeNoDominio() {
		return nomeNoDominio;
	}

	public String getNomeCurto() {
		return nomeCurto;
	}

	public UsuarioLDAPInfo(String matricula) {
		this.matricula = matricula;
		buscaDadosDoUsuario();
	}

	private void buscaDadosDoUsuario() {

		Hashtable<String, String> env = new Hashtable<String, String>();

		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL,
				"ldap://" + LDAP_SERVER + ":" + LDAP_SERVER_PORT);

		env.put(Context.REFERRAL, "follow");
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, LDAP_BIND_DN);
		env.put(Context.SECURITY_CREDENTIALS, LDAP_BIND_PASSWORD);

		DirContext ctx;
		try {
			ctx = new InitialDirContext(env);
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}

		NamingEnumeration<SearchResult> results = null;

		try {
			SearchControls controls = new SearchControls();
			controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			controls.setCountLimit(1);
			controls.setTimeLimit(5000);

			String searchString = "(&(objectCategory=user)(sAMAccountName="
					+ matricula + "))";

			results = ctx.search("", searchString, controls);

			if (results.hasMore()) {
				SearchResult result = (SearchResult) results.next();
				Attributes attrs = result.getAttributes();

				nomeCompleto = attrs.get("name").getAll().next().toString();

				nomeNoDominio = attrs.get("userPrincipalName").getAll().next()
						.toString();
				nomeCurto = attrs.get("givenName").getAll().next().toString();

				try {
					email = attrs.get("mail").getAll().next().toString();
				} catch (Exception e) {
					email = "";
				}

				criacao = converteParaData(
						attrs.get("whenCreated").getAll().next().toString());

				alteracao = converteParaData(
						attrs.get("whenChanged").getAll().next().toString());

				ultimoLogon = converteParaData(converteTimeStampDoWindows(attrs
						.get("lastLogonTimestamp").getAll().next().toString()));

			}

		} catch (Exception e) {
			throw new RuntimeException("Contrate o administrador do sistema",
					e);
		} finally {

			if (results != null) {
				try {
					results.close();
				} catch (Exception e) {
				}
			}

			if (ctx != null) {
				try {
					ctx.close();
				} catch (Exception e) {
				}
			}
		}
	}

	private Calendar converteParaData(String texto) {
		Calendar calendario = Calendar.getInstance();
		try {
			String[] split = texto.split("\\.");

			DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			calendario.setTime(df.parse(split[0]));
			return calendario;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String converteTimeStampDoWindows(String s) {
		long pwdSetDate = java.lang.Long.parseLong(s);
		long timeAdjust = 11644473600000L;
		Date pwdSet = new Date(pwdSetDate / 10000 - timeAdjust);
		DateFormat mydate = new SimpleDateFormat("yyyyMMddHHmmss");
		return (mydate.format(pwdSet));
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
