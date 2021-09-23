package br.com.gift4us.status;

public enum StatusEnum {
	INATIVO(0), ATIVO(1);

	private final int valor;

	StatusEnum(int valorOpcao) {
		valor = valorOpcao;
	}

	public int getValor() {
		return valor;
	}
}