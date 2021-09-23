package br.com.gift4us.modelo;

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
public class ModeloRestController {



	@Autowired
	private ModeloDAO modeloDAO;
	
	@Autowired
	private GerenciadorDeHistorico historico;

	@Autowired
	private ConfiguracoesDoSistemaDAO configuracoesDoSistemaDAO;

	@Autowired
	UsuarioAdaptador usuarioAdaptador;

	@Autowired
	private MensagensDoSistemaDAO mensagensDoSistemaDAO;


	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_MODELO,ListaDeURLs.SERVICO_DE_MODELO_LOGADO}, method = RequestMethod.POST, produces = "application/json;charset=UTF-8", consumes = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> insere(@Valid @RequestBody ModeloModel dados, BindingResult bindingResult) {

		Resposta resposta = new Resposta();
		uploadArquivoImagemUnica(dados, bindingResult);
		uploadArquivoImagemCabecalho(dados, bindingResult);
		uploadArquivoImagemRodape(dados, bindingResult);

		try {
			if (bindingResult.hasErrors()) {
				for (ObjectError objectError : bindingResult.getAllErrors()) {
					resposta.addErro(mensagensDoSistemaDAO.buscaPorError(objectError));
				}
				return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
			}
			modeloDAO.insere(dados);
			ModeloModel encontrado = modeloDAO.buscaPorIdClonando(dados.getId());
			historico.inserir(encontrado, "Modelo");
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
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_MODELO,ListaDeURLs.SERVICO_DE_MODELO_LOGADO}, method = RequestMethod.PUT, produces = "application/json;charset=UTF-8", consumes = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> altera(@Valid @RequestBody ModeloModel dados, BindingResult bindingResult) {
		Resposta resposta = new Resposta();
		uploadArquivoImagemUnica(dados, bindingResult);
		uploadArquivoImagemCabecalho(dados, bindingResult);
		uploadArquivoImagemRodape(dados, bindingResult);

		try {
			if (dados.getId() == null) {
				resposta.addErro(mensagensDoSistemaDAO.buscaPorPropriedade("MensagemErroIdNulo").getValor());
			}
			if (bindingResult.hasErrors()) {
				for (ObjectError objectError : bindingResult.getAllErrors()) {
					resposta.addErro(mensagensDoSistemaDAO.buscaPorError(objectError));
				}
			}
			ModeloModel dadosDoBanco = modeloDAO.buscaPorId(dados.getId());
			if (dadosDoBanco == null || dadosDoBanco.getId() == null) {
				resposta.addErro(mensagensDoSistemaDAO.buscaPorPropriedade("MensagemErroAlteracaoNaoLocalizadaNoBanco")
						.getValor());
			}
			if (resposta.getErros().size() > 0) {
				return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
			}
			ModeloModel anterior = modeloDAO.buscaPorIdClonando(dados.getId());
			modeloDAO.altera(dados);
			ModeloModel atual = modeloDAO.buscaPorIdClonando(dados.getId());
			historico.alterar(anterior, atual, "Modelo");
			resposta.setData(dados);
			return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
			return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
		}
	}


	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_MODELO + "/{id}",ListaDeURLs.SERVICO_DE_MODELO_LOGADO + "/{id}"}, method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> exclui(@PathVariable(value = "id") Long id) {

		Resposta resposta = new Resposta();

		if (id == null || id <= 0) {
			ModeloModel retorno = new ModeloModel();
			resposta.addErro("id inválido: " + id);
			retorno.setId(id);
			resposta.setData(retorno);
			return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
		}

		ModeloModel retorno = modeloDAO.buscaPorIdClonando(id);

		if (retorno == null || retorno.getId() == null) {
			retorno = new ModeloModel();
			retorno.setId(id);
			resposta.addErro("Recurso inexistente: " + id);
			resposta.setData(retorno);
			return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
		}

		try {
			modeloDAO.exclui(retorno);
			historico.excluir(retorno, "Modelo");
			resposta.setData(retorno);
			new File(retorno.getImagemUnica()).delete();new File(retorno.getImagemCabecalho()).delete();new File(retorno.getImagemRodape()).delete();
			return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
		} catch (Exception e) {
			retorno = new ModeloModel();
			retorno.setId(id);
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
			resposta.setData(retorno);
			return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
		}
	}


	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_MODELO,ListaDeURLs.SERVICO_DE_MODELO_LOGADO}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> listaTudo() {

		Resposta resposta = new Resposta();
		try {
			resposta.setData(modeloDAO.listaTudo());
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_MODELO + "/id/{id}",ListaDeURLs.SERVICO_DE_MODELO_LOGADO + "/id/{id}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorId(@PathVariable(value = "id") Long id) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(modeloDAO.buscaPorId(id));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_MODELO + "/descricao/{descricao}",ListaDeURLs.SERVICO_DE_MODELO_LOGADO + "/descricao/{descricao}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorDescricao(@PathVariable(value = "descricao") String descricao) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(modeloDAO.buscaPorDescricao(descricao));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_MODELO + "/titulo/{titulo}",ListaDeURLs.SERVICO_DE_MODELO_LOGADO + "/titulo/{titulo}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorTitulo(@PathVariable(value = "titulo") String titulo) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(modeloDAO.buscaPorTitulo(titulo));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_MODELO + "/corTitulo/{corTitulo}",ListaDeURLs.SERVICO_DE_MODELO_LOGADO + "/corTitulo/{corTitulo}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorCorTitulo(@PathVariable(value = "corTitulo") String corTitulo) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(modeloDAO.buscaPorCorTitulo(corTitulo));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_MODELO + "/imagemUnica/{imagemUnica}",ListaDeURLs.SERVICO_DE_MODELO_LOGADO + "/imagemUnica/{imagemUnica}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorImagemUnica(@PathVariable(value = "imagemUnica") String imagemUnica) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(modeloDAO.buscaPorImagemUnica(imagemUnica));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_MODELO + "/imagemCabecalho/{imagemCabecalho}",ListaDeURLs.SERVICO_DE_MODELO_LOGADO + "/imagemCabecalho/{imagemCabecalho}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorImagemCabecalho(@PathVariable(value = "imagemCabecalho") String imagemCabecalho) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(modeloDAO.buscaPorImagemCabecalho(imagemCabecalho));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_MODELO + "/imagemRodape/{imagemRodape}",ListaDeURLs.SERVICO_DE_MODELO_LOGADO + "/imagemRodape/{imagemRodape}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorImagemRodape(@PathVariable(value = "imagemRodape") String imagemRodape) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(modeloDAO.buscaPorImagemRodape(imagemRodape));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_MODELO + "/ativo/{ativo}",ListaDeURLs.SERVICO_DE_MODELO_LOGADO + "/ativo/{ativo}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorAtivo(@PathVariable(value = "ativo") Boolean ativo) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(modeloDAO.buscaPorAtivo(ativo));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}



	private void uploadArquivoImagemUnica(ModeloModel model, BindingResult bindingResult) {
		String diretorio = configuracoesDoSistemaDAO.buscaPorPropriedade("DIRETORIO_DOS_ARQUIVOS").getValor();
		UploadDeArquivo manipulador = new UploadDeArquivo(model.getImagemUnicaFile(), diretorio);

		if(!manipulador.isValido()){
			ObjectError erro = new ObjectError(manipulador.getErro(), "");
			bindingResult.addError(erro);
		}
		
		if(!manipulador.salvaComNomeNormalizado()){
			ObjectError erro = new ObjectError(manipulador.getErro(), "");
			bindingResult.addError(erro);
		}
		
		model.setImagemUnica(manipulador.getNomeDoArquivoSalvo());
	}
	private void uploadArquivoImagemCabecalho(ModeloModel model, BindingResult bindingResult) {
		String diretorio = configuracoesDoSistemaDAO.buscaPorPropriedade("DIRETORIO_DOS_ARQUIVOS").getValor();
		UploadDeArquivo manipulador = new UploadDeArquivo(model.getImagemCabecalhoFile(), diretorio);

		if(!manipulador.isValido()){
			ObjectError erro = new ObjectError(manipulador.getErro(), "");
			bindingResult.addError(erro);
		}
		
		if(!manipulador.salvaComNomeNormalizado()){
			ObjectError erro = new ObjectError(manipulador.getErro(), "");
			bindingResult.addError(erro);
		}
		
		model.setImagemCabecalho(manipulador.getNomeDoArquivoSalvo());
	}
	private void uploadArquivoImagemRodape(ModeloModel model, BindingResult bindingResult) {
		String diretorio = configuracoesDoSistemaDAO.buscaPorPropriedade("DIRETORIO_DOS_ARQUIVOS").getValor();
		UploadDeArquivo manipulador = new UploadDeArquivo(model.getImagemRodapeFile(), diretorio);

		if(!manipulador.isValido()){
			ObjectError erro = new ObjectError(manipulador.getErro(), "");
			bindingResult.addError(erro);
		}
		
		if(!manipulador.salvaComNomeNormalizado()){
			ObjectError erro = new ObjectError(manipulador.getErro(), "");
			bindingResult.addError(erro);
		}
		
		model.setImagemRodape(manipulador.getNomeDoArquivoSalvo());
	}

}