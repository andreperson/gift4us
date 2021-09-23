package br.com.gift4us.produto;

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
public class ProdutoRestController {



	@Autowired
	private ProdutoDAO produtoDAO;
	
	@Autowired
	private GerenciadorDeHistorico historico;

	@Autowired
	UsuarioAdaptador usuarioAdaptador;

	@Autowired
	private MensagensDoSistemaDAO mensagensDoSistemaDAO;


	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_PRODUTO,ListaDeURLs.SERVICO_DE_PRODUTO_LOGADO}, method = RequestMethod.POST, produces = "application/json;charset=UTF-8", consumes = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> insere(@Valid @RequestBody ProdutoModel dados, BindingResult bindingResult) {

		Resposta resposta = new Resposta();

		try {
			if (bindingResult.hasErrors()) {
				for (ObjectError objectError : bindingResult.getAllErrors()) {
					resposta.addErro(mensagensDoSistemaDAO.buscaPorError(objectError));
				}
				return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
			}
			produtoDAO.insere(dados);
			ProdutoModel encontrado = produtoDAO.buscaPorIdClonando(dados.getId());
			historico.inserir(encontrado, "Produto");
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
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_PRODUTO,ListaDeURLs.SERVICO_DE_PRODUTO_LOGADO}, method = RequestMethod.PUT, produces = "application/json;charset=UTF-8", consumes = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> altera(@Valid @RequestBody ProdutoModel dados, BindingResult bindingResult) {
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
			ProdutoModel dadosDoBanco = produtoDAO.buscaPorId(dados.getId());
			if (dadosDoBanco == null || dadosDoBanco.getId() == null) {
				resposta.addErro(mensagensDoSistemaDAO.buscaPorPropriedade("MensagemErroAlteracaoNaoLocalizadaNoBanco")
						.getValor());
			}
			if (resposta.getErros().size() > 0) {
				return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
			}
			ProdutoModel anterior = produtoDAO.buscaPorIdClonando(dados.getId());
			produtoDAO.altera(dados);
			ProdutoModel atual = produtoDAO.buscaPorIdClonando(dados.getId());
			historico.alterar(anterior, atual, "Produto");
			resposta.setData(dados);
			return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
			return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
		}
	}


	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_PRODUTO + "/{id}",ListaDeURLs.SERVICO_DE_PRODUTO_LOGADO + "/{id}"}, method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> exclui(@PathVariable(value = "id") Long id) {

		Resposta resposta = new Resposta();

		if (id == null || id <= 0) {
			ProdutoModel retorno = new ProdutoModel();
			resposta.addErro("id inválido: " + id);
			retorno.setId(id);
			resposta.setData(retorno);
			return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
		}

		ProdutoModel retorno = produtoDAO.buscaPorIdClonando(id);

		if (retorno == null || retorno.getId() == null) {
			retorno = new ProdutoModel();
			retorno.setId(id);
			resposta.addErro("Recurso inexistente: " + id);
			resposta.setData(retorno);
			return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
		}

		try {
			produtoDAO.exclui(retorno);
			historico.excluir(retorno, "Produto");
			resposta.setData(retorno);
			
			return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
		} catch (Exception e) {
			retorno = new ProdutoModel();
			retorno.setId(id);
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
			resposta.setData(retorno);
			return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
		}
	}


	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_PRODUTO,ListaDeURLs.SERVICO_DE_PRODUTO_LOGADO}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> listaTudo() {

		Resposta resposta = new Resposta();
		try {
			resposta.setData(produtoDAO.listaTudo());
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_PRODUTO + "/id/{id}",ListaDeURLs.SERVICO_DE_PRODUTO_LOGADO + "/id/{id}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorId(@PathVariable(value = "id") Long id) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(produtoDAO.buscaPorId(id));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_PRODUTO + "/codigo/{codigo}",ListaDeURLs.SERVICO_DE_PRODUTO_LOGADO + "/codigo/{codigo}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorCodigo(@PathVariable(value = "codigo") String codigo) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(produtoDAO.buscaPorCodigo(codigo));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_PRODUTO + "/titulo/{titulo}",ListaDeURLs.SERVICO_DE_PRODUTO_LOGADO + "/titulo/{titulo}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorTitulo(@PathVariable(value = "titulo") String titulo) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(produtoDAO.buscaPorTitulo(titulo));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_PRODUTO + "/brevedescricao/{brevedescricao}",ListaDeURLs.SERVICO_DE_PRODUTO_LOGADO + "/brevedescricao/{brevedescricao}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorBrevedescricao(@PathVariable(value = "brevedescricao") String brevedescricao) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(produtoDAO.buscaPorBrevedescricao(brevedescricao));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_PRODUTO + "/descricaocompleta/{descricaocompleta}",ListaDeURLs.SERVICO_DE_PRODUTO_LOGADO + "/descricaocompleta/{descricaocompleta}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorDescricaocompleta(@PathVariable(value = "descricaocompleta") String descricaocompleta) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(produtoDAO.buscaPorDescricaocompleta(descricaocompleta));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_PRODUTO + "/tag/{tag}",ListaDeURLs.SERVICO_DE_PRODUTO_LOGADO + "/tag/{tag}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorTag(@PathVariable(value = "tag") String tag) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(produtoDAO.buscaPorTag(tag));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_PRODUTO + "/qtdademin/{qtdademin}",ListaDeURLs.SERVICO_DE_PRODUTO_LOGADO + "/qtdademin/{qtdademin}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorQtdademin(@PathVariable(value = "qtdademin") Integer qtdademin) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(produtoDAO.buscaPorQtdademin(qtdademin));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_PRODUTO + "/preco/{preco}",ListaDeURLs.SERVICO_DE_PRODUTO_LOGADO + "/preco/{preco}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorPreco(@PathVariable(value = "preco") Double preco) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(produtoDAO.buscaPorPreco(preco));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_PRODUTO + "/faixadepreco/{faixadepreco}",ListaDeURLs.SERVICO_DE_PRODUTO_LOGADO + "/faixadepreco/{faixadepreco}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorFaixadepreco(@PathVariable(value = "faixadepreco") String faixadepreco) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(produtoDAO.buscaPorFaixadepreco(faixadepreco));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_PRODUTO + "/imagem/{imagem}",ListaDeURLs.SERVICO_DE_PRODUTO_LOGADO + "/imagem/{imagem}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorImagem(@PathVariable(value = "imagem") String imagem) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(produtoDAO.buscaPorImagem(imagem));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_PRODUTO + "/urlanunciante/{urlanunciante}",ListaDeURLs.SERVICO_DE_PRODUTO_LOGADO + "/urlanunciante/{urlanunciante}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorUrlanunciante(@PathVariable(value = "urlanunciante") String urlanunciante) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(produtoDAO.buscaPorUrlanunciante(urlanunciante));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_PRODUTO + "/dataIncl/{dataIncl}",ListaDeURLs.SERVICO_DE_PRODUTO_LOGADO + "/dataIncl/{dataIncl}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorDataIncl(@PathVariable(value = "dataIncl") Calendar dataIncl) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(produtoDAO.buscaPorDataIncl(dataIncl));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_PRODUTO + "/dataAlt/{dataAlt}",ListaDeURLs.SERVICO_DE_PRODUTO_LOGADO + "/dataAlt/{dataAlt}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorDataAlt(@PathVariable(value = "dataAlt") Calendar dataAlt) {
		
		Resposta resposta = new Resposta();
		try {
			resposta.setData(produtoDAO.buscaPorDataAlt(dataAlt));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}

		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}




}