package br.com.gift4us.util;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.annotations.VisibleForTesting;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component

public class FileUploader {

	@Autowired
	MessageSource msgSource;

	@VisibleForTesting
	public FileUploader(MessageSource msgSource) {
		this.msgSource = msgSource;
	}

	public FileUploader() {
	}

	public boolean grava(MultipartFile arquivo, BindingResult result,
			String diretorio) {
		try {
			
			System.out.println("caminho do diretorio: " + diretorio);
			
			File file = new File(diretorio);  

			System.out.println("existe: " + !file.exists());
			
			if (!file.exists()) {
								
			    file.mkdirs();
			}
			
			System.out.println("depois de criar o diretorio: " + diretorio);
			
			file = new File(diretorio + "/"
					+ arquivo.getOriginalFilename());

			if (file.exists()) {
				String msg = msgSource.getMessage("arquivo.duplicado", null,
						"", new Locale("pt", "BR"));
				ObjectError error = new ObjectError("arquivoDuplicado", "\""
						+ arquivo.getOriginalFilename() + "\". " + msg);
				result.addError(error);
				return false;
			}
			arquivo.transferTo(file);
		} catch (IOException e) {
			e.printStackTrace();
			String msg = msgSource.getMessage("erro.sistema", null, "",
					new Locale("pt", "BR"));
			ObjectError error = new ObjectError("arquivoErro", msg);
			System.out.println("arquivoErro: " + e.getMessage());
			result.addError(error);
			return false;
		}
		return true;
	}

	public void deleta(MultipartFile arquivo, BindingResult result,
			String diretorio) {
		new File(diretorio + "/" + arquivo.getOriginalFilename()).delete();

	}

	public void deleta(String arquivo, BindingResult result, String diretorio) {
		new File(diretorio + "/" + arquivo).delete();

	}

	public void deletaDiretorio(String diretorio) {
		new File(diretorio).delete();

	}
	
	public void renomeia(String arquivo, String nomeNovo, String diretorio) {
		Path source = Paths.get(diretorio + "/" + arquivo);

		try{
		  Files.move(source, source.resolveSibling(nomeNovo));

		} catch (IOException e) {
		  e.printStackTrace();
		}
	}
}
