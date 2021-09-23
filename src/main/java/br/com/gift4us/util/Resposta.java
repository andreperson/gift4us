package br.com.gift4us.util;

import java.util.ArrayList;
import java.util.List;

public class Resposta {

	Object data;

	List<String> erros = new ArrayList<String>();

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public List<String> getErros() {
		return erros;
	}

	public void setErros(List<String> erros) {
		this.erros = erros;
	}

	public void addErro(String erro) {
		erros.add(erro);
	}

}
