package br.com.gift4us.login;

import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NameNotFoundException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.SizeLimitExceededException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class Autentica {

	private final String LDAP_SERVER = "dti-dc-01";
	private final String LDAP_SERVER_PORT = "3268";
	private final String LDAP_BIND_DN = "devweb";
	private final String LDAP_BIND_PASSWORD = "#D3w@_Dti0308";
	private String matricula;
	private String senha;

	public Autentica(String matricula, String senha) {
		this.matricula = matricula;
		this.senha = senha;
	}

	public Boolean verificaLogin() {
		if (matricula == null || senha == null || "".equals(matricula)
				|| "".equals(senha)) {
			return false;
		}
		
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
				Attribute dnAttr = attrs.get("distinguishedName");
				String dn = (String) dnAttr.get();
				env.put(Context.SECURITY_PRINCIPAL, dn);
				env.put(Context.SECURITY_CREDENTIALS, senha);

				new InitialDirContext(env);
				return true;
			} else
				return false;

		} catch (AuthenticationException e) {
			return false;
		} catch (NameNotFoundException e) {
			return false;
		} catch (SizeLimitExceededException e) {
			throw new RuntimeException("Contrate o administrador do sistema",
					e);
		} catch (NamingException e) {
			throw new RuntimeException(e);
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
}
