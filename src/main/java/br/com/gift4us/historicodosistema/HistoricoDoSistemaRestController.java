package br.com.gift4us.historicodosistema;

import java.io.File;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
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
import br.com.gift4us.urls.ListaDeURLs;
import br.com.gift4us.util.UploadDeArquivo;
import br.com.gift4us.historicodosistema.GerenciadorDeHistorico;

import br.com.gift4us.usuario.UsuarioModel;
import br.com.gift4us.usuario.UsuarioAdaptador;
import br.com.gift4us.configuracoesdosistema.ConfiguracoesDoSistemaDAO;

import java.util.Calendar;
import br.com.gift4us.util.Resposta;
import br.com.gift4us.util.UploadDeArquivo;
import br.com.gift4us.mensagensdosistema.MensagensDoSistemaDAO;
import br.com.gift4us.urls.ListaDeURLs;


@RestController
public class HistoricoDoSistemaRestController {



	@Autowired
	private HistoricoDoSistemaDAO historicoDoSistemaDAO;
	
	@Autowired
	private GerenciadorDeHistorico historico;

	@Autowired
	UsuarioAdaptador usuarioAdaptador;

	@Autowired
	private MensagensDoSistemaDAO mensagensDoSistemaDAO;


	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_HISTORICODOSISTEMA,ListaDeURLs.SERVICO_DE_HISTORICODOSISTEMA_LOGADO}, method = RequestMethod.POST, produces = "application/json;charset=UTF-8", consumes = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> insere(@Valid @RequestBody HistoricoDoSistemaModel dados, BindingResult bindingResult) {

		Resposta resposta = new Resposta();

		try {
			if (bindingResult.hasErrors()) {
				for (ObjectError objectError : bindingResult.getAllErrors()) {
					resposta.addErro(mensagensDoSistemaDAO.buscaPorError(objectError));
				}
				return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
			}
			historicoDoSistemaDAO.insere(dados);
			HistoricoDoSistemaModel encontrado = historicoDoSistemaDAO.buscaPorIdClonando(dados.getId());
			historico.inserir(encontrado, "Histórico do sistema");
			resposta.setData(encontrado);
			return new ResponseEntity<Resposta>(resposta, HttpStatus.CREATED);
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
			return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
		}
	}






	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_HISTORICODOSISTEMA,ListaDeURLs.SERVICO_DE_HISTORICODOSISTEMA_LOGADO}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> listaTudo() {

		Resposta resposta = new Resposta();
		try {
			resposta.setData(historicoDoSistemaDAO.listaTudo());
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_HISTORICODOSISTEMA + "/id/{id}",ListaDeURLs.SERVICO_DE_HISTORICODOSISTEMA_LOGADO + "/id/{id}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorId(@PathVariable(value = "id") Long id) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(historicoDoSistemaDAO.buscaPorId(id));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_HISTORICODOSISTEMA + "/login/{login}",ListaDeURLs.SERVICO_DE_HISTORICODOSISTEMA_LOGADO + "/login/{login}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorLogin(@PathVariable(value = "login") String login) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(historicoDoSistemaDAO.buscaPorLogin(login));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_HISTORICODOSISTEMA + "/nome/{nome}",ListaDeURLs.SERVICO_DE_HISTORICODOSISTEMA_LOGADO + "/nome/{nome}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorNome(@PathVariable(value = "nome") String nome) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(historicoDoSistemaDAO.buscaPorNome(nome));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_HISTORICODOSISTEMA + "/datahora/{datahora}",ListaDeURLs.SERVICO_DE_HISTORICODOSISTEMA_LOGADO + "/datahora/{datahora}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorDatahora(@PathVariable(value = "datahora") Calendar datahora) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(historicoDoSistemaDAO.buscaPorDatahora(datahora));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_HISTORICODOSISTEMA + "/local/{local}",ListaDeURLs.SERVICO_DE_HISTORICODOSISTEMA_LOGADO + "/local/{local}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorLocal(@PathVariable(value = "local") String local) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(historicoDoSistemaDAO.buscaPorLocal(local));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_HISTORICODOSISTEMA + "/acao/{acao}",ListaDeURLs.SERVICO_DE_HISTORICODOSISTEMA_LOGADO + "/acao/{acao}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorAcao(@PathVariable(value = "acao") String acao) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(historicoDoSistemaDAO.buscaPorAcao(acao));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_HISTORICODOSISTEMA + "/dados/{dados}",ListaDeURLs.SERVICO_DE_HISTORICODOSISTEMA_LOGADO + "/dados/{dados}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorDados(@PathVariable(value = "dados") String dados) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(historicoDoSistemaDAO.buscaPorDados(dados));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}




}