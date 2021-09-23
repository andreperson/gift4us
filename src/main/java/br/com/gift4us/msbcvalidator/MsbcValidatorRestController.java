package br.com.gift4us.msbcvalidator;

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
public class MsbcValidatorRestController {



	@Autowired
	private MsbcValidatorDAO msbcValidatorDAO;
	
	@Autowired
	private GerenciadorDeHistorico historico;

	@Autowired
	private ConfiguracoesDoSistemaDAO configuracoesDoSistemaDAO;

	@Autowired
	UsuarioAdaptador usuarioAdaptador;

	@Autowired
	private MensagensDoSistemaDAO mensagensDoSistemaDAO;


	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_MSBCVALIDATOR,ListaDeURLs.SERVICO_DE_MSBCVALIDATOR_LOGADO}, method = RequestMethod.POST, produces = "application/json;charset=UTF-8", consumes = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> insere(@Valid @RequestBody MsbcValidatorModel dados, BindingResult bindingResult) {

		Resposta resposta = new Resposta();
		uploadArquivoArquivoTeste(dados, bindingResult);

		try {
			if (bindingResult.hasErrors()) {
				for (ObjectError objectError : bindingResult.getAllErrors()) {
					resposta.addErro(mensagensDoSistemaDAO.buscaPorError(objectError));
				}
				return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
			}
			msbcValidatorDAO.insere(dados);
			MsbcValidatorModel encontrado = msbcValidatorDAO.buscaPorIdClonando(dados.getId());
			historico.inserir(encontrado, "MsbcValidator");
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
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_MSBCVALIDATOR,ListaDeURLs.SERVICO_DE_MSBCVALIDATOR_LOGADO}, method = RequestMethod.PUT, produces = "application/json;charset=UTF-8", consumes = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> altera(@Valid @RequestBody MsbcValidatorModel dados, BindingResult bindingResult) {
		Resposta resposta = new Resposta();
		uploadArquivoArquivoTeste(dados, bindingResult);

		try {
			if (dados.getId() == null) {
				resposta.addErro(mensagensDoSistemaDAO.buscaPorPropriedade("MensagemErroIdNulo").getValor());
			}
			if (bindingResult.hasErrors()) {
				for (ObjectError objectError : bindingResult.getAllErrors()) {
					resposta.addErro(mensagensDoSistemaDAO.buscaPorError(objectError));
				}
			}
			MsbcValidatorModel dadosDoBanco = msbcValidatorDAO.buscaPorId(dados.getId());
			if (dadosDoBanco == null || dadosDoBanco.getId() == null) {
				resposta.addErro(mensagensDoSistemaDAO.buscaPorPropriedade("MensagemErroAlteracaoNaoLocalizadaNoBanco")
						.getValor());
			}
			if (resposta.getErros().size() > 0) {
				return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
			}
			MsbcValidatorModel anterior = msbcValidatorDAO.buscaPorIdClonando(dados.getId());
			msbcValidatorDAO.altera(dados);
			MsbcValidatorModel atual = msbcValidatorDAO.buscaPorIdClonando(dados.getId());
			historico.alterar(anterior, atual, "MsbcValidator");
			resposta.setData(dados);
			return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
			return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
		}
	}


	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_MSBCVALIDATOR + "/{id}",ListaDeURLs.SERVICO_DE_MSBCVALIDATOR_LOGADO + "/{id}"}, method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> exclui(@PathVariable(value = "id") Long id) {

		Resposta resposta = new Resposta();

		if (id == null || id <= 0) {
			MsbcValidatorModel retorno = new MsbcValidatorModel();
			resposta.addErro("id inválido: " + id);
			retorno.setId(id);
			resposta.setData(retorno);
			return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
		}

		MsbcValidatorModel retorno = msbcValidatorDAO.buscaPorIdClonando(id);

		if (retorno == null || retorno.getId() == null) {
			retorno = new MsbcValidatorModel();
			retorno.setId(id);
			resposta.addErro("Recurso inexistente: " + id);
			resposta.setData(retorno);
			return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
		}

		try {
			msbcValidatorDAO.exclui(retorno);
			historico.excluir(retorno, "MsbcValidator");
			resposta.setData(retorno);
			new File(retorno.getArquivoTeste()).delete();
			return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
		} catch (Exception e) {
			retorno = new MsbcValidatorModel();
			retorno.setId(id);
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
			resposta.setData(retorno);
			return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
		}
	}


	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_MSBCVALIDATOR,ListaDeURLs.SERVICO_DE_MSBCVALIDATOR_LOGADO}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> listaTudo() {

		Resposta resposta = new Resposta();
		try {
			resposta.setData(msbcValidatorDAO.listaTudo());
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_MSBCVALIDATOR + "/id/{id}",ListaDeURLs.SERVICO_DE_MSBCVALIDATOR_LOGADO + "/id/{id}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorId(@PathVariable(value = "id") Long id) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(msbcValidatorDAO.buscaPorId(id));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_MSBCVALIDATOR + "/requiredOnly/{requiredOnly}",ListaDeURLs.SERVICO_DE_MSBCVALIDATOR_LOGADO + "/requiredOnly/{requiredOnly}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorRequiredOnly(@PathVariable(value = "requiredOnly") String requiredOnly) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(msbcValidatorDAO.buscaPorRequiredOnly(requiredOnly));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_MSBCVALIDATOR + "/maxLengthOnly/{maxLengthOnly}",ListaDeURLs.SERVICO_DE_MSBCVALIDATOR_LOGADO + "/maxLengthOnly/{maxLengthOnly}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorMaxLengthOnly(@PathVariable(value = "maxLengthOnly") String maxLengthOnly) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(msbcValidatorDAO.buscaPorMaxLengthOnly(maxLengthOnly));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_MSBCVALIDATOR + "/somenteNumerosOnly/{somenteNumerosOnly}",ListaDeURLs.SERVICO_DE_MSBCVALIDATOR_LOGADO + "/somenteNumerosOnly/{somenteNumerosOnly}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorSomenteNumerosOnly(@PathVariable(value = "somenteNumerosOnly") String somenteNumerosOnly) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(msbcValidatorDAO.buscaPorSomenteNumerosOnly(somenteNumerosOnly));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_MSBCVALIDATOR + "/removeAcentosOnly/{removeAcentosOnly}",ListaDeURLs.SERVICO_DE_MSBCVALIDATOR_LOGADO + "/removeAcentosOnly/{removeAcentosOnly}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorRemoveAcentosOnly(@PathVariable(value = "removeAcentosOnly") String removeAcentosOnly) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(msbcValidatorDAO.buscaPorRemoveAcentosOnly(removeAcentosOnly));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_MSBCVALIDATOR + "/textDefault/{textDefault}",ListaDeURLs.SERVICO_DE_MSBCVALIDATOR_LOGADO + "/textDefault/{textDefault}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorTextDefault(@PathVariable(value = "textDefault") String textDefault) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(msbcValidatorDAO.buscaPorTextDefault(textDefault));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_MSBCVALIDATOR + "/textSomenteNumero/{textSomenteNumero}",ListaDeURLs.SERVICO_DE_MSBCVALIDATOR_LOGADO + "/textSomenteNumero/{textSomenteNumero}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorTextSomenteNumero(@PathVariable(value = "textSomenteNumero") String textSomenteNumero) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(msbcValidatorDAO.buscaPorTextSomenteNumero(textSomenteNumero));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_MSBCVALIDATOR + "/textRemoveAcentos/{textRemoveAcentos}",ListaDeURLs.SERVICO_DE_MSBCVALIDATOR_LOGADO + "/textRemoveAcentos/{textRemoveAcentos}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorTextRemoveAcentos(@PathVariable(value = "textRemoveAcentos") String textRemoveAcentos) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(msbcValidatorDAO.buscaPorTextRemoveAcentos(textRemoveAcentos));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_MSBCVALIDATOR + "/textEmail/{textEmail}",ListaDeURLs.SERVICO_DE_MSBCVALIDATOR_LOGADO + "/textEmail/{textEmail}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorTextEmail(@PathVariable(value = "textEmail") String textEmail) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(msbcValidatorDAO.buscaPorTextEmail(textEmail));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_MSBCVALIDATOR + "/cpfSomenteNumeros/{cpfSomenteNumeros}",ListaDeURLs.SERVICO_DE_MSBCVALIDATOR_LOGADO + "/cpfSomenteNumeros/{cpfSomenteNumeros}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorCpfSomenteNumeros(@PathVariable(value = "cpfSomenteNumeros") String cpfSomenteNumeros) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(msbcValidatorDAO.buscaPorCpfSomenteNumeros(cpfSomenteNumeros));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_MSBCVALIDATOR + "/cnpjSomenteNumeros/{cnpjSomenteNumeros}",ListaDeURLs.SERVICO_DE_MSBCVALIDATOR_LOGADO + "/cnpjSomenteNumeros/{cnpjSomenteNumeros}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorCnpjSomenteNumeros(@PathVariable(value = "cnpjSomenteNumeros") String cnpjSomenteNumeros) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(msbcValidatorDAO.buscaPorCnpjSomenteNumeros(cnpjSomenteNumeros));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_MSBCVALIDATOR + "/telDDD/{telDDD}",ListaDeURLs.SERVICO_DE_MSBCVALIDATOR_LOGADO + "/telDDD/{telDDD}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorTelDDD(@PathVariable(value = "telDDD") String telDDD) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(msbcValidatorDAO.buscaPorTelDDD(telDDD));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_MSBCVALIDATOR + "/telNumero/{telNumero}",ListaDeURLs.SERVICO_DE_MSBCVALIDATOR_LOGADO + "/telNumero/{telNumero}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorTelNumero(@PathVariable(value = "telNumero") String telNumero) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(msbcValidatorDAO.buscaPorTelNumero(telNumero));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_MSBCVALIDATOR + "/cepSomenteNumeros/{cepSomenteNumeros}",ListaDeURLs.SERVICO_DE_MSBCVALIDATOR_LOGADO + "/cepSomenteNumeros/{cepSomenteNumeros}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorCepSomenteNumeros(@PathVariable(value = "cepSomenteNumeros") String cepSomenteNumeros) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(msbcValidatorDAO.buscaPorCepSomenteNumeros(cepSomenteNumeros));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_MSBCVALIDATOR + "/dataTeste/{dataTeste}",ListaDeURLs.SERVICO_DE_MSBCVALIDATOR_LOGADO + "/dataTeste/{dataTeste}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorDataTeste(@PathVariable(value = "dataTeste") Calendar dataTeste) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(msbcValidatorDAO.buscaPorDataTeste(dataTeste));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_MSBCVALIDATOR + "/dataTime/{dataTime}",ListaDeURLs.SERVICO_DE_MSBCVALIDATOR_LOGADO + "/dataTime/{dataTime}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorDataTime(@PathVariable(value = "dataTime") Calendar dataTime) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(msbcValidatorDAO.buscaPorDataTime(dataTime));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_MSBCVALIDATOR + "/doubleTeste/{doubleTeste}",ListaDeURLs.SERVICO_DE_MSBCVALIDATOR_LOGADO + "/doubleTeste/{doubleTeste}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorDoubleTeste(@PathVariable(value = "doubleTeste") Double doubleTeste) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(msbcValidatorDAO.buscaPorDoubleTeste(doubleTeste));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_MSBCVALIDATOR + "/integerTeste/{integerTeste}",ListaDeURLs.SERVICO_DE_MSBCVALIDATOR_LOGADO + "/integerTeste/{integerTeste}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorIntegerTeste(@PathVariable(value = "integerTeste") Integer integerTeste) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(msbcValidatorDAO.buscaPorIntegerTeste(integerTeste));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_MSBCVALIDATOR + "/longTeste/{longTeste}",ListaDeURLs.SERVICO_DE_MSBCVALIDATOR_LOGADO + "/longTeste/{longTeste}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorLongTeste(@PathVariable(value = "longTeste") Long longTeste) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(msbcValidatorDAO.buscaPorLongTeste(longTeste));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_MSBCVALIDATOR + "/timeStampTeste/{timeStampTeste}",ListaDeURLs.SERVICO_DE_MSBCVALIDATOR_LOGADO + "/timeStampTeste/{timeStampTeste}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorTimeStampTeste(@PathVariable(value = "timeStampTeste") Calendar timeStampTeste) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(msbcValidatorDAO.buscaPorTimeStampTeste(timeStampTeste));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_MSBCVALIDATOR + "/textAreaSimples/{textAreaSimples}",ListaDeURLs.SERVICO_DE_MSBCVALIDATOR_LOGADO + "/textAreaSimples/{textAreaSimples}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorTextAreaSimples(@PathVariable(value = "textAreaSimples") String textAreaSimples) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(msbcValidatorDAO.buscaPorTextAreaSimples(textAreaSimples));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_MSBCVALIDATOR + "/textAreaHtml/{textAreaHtml}",ListaDeURLs.SERVICO_DE_MSBCVALIDATOR_LOGADO + "/textAreaHtml/{textAreaHtml}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorTextAreaHtml(@PathVariable(value = "textAreaHtml") String textAreaHtml) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(msbcValidatorDAO.buscaPorTextAreaHtml(textAreaHtml));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_MSBCVALIDATOR + "/booleanTeste/{booleanTeste}",ListaDeURLs.SERVICO_DE_MSBCVALIDATOR_LOGADO + "/booleanTeste/{booleanTeste}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorBooleanTeste(@PathVariable(value = "booleanTeste") Boolean booleanTeste) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(msbcValidatorDAO.buscaPorBooleanTeste(booleanTeste));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_MSBCVALIDATOR + "/arquivoTeste/{arquivoTeste}",ListaDeURLs.SERVICO_DE_MSBCVALIDATOR_LOGADO + "/arquivoTeste/{arquivoTeste}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorArquivoTeste(@PathVariable(value = "arquivoTeste") String arquivoTeste) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(msbcValidatorDAO.buscaPorArquivoTeste(arquivoTeste));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}



	private void uploadArquivoArquivoTeste(MsbcValidatorModel model, BindingResult bindingResult) {
		String diretorio = configuracoesDoSistemaDAO.buscaPorPropriedade("DIRETORIO_DOS_ARQUIVOS").getValor();
		UploadDeArquivo manipulador = new UploadDeArquivo(model.getArquivoTesteFile(), diretorio);

		if(!manipulador.isValido()){
			ObjectError erro = new ObjectError(manipulador.getErro(), "");
			bindingResult.addError(erro);
		}
		
		if(!manipulador.salvaComNomeNormalizado()){
			ObjectError erro = new ObjectError(manipulador.getErro(), "");
			bindingResult.addError(erro);
		}
		
		model.setArquivoTeste(manipulador.getNomeDoArquivoSalvo());
	}

}