package br.com.gift4us.anunciante;

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
public class AnuncianteRestController {



	@Autowired
	private AnuncianteDAO anuncianteDAO;
	
	@Autowired
	private GerenciadorDeHistorico historico;

	@Autowired
	UsuarioAdaptador usuarioAdaptador;

	@Autowired
	private MensagensDoSistemaDAO mensagensDoSistemaDAO;


	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_ANUNCIANTE,ListaDeURLs.SERVICO_DE_ANUNCIANTE_LOGADO}, method = RequestMethod.POST, produces = "application/json;charset=UTF-8", consumes = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> insere(@Valid @RequestBody AnuncianteModel dados, BindingResult bindingResult) {

		Resposta resposta = new Resposta();

		try {
			if (bindingResult.hasErrors()) {
				for (ObjectError objectError : bindingResult.getAllErrors()) {
					resposta.addErro(mensagensDoSistemaDAO.buscaPorError(objectError));
				}
				return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
			}
			anuncianteDAO.insere(dados);
			AnuncianteModel encontrado = anuncianteDAO.buscaPorIdClonando(dados.getId());
			historico.inserir(encontrado, "Anunciante");
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
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_ANUNCIANTE,ListaDeURLs.SERVICO_DE_ANUNCIANTE_LOGADO}, method = RequestMethod.PUT, produces = "application/json;charset=UTF-8", consumes = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> altera(@Valid @RequestBody AnuncianteModel dados, BindingResult bindingResult) {
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
			AnuncianteModel dadosDoBanco = anuncianteDAO.buscaPorId(dados.getId());
			if (dadosDoBanco == null || dadosDoBanco.getId() == null) {
				resposta.addErro(mensagensDoSistemaDAO.buscaPorPropriedade("MensagemErroAlteracaoNaoLocalizadaNoBanco")
						.getValor());
			}
			if (resposta.getErros().size() > 0) {
				return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
			}
			AnuncianteModel anterior = anuncianteDAO.buscaPorIdClonando(dados.getId());
			anuncianteDAO.altera(dados);
			AnuncianteModel atual = anuncianteDAO.buscaPorIdClonando(dados.getId());
			historico.alterar(anterior, atual, "Anunciante");
			resposta.setData(dados);
			return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
			return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
		}
	}


	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_ANUNCIANTE + "/{id}",ListaDeURLs.SERVICO_DE_ANUNCIANTE_LOGADO + "/{id}"}, method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> exclui(@PathVariable(value = "id") Long id) {

		Resposta resposta = new Resposta();

		if (id == null || id <= 0) {
			AnuncianteModel retorno = new AnuncianteModel();
			resposta.addErro("id inválido: " + id);
			retorno.setId(id);
			resposta.setData(retorno);
			return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
		}

		AnuncianteModel retorno = anuncianteDAO.buscaPorIdClonando(id);

		if (retorno == null || retorno.getId() == null) {
			retorno = new AnuncianteModel();
			retorno.setId(id);
			resposta.addErro("Recurso inexistente: " + id);
			resposta.setData(retorno);
			return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
		}

		try {
			anuncianteDAO.exclui(retorno);
			historico.excluir(retorno, "Anunciante");
			resposta.setData(retorno);
			
			return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
		} catch (Exception e) {
			retorno = new AnuncianteModel();
			retorno.setId(id);
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
			resposta.setData(retorno);
			return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
		}
	}


	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_ANUNCIANTE,ListaDeURLs.SERVICO_DE_ANUNCIANTE_LOGADO}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> listaTudo() {

		Resposta resposta = new Resposta();
		try {
			resposta.setData(anuncianteDAO.listaTudo());
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_ANUNCIANTE + "/id/{id}",ListaDeURLs.SERVICO_DE_ANUNCIANTE_LOGADO + "/id/{id}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorId(@PathVariable(value = "id") Long id) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(anuncianteDAO.buscaPorId(id));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_ANUNCIANTE + "/razaosocial/{razaosocial}",ListaDeURLs.SERVICO_DE_ANUNCIANTE_LOGADO + "/razaosocial/{razaosocial}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorRazaosocial(@PathVariable(value = "razaosocial") String razaosocial) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(anuncianteDAO.buscaPorRazaosocial(razaosocial));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_ANUNCIANTE + "/fantasia/{fantasia}",ListaDeURLs.SERVICO_DE_ANUNCIANTE_LOGADO + "/fantasia/{fantasia}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorFantasia(@PathVariable(value = "fantasia") String fantasia) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(anuncianteDAO.buscaPorFantasia(fantasia));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_ANUNCIANTE + "/email/{email}",ListaDeURLs.SERVICO_DE_ANUNCIANTE_LOGADO + "/email/{email}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorEmail(@PathVariable(value = "email") String email) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(anuncianteDAO.buscaPorEmail(email));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_ANUNCIANTE + "/ddd/{ddd}",ListaDeURLs.SERVICO_DE_ANUNCIANTE_LOGADO + "/ddd/{ddd}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorDdd(@PathVariable(value = "ddd") String ddd) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(anuncianteDAO.buscaPorDdd(ddd));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_ANUNCIANTE + "/telefone/{telefone}",ListaDeURLs.SERVICO_DE_ANUNCIANTE_LOGADO + "/telefone/{telefone}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorTelefone(@PathVariable(value = "telefone") String telefone) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(anuncianteDAO.buscaPorTelefone(telefone));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_ANUNCIANTE + "/dataIncl/{dataIncl}",ListaDeURLs.SERVICO_DE_ANUNCIANTE_LOGADO + "/dataIncl/{dataIncl}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorDataIncl(@PathVariable(value = "dataIncl") Calendar dataIncl) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(anuncianteDAO.buscaPorDataIncl(dataIncl));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_ANUNCIANTE + "/dataAlt/{dataAlt}",ListaDeURLs.SERVICO_DE_ANUNCIANTE_LOGADO + "/dataAlt/{dataAlt}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorDataAlt(@PathVariable(value = "dataAlt") Calendar dataAlt) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(anuncianteDAO.buscaPorDataAlt(dataAlt));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}




}