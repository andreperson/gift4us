package br.com.gift4us.mensagensdosistema;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Component
public class Alerta {

	public void setMensagem(RedirectAttributes redirectAttributes, String mensagemAlerta) {
		if (redirectAttributes == null) {
			throw new RuntimeException("Não foi atribuído RedirectAtributes a esta classe");
		}
		redirectAttributes.addFlashAttribute("alerta", mensagemAlerta);
	}

	public void setMensagem(Model model, String mensagemAlerta) {
		if (model == null) {
			throw new RuntimeException("Não foi atribuído Model a esta classe");
		}
		model.addAttribute("sucesso", mensagemAlerta);
	}
}
