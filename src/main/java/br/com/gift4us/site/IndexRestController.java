package br.com.gift4us.site;

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
import br.com.gift4us.produto.ProdutoDAO;
import br.com.gift4us.urls.ListaDeURLs;
import br.com.gift4us.util.UploadDeArquivo;
import br.com.gift4us.historicodosistema.GerenciadorDeHistorico;
import br.com.gift4us.linha.LinhaDAO;
import br.com.gift4us.linha.LinhaModel;
import br.com.gift4us.usuario.UsuarioModel;
import br.com.gift4us.usuario.UsuarioAdaptador;
import br.com.gift4us.configuracoesdosistema.ConfiguracoesDoSistemaDAO;

import java.util.Calendar;
import br.com.gift4us.util.Resposta;


@RestController
public class IndexRestController {

	@Autowired
	private ProdutoDAO produtoDAO;

	@Autowired
	private LinhaDAO linhaDAO;

	@Autowired
	UsuarioAdaptador usuarioAdaptador;

	@ResponseBody
	@RequestMapping(value = {ListaDeURLs.SERVICO_DE_INDEXPRODUTOPORLINHA + "/{id}"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Resposta> buscaPorId(@PathVariable(value = "id") Long id) {
		LinhaModel linha = linhaDAO.buscaPorId(id);
		Resposta resposta = new Resposta();
		try {
			resposta.setData(produtoDAO.buscaProdutoByLinha(linha));
		} catch (Exception e) {
			for (StackTraceElement trace : e.getStackTrace()) {
				resposta.addErro(trace.toString());
			}
		}
		return new ResponseEntity<Resposta>(resposta, HttpStatus.OK);
	}
}