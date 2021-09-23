package br.com.gift4us.mensagensdosistema;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Component
public class Sucesso {

	public void setMensagem(RedirectAttributes redirectAttributes, String mensagemSucesso) {
		if (redirectAttributes == null) {
			throw new RuntimeException("Não foi atribuído RedirectAtributes a esta classe");
		}
		redirectAttributes.addFlashAttribute("sucesso", mensagemSucesso);
	}

	public void setMensagem(Model model, String mensagemSucesso) {
		if (model == null) {
			throw new RuntimeException("Não foi atribuído Model a esta classe");
		}
		model.addAttribute("sucesso", mensagemSucesso);
	}
}
