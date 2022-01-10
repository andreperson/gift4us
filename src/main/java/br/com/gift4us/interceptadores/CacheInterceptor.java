package br.com.gift4us.interceptadores;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import br.com.gift4us.mensagensdosistema.MensagensDoSistemaDAO;
import br.com.gift4us.configuracoesdosistema.ConfiguracoesDoSistemaDAO;

public class CacheInterceptor extends HandlerInterceptorAdapter {


	@Autowired
	private MensagensDoSistemaDAO mensagemDAO;

	@Autowired
	private ConfiguracoesDoSistemaDAO configuracoesDAO;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		if (modelAndView != null) {
			modelAndView.addObject("mensagens", mensagemDAO.listaTudoComCache());
			modelAndView.addObject("configuracoes", configuracoesDAO.listaTudoComCache());
			
			String urlRecursos = null;
			if(request.getRequestURL().toString().startsWith("https")) {
				urlRecursos = configuracoesDAO.buscarPeloNomeDaPropriedade("HTTPS_RECURSOS").getValor(); 
			} else {
				urlRecursos = configuracoesDAO.buscarPeloNomeDaPropriedade("HTTP_RECURSOS").getValor(); 
			}
			modelAndView.addObject("urlRecursos", urlRecursos);
			
			String ambiente = configuracoesDAO.buscarPeloNomeDaPropriedade("AMBIENTE").getValor();
			modelAndView.addObject("qualambiente", ambiente);
			
			super.postHandle(request, response, handler, modelAndView);
		}
	}
}
