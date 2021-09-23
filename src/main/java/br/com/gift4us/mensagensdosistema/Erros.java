package br.com.gift4us.mensagensdosistema;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Erros {

	private Set<String> erros = new HashSet<String>();
	private Model model;
	private RedirectAttributes redirectAttributes;

	public void setRedirectOrModel(RedirectAttributes redirectAttributes) {
		if (redirectAttributes == null) {
			throw new RuntimeException("RedirectAtributes não pode ser null");
		}
		this.redirectAttributes = redirectAttributes;
		this.redirectAttributes.addFlashAttribute("erros", erros);
		this.model = null;
	}

	public void setRedirectOrModel(Model model) {
		if (model == null) {
			throw new RuntimeException("Model não pode ser null");
		}
		this.model = model;
		this.model.addAttribute("erros", erros);
		this.redirectAttributes = null;
	}

	public void adiciona(String mensagem) {
		erros.add(mensagem);
	}

	public void adicionaTodos(Collection<String> todasMensagens) {
		erros.addAll(todasMensagens);
	}

	public Set<String> getErros() {
		return erros;
	}

}
