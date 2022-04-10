package br.com.gift4us.orcamento;

public enum OrcamentoEnum {
	NOVO(1), RESPONDIDO(2), INATIVO(3);

	private final int valor;

	OrcamentoEnum(int valorOpcao) {
		valor = valorOpcao;
	}

	public int getValor() {
		return valor;
	}
}