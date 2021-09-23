package br.com.gift4us.login;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Size;

public class AlterarSenhaModel {

	private String senhaAtual;
	
	@Size(min = 6, max = 30, message = "ValidacaoErroNovaSenhaObrigatoria")
	private String novaSenha;
	
	@Size(min = 6, max = 30, message = "ValidacaoErroRepeteSenhaObrigatoria")
	private String repeteSenha;

	@AssertTrue(message = "ValidacaoErroSenhasDiferentes")
	public boolean isSenhasIguais() {
		if (novaSenha == null || repeteSenha == null) {
			return true;
		} else {
			return novaSenha.equals(repeteSenha);
		}
	}

	public String getSenhaAtual() {
		return senhaAtual;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getRepeteSenha() {
		return repeteSenha;
	}

	public void setRepeteSenha(String repeteSenha) {
		this.repeteSenha = repeteSenha;
	}
}