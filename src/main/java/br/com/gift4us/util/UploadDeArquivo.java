package br.com.gift4us.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class UploadDeArquivo {

	private MultipartFile arquivo;
	private String diretorio;
	private String nomeDoArquivoSalvo;
	private String erro;

	public UploadDeArquivo(MultipartFile arquivo, String diretorio) {
		this.arquivo = arquivo;
		this.diretorio = diretorio;
	}

	public boolean isValido() {
		try {
			if (!"".equals(arquivo.getOriginalFilename()) && arquivo.getSize() == 0) {
				this.erro = "uploadArquivoVazioException";
				return false;
			}
		} catch (Exception e) {
			this.erro = "uploadArquivoException";
			return false;
		}
		return true;
	}

	public boolean salvaComNomeOriginal() {
		try {
			this.nomeDoArquivoSalvo = "";
			if(arquivo == null || arquivo.getSize() == 0){
				return true;
			}
			this.nomeDoArquivoSalvo = diretorio + arquivo.getOriginalFilename();
			return noCaminho();
		} catch (Exception e) {
			this.erro = "salvaComNomeOriginalException";
			System.out.println(e);
			return false;
		}
	}

	public boolean salvaComNomeNormalizado() {
		try {
			this.nomeDoArquivoSalvo = "";
			if(arquivo == null || arquivo.getSize() == 0){
				return true;
			}
			this.nomeDoArquivoSalvo = diretorio + arquivo.getOriginalFilename();
			return noCaminho();
		} catch (Exception e) {
			this.erro = "salvaComNomeNormalizadoException";
			System.out.println(e);
			return false;
		}
	}

	public boolean salvaComHash(int tamanho) {
		try {
			this.nomeDoArquivoSalvo = "";
			if(arquivo == null || arquivo.getSize() == 0){
				return true;
			}
			this.nomeDoArquivoSalvo = diretorio + arquivo.getOriginalFilename();
			return noCaminho();
		} catch (Exception e) {
			this.erro = "salvaComHashException";
			System.out.println(e);
			return false;
		}
	}

	private boolean noCaminho() {
		try {
			File serverFile = new File(this.nomeDoArquivoSalvo);
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
			stream.write(arquivo.getBytes());
			stream.close();
			return true;
		} catch (IOException e) {
			this.erro = "salvaNoCaminhoException";
			e.printStackTrace();
			return false;
		}
	}

	public String getNomeDoArquivoSalvo() {
		return nomeDoArquivoSalvo;
	}

	public String getErro() {
		return erro;
	}
}