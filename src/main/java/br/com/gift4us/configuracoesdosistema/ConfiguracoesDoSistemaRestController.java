package br.com.gift4us.configuracoesdosistema;

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
public class ConfiguracoesDoSistemaRestController {



	@Autowired
	private ConfiguracoesDoSistemaDAO configuracoesDoSistemaDAO;
	
	@Autowired
	private GerenciadorDeHistorico historico;

	@Autowired
	UsuarioAdaptador usuarioAdaptador;

	@Autowired
	private MensagensDoSistemaDAO mensagensDoSistemaDAO;


	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_CONFIGURACOESDOSISTEMA,ListaDeURLs.SERVICO_DE_CONFIGURACOESDOSISTEMA_LOGADO}, method = RequestMethod.POST, produces = "application/json;charset=UTF-8", consumes = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> insere(@Valid @RequestBody ConfiguracoesDoSistemaModel dados, BindingResult bindingResult) {

		Resposta resposta = new Resposta();

		try {
			if (bindingResult.hasErrors()) {
				for (ObjectError objectError : bindingResult.getAllErrors()) {
					resposta.addErro(mensagensDoSistemaDAO.buscaPorError(objectError));
				}
				return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
			}
			configuracoesDoSistemaDAO.insere(dados);
			ConfiguracoesDoSistemaModel encontrado = configuracoesDoSistemaDAO.buscaPorPropriedadeClonando(dados.getPropriedade());
			historico.inserir(encontrado, "Configurações do sistema");
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
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_CONFIGURACOESDOSISTEMA,ListaDeURLs.SERVICO_DE_CONFIGURACOESDOSISTEMA_LOGADO}, method = RequestMethod.PUT, produces = "application/json;charset=UTF-8", consumes = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> altera(@Valid @RequestBody ConfiguracoesDoSistemaModel dados, BindingResult bindingResult) {
		Resposta resposta = new Resposta();

		try {
			if (dados.getPropriedade() == null) {
				resposta.addErro(mensagensDoSistemaDAO.buscaPorPropriedade("MensagemErroIdNulo").getValor());
			}
			if (bindingResult.hasErrors()) {
				for (ObjectError objectError : bindingResult.getAllErrors()) {
					resposta.addErro(mensagensDoSistemaDAO.buscaPorError(objectError));
				}
			}
			ConfiguracoesDoSistemaModel dadosDoBanco = configuracoesDoSistemaDAO.buscaPorPropriedade(dados.getPropriedade());
			if (dadosDoBanco == null || dadosDoBanco.getPropriedade() == null) {
				resposta.addErro(mensagensDoSistemaDAO.buscaPorPropriedade("MensagemErroAlteracaoNaoLocalizadaNoBanco")
						.getValor());
			}
			if (resposta.getErros().size() > 0) {
				return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
			}
			ConfiguracoesDoSistemaModel anterior = configuracoesDoSistemaDAO.buscaPorPropriedadeClonando(dados.getPropriedade());
			configuracoesDoSistemaDAO.altera(dados);
			ConfiguracoesDoSistemaModel atual = configuracoesDoSistemaDAO.buscaPorPropriedadeClonando(dados.getPropriedade());
			historico.alterar(anterior, atual, "Configurações do sistema");
			resposta.setData(dados);
			return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
			return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
		}
	}


	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_CONFIGURACOESDOSISTEMA + "/{propriedade}",ListaDeURLs.SERVICO_DE_CONFIGURACOESDOSISTEMA_LOGADO + "/{propriedade}"}, method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> exclui(@PathVariable(value = "propriedade") String propriedade) {

		Resposta resposta = new Resposta();

		if (propriedade == null || !"".equals(propriedade)) {
			ConfiguracoesDoSistemaModel retorno = new ConfiguracoesDoSistemaModel();
			resposta.addErro("propriedade inválido: " + propriedade);
			retorno.setPropriedade(propriedade);
			resposta.setData(retorno);
			return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
		}

		ConfiguracoesDoSistemaModel retorno = configuracoesDoSistemaDAO.buscaPorPropriedadeClonando(propriedade);

		if (retorno == null || retorno.getPropriedade() == null) {
			retorno = new ConfiguracoesDoSistemaModel();
			retorno.setPropriedade(propriedade);
			resposta.addErro("Recurso inexistente: " + propriedade);
			resposta.setData(retorno);
			return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
		}

		try {
			configuracoesDoSistemaDAO.exclui(retorno);
			historico.excluir(retorno, "Configurações do sistema");
			resposta.setData(retorno);
			
			return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
		} catch (Exception e) {
			retorno = new ConfiguracoesDoSistemaModel();
			retorno.setPropriedade(propriedade);
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
			resposta.setData(retorno);
			return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
		}
	}


	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_CONFIGURACOESDOSISTEMA,ListaDeURLs.SERVICO_DE_CONFIGURACOESDOSISTEMA_LOGADO}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> listaTudoSemCache() {

		Resposta resposta = new Resposta();
		try {
			resposta.setData(configuracoesDoSistemaDAO.listaTudoSemCache());
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_CONFIGURACOESDOSISTEMA + "/propriedade/{propriedade}",ListaDeURLs.SERVICO_DE_CONFIGURACOESDOSISTEMA_LOGADO + "/propriedade/{propriedade}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorPropriedade(@PathVariable(value = "propriedade") String propriedade) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(configuracoesDoSistemaDAO.buscaPorPropriedade(propriedade));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}




}