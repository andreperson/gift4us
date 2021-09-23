package br.com.gift4us.historicodosistema;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.ManyToMany;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import br.com.gift4us.usuario.UsuarioAdaptador;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GerenciadorDeHistorico {

	@Autowired
	private UsuarioAdaptador usuarioAdaptador;

	@Autowired
	private HistoricoDoSistemaDAO historicoDAO;

	public void inserir(Object dadosAtuais, String local) {
		HistoricoDoSistemaModel historico = new HistoricoDoSistemaModel();
		historico.setLogin(usuarioAdaptador.obterUsuarioLogado().getLogin());
		historico.setNome(usuarioAdaptador.obterUsuarioLogado().getNome());
		historico.setLocal(local);
		historico.setAcao("inserir");
		historico.setDados(montaTabelaComDadosUnicos(dadosAtuais, "Valor Atual"));
		historicoDAO.insere(historico);
	}

	public void alterar(Object dadosAnteriores, Object dadosAtuais, String local) {
		HistoricoDoSistemaModel historico = new HistoricoDoSistemaModel();
		historico.setLogin(usuarioAdaptador.obterUsuarioLogado().getLogin());
		historico.setNome(usuarioAdaptador.obterUsuarioLogado().getNome());
		historico.setLocal(local);
		historico.setAcao("alterar");
		historico.setDados(montaTabelaComDadosAnterioresEAtuais(dadosAnteriores, dadosAtuais));
		historicoDAO.insere(historico);
	}

	public void excluir(Object dadosAnteriores, String local) {
		HistoricoDoSistemaModel historico = new HistoricoDoSistemaModel();
		historico.setLogin(usuarioAdaptador.obterUsuarioLogado().getLogin());
		historico.setNome(usuarioAdaptador.obterUsuarioLogado().getNome());
		historico.setLocal(local);
		historico.setAcao("excluir");
		historico.setDados(montaTabelaComDadosUnicos(dadosAnteriores, "Valor Anterior"));
		historicoDAO.insere(historico);

	}

	private String montaTabelaComDadosUnicos(Object dados, String valor) {
		StringBuilder sb = new StringBuilder();
		sb.append("<table class='table table-bordered'>");
		sb.append("<thead>");
		sb.append("<th>Atributo</th>");
		sb.append("<th>" + valor + "</th>");
		sb.append("</thead>");
		sb.append("<tbody>");

		for (Field field : dados.getClass().getDeclaredFields()) {
			if (!"serialVersionUID".equals(field.getName())) {
				field.setAccessible(true);
				sb.append("<tr>");
				sb.append("<td>" + field.getName() + "</td>");
				try {
					if (field.getType().getName().equals("java.util.Calendar")) {
						sb.append("<td>" + retornaValorDaDataComoString(dados, field) + "</td>");
					} else if (field.getType().getName().contains("br.com.gift4us")) {
						sb.append("<td>" + retornaValorDoObjetoDeRelacionamento(dados, field) + "</td>");
					} else if (field.getType().getName().contains("java.util.List")) {
						sb.append("<td>" + retornaValorDoObjetoDeRelacionamentoList(dados, field) + "</td>");
					} else {
						sb.append("<td>" + field.get(dados) + "</td>");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				sb.append("</tr>");
			}
		}

		sb.append("</tbody>");
		sb.append("</table>");
		return sb.toString();
	}

	private String retornaValorDoObjetoDeRelacionamentoList(Object dados, Field field) throws Exception {
		String dadosComplentos = String.valueOf(field.get(dados));
		dadosComplentos = dadosComplentos.replace("[", "").replace("]", "");
		String[] split = dadosComplentos.split(",");

		StringBuilder sb = new StringBuilder();
		for (String string : split) {
			sb.append("<div>" + string.trim() + "</div>");
		}
		return sb.toString();
	}

	private String retornaValorDoObjetoDeRelacionamento(Object dados, Field field) throws Exception {
		return field.get(dados).toString();
	}

	private String retornaValorDaDataComoString(Object dados, Field field) throws IllegalAccessException {
		DateTimeFormat annotation = field.getAnnotation(DateTimeFormat.class);
		String pattern = "dd/mm/yyyy hh:mm:ss SSS";
		if (annotation != null) {
			pattern = annotation.pattern();
		}
		Calendar data = (Calendar) field.get(dados);
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String strdate = sdf.format(data.getTime());
		return strdate;
	}

	private String montaTabelaComDadosAnterioresEAtuais(Object dadosAnteriores, Object dadosAtuais) {
		StringBuilder sb = new StringBuilder();
		sb.append("<table class='table table-bordered'>");
		sb.append("<thead>");
		sb.append("<th>Atributo</th>");
		sb.append("<th>Valor Anterior</th>");
		sb.append("<th>Valor Atual</th>");
		sb.append("</thead>");
		sb.append("<tbody>");

		for (Field field : dadosAnteriores.getClass().getDeclaredFields()) {
			if (!"serialVersionUID".equals(field.getName())) {
				field.setAccessible(true);
				sb.append("<tr>");
				sb.append("<td>" + field.getName() + "</td>");
				try {
					if (field.getType().getName().equals("java.util.Calendar")) {
						sb.append("<td>" + retornaValorDaDataComoString(dadosAnteriores, field) + "</td>");
					} else if (field.getType().getName().contains("br.com.gift4us")) {
						sb.append("<td>" + retornaValorDoObjetoDeRelacionamento(dadosAnteriores, field) + "</td>");
					} else if (field.getType().getName().contains("java.util.List")) {
						sb.append("<td>" + retornaValorDoObjetoDeRelacionamentoList(dadosAnteriores, field) + "</td>");
					} else {
						sb.append("<td>" + field.get(dadosAnteriores) + "</td>");
					}
				} catch (Exception e) {
					sb.append("<td>ocorreu uma exception</td>");
					e.printStackTrace();
				}
				try {
					if (field.getType().getName().equals("java.util.Calendar")) {
						sb.append("<td>" + retornaValorDaDataComoString(dadosAtuais, field) + "</td>");
					} else if (field.getType().getName().contains("br.com.gift4us")) {
						sb.append("<td>" + retornaValorDoObjetoDeRelacionamento(dadosAtuais, field) + "</td>");
					} else if (field.getType().getName().contains("java.util.List")) {
						sb.append("<td>" + retornaValorDoObjetoDeRelacionamentoList(dadosAtuais, field) + "</td>");
					} else {
						sb.append("<td>" + field.get(dadosAtuais) + "</td>");
					}
				} catch (Exception e) {
					sb.append("<td>ocorreu uma exception</td>");
					e.printStackTrace();
				}
				sb.append("</tr>");
			}
		}

		sb.append("</tbody>");
		sb.append("</table>");
		return sb.toString();
	}

}