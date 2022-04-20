package br.com.gift4us.orcamento;

public enum OrcamentoEnum {
	NOVO(0), RESPONDIDO(1), INATIVO(2);

	private final int valor;

	OrcamentoEnum(int valorOpcao) {
		valor = valorOpcao;
	}

	public int getValor() {
		return valor;
	}
}