package br.com.gift4us.util;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.stereotype.Component;

import com.google.common.annotations.VisibleForTesting;

@Component
public class Propriedades {
	private Properties props;
	private String producao = "/producao.properties";
	private String homologacao = "/homologacao.properties";
	private String desenvolvimento = "/desenvolvimento.properties";

	String sysvar;

	@VisibleForTesting
	Propriedades(String sysvar) {
		this.sysvar = sysvar;
		init();
	}

	public Propriedades() {
		System.out.println("Antes da sysvar");
		sysvar = System.getenv("AMBIENTE");
		System.out.println("ambiente sysvar: " + sysvar);
		init();
		System.out.println("Depois do INIT");
	}

	private void init() {
		if (!(props instanceof Properties)) {
			this.props = new Properties();
			InputStream in = null;

			if (sysvar.toUpperCase().contains("PRODUCAO")) {
				System.out.println("Carregando producao");
				in = getClass().getResourceAsStream(this.producao);
			} else if (sysvar.toUpperCase().contains("HOMOLOGACAO")) {
				System.out.println("Carregando homologacao");
				in = getClass().getResourceAsStream(this.homologacao);
			} else if (sysvar.toUpperCase().contains("DESENVOLVIMENTO")) {
				System.out.println("Carregando desenvolvimento");
				in = getClass().getResourceAsStream(this.desenvolvimento);
			}

			try {
				this.props.load(in);
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String getValor(String chave) {
		return this.props.getProperty(chave);
	}

}