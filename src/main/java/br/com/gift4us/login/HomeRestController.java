package br.com.gift4us.login;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.gift4us.mensagensdosistema.Erros;
import br.com.gift4us.mensagensdosistema.MensagensDoSistemaDAO;
import br.com.gift4us.mensagensdosistema.Sucesso;
import br.com.gift4us.produto.ProdutoDAO;
import br.com.gift4us.produto.ProdutoModel;
import br.com.gift4us.urls.ListaDeURLs;
import br.com.gift4us.util.UploadDeArquivo;
import br.com.gift4us.historicodosistema.GerenciadorDeHistorico;
import br.com.gift4us.linha.LinhaModel;
import br.com.gift4us.usuario.UsuarioModel;
import br.com.gift4us.usuario.UsuarioAdaptador;
import br.com.gift4us.usuario.UsuarioDAO;
import br.com.gift4us.anunciante.AnuncianteDAO;
import br.com.gift4us.anunciante.AnuncianteModel;
import br.com.gift4us.configuracoesdosistema.ConfiguracoesDoSistemaDAO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import br.com.gift4us.util.Resposta;


@RestController
public class HomeRestController {

	@Autowired
	private UsuarioDAO usuarioDAO;
	
	@Autowired
	private AnuncianteDAO anuncianteDAO;
	
	@Autowired
	private ProdutoDAO produtoDAO;
	
	@Autowired
	UsuarioAdaptador usuarioAdaptador;

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_TOTALDEPRODUTOS + "/{id}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaTotalDeProdutos(@PathVariable(value = "id") Long id) {
		Resposta resposta = new Resposta();
		List<Integer> lst = new ArrayList();
		
		AnuncianteModel anunciante = buscaAnunciantePeloUsuarioLogado();
		Integer total= produtoDAO.buscaProdutoByAnunciante(anunciante).size();
		lst.add(total);
		
		
		GregorianCalendar datahoje = new GregorianCalendar();
		Integer mes = datahoje.MONTH;
		Integer totalmes= produtoDAO.buscaProdutoByAnuncianteNoMes(anunciante, mes).size();
		lst.add(totalmes);
		
		try {
			resposta.setData(lst);
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}
		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}
	
	private AnuncianteModel buscaAnunciantePeloUsuarioLogado() {
		AnuncianteModel anunciante = new AnuncianteModel();
		UsuarioModel usuario = usuarioAdaptador.obterUsuarioLogado();
		usuario = usuarioDAO.buscaPorId(usuario.getId());
		anunciante = buscaAnunciante(usuario.getAnunciante().getId());
		return anunciante;
	}

	private AnuncianteModel buscaAnunciante(Long id) {
		return anuncianteDAO.buscaPorId(id);
	}


}