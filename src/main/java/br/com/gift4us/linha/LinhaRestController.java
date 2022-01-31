package br.com.gift4us.linha;

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


@RestController
public class LinhaRestController {

	@Autowired
	private LinhaDAO linhaDAO;
	
	@Autowired
	private GerenciadorDeHistorico historico;

	@Autowired
	UsuarioAdaptador usuarioAdaptador;

	@Autowired
	private MensagensDoSistemaDAO mensagensDoSistemaDAO;


	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_LINHA,ListaDeURLs.SERVICO_DE_LINHA_LOGADO}, method = RequestMethod.POST, produces = "application/json;charset=UTF-8", consumes = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> insere(@Valid @RequestBody LinhaModel dados, BindingResult bindingResult) {

		Resposta resposta = new Resposta();

		try {
			if (bindingResult.hasErrors()) {
				for (ObjectError objectError : bindingResult.getAllErrors()) {
					resposta.addErro(mensagensDoSistemaDAO.buscaPorError(objectError));
				}
				return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
			}
			linhaDAO.insere(dados);
			LinhaModel encontrado = linhaDAO.buscaPorIdClonando(dados.getId());
			historico.inserir(encontrado, "LINHA");
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
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_LINHA,ListaDeURLs.SERVICO_DE_LINHA_LOGADO}, method = RequestMethod.PUT, produces = "application/json;charset=UTF-8", consumes = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> altera(@Valid @RequestBody LinhaModel dados, BindingResult bindingResult) {
		Resposta resposta = new Resposta();

		try {
			if (dados.getId() == null) {
				resposta.addErro(mensagensDoSistemaDAO.buscaPorPropriedade("MensagemErroIdNulo").getValor());
			}
			if (bindingResult.hasErrors()) {
				for (ObjectError objectError : bindingResult.getAllErrors()) {
					resposta.addErro(mensagensDoSistemaDAO.buscaPorError(objectError));
				}
			}
			LinhaModel dadosDoBanco = linhaDAO.buscaPorId(dados.getId());
			if (dadosDoBanco == null || dadosDoBanco.getId() == null) {
				resposta.addErro(mensagensDoSistemaDAO.buscaPorPropriedade("MensagemErroAlteracaoNaoLocalizadaNoBanco")
						.getValor());
			}
			if (resposta.getErros().size() > 0) {
				return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
			}
			LinhaModel anterior = linhaDAO.buscaPorIdClonando(dados.getId());
			linhaDAO.altera(dados);
			LinhaModel atual = linhaDAO.buscaPorIdClonando(dados.getId());
			historico.alterar(anterior, atual, "LINHA");
			resposta.setData(dados);
			return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
			return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
		}
	}


	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_LINHA + "/{id}",ListaDeURLs.SERVICO_DE_LINHA_LOGADO + "/{id}"}, method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> exclui(@PathVariable(value = "id") Long id) {

		Resposta resposta = new Resposta();

		if (id == null || id <= 0) {
			LinhaModel retorno = new LinhaModel();
			resposta.addErro("id inválido: " + id);
			retorno.setId(id);
			resposta.setData(retorno);
			return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
		}

		LinhaModel retorno = linhaDAO.buscaPorIdClonando(id);

		if (retorno == null || retorno.getId() == null) {
			retorno = new LinhaModel();
			retorno.setId(id);
			resposta.addErro("Recurso inexistente: " + id);
			resposta.setData(retorno);
			return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
		}

		try {
			linhaDAO.exclui(retorno);
			historico.excluir(retorno, "LINHA");
			resposta.setData(retorno);
			
			return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
		} catch (Exception e) {
			retorno = new LinhaModel();
			retorno.setId(id);
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
			resposta.setData(retorno);
			return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
		}
	}


	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_LINHA,ListaDeURLs.SERVICO_DE_LINHA_LOGADO}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> listaTudo() {

		Resposta resposta = new Resposta();
		try {
			resposta.setData(linhaDAO.listaTudo());
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_LINHA + "/id/{id}",ListaDeURLs.SERVICO_DE_LINHA_LOGADO + "/id/{id}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorId(@PathVariable(value = "id") Long id) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(linhaDAO.buscaPorId(id));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_LINHA + "/nome/{nome}",ListaDeURLs.SERVICO_DE_LINHA_LOGADO + "/nome/{nome}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorNome(@PathVariable(value = "nome") String nome) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(linhaDAO.buscaPorNome(nome));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_LINHA + "/dataIncl/{dataIncl}",ListaDeURLs.SERVICO_DE_LINHA_LOGADO + "/dataIncl/{dataIncl}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorDataIncl(@PathVariable(value = "dataIncl") Calendar dataIncl) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(linhaDAO.buscaPorDataIncl(dataIncl));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_LINHA + "/dataAlt/{dataAlt}",ListaDeURLs.SERVICO_DE_LINHA_LOGADO + "/dataAlt/{dataAlt}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorDataAlt(@PathVariable(value = "dataAlt") Calendar dataAlt) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(linhaDAO.buscaPorDataAlt(dataAlt));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}




}