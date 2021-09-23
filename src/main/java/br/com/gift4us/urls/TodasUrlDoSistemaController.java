package br.com.gift4us.urls;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Controller
public class TodasUrlDoSistemaController {

	@Autowired
	private RequestMappingHandlerMapping handlerMapping;

	@PersistenceContext
	private EntityManager em;

	@ResponseBody
	@RequestMapping(value = "/listaDeUrls", method = RequestMethod.GET)
	public String urls(HttpServletRequest request) {
		String urlRecursos = "https://www.saobernardo.sp.gov.br";

		Query query = em.createNativeQuery("select * from dual");
		Object r = query.getSingleResult();
		if (r == null || r.toString() == null || "null".equals(r.toString())) {
			throw new RuntimeException("Banco inativo!");
		}
		Map<RequestMappingInfo, HandlerMethod> handlerMethods = this.handlerMapping.getHandlerMethods();
		Set<Entry<RequestMappingInfo, HandlerMethod>> entrySet = handlerMethods.entrySet();
		if (entrySet.isEmpty()) {
			throw new RuntimeException("Sistema inativo!");
		}
		StringBuilder sb = new StringBuilder();
		sb.append("<!doctype html>");
		sb.append("<html lang='pt-br'>");
		sb.append("<head>");
		sb.append("<meta charset='UTF-8'>");
		sb.append("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
		sb.append(
				"<meta name='viewport' content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no'>");
		sb.append("<title></title>");
		sb.append("<link rel='stylesheet' href='" + urlRecursos + "/recursosweb/css/bootstrap.min.css'>");
		sb.append("</head>");
		sb.append("<body>");
		sb.append("<table class='table table-bordered'>");
		sb.append("<head>");
		sb.append("<tr>");
		sb.append("<th>Urls</th>");
		sb.append("<th>Metodos</th>");
		sb.append("<th>Produces</th>");
		sb.append("<th>Parametros</th>");
		sb.append("<th>Consumes</th>");
		sb.append("<th>Custom</th>");
		sb.append("<th>Headers</th>");
		sb.append("<th>classe</th>");
		sb.append("<th>Nome</th>");
		sb.append("</tr>");
		sb.append("</head>");
		;
		sb.append("<body>");
		;
		sb.append("<div class='container-fluid'>");

		for (Entry<RequestMappingInfo, HandlerMethod> entry : entrySet) {
			RequestMappingInfo key = entry.getKey();
			sb.append("<tr>");
			sb.append("<td>" + key.getPatternsCondition() + "</td>");
			sb.append("<td>" + key.getMethodsCondition() + "</td>");
			sb.append("<td>" + key.getProducesCondition() + "</td>");
			sb.append("<td>" + key.getParamsCondition() + "</td>");
			sb.append("<td>" + key.getConsumesCondition() + "</td>");
			sb.append("<td>" + key.getCustomCondition() + "</td>");
			sb.append("<td>" + key.getHeadersCondition() + "</td>");
			sb.append("<td>" + entry.getValue() + "</td>");
			sb.append("<td>" + key.getName() + "</td>");
			sb.append("</tr>");
		}
		sb.append("</div>");
		sb.append("</body>");
		sb.append("</table>");
		sb.append("<script src='" + urlRecursos
				+ "/recursosweb/js/jquery.min.js' type='text/javascript' charset='UTF-8'></script>");
		sb.append("<script src='" + urlRecursos
				+ "/recursosweb/js/bootstrap.min.js' type='text/javascript' charset='UTF-8'></script>");
		sb.append("<script src='" + urlRecursos
				+ "/recursosweb/js/listaDeUrls.js' type='text/javascript' charset='UTF-8'></script>");
		sb.append("</body>");
		sb.append("</html>");
		return sb.toString();
	}

}
