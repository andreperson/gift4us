package br.com.gift4us.mensagensdosistema;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import javax.transaction.Transactional;
import org.springframework.cache.annotation.Cacheable;
import java.util.Set;
import org.springframework.cache.annotation.CacheEvict;
import java.util.HashMap;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Calendar;
import org.springframework.validation.ObjectError;
import com.google.gson.Gson;

import br.com.gift4us.grupo.GrupoModel;

import java.util.Map;
import org.springframework.security.core.userdetails.UserDetails;
import com.google.common.annotations.VisibleForTesting;
import org.springframework.stereotype.Repository;

@Repository
public class MensagensDoSistemaDAO {

	private final String TABELA = MensagensDoSistemaModel.class.getSimpleName();

	private Map<String, MensagensDoSistemaModel> mapa;

	@PersistenceContext
	private EntityManager manager;

	public MensagensDoSistemaDAO() {
	}

	@VisibleForTesting
	public MensagensDoSistemaDAO(EntityManager em) {
		this.manager = em;
	}

	
	@Transactional
	public void insere(MensagensDoSistemaModel mensagensdosistema) {
		manager.persist(mensagensdosistema);
		manager.flush();
		manager.detach(mensagensdosistema);
	}

	@Transactional
	public void altera(MensagensDoSistemaModel mensagensdosistema) {
		manager.merge(mensagensdosistema);
		manager.flush();
		manager.detach(mensagensdosistema);
	}

	@Transactional
	public void exclui(MensagensDoSistemaModel mensagensdosistema) {
		manager.remove(manager.find(MensagensDoSistemaModel.class, mensagensdosistema.getPropriedade()));
	}

	public MensagensDoSistemaModel buscaPorPropriedade(String propriedade) {

		if (mapa == null) {
			this.listaTudoComCache();
		}

		MensagensDoSistemaModel retorno = mapa.get(propriedade);

		if(retorno == null){
			retorno = new MensagensDoSistemaModel();
			retorno.setPropriedade("");
			retorno.setValor("");
			retorno.setTela("");
		}

		return retorno;
	}
	@Cacheable(value = "cacheMensagensDoSistema")
	public Map<String, MensagensDoSistemaModel> listaTudoComCache() {
		String jpql = "SELECT m FROM " + TABELA + " m ORDER BY m.id";
		TypedQuery<MensagensDoSistemaModel> query = manager.createQuery(jpql, MensagensDoSistemaModel.class);

		mapa = new HashMap<String, MensagensDoSistemaModel>();

		for (MensagensDoSistemaModel dados : query.getResultList()) {
			mapa.put(dados.getPropriedade(), dados);
		}
		return mapa;
	}

	public List<MensagensDoSistemaModel> buscaPorPropriedadeExata(MensagensDoSistemaModel mensagensdosistema) {

		String jpql = "SELECT g FROM " + TABELA + " g WHERE g.valor = :valor and g.tela = :tela";

		TypedQuery<MensagensDoSistemaModel> query = manager.createQuery(jpql, MensagensDoSistemaModel.class);

		query.setParameter("tela", mensagensdosistema.getTela());
		query.setParameter("valor", mensagensdosistema.getValor());

		return query.getResultList();
	}
	
	@CacheEvict(value = "cacheMensagensDoSistema")
	public void limpaCache() {
		mapa = null;
	}

	public List<MensagensDoSistemaModel> listaTudoSemCache() {
		String jpql = "SELECT m FROM " + TABELA + " m ORDER BY m.id";
		TypedQuery<MensagensDoSistemaModel> query = manager.createQuery(jpql, MensagensDoSistemaModel.class);
		return query.getResultList();
	}
	public MensagensDoSistemaModel buscaPorPropriedadeSemCache(String id) {

		String jpql = "SELECT m FROM " + TABELA + " m WHERE m.id = :id ";

		TypedQuery<MensagensDoSistemaModel> query = manager.createQuery(jpql, MensagensDoSistemaModel.class);

		query.setParameter("id", id);

		List<MensagensDoSistemaModel> resultado = query.getResultList();

		if(resultado.size() == 0){
			return new MensagensDoSistemaModel();
		}else{
			return resultado.get(0);
		}
	}

	public MensagensDoSistemaModel buscaPorPropriedadeClonando(String id) {
		MensagensDoSistemaModel encontrado = buscaPorPropriedadeSemCache(id);
		Gson gson = new Gson();
		return gson.fromJson(gson.toJson(encontrado), MensagensDoSistemaModel.class);
	}

	public String buscaPorError(ObjectError objectError) {
		return buscaPorPropriedade(objectError.getDefaultMessage()).getValor();
	}


	@Transactional
	public void insertsParaSeremUtilizadosNoPostConstruct() {
		List<MensagensDoSistemaModel> lista = listaTudoSemCache();
		criaSeNaoExistir(lista, "NomeDoProjeto", "Gift For Us", "Nome do Projeto");
		criaSeNaoExistir(lista, "SaudacaoHome", "Bem-vindo: ", "Home");
		criaSeNaoExistir(lista, "ErroLoginAutenticacao", "Usuário ou senha inválida!", "Home");
		criaSeNaoExistir(lista, "AnuncianteFormularioId", "Código", "Formulário de Anunciante");
		criaSeNaoExistir(lista, "AnuncianteListaId", "Código", "Lista de Anunciante");
		criaSeNaoExistir(lista, "AnuncianteFormularioRazaosocial", "Razão Social", "Formulário de Anunciante");
		criaSeNaoExistir(lista, "AnuncianteListaRazaosocial", "Razão Social", "Lista de Anunciante");
		criaSeNaoExistir(lista, "AnuncianteFormularioFantasia", "Nome Fantasia", "Formulário de Anunciante");
		criaSeNaoExistir(lista, "AnuncianteListaFantasia", "Nome Fantasia", "Lista de Anunciante");
		criaSeNaoExistir(lista, "AnuncianteFormularioEmail", "E-Mail", "Formulário de Anunciante");
		criaSeNaoExistir(lista, "AnuncianteListaEmail", "E-Mail", "Lista de Anunciante");
		criaSeNaoExistir(lista, "AnuncianteFormularioDdd", "DDD", "Formulário de Anunciante");
		criaSeNaoExistir(lista, "AnuncianteListaDdd", "DDD", "Lista de Anunciante");
		criaSeNaoExistir(lista, "AnuncianteFormularioTelefone", "Telefone", "Formulário de Anunciante");
		criaSeNaoExistir(lista, "AnuncianteListaTelefone", "Telefone", "Lista de Anunciante");
		criaSeNaoExistir(lista, "AnuncianteFormularioDataIncl", "Data Inclusão", "Formulário de Anunciante");
		criaSeNaoExistir(lista, "AnuncianteListaDataIncl", "Data Inclusão", "Lista de Anunciante");
		criaSeNaoExistir(lista, "AnuncianteFormularioDataAlt", "Data Alteração", "Formulário de Anunciante");
		criaSeNaoExistir(lista, "AnuncianteListaDataAlt", "Data Alteração", "Lista de Anunciante");
		criaSeNaoExistir(lista, "AnuncianteFormularioAnuncianteTipo", "AnuncianteTipo", "Formulário de Anunciante");
		criaSeNaoExistir(lista, "AnuncianteListaAnuncianteTipo", "AnuncianteTipo", "Lista de Anunciante");
		criaSeNaoExistir(lista, "AnuncianteMenuPrincipal", "Anunciante", "Menu");
		criaSeNaoExistir(lista, "AnuncianteMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "AnuncianteMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "AnuncianteFormularioBotaoInsere", "Insere", "Formulário de Anunciante");
		criaSeNaoExistir(lista, "AnuncianteFormularioBotaoAltera", "Altera", "Formulário de Anunciante");
		criaSeNaoExistir(lista, "AnuncianteFormularioTituloDaPagina", "Cadastro de Anunciante", "Formulário de Anunciante");
		criaSeNaoExistir(lista, "AnuncianteListaTituloDaPagina", "Lista de Anunciante", "Lista de Anunciante");
		criaSeNaoExistir(lista, "AnuncianteListaAcoes", "Ações", "Lista de Anunciante");
		criaSeNaoExistir(lista, "AnuncianteListaSemRegistro", "Não foram encontrados registros para Anunciante", "Lista de Anunciante");
		criaSeNaoExistir(lista, "AnuncianteTipoFormularioId", "Código", "Formulário de AnuncianteTipo");
		criaSeNaoExistir(lista, "AnuncianteTipoListaId", "Código", "Lista de AnuncianteTipo");
		criaSeNaoExistir(lista, "AnuncianteTipoFormularioNome", "Nome", "Formulário de AnuncianteTipo");
		criaSeNaoExistir(lista, "AnuncianteTipoListaNome", "Nome", "Lista de AnuncianteTipo");
		criaSeNaoExistir(lista, "AnuncianteTipoFormularioDataIncl", "Data Inclusão", "Formulário de AnuncianteTipo");
		criaSeNaoExistir(lista, "AnuncianteTipoListaDataIncl", "Data Inclusão", "Lista de AnuncianteTipo");
		criaSeNaoExistir(lista, "AnuncianteTipoFormularioDataAlt", "Data Alteração", "Formulário de AnuncianteTipo");
		criaSeNaoExistir(lista, "AnuncianteTipoListaDataAlt", "Data Alteração", "Lista de AnuncianteTipo");
		criaSeNaoExistir(lista, "AnuncianteTipoMenuPrincipal", "AnuncianteTipo", "Menu");
		criaSeNaoExistir(lista, "AnuncianteTipoMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "AnuncianteTipoMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "AnuncianteTipoFormularioBotaoInsere", "Insere", "Formulário de AnuncianteTipo");
		criaSeNaoExistir(lista, "AnuncianteTipoFormularioBotaoAltera", "Altera", "Formulário de AnuncianteTipo");
		criaSeNaoExistir(lista, "AnuncianteTipoFormularioTituloDaPagina", "Cadastro de AnuncianteTipo", "Formulário de AnuncianteTipo");
		criaSeNaoExistir(lista, "AnuncianteTipoListaTituloDaPagina", "Lista de AnuncianteTipo", "Lista de AnuncianteTipo");
		criaSeNaoExistir(lista, "AnuncianteTipoListaAcoes", "Ações", "Lista de AnuncianteTipo");
		criaSeNaoExistir(lista, "AnuncianteTipoListaSemRegistro", "Não foram encontrados registros para AnuncianteTipo", "Lista de AnuncianteTipo");
		criaSeNaoExistir(lista, "CategoriaFormularioId", "Código", "Formulário de Categoria");
		criaSeNaoExistir(lista, "CategoriaListaId", "Código", "Lista de Categoria");
		criaSeNaoExistir(lista, "CategoriaFormularioNome", "Nome", "Formulário de Categoria");
		criaSeNaoExistir(lista, "CategoriaFormularioImagem", "Imagem", "Formulário de Categoria");
		criaSeNaoExistir(lista, "CategoriaListaNome", "Nome", "Lista de Categoria");
		criaSeNaoExistir(lista, "CategoriaListaImagem", "Imagem", "Lista de Categoria");
		criaSeNaoExistir(lista, "CategoriaFormularioDataIncl", "Data Inclusão", "Formulário de Categoria");
		criaSeNaoExistir(lista, "CategoriaListaDataIncl", "Data Inclusão", "Lista de Categoria");
		criaSeNaoExistir(lista, "CategoriaFormularioDataAlt", "Data Alteração", "Formulário de Categoria");
		criaSeNaoExistir(lista, "CategoriaListaDataAlt", "Data Alteração", "Lista de Categoria");
		criaSeNaoExistir(lista, "CategoriaMenuPrincipal", "Categoria", "Menu");
		criaSeNaoExistir(lista, "CategoriaMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "CategoriaMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "CategoriaFormularioBotaoInsere", "Insere", "Formulário de Categoria");
		criaSeNaoExistir(lista, "CategoriaFormularioBotaoAltera", "Altera", "Formulário de Categoria");
		criaSeNaoExistir(lista, "CategoriaFormularioTituloDaPagina", "Cadastro de Categoria", "Formulário de Categoria");
		criaSeNaoExistir(lista, "CategoriaListaTituloDaPagina", "Lista de Categoria", "Lista de Categoria");
		criaSeNaoExistir(lista, "CategoriaListaAcoes", "Ações", "Lista de Categoria");
		criaSeNaoExistir(lista, "CategoriaListaSemRegistro", "Não foram encontrados registros para Categoria", "Lista de Categoria");
		criaSeNaoExistir(lista, "FaixaDePrecoFormularioId", "Código", "Formulário de Faixa de Preço");
		criaSeNaoExistir(lista, "FaixaDePrecoListaId", "Código", "Lista de Faixa de Preço");
		criaSeNaoExistir(lista, "FaixaDePrecoFormularioNome", "Descrição", "Formulário de Faixa de Preço");
		criaSeNaoExistir(lista, "FaixaDePrecoFormularioPrecoDe", "Preço De", "Formulário de Faixa de Preço");
		criaSeNaoExistir(lista, "FaixaDePrecoFormularioPrecoAte", "Preço Até", "Formulário de Faixa de Preço");
		criaSeNaoExistir(lista, "FaixaDePrecoListaNome", "Descrição", "Lista de Faixa de Preço");
		criaSeNaoExistir(lista, "FaixaDePrecoListaPrecoDe", "Preço De", "Lista de Faixa de Preço");
		criaSeNaoExistir(lista, "FaixaDePrecoListaPrecoAte", "Preço Até", "Lista de Faixa de Preço");
		criaSeNaoExistir(lista, "FaixaDePrecoFormularioDataIncl", "Data Inclusão", "Formulário de Faixa de Preço");
		criaSeNaoExistir(lista, "FaixaDePrecoListaDataIncl", "Data Inclusão", "Lista de Faixa de Preço");
		criaSeNaoExistir(lista, "FaixaDePrecoFormularioDataAlt", "Data Alteração", "Formulário de Faixa de Preço");
		criaSeNaoExistir(lista, "FaixaDePrecoListaDataAlt", "Data Alteração", "Lista de Faixa de Preço");
		criaSeNaoExistir(lista, "FaixaDePrecoMenuPrincipal", "Faixa de Preço", "Menu");
		criaSeNaoExistir(lista, "FaixaDePrecoMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "FaixaDePrecoMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "FaixaDePrecoFormularioBotaoInsere", "Insere", "Formulário de Faixa de Preço");
		criaSeNaoExistir(lista, "FaixaDePrecoFormularioBotaoAltera", "Altera", "Formulário de Faixa de Preço");
		criaSeNaoExistir(lista, "FaixaDePrecoFormularioTituloDaPagina", "Cadastro de Faixa de Preço", "Formulário de Faixa de Preço");
		criaSeNaoExistir(lista, "FaixaDePrecoListaTituloDaPagina", "Lista de Faixa de Preço", "Lista de Faixa de Preço");
		criaSeNaoExistir(lista, "FaixaDePrecoListaAcoes", "Ações", "Lista de Faixa de Preço");
		criaSeNaoExistir(lista, "FaixaDePrecoListaSemRegistro", "Não foram encontrados registros para Faixa de Preço", "Lista de Faixa de Preço");
		criaSeNaoExistir(lista, "AtividadeFormularioId", "Código", "Formulário de Atividade");
		criaSeNaoExistir(lista, "AtividadeListaId", "Código", "Lista de Atividade");
		criaSeNaoExistir(lista, "AtividadeFormularioNome", "Nome", "Formulário de Atividade");
		criaSeNaoExistir(lista, "AtividadeListaNome", "Nome", "Lista de Atividade");
		criaSeNaoExistir(lista, "AtividadeFormularioProduto", "Produto", "Formulário de Atividade");
		criaSeNaoExistir(lista, "AtividadeListaProduto", "Produto", "Lista de Atividade");
		criaSeNaoExistir(lista, "AtividadeFormularioDataIncl", "Data Inclusão", "Formulário de Atividade");
		criaSeNaoExistir(lista, "AtividadeListaDataIncl", "Data Inclusão", "Lista de Atividade");
		criaSeNaoExistir(lista, "AtividadeFormularioDataAlt", "Data Alteração", "Formulário de Atividade");
		criaSeNaoExistir(lista, "AtividadeListaDataAlt", "Data Alteração", "Lista de Atividade");
		criaSeNaoExistir(lista, "AtividadeMenuPrincipal", "Atividade", "Menu");
		criaSeNaoExistir(lista, "AtividadeMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "AtividadeMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "AtividadeFormularioBotaoInsere", "Insere", "Formulário de Atividade");
		criaSeNaoExistir(lista, "AtividadeFormularioBotaoAltera", "Altera", "Formulário de Atividade");
		criaSeNaoExistir(lista, "AtividadeFormularioTituloDaPagina", "Cadastro de Atividade", "Formulário de Atividade");
		criaSeNaoExistir(lista, "AtividadeListaTituloDaPagina", "Lista de Atividade", "Lista de Atividade");
		criaSeNaoExistir(lista, "AtividadeListaAcoes", "Ações", "Lista de Atividade");
		criaSeNaoExistir(lista, "AtividadeListaSemRegistro", "Não foram encontrados registros para Atividade", "Lista de Atividade");
		
		criaSeNaoExistir(lista, "LinhaFormularioId", "Código", "Formulário de Linha");
		criaSeNaoExistir(lista, "LinhaListaId", "Código", "Lista de Linha");
		criaSeNaoExistir(lista, "LinhaFormularioNome", "Nome", "Formulário de Linha");
		criaSeNaoExistir(lista, "LinhaFormularioCampanha", "Campanha", "Formulário de Linha");
		criaSeNaoExistir(lista, "LinhaListaNome", "Nome", "Lista de Linha");
		criaSeNaoExistir(lista, "LinhaListaCampanha", "Campanha", "Lista de Linha");
		criaSeNaoExistir(lista, "LinhaFormularioProduto", "Produto", "Formulário de Linha");
		criaSeNaoExistir(lista, "LinhaListaProduto", "Produto", "Lista de Linha");
		criaSeNaoExistir(lista, "LinhaFormularioDataIncl", "Data Inclusão", "Formulário de Linha");
		criaSeNaoExistir(lista, "LinhaListaDataIncl", "Data Inclusão", "Lista de Linha");
		criaSeNaoExistir(lista, "LinhaFormularioDataAlt", "Data Alteração", "Formulário de Linha");
		criaSeNaoExistir(lista, "LinhaListaDataAlt", "Data Alteração", "Lista de Linha");
		criaSeNaoExistir(lista, "LinhaMenuPrincipal", "Linha", "Menu");
		criaSeNaoExistir(lista, "LinhaMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "LinhaMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "LinhaFormularioBotaoInsere", "Insere", "Formulário de Linha");
		criaSeNaoExistir(lista, "LinhaFormularioBotaoAltera", "Altera", "Formulário de Linha");
		criaSeNaoExistir(lista, "LinhaFormularioTituloDaPagina", "Cadastro de Linha", "Formulário de Linha");
		criaSeNaoExistir(lista, "LinhaListaTituloDaPagina", "Lista de Linha", "Lista de Linha");
		criaSeNaoExistir(lista, "LinhaListaAcoes", "Ações", "Lista de Linha");
		criaSeNaoExistir(lista, "LinhaListaSemRegistro", "Não foram encontrados registros para Linha", "Lista de Linha");
		
		criaSeNaoExistir(lista, "CampanhaFormularioId", "Código", "Formulário de Campanha");
		criaSeNaoExistir(lista, "CampanhaListaId", "Código", "Lista de Campanha");
		criaSeNaoExistir(lista, "CampanhaFormularioNome", "Nome", "Formulário de Campanha");
		criaSeNaoExistir(lista, "CampanhaFormularioOrdem", "Ordem", "Formulário de Campanha");
		criaSeNaoExistir(lista, "CampanhaListaNome", "Nome", "Lista de Campanha");
		criaSeNaoExistir(lista, "CampanhaListaOrdem", "Ordem", "Lista de Campanha");
		criaSeNaoExistir(lista, "CampanhaFormularioProduto", "Produto", "Formulário de Campanha");
		criaSeNaoExistir(lista, "CampanhaListaProduto", "Produto", "Lista de Campanha");
		criaSeNaoExistir(lista, "CampanhaFormularioDataIncl", "Data Inclusão", "Formulário de Campanha");
		criaSeNaoExistir(lista, "CampanhaListaDataIncl", "Data Inclusão", "Lista de Campanha");
		criaSeNaoExistir(lista, "CampanhaFormularioDataAlt", "Data Alteração", "Formulário de Campanha");
		criaSeNaoExistir(lista, "CampanhaListaDataAlt", "Data Alteração", "Lista de Campanha");
		criaSeNaoExistir(lista, "CampanhaMenuPrincipal", "Campanha", "Menu");
		criaSeNaoExistir(lista, "CampanhaMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "CampanhaMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "CampanhaFormularioBotaoInsere", "Insere", "Formulário de Campanha");
		criaSeNaoExistir(lista, "CampanhaFormularioBotaoAltera", "Altera", "Formulário de Campanha");
		criaSeNaoExistir(lista, "CampanhaFormularioTituloDaPagina", "Cadastro de Campanha", "Formulário de Campanha");
		criaSeNaoExistir(lista, "CampanhaListaTituloDaPagina", "Lista de Campanha", "Lista de Campanha");
		criaSeNaoExistir(lista, "CampanhaListaAcoes", "Ações", "Lista de Campanha");
		criaSeNaoExistir(lista, "CampanhaListaSemRegistro", "Não foram encontrados registros para Campanha", "Lista de Campanha");		
		
		criaSeNaoExistir(lista, "ModeloFormularioId", "Código", "Formulário de Modelo");
		criaSeNaoExistir(lista, "ModeloListaId", "Código", "Lista de Modelo");
		criaSeNaoExistir(lista, "ModeloFormularioDescricao", "Descrição", "Formulário de Modelo");
		criaSeNaoExistir(lista, "ModeloListaDescricao", "Descrição", "Lista de Modelo");
		criaSeNaoExistir(lista, "ModeloFormularioTitulo", "Título do Certificado", "Formulário de Modelo");
		criaSeNaoExistir(lista, "ModeloListaTitulo", "Título do Certificado", "Lista de Modelo");
		criaSeNaoExistir(lista, "ModeloFormularioCorTitulo", "Cor do Título", "Formulário de Modelo");
		criaSeNaoExistir(lista, "ModeloListaCorTitulo", "Cor do Título", "Lista de Modelo");
		criaSeNaoExistir(lista, "ModeloFormularioImagemUnica", "Imagem", "Formulário de Modelo");
		criaSeNaoExistir(lista, "ModeloListaImagemUnica", "Imagem", "Lista de Modelo");
		criaSeNaoExistir(lista, "ModeloFormularioImagemCabecalho", "Imagem do Cabecalho", "Formulário de Modelo");
		criaSeNaoExistir(lista, "ModeloListaImagemCabecalho", "Imagem do Cabecalho", "Lista de Modelo");
		criaSeNaoExistir(lista, "ModeloFormularioImagemRodape", "Imagem do Rodapé", "Formulário de Modelo");
		criaSeNaoExistir(lista, "ModeloListaImagemRodape", "Imagem do Rodapé", "Lista de Modelo");
		criaSeNaoExistir(lista, "ModeloFormularioAtivo", "Ativo", "Formulário de Modelo");
		criaSeNaoExistir(lista, "ModeloListaAtivo", "Ativo", "Lista de Modelo");
		criaSeNaoExistir(lista, "ModeloFormularioAtivoLigado", "Sim", "Formulário de Modelo");
		criaSeNaoExistir(lista, "ModeloListaAtivoLigado", "Sim", "Lista de Modelo");
		criaSeNaoExistir(lista, "ModeloFormularioAtivoDesligado", "Não", "Formulário de Modelo");
		criaSeNaoExistir(lista, "ModeloListaAtivoDesligado", "Não", "Lista de Modelo");
		criaSeNaoExistir(lista, "ModeloMenuPrincipal", "Modelo", "Menu");
		criaSeNaoExistir(lista, "ModeloMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "ModeloMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "ModeloFormularioBotaoInsere", "Insere", "Formulário de Modelo");
		criaSeNaoExistir(lista, "ModeloFormularioBotaoAltera", "Altera", "Formulário de Modelo");
		criaSeNaoExistir(lista, "ModeloFormularioTituloDaPagina", "Cadastro de Modelo", "Formulário de Modelo");
		criaSeNaoExistir(lista, "ModeloListaTituloDaPagina", "Lista de Modelo", "Lista de Modelo");
		criaSeNaoExistir(lista, "ModeloListaAcoes", "Ações", "Lista de Modelo");
		criaSeNaoExistir(lista, "ModeloListaSemRegistro", "Não foram encontrados registros para Modelo", "Lista de Modelo");
		criaSeNaoExistir(lista, "OrcamentoFormularioId", "Código", "Formulário de Orcamento");
		criaSeNaoExistir(lista, "OrcamentoListaId", "Código", "Lista de Orcamento");
		criaSeNaoExistir(lista, "OrcamentoFormularioQuantidade", "Quantidade", "Formulário de Orcamento");
		criaSeNaoExistir(lista, "OrcamentoFormularioNome", "Nome", "Formulário de Orcamento");
		criaSeNaoExistir(lista, "OrcamentoFormularioEmail", "Email", "Formulário de Orcamento");
		criaSeNaoExistir(lista, "OrcamentoFormularioDDD", "DDD", "Formulário de Orcamento");
		criaSeNaoExistir(lista, "OrcamentoFormularioCelular", "Celular", "Formulário de Orcamento");
		criaSeNaoExistir(lista, "OrcamentoFormularioBotaoEnviar", "Enviar Orçamento", "Formulário de Orcamento");
		criaSeNaoExistir(lista, "OrcamentoListaQuantidade", "Quantidade", "Lista de Orcamento");
		criaSeNaoExistir(lista, "OrcamentoFormularioDataIncl", "Data Inclusão", "Formulário de Orcamento");
		criaSeNaoExistir(lista, "OrcamentoListaDataIncl", "Data Inclusão", "Lista de Orcamento");
		criaSeNaoExistir(lista, "OrcamentoFormularioDataAlt", "Data Alteração", "Formulário de Orcamento");
		criaSeNaoExistir(lista, "OrcamentoListaDataAlt", "Data Alteração", "Lista de Orcamento");
		criaSeNaoExistir(lista, "OrcamentoFormularioProduto", "Produto", "Formulário de Orcamento");
		criaSeNaoExistir(lista, "OrcamentoListaProduto", "Produto", "Lista de Orcamento");
		criaSeNaoExistir(lista, "OrcamentoFormularioAnunciante", "Anunciante", "Formulário de Orcamento");
		criaSeNaoExistir(lista, "OrcamentoListaAnunciante", "Anunciante", "Lista de Orcamento");
		criaSeNaoExistir(lista, "OrcamentoMenuPrincipal", "Orcamento", "Menu");
		criaSeNaoExistir(lista, "OrcamentoMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "OrcamentoMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "OrcamentoFormularioBotaoInsere", "Insere", "Formulário de Orcamento");
		criaSeNaoExistir(lista, "OrcamentoFormularioBotaoAltera", "Altera", "Formulário de Orcamento");
		criaSeNaoExistir(lista, "OrcamentoFormularioTituloDaPagina", "Cadastro de Orcamento", "Formulário de Orcamento");
		criaSeNaoExistir(lista, "OrcamentoListaTituloDaPagina", "Lista de Orcamento", "Lista de Orcamento");
		criaSeNaoExistir(lista, "OrcamentoListaAcoes", "Ações", "Lista de Orcamento");
		criaSeNaoExistir(lista, "OrcamentoListaSemRegistro", "Não foram encontrados registros para Orcamento", "Lista de Orcamento");
		criaSeNaoExistir(lista, "ProdutoFormularioId", "ID", "Formulário de Produto");
		criaSeNaoExistir(lista, "ProdutoListaId", "ID", "Lista de Produto");
		criaSeNaoExistir(lista, "ProdutoFormularioCodigo", "Código", "Formulário de Produto");
		criaSeNaoExistir(lista, "ProdutoListaCodigo", "Código", "Lista de Produto");
		criaSeNaoExistir(lista, "ProdutoFormularioTitulo", "Título", "Formulário de Produto");
		criaSeNaoExistir(lista, "ProdutoListaTitulo", "Título", "Lista de Produto");
		criaSeNaoExistir(lista, "ProdutoFormularioBrevedescricao", "Breve Descricao", "Formulário de Produto");
		criaSeNaoExistir(lista, "ProdutoListaBrevedescricao", "Breve Descricao", "Lista de Produto");
		criaSeNaoExistir(lista, "ProdutoFormularioDescricaocompleta", "Descricao Completa", "Formulário de Produto");
		criaSeNaoExistir(lista, "ProdutoListaDescricaocompleta", "Descricao Completa", "Lista de Produto");
		criaSeNaoExistir(lista, "ProdutoFormularioTag", "Tags (usados na busca)", "Formulário de Produto");
		criaSeNaoExistir(lista, "ProdutoListaTag", "Tags", "Lista de Produto");
		criaSeNaoExistir(lista, "ProdutoFormularioQtdademin", "Quantidade Mínima", "Formulário de Produto");
		criaSeNaoExistir(lista, "ProdutoListaQtdademin", "Quantidade Mínima", "Lista de Produto");
		criaSeNaoExistir(lista, "ProdutoFormularioEstoque", "Estoque", "Formulário de Produto");
		criaSeNaoExistir(lista, "ProdutoListaEstoque", "Estoque", "Lista de Produto");
		criaSeNaoExistir(lista, "ProdutoFormularioPreco", "Preço", "Formulário de Produto");
		criaSeNaoExistir(lista, "ProdutoFormularioDesconto", "% Desconto", "Formulário de Produto");
		criaSeNaoExistir(lista, "ProdutoListaPreco", "Preço", "Lista de Produto");
		criaSeNaoExistir(lista, "ProdutoFormularioFaixadepreco", "Faixa de Preço", "Formulário de Produto");
		criaSeNaoExistir(lista, "ProdutoListaFaixadepreco", "Faixa de Preço", "Lista de Produto");
		criaSeNaoExistir(lista, "ProdutoFormularioImagem", "Imagem", "Formulário de Produto");
		criaSeNaoExistir(lista, "ProdutoListaImagem", "Imagem", "Lista de Produto");
		criaSeNaoExistir(lista, "ProdutoFormularioUrlanunciante", "URL do Seu Site", "Formulário de Produto");
		criaSeNaoExistir(lista, "ProdutoListaUrlanunciante", "URL do Seu Site", "Lista de Produto");
		criaSeNaoExistir(lista, "ProdutoFormularioDataIncl", "Data Inclusão", "Formulário de Produto");
		criaSeNaoExistir(lista, "ProdutoListaDataIncl", "Data Inclusão", "Lista de Produto");
		criaSeNaoExistir(lista, "ProdutoFormularioDataAlt", "Data Alteração", "Formulário de Produto");
		criaSeNaoExistir(lista, "ProdutoListaDataAlt", "Data Alteração", "Lista de Produto");
		criaSeNaoExistir(lista, "ProdutoFormularioCategoria", "Categoria", "Formulário de Produto");
		criaSeNaoExistir(lista, "ProdutoListaCategoria", "Categoria", "Lista de Produto");
		criaSeNaoExistir(lista, "ProdutoFormularioSubCategoria", "SubCategoria", "Formulário de Produto");
		criaSeNaoExistir(lista, "ProdutoListaSubCategoria", "SubCategoria", "Lista de Produto");
		criaSeNaoExistir(lista, "ProdutoFormularioAnunciante", "Anunciante", "Formulário de Produto");
		criaSeNaoExistir(lista, "ProdutoListaAnunciante", "Anunciante", "Lista de Produto");
		criaSeNaoExistir(lista, "ProdutoFormularioStatus", "Status", "Formulário de Produto");
		criaSeNaoExistir(lista, "ProdutoFormularioLinha", "Linha", "Formulário de Produto");
		criaSeNaoExistir(lista, "ProdutoListaStatus", "Status", "Lista de Produto");
		criaSeNaoExistir(lista, "ProdutoMenuPrincipal", "Produto", "Menu");
		criaSeNaoExistir(lista, "ProdutoMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "ProdutoMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "ProdutoFormularioBotaoInsere", "Insere", "Formulário de Produto");
		criaSeNaoExistir(lista, "ProdutoFormularioBotaoAltera", "Altera", "Formulário de Produto");
		criaSeNaoExistir(lista, "ProdutoFormularioBotaoImagemInsere", "Inserir", "Formulário de Produto");
		criaSeNaoExistir(lista, "ProdutoFormularioTituloDaPagina", "Cadastro de Produto", "Formulário de Produto");
		criaSeNaoExistir(lista, "ProdutoListaTituloDaPagina", "Lista de Produto", "Lista de Produto");
		criaSeNaoExistir(lista, "ProdutoListaAcoes", "Ações", "Lista de Produto");
		criaSeNaoExistir(lista, "ProdutoListaSemRegistro", "Não foram encontrados registros para Produto", "Lista de Produto");
		criaSeNaoExistir(lista, "ImagensFormularioTituloDaPagina", "Cadastro de Imagens", "Formulário de Imagens");
		criaSeNaoExistir(lista, "ImagemListaId", "ID", "Lista de Imagem");
		criaSeNaoExistir(lista, "ImagemListaNome", "Descrição", "Lista de Imagem");
		criaSeNaoExistir(lista, "ImagemListaProduto", "Id do Produto", "Lista de Imagem");
		criaSeNaoExistir(lista, "ImagemListaMiniatura", "Miniatura", "Lista de Imagem");
		criaSeNaoExistir(lista, "ImagemListaAcoes", "Ações", "Lista de Imagem");
		criaSeNaoExistir(lista, "StatusFormularioId", "Código", "Formulário de Status");
		criaSeNaoExistir(lista, "StatusListaId", "Código", "Lista de Status");
		criaSeNaoExistir(lista, "StatusFormularioNome", "Nome", "Formulário de Status");
		criaSeNaoExistir(lista, "StatusListaNome", "Nome", "Lista de Status");
		criaSeNaoExistir(lista, "StatusFormularioDataIncl", "Data Inclusão", "Formulário de Status");
		criaSeNaoExistir(lista, "StatusListaDataIncl", "Data Inclusão", "Lista de Status");
		criaSeNaoExistir(lista, "StatusFormularioDataAlt", "Data Alteração", "Formulário de Status");
		criaSeNaoExistir(lista, "StatusListaDataAlt", "Data Alteração", "Lista de Status");
		criaSeNaoExistir(lista, "StatusMenuPrincipal", "Status", "Menu");
		criaSeNaoExistir(lista, "StatusMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "StatusMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "StatusFormularioBotaoInsere", "Insere", "Formulário de Status");
		criaSeNaoExistir(lista, "StatusFormularioBotaoAltera", "Altera", "Formulário de Status");
		criaSeNaoExistir(lista, "StatusFormularioTituloDaPagina", "Cadastro de Status", "Formulário de Status");
		criaSeNaoExistir(lista, "StatusListaTituloDaPagina", "Lista de Status", "Lista de Status");
		criaSeNaoExistir(lista, "StatusListaAcoes", "Ações", "Lista de Status");
		criaSeNaoExistir(lista, "StatusListaSemRegistro", "Não foram encontrados registros para Status", "Lista de Status");
		criaSeNaoExistir(lista, "SubCategoriaFormularioId", "Código", "Formulário de SubCategoria");
		criaSeNaoExistir(lista, "SubCategoriaListaId", "Código", "Lista de SubCategoria");
		criaSeNaoExistir(lista, "SubCategoriaFormularioNome", "Nome", "Formulário de SubCategoria");
		criaSeNaoExistir(lista, "SubCategoriaListaNome", "Nome", "Lista de SubCategoria");
		criaSeNaoExistir(lista, "SubCategoriaFormularioDataIncl", "Data Inclusão", "Formulário de SubCategoria");
		criaSeNaoExistir(lista, "SubCategoriaListaDataIncl", "Data Inclusão", "Lista de SubCategoria");
		criaSeNaoExistir(lista, "SubCategoriaFormularioDataAlt", "Data Alteração", "Formulário de SubCategoria");
		criaSeNaoExistir(lista, "SubCategoriaListaDataAlt", "Data Alteração", "Lista de SubCategoria");
		criaSeNaoExistir(lista, "SubCategoriaFormularioCategoria", "Categoria", "Formulário de SubCategoria");
		criaSeNaoExistir(lista, "SubCategoriaListaCategoria", "Categoria", "Lista de SubCategoria");
		criaSeNaoExistir(lista, "SubCategoriaMenuPrincipal", "SubCategoria", "Menu");
		criaSeNaoExistir(lista, "SubCategoriaMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "SubCategoriaMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "SubCategoriaFormularioBotaoInsere", "Insere", "Formulário de SubCategoria");
		criaSeNaoExistir(lista, "SubCategoriaFormularioBotaoAltera", "Altera", "Formulário de SubCategoria");
		criaSeNaoExistir(lista, "SubCategoriaFormularioTituloDaPagina", "Cadastro de SubCategoria", "Formulário de SubCategoria");
		criaSeNaoExistir(lista, "SubCategoriaListaTituloDaPagina", "Lista de SubCategoria", "Lista de SubCategoria");
		criaSeNaoExistir(lista, "SubCategoriaListaAcoes", "Ações", "Lista de SubCategoria");
		criaSeNaoExistir(lista, "SubCategoriaListaSemRegistro", "Não foram encontrados registros para SubCategoria", "Lista de SubCategoria");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioId", "Código", "Formulário de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaId", "Código", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioRequiredOnly", "required Only", "Formulário de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaRequiredOnly", "required Only", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioMaxLengthOnly", "maxLength Only", "Formulário de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaMaxLengthOnly", "maxLength Only", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioSomenteNumerosOnly", "somente Numeros Only", "Formulário de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaSomenteNumerosOnly", "somente Numeros Only", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioRemoveAcentosOnly", "remove Acentos Only", "Formulário de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaRemoveAcentosOnly", "remove Acentos Only", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioTextDefault", "text Default", "Formulário de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaTextDefault", "text Default", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioTextSomenteNumero", "text Somente Numero", "Formulário de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaTextSomenteNumero", "text Somente Numero", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioTextRemoveAcentos", "text Remove Acentos", "Formulário de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaTextRemoveAcentos", "text Remove Acentos", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioTextEmail", "text Email", "Formulário de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaTextEmail", "text Email", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioCpfSomenteNumeros", "cpf Somente Numeros", "Formulário de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaCpfSomenteNumeros", "cpf Somente Numeros", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioCnpjSomenteNumeros", "cnpj Somente Numeros", "Formulário de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaCnpjSomenteNumeros", "cnpj Somente Numeros", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioTelDDD", "tel DDD", "Formulário de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaTelDDD", "tel DDD", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioTelNumero", "tel Numero", "Formulário de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaTelNumero", "tel Numero", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioCepSomenteNumeros", "cep Somente Numeros", "Formulário de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaCepSomenteNumeros", "cep Somente Numeros", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioDataTeste", "data teste", "Formulário de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaDataTeste", "data teste", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioDataTime", "data Time", "Formulário de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaDataTime", "data Time", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioDoubleTeste", "double Teste", "Formulário de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaDoubleTeste", "double Teste", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioIntegerTeste", "integer Teste", "Formulário de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaIntegerTeste", "integer Teste", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioLongTeste", "long Teste", "Formulário de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaLongTeste", "long Teste", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioTimeStampTeste", "time Stamp Teste", "Formulário de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaTimeStampTeste", "time Stamp Teste", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioTextAreaSimples", "text Area Simples", "Formulário de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaTextAreaSimples", "text Area Simples", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioTextAreaHtml", "text Area Html", "Formulário de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaTextAreaHtml", "text Area Html", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioBooleanTeste", "boolean Teste", "Formulário de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaBooleanTeste", "boolean Teste", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioBooleanTesteLigado", "Sim", "Formulário de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaBooleanTesteLigado", "Sim", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioBooleanTesteDesligado", "Não", "Formulário de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaBooleanTesteDesligado", "Não", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioArquivoTeste", "arquivo Teste", "Formulário de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaArquivoTeste", "arquivo Teste", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorMenuPrincipal", "MsbcValidator", "Menu");
		criaSeNaoExistir(lista, "MsbcValidatorMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "MsbcValidatorMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioBotaoInsere", "Insere", "Formulário de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioBotaoAltera", "Altera", "Formulário de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioTituloDaPagina", "Cadastro de MsbcValidator", "Formulário de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaTituloDaPagina", "Lista de MsbcValidator", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaAcoes", "Ações", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaSemRegistro", "Não foram encontrados registros para MsbcValidator", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "GrupoFormularioId", "Código", "Formulário de Grupo");
		criaSeNaoExistir(lista, "GrupoListaId", "Código", "Lista de Grupo");
		criaSeNaoExistir(lista, "GrupoFormularioNome", "Nome", "Formulário de Grupo");
		criaSeNaoExistir(lista, "GrupoListaNome", "Nome", "Lista de Grupo");
		criaSeNaoExistir(lista, "GrupoMenuPrincipal", "Grupo", "Menu");
		criaSeNaoExistir(lista, "GrupoMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "GrupoMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "GrupoFormularioBotaoInsere", "Insere", "Formulário de Grupo");
		criaSeNaoExistir(lista, "GrupoFormularioBotaoAltera", "Altera", "Formulário de Grupo");
		criaSeNaoExistir(lista, "GrupoFormularioTituloDaPagina", "Cadastro de Grupo", "Formulário de Grupo");
		criaSeNaoExistir(lista, "GrupoListaTituloDaPagina", "Lista de Grupo", "Lista de Grupo");
		criaSeNaoExistir(lista, "GrupoListaAcoes", "Ações", "Lista de Grupo");
		criaSeNaoExistir(lista, "GrupoListaSemRegistro", "Não foram encontrados registros para Grupo", "Lista de Grupo");
		criaSeNaoExistir(lista, "UsuarioFormularioId", "Código", "Formulário de Usuário");
		criaSeNaoExistir(lista, "UsuarioListaId", "Código", "Lista de Usuário");
		criaSeNaoExistir(lista, "UsuarioFormularioLogin", "Login", "Formulário de Usuário");
		criaSeNaoExistir(lista, "UsuarioListaLogin", "Login", "Lista de Usuário");
		criaSeNaoExistir(lista, "UsuarioFormularioNome", "Nome", "Formulário de Usuário");
		criaSeNaoExistir(lista, "UsuarioListaNome", "Nome", "Lista de Usuário");
		criaSeNaoExistir(lista, "UsuarioFormularioApelido", "Apelido", "Formulário de Usuário");
		criaSeNaoExistir(lista, "UsuarioListaApelido", "Apelido", "Lista de Usuário");
		criaSeNaoExistir(lista, "UsuarioFormularioEmail", "Email", "Formulário de Usuário");
		criaSeNaoExistir(lista, "UsuarioListaEmail", "Email", "Lista de Usuário");
		criaSeNaoExistir(lista, "UsuarioFormularioSenha", "Senha", "Formulário de Usuário");
		criaSeNaoExistir(lista, "UsuarioListaSenha", "Senha", "Lista de Usuário");
		criaSeNaoExistir(lista, "UsuarioFormularioGrupo", "Grupo", "Formulário de Usuário");
		criaSeNaoExistir(lista, "UsuarioListaGrupo", "Grupo", "Lista de Usuário");
		criaSeNaoExistir(lista, "UsuarioFormularioAnunciante", "Anunciante", "Formulário de Usuário");
		criaSeNaoExistir(lista, "UsuarioListaAnunciante", "Anunciante", "Lista de Usuário");
		criaSeNaoExistir(lista, "UsuarioMenuPrincipal", "Usuário", "Menu");
		criaSeNaoExistir(lista, "UsuarioMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "UsuarioMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "UsuarioFormularioBotaoInsere", "Insere", "Formulário de Usuário");
		criaSeNaoExistir(lista, "UsuarioFormularioBotaoAltera", "Altera", "Formulário de Usuário");
		criaSeNaoExistir(lista, "UsuarioFormularioTituloDaPagina", "Cadastro de Usuário", "Formulário de Usuário");
		criaSeNaoExistir(lista, "UsuarioListaTituloDaPagina", "Lista de Usuário", "Lista de Usuário");
		criaSeNaoExistir(lista, "UsuarioListaAcoes", "Ações", "Lista de Usuário");
		criaSeNaoExistir(lista, "UsuarioListaSemRegistro", "Não foram encontrados registros para Usuário", "Lista de Usuário");
		criaSeNaoExistir(lista, "HistoricoDoSistemaFormularioId", "Código", "Formulário de Histórico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaListaId", "Código", "Lista de Histórico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaFormularioLogin", "Login", "Formulário de Histórico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaListaLogin", "Login", "Lista de Histórico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaFormularioNome", "Nome", "Formulário de Histórico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaListaNome", "Nome", "Lista de Histórico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaFormularioDatahora", "Data / Hora", "Formulário de Histórico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaListaDatahora", "Data / Hora", "Lista de Histórico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaFormularioLocal", "Local", "Formulário de Histórico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaListaLocal", "Local", "Lista de Histórico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaFormularioAcao", "Ação", "Formulário de Histórico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaListaAcao", "Ação", "Lista de Histórico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaFormularioDados", "Dados", "Formulário de Histórico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaListaDados", "Dados", "Lista de Histórico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaMenuPrincipal", "Histórico do sistema", "Menu");
		criaSeNaoExistir(lista, "HistoricoDoSistemaMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "HistoricoDoSistemaMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "HistoricoDoSistemaFormularioBotaoInsere", "Insere", "Formulário de Histórico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaFormularioBotaoAltera", "Altera", "Formulário de Histórico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaFormularioTituloDaPagina", "Cadastro de Histórico do sistema", "Formulário de Histórico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaListaTituloDaPagina", "Lista de Histórico do sistema", "Lista de Histórico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaListaAcoes", "Ações", "Lista de Histórico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaListaSemRegistro", "Não foram encontrados registros para Histórico do sistema", "Lista de Histórico do sistema");
		criaSeNaoExistir(lista, "MensagensDoSistemaFormularioPropriedade", "Propriedade", "Formulário de Mensagens do sistema");
		criaSeNaoExistir(lista, "MensagensDoSistemaListaPropriedade", "Propriedade", "Lista de Mensagens do sistema");
		criaSeNaoExistir(lista, "MensagensDoSistemaFormularioValor", "Valor", "Formulário de Mensagens do sistema");
		criaSeNaoExistir(lista, "MensagensDoSistemaListaValor", "Valor", "Lista de Mensagens do sistema");
		criaSeNaoExistir(lista, "MensagensDoSistemaFormularioTela", "Tela", "Formulário de Mensagens do sistema");
		criaSeNaoExistir(lista, "MensagensDoSistemaListaTela", "Tela", "Lista de Mensagens do sistema");
		criaSeNaoExistir(lista, "MensagensDoSistemaMenuPrincipal", "Mensagens do sistema", "Menu");
		criaSeNaoExistir(lista, "MensagensDoSistemaMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "MensagensDoSistemaMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "MensagensDoSistemaFormularioBotaoInsere", "Insere", "Formulário de Mensagens do sistema");
		criaSeNaoExistir(lista, "MensagensDoSistemaFormularioBotaoAltera", "Altera", "Formulário de Mensagens do sistema");
		criaSeNaoExistir(lista, "MensagensDoSistemaFormularioTituloDaPagina", "Cadastro de Mensagens do sistema", "Formulário de Mensagens do sistema");
		criaSeNaoExistir(lista, "MensagensDoSistemaListaTituloDaPagina", "Lista de Mensagens do sistema", "Lista de Mensagens do sistema");
		criaSeNaoExistir(lista, "MensagensDoSistemaListaAcoes", "Ações", "Lista de Mensagens do sistema");
		criaSeNaoExistir(lista, "MensagensDoSistemaListaSemRegistro", "Não foram encontrados registros para Mensagens do sistema", "Lista de Mensagens do sistema");
		criaSeNaoExistir(lista, "ConfiguracoesDoSistemaFormularioPropriedade", "Propriedade", "Formulário de Configurações do sistema");
		criaSeNaoExistir(lista, "ConfiguracoesDoSistemaListaPropriedade", "Propriedade", "Lista de Configurações do sistema");
		criaSeNaoExistir(lista, "ConfiguracoesDoSistemaFormularioValor", "Valor", "Formulário de Configurações do sistema");
		criaSeNaoExistir(lista, "ConfiguracoesDoSistemaListaValor", "Valor", "Lista de Configurações do sistema");
		criaSeNaoExistir(lista, "ConfiguracoesDoSistemaMenuPrincipal", "Configurações do sistema", "Menu");
		criaSeNaoExistir(lista, "ConfiguracoesDoSistemaMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "ConfiguracoesDoSistemaMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "ConfiguracoesDoSistemaFormularioBotaoInsere", "Insere", "Formulário de Configurações do sistema");
		criaSeNaoExistir(lista, "ConfiguracoesDoSistemaFormularioBotaoAltera", "Altera", "Formulário de Configurações do sistema");
		criaSeNaoExistir(lista, "ConfiguracoesDoSistemaFormularioTituloDaPagina", "Cadastro de Configurações do sistema", "Formulário de Configurações do sistema");
		criaSeNaoExistir(lista, "ConfiguracoesDoSistemaListaTituloDaPagina", "Lista de Configurações do sistema", "Lista de Configurações do sistema");
		criaSeNaoExistir(lista, "ConfiguracoesDoSistemaListaAcoes", "Ações", "Lista de Configurações do sistema");
		criaSeNaoExistir(lista, "ConfiguracoesDoSistemaListaSemRegistro", "Não foram encontrados registros para Configurações do sistema", "Lista de Configurações do sistema");
		criaSeNaoExistir(lista, "ModalExclusaoTitulo", "Confirmar exclusão", "Modal de exclusão");
		criaSeNaoExistir(lista, "ModalExclusaoMensagemInicio", "Deseja mesmo excluir o ", "Modal de exclusão");
		criaSeNaoExistir(lista, "ModalExclusaoMensagemFim", "? Essa ação não poderá ser revertida!", "Modal de exclusão");
		criaSeNaoExistir(lista, "ModalExclusaoBotaoSim", "Sim, excluir!", "Modal de exclusão");
		criaSeNaoExistir(lista, "ModalExclusaoBotaoNao", "Não, Fechar!", "Modal de exclusão");
		criaSeNaoExistir(lista, "MensagemAdicionadoComSucesso", "Inserido com sucesso!", "Todas as telas de inserção");
		criaSeNaoExistir(lista, "ModalRedefinirSenhaTitulo", "Confirmar redefinição de senha", "Modal de redefinição de senha");
		criaSeNaoExistir(lista, "ModalRedefinirSenhaMensagemInicio", "Deseja mesmo redefinir a senha de ", "Modal de redefinição de senha");
		criaSeNaoExistir(lista, "ModalRedefinirSenhaMensagemFim", "? A nova senha será 123456!", "Modal de redefinição de senha");
		criaSeNaoExistir(lista, "ModalRedefinirSenhaBotaoSim", "Sim, redefinir!", "Modal de redefinição de senha");
		criaSeNaoExistir(lista, "ModalRedefinirSenhaBotaoNao", "Não, Fechar!", "Modal de redefinição de senha");
		criaSeNaoExistir(lista, "MensagemAlteradoComSucesso", "Alterado com sucesso!", "Todas as telas de alteração");
		criaSeNaoExistir(lista, "MensagemExcluidoComSucesso", "Excluído com sucesso!", "Todas as telas de exclusão");
		criaSeNaoExistir(lista, "MensagemErroIdNulo", "O id não pode ser nulo!", "Rest Controller");
		criaSeNaoExistir(lista, "MensagemErroAlteracaoNaoLocalizadaNoBanco", "Não foi possivel realizar a alteração, não localizado no banco!", "Rest Controller");
		criaSeNaoExistir(lista, "MensagemErroExclusaoNaoLocalizadaNoBanco", "Não foi possivel realizar a exclusão, não localizado no banco!", "Rest Controller");
		criaSeNaoExistir(lista, "SenhaAtualNaoConfere", "Senha atual não confere!", "Tela de alteração de senha");
		criaSeNaoExistir(lista, "ValidacaoErroNovaSenhaObrigatoria", "O campo Nova senha deve ter de 6 a 30 caracteres!", "Tela de alteração de senha");
		criaSeNaoExistir(lista, "ValidacaoErroRepeteSenhaObrigatoria", "O campo Repetir nova senha deve ter de 6 a 30 caracteres!", "Tela de alteração de senha");
		criaSeNaoExistir(lista, "ValidacaoErroSenhasDiferentes", "O campo Nova senha e Repetir nova senha são diferentes!", "Tela de alteração de senha");
		criaSeNaoExistir(lista, "AlterarSenhaTitulo", "Alterar Senha", "Tela de alteração de senha");
		criaSeNaoExistir(lista, "AlterarSenhaSenhaAtual", "Senha atual", "Tela de alteração de senha");
		criaSeNaoExistir(lista, "AlterarSenhaNovaSenha", "Nova senha", "Tela de alteração de senha");
		criaSeNaoExistir(lista, "AlterarSenhaRepetirNovaSenha", "Repetir nova senha", "Tela de alteração de senha");
		criaSeNaoExistir(lista, "AlterarSenhaBotao", "Altera", "Tela de alteração de senha");
		criaSeNaoExistir(lista, "AlterarSenhaUsuariosDaRede", "Usuários da rede não podem alterar a senha!", "Tela de alteração de senha");
		criaSeNaoExistir(lista, "MenuBotaoSair", "Sair", "Menu");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioAnuncianteRazaosocial", "O campo Razão Social deve ser preenchido!", "Formulário de Anunciante");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioAnuncianteFantasia", "O campo Nome Fantasia deve ser preenchido!", "Formulário de Anunciante");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioAnuncianteEmail", "O campo E-Mail deve ser preenchido!", "Formulário de Anunciante");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioAnuncianteDdd", "O campo DDD deve ser preenchido!", "Formulário de Anunciante");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioAnuncianteTelefone", "O campo Telefone deve ser preenchido!", "Formulário de Anunciante");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioAnuncianteDataIncl", "O campo Data Inclusão deve ser preenchido!", "Formulário de Anunciante");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioAnuncianteDataAlt", "O campo Data Alteração deve ser preenchido!", "Formulário de Anunciante");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioAnuncianteTipoNome", "O campo Nome deve ser preenchido!", "Formulário de AnuncianteTipo");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioAnuncianteTipoDataIncl", "O campo Data Inclusão deve ser preenchido!", "Formulário de AnuncianteTipo");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioAnuncianteTipoDataAlt", "O campo Data Alteração deve ser preenchido!", "Formulário de AnuncianteTipo");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioCategoriaNome", "O campo Nome deve ser preenchido!", "Formulário de Categoria");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioCategoriaDataIncl", "O campo Data Inclusão deve ser preenchido!", "Formulário de Categoria");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioCategoriaDataAlt", "O campo Data Alteração deve ser preenchido!", "Formulário de Categoria");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioAtividadeNome", "O campo Nome deve ser preenchido!", "Formulário de Atividade");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioAtividadeDataIncl", "O campo Data Inclusão deve ser preenchido!", "Formulário de Atividade");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioAtividadeDataAlt", "O campo Data Alteração deve ser preenchido!", "Formulário de Atividade");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioLinhaNome", "O campo Nome deve ser preenchido!", "Formulário de Linha");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioLinhaDataIncl", "O campo Data Inclusão deve ser preenchido!", "Formulário de Linha");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioLinhaDataAlt", "O campo Data Alteração deve ser preenchido!", "Formulário de Linha");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioCampanhaNome", "O campo Nome deve ser preenchido!", "Formulário de Campanha");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioCampanhaOrdem", "O campo Ordem deve ser preenchido!", "Formulário de Campanha");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioModeloDescricao", "O campo Descrição deve ser preenchido!", "Formulário de Modelo");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioModeloTitulo", "O campo Título do Certificado deve ser preenchido!", "Formulário de Modelo");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioModeloCorTitulo", "O campo Cor do Título deve ser preenchido!", "Formulário de Modelo");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioModeloAtivo", "O campo Ativo|Sim|Não deve ser preenchido!", "Formulário de Modelo");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioOrcamentoQuantidade", "O campo Quantidade deve ser preenchido!", "Formulário de Orcamento");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioOrcamentoDataIncl", "O campo Data Inclusão deve ser preenchido!", "Formulário de Orcamento");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioOrcamentoDataAlt", "O campo Data Alteração deve ser preenchido!", "Formulário de Orcamento");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioOrcamentoNome", "O campo Nome deve ser preenchido!", "Formulário de Orcamento");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioOrcamentoEmail", "O campo Email deve ser preenchido!", "Formulário de Orcamento");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioProdutoCodigo", "O campo Código deve ser preenchido!", "Formulário de Produto");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioProdutoTitulo", "O campo Título deve ser preenchido!", "Formulário de Produto");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioProdutoBrevedescricao", "O campo Breve Descricao deve ser preenchido!", "Formulário de Produto");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioProdutoDescricaocompleta", "O campo Descricao Completa deve ser preenchido!", "Formulário de Produto");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioProdutoTag", "O campo Tags deve ser preenchido!", "Formulário de Produto");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioProdutoPreco", "O campo Preço deve ser preenchido!", "Formulário de Produto");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioProdutoFaixadepreco", "O campo Faixa de Preço deve ser preenchido!", "Formulário de Produto");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioProdutoImagem", "O campo Imagem deve ser preenchido!", "Formulário de Produto");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioProdutoUrlanunciante", "O campo URL do Seu Site deve ser preenchido!", "Formulário de Produto");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioProdutoDataIncl", "O campo Data Inclusão deve ser preenchido!", "Formulário de Produto");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioProdutoDataAlt", "O campo Data Alteração deve ser preenchido!", "Formulário de Produto");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioStatusNome", "O campo Nome deve ser preenchido!", "Formulário de Status");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioStatusDataIncl", "O campo Data Inclusão deve ser preenchido!", "Formulário de Status");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioStatusDataAlt", "O campo Data Alteração deve ser preenchido!", "Formulário de Status");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioSubCategoriaNome", "O campo Nome deve ser preenchido!", "Formulário de SubCategoria");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioSubCategoriaDataIncl", "O campo Data Inclusão deve ser preenchido!", "Formulário de SubCategoria");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioSubCategoriaDataAlt", "O campo Data Alteração deve ser preenchido!", "Formulário de SubCategoria");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorRequiredOnly", "O campo required Only deve ser preenchido!", "Formulário de MsbcValidator");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorMaxLengthOnly", "O campo maxLength Only deve ser preenchido!", "Formulário de MsbcValidator");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorSomenteNumerosOnly", "O campo somente Numeros Only deve ser preenchido!", "Formulário de MsbcValidator");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorRemoveAcentosOnly", "O campo remove Acentos Only deve ser preenchido!", "Formulário de MsbcValidator");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorTextDefault", "O campo text Default deve ser preenchido!", "Formulário de MsbcValidator");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorTextSomenteNumero", "O campo text Somente Numero deve ser preenchido!", "Formulário de MsbcValidator");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorTextRemoveAcentos", "O campo text Remove Acentos deve ser preenchido!", "Formulário de MsbcValidator");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorTextEmail", "O campo text Email deve ser preenchido!", "Formulário de MsbcValidator");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorCpfSomenteNumeros", "O campo cpf Somente Numeros deve ser preenchido!", "Formulário de MsbcValidator");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorCnpjSomenteNumeros", "O campo cnpj Somente Numeros deve ser preenchido!", "Formulário de MsbcValidator");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorTelDDD", "O campo tel DDD deve ser preenchido!", "Formulário de MsbcValidator");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorTelNumero", "O campo tel Numero deve ser preenchido!", "Formulário de MsbcValidator");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorCepSomenteNumeros", "O campo cep Somente Numeros deve ser preenchido!", "Formulário de MsbcValidator");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorDataTeste", "O campo data teste deve ser preenchido!", "Formulário de MsbcValidator");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorDataTime", "O campo data Time deve ser preenchido!", "Formulário de MsbcValidator");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorDoubleTeste", "O campo double Teste deve ser preenchido!", "Formulário de MsbcValidator");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorIntegerTeste", "O campo integer Teste deve ser preenchido!", "Formulário de MsbcValidator");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorLongTeste", "O campo long Teste deve ser preenchido!", "Formulário de MsbcValidator");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorTimeStampTeste", "O campo time Stamp Teste deve ser preenchido!", "Formulário de MsbcValidator");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorTextAreaSimples", "O campo text Area Simples deve ser preenchido!", "Formulário de MsbcValidator");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorTextAreaHtml", "O campo text Area Html deve ser preenchido!", "Formulário de MsbcValidator");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorBooleanTeste", "O campo boolean Teste|Sim|Não deve ser preenchido!", "Formulário de MsbcValidator");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorArquivoTeste", "O campo arquivo Teste deve ser preenchido!", "Formulário de MsbcValidator");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioGrupoNome", "O campo Nome deve ser preenchido!", "Formulário de Grupo");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioUsuarioLogin", "O campo Login deve ser preenchido!", "Formulário de Usuario");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioUsuarioNome", "O campo Nome deve ser preenchido!", "Formulário de Usuario");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioUsuarioApelido", "O campo Apelido deve ser preenchido!", "Formulário de Usuario");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioUsuarioEmail", "O campo Email deve ser preenchido!", "Formulário de Usuario");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioUsuarioSenha", "O campo Senha deve ser preenchido!", "Formulário de Usuario");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioHistoricoDoSistemaLogin", "O campo Login deve ser preenchido!", "Formulário de HistoricoDoSistema");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioHistoricoDoSistemaNome", "O campo Nome deve ser preenchido!", "Formulário de HistoricoDoSistema");
		criaSeNaoExistir(lista, "ValidacaoErroDataObrigatoriaHistoricoDoSistemaDatahora", "O campo Data / Hora deve ser preenchido!", "Formulário de HistoricoDoSistema");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioHistoricoDoSistemaLocal", "O campo Local deve ser preenchido!", "Formulário de HistoricoDoSistema");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioHistoricoDoSistemaAcao", "O campo Ação deve ser preenchido!", "Formulário de HistoricoDoSistema");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioHistoricoDoSistemaDados", "O campo Dados deve ser preenchido!", "Formulário de HistoricoDoSistema");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMensagensDoSistemaPropriedade", "O campo Propriedade deve ser preenchido!", "Formulário de MensagensDoSistema");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMensagensDoSistemaValor", "O campo Valor deve ser preenchido!", "Formulário de MensagensDoSistema");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMensagensDoSistemaTela", "O campo Tela deve ser preenchido!", "Formulário de MensagensDoSistema");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioConfiguracoesDoSistemaPropriedade", "O campo Propriedade deve ser preenchido!", "Formulário de ConfiguracoesDoSistema");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioConfiguracoesDoSistemaValor", "O campo Valor deve ser preenchido!", "Formulário de ConfiguracoesDoSistema");
		criaSeNaoExistir(lista, "RegistroDuplicado", "Já existe um cadastro com esse nome!", "Formulário de Cadastro");
	}

	private void criaSeNaoExistir(List<MensagensDoSistemaModel> lista, String propriedade, String valor, String tela) {
		boolean existe = false;
		for (MensagensDoSistemaModel prop : lista) {
			if (prop.getPropriedade().equals(propriedade)) {
				existe = true;
				break;
			}
		}
		if (!existe) {
			MensagensDoSistemaModel prop = new MensagensDoSistemaModel();
			prop.setPropriedade(propriedade);
			prop.setValor(valor);
			prop.setTela(tela);
			manager.persist(prop);
		}
	}

}