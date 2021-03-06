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
		criaSeNaoExistir(lista, "ErroLoginAutenticacao", "Usu??rio ou senha inv??lida!", "Home");
		criaSeNaoExistir(lista, "AnuncianteFormularioId", "C??digo", "Formul??rio de Anunciante");
		criaSeNaoExistir(lista, "AnuncianteListaId", "C??digo", "Lista de Anunciante");
		criaSeNaoExistir(lista, "AnuncianteFormularioRazaosocial", "Raz??o Social", "Formul??rio de Anunciante");
		criaSeNaoExistir(lista, "AnuncianteListaRazaosocial", "Raz??o Social", "Lista de Anunciante");
		criaSeNaoExistir(lista, "AnuncianteFormularioFantasia", "Nome Fantasia", "Formul??rio de Anunciante");
		criaSeNaoExistir(lista, "AnuncianteListaFantasia", "Nome Fantasia", "Lista de Anunciante");
		criaSeNaoExistir(lista, "AnuncianteFormularioEmail", "E-Mail", "Formul??rio de Anunciante");
		criaSeNaoExistir(lista, "AnuncianteListaEmail", "E-Mail", "Lista de Anunciante");
		criaSeNaoExistir(lista, "AnuncianteFormularioDdd", "DDD", "Formul??rio de Anunciante");
		criaSeNaoExistir(lista, "AnuncianteListaDdd", "DDD", "Lista de Anunciante");
		criaSeNaoExistir(lista, "AnuncianteFormularioTelefone", "Telefone", "Formul??rio de Anunciante");
		criaSeNaoExistir(lista, "AnuncianteListaTelefone", "Telefone", "Lista de Anunciante");
		criaSeNaoExistir(lista, "AnuncianteFormularioDataIncl", "Data Inclus??o", "Formul??rio de Anunciante");
		criaSeNaoExistir(lista, "AnuncianteListaDataIncl", "Data Inclus??o", "Lista de Anunciante");
		criaSeNaoExistir(lista, "AnuncianteFormularioDataAlt", "Data Altera????o", "Formul??rio de Anunciante");
		criaSeNaoExistir(lista, "AnuncianteListaDataAlt", "Data Altera????o", "Lista de Anunciante");
		criaSeNaoExistir(lista, "AnuncianteFormularioAnuncianteTipo", "AnuncianteTipo", "Formul??rio de Anunciante");
		criaSeNaoExistir(lista, "AnuncianteListaAnuncianteTipo", "AnuncianteTipo", "Lista de Anunciante");
		criaSeNaoExistir(lista, "AnuncianteMenuPrincipal", "Anunciante", "Menu");
		criaSeNaoExistir(lista, "AnuncianteMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "AnuncianteMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "AnuncianteFormularioBotaoInsere", "Insere", "Formul??rio de Anunciante");
		criaSeNaoExistir(lista, "AnuncianteFormularioBotaoAltera", "Altera", "Formul??rio de Anunciante");
		criaSeNaoExistir(lista, "AnuncianteFormularioTituloDaPagina", "Cadastro de Anunciante", "Formul??rio de Anunciante");
		criaSeNaoExistir(lista, "AnuncianteListaTituloDaPagina", "Lista de Anunciante", "Lista de Anunciante");
		criaSeNaoExistir(lista, "AnuncianteListaAcoes", "A????es", "Lista de Anunciante");
		criaSeNaoExistir(lista, "AnuncianteListaSemRegistro", "N??o foram encontrados registros para Anunciante", "Lista de Anunciante");
		criaSeNaoExistir(lista, "AnuncianteTipoFormularioId", "C??digo", "Formul??rio de AnuncianteTipo");
		criaSeNaoExistir(lista, "AnuncianteTipoListaId", "C??digo", "Lista de AnuncianteTipo");
		criaSeNaoExistir(lista, "AnuncianteTipoFormularioNome", "Nome", "Formul??rio de AnuncianteTipo");
		criaSeNaoExistir(lista, "AnuncianteTipoListaNome", "Nome", "Lista de AnuncianteTipo");
		criaSeNaoExistir(lista, "AnuncianteTipoFormularioDataIncl", "Data Inclus??o", "Formul??rio de AnuncianteTipo");
		criaSeNaoExistir(lista, "AnuncianteTipoListaDataIncl", "Data Inclus??o", "Lista de AnuncianteTipo");
		criaSeNaoExistir(lista, "AnuncianteTipoFormularioDataAlt", "Data Altera????o", "Formul??rio de AnuncianteTipo");
		criaSeNaoExistir(lista, "AnuncianteTipoListaDataAlt", "Data Altera????o", "Lista de AnuncianteTipo");
		criaSeNaoExistir(lista, "AnuncianteTipoMenuPrincipal", "AnuncianteTipo", "Menu");
		criaSeNaoExistir(lista, "AnuncianteTipoMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "AnuncianteTipoMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "AnuncianteTipoFormularioBotaoInsere", "Insere", "Formul??rio de AnuncianteTipo");
		criaSeNaoExistir(lista, "AnuncianteTipoFormularioBotaoAltera", "Altera", "Formul??rio de AnuncianteTipo");
		criaSeNaoExistir(lista, "AnuncianteTipoFormularioTituloDaPagina", "Cadastro de AnuncianteTipo", "Formul??rio de AnuncianteTipo");
		criaSeNaoExistir(lista, "AnuncianteTipoListaTituloDaPagina", "Lista de AnuncianteTipo", "Lista de AnuncianteTipo");
		criaSeNaoExistir(lista, "AnuncianteTipoListaAcoes", "A????es", "Lista de AnuncianteTipo");
		criaSeNaoExistir(lista, "AnuncianteTipoListaSemRegistro", "N??o foram encontrados registros para AnuncianteTipo", "Lista de AnuncianteTipo");
		criaSeNaoExistir(lista, "CategoriaFormularioId", "C??digo", "Formul??rio de Categoria");
		criaSeNaoExistir(lista, "CategoriaListaId", "C??digo", "Lista de Categoria");
		criaSeNaoExistir(lista, "CategoriaFormularioNome", "Nome", "Formul??rio de Categoria");
		criaSeNaoExistir(lista, "CategoriaFormularioImagem", "Imagem", "Formul??rio de Categoria");
		criaSeNaoExistir(lista, "CategoriaListaNome", "Nome", "Lista de Categoria");
		criaSeNaoExistir(lista, "CategoriaListaImagem", "Imagem", "Lista de Categoria");
		criaSeNaoExistir(lista, "CategoriaFormularioDataIncl", "Data Inclus??o", "Formul??rio de Categoria");
		criaSeNaoExistir(lista, "CategoriaListaDataIncl", "Data Inclus??o", "Lista de Categoria");
		criaSeNaoExistir(lista, "CategoriaFormularioDataAlt", "Data Altera????o", "Formul??rio de Categoria");
		criaSeNaoExistir(lista, "CategoriaListaDataAlt", "Data Altera????o", "Lista de Categoria");
		criaSeNaoExistir(lista, "CategoriaMenuPrincipal", "Categoria", "Menu");
		criaSeNaoExistir(lista, "CategoriaMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "CategoriaMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "CategoriaFormularioBotaoInsere", "Insere", "Formul??rio de Categoria");
		criaSeNaoExistir(lista, "CategoriaFormularioBotaoAltera", "Altera", "Formul??rio de Categoria");
		criaSeNaoExistir(lista, "CategoriaFormularioTituloDaPagina", "Cadastro de Categoria", "Formul??rio de Categoria");
		criaSeNaoExistir(lista, "CategoriaListaTituloDaPagina", "Lista de Categoria", "Lista de Categoria");
		criaSeNaoExistir(lista, "CategoriaListaAcoes", "A????es", "Lista de Categoria");
		criaSeNaoExistir(lista, "CategoriaListaSemRegistro", "N??o foram encontrados registros para Categoria", "Lista de Categoria");
		criaSeNaoExistir(lista, "FaixaDePrecoFormularioId", "C??digo", "Formul??rio de Faixa de Pre??o");
		criaSeNaoExistir(lista, "FaixaDePrecoListaId", "C??digo", "Lista de Faixa de Pre??o");
		criaSeNaoExistir(lista, "FaixaDePrecoFormularioNome", "Descri????o", "Formul??rio de Faixa de Pre??o");
		criaSeNaoExistir(lista, "FaixaDePrecoFormularioPrecoDe", "Pre??o De", "Formul??rio de Faixa de Pre??o");
		criaSeNaoExistir(lista, "FaixaDePrecoFormularioPrecoAte", "Pre??o At??", "Formul??rio de Faixa de Pre??o");
		criaSeNaoExistir(lista, "FaixaDePrecoListaNome", "Descri????o", "Lista de Faixa de Pre??o");
		criaSeNaoExistir(lista, "FaixaDePrecoListaPrecoDe", "Pre??o De", "Lista de Faixa de Pre??o");
		criaSeNaoExistir(lista, "FaixaDePrecoListaPrecoAte", "Pre??o At??", "Lista de Faixa de Pre??o");
		criaSeNaoExistir(lista, "FaixaDePrecoFormularioDataIncl", "Data Inclus??o", "Formul??rio de Faixa de Pre??o");
		criaSeNaoExistir(lista, "FaixaDePrecoListaDataIncl", "Data Inclus??o", "Lista de Faixa de Pre??o");
		criaSeNaoExistir(lista, "FaixaDePrecoFormularioDataAlt", "Data Altera????o", "Formul??rio de Faixa de Pre??o");
		criaSeNaoExistir(lista, "FaixaDePrecoListaDataAlt", "Data Altera????o", "Lista de Faixa de Pre??o");
		criaSeNaoExistir(lista, "FaixaDePrecoMenuPrincipal", "Faixa de Pre??o", "Menu");
		criaSeNaoExistir(lista, "FaixaDePrecoMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "FaixaDePrecoMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "FaixaDePrecoFormularioBotaoInsere", "Insere", "Formul??rio de Faixa de Pre??o");
		criaSeNaoExistir(lista, "FaixaDePrecoFormularioBotaoAltera", "Altera", "Formul??rio de Faixa de Pre??o");
		criaSeNaoExistir(lista, "FaixaDePrecoFormularioTituloDaPagina", "Cadastro de Faixa de Pre??o", "Formul??rio de Faixa de Pre??o");
		criaSeNaoExistir(lista, "FaixaDePrecoListaTituloDaPagina", "Lista de Faixa de Pre??o", "Lista de Faixa de Pre??o");
		criaSeNaoExistir(lista, "FaixaDePrecoListaAcoes", "A????es", "Lista de Faixa de Pre??o");
		criaSeNaoExistir(lista, "FaixaDePrecoListaSemRegistro", "N??o foram encontrados registros para Faixa de Pre??o", "Lista de Faixa de Pre??o");
		criaSeNaoExistir(lista, "AtividadeFormularioId", "C??digo", "Formul??rio de Atividade");
		criaSeNaoExistir(lista, "AtividadeListaId", "C??digo", "Lista de Atividade");
		criaSeNaoExistir(lista, "AtividadeFormularioNome", "Nome", "Formul??rio de Atividade");
		criaSeNaoExistir(lista, "AtividadeListaNome", "Nome", "Lista de Atividade");
		criaSeNaoExistir(lista, "AtividadeFormularioProduto", "Produto", "Formul??rio de Atividade");
		criaSeNaoExistir(lista, "AtividadeListaProduto", "Produto", "Lista de Atividade");
		criaSeNaoExistir(lista, "AtividadeFormularioDataIncl", "Data Inclus??o", "Formul??rio de Atividade");
		criaSeNaoExistir(lista, "AtividadeListaDataIncl", "Data Inclus??o", "Lista de Atividade");
		criaSeNaoExistir(lista, "AtividadeFormularioDataAlt", "Data Altera????o", "Formul??rio de Atividade");
		criaSeNaoExistir(lista, "AtividadeListaDataAlt", "Data Altera????o", "Lista de Atividade");
		criaSeNaoExistir(lista, "AtividadeMenuPrincipal", "Atividade", "Menu");
		criaSeNaoExistir(lista, "AtividadeMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "AtividadeMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "AtividadeFormularioBotaoInsere", "Insere", "Formul??rio de Atividade");
		criaSeNaoExistir(lista, "AtividadeFormularioBotaoAltera", "Altera", "Formul??rio de Atividade");
		criaSeNaoExistir(lista, "AtividadeFormularioTituloDaPagina", "Cadastro de Atividade", "Formul??rio de Atividade");
		criaSeNaoExistir(lista, "AtividadeListaTituloDaPagina", "Lista de Atividade", "Lista de Atividade");
		criaSeNaoExistir(lista, "AtividadeListaAcoes", "A????es", "Lista de Atividade");
		criaSeNaoExistir(lista, "AtividadeListaSemRegistro", "N??o foram encontrados registros para Atividade", "Lista de Atividade");
		
		criaSeNaoExistir(lista, "LinhaFormularioId", "C??digo", "Formul??rio de Linha");
		criaSeNaoExistir(lista, "LinhaListaId", "C??digo", "Lista de Linha");
		criaSeNaoExistir(lista, "LinhaFormularioNome", "Nome", "Formul??rio de Linha");
		criaSeNaoExistir(lista, "LinhaFormularioCampanha", "Campanha", "Formul??rio de Linha");
		criaSeNaoExistir(lista, "LinhaListaNome", "Nome", "Lista de Linha");
		criaSeNaoExistir(lista, "LinhaListaCampanha", "Campanha", "Lista de Linha");
		criaSeNaoExistir(lista, "LinhaFormularioProduto", "Produto", "Formul??rio de Linha");
		criaSeNaoExistir(lista, "LinhaListaProduto", "Produto", "Lista de Linha");
		criaSeNaoExistir(lista, "LinhaFormularioDataIncl", "Data Inclus??o", "Formul??rio de Linha");
		criaSeNaoExistir(lista, "LinhaListaDataIncl", "Data Inclus??o", "Lista de Linha");
		criaSeNaoExistir(lista, "LinhaFormularioDataAlt", "Data Altera????o", "Formul??rio de Linha");
		criaSeNaoExistir(lista, "LinhaListaDataAlt", "Data Altera????o", "Lista de Linha");
		criaSeNaoExistir(lista, "LinhaMenuPrincipal", "Linha", "Menu");
		criaSeNaoExistir(lista, "LinhaMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "LinhaMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "LinhaFormularioBotaoInsere", "Insere", "Formul??rio de Linha");
		criaSeNaoExistir(lista, "LinhaFormularioBotaoAltera", "Altera", "Formul??rio de Linha");
		criaSeNaoExistir(lista, "LinhaFormularioTituloDaPagina", "Cadastro de Linha", "Formul??rio de Linha");
		criaSeNaoExistir(lista, "LinhaListaTituloDaPagina", "Lista de Linha", "Lista de Linha");
		criaSeNaoExistir(lista, "LinhaListaAcoes", "A????es", "Lista de Linha");
		criaSeNaoExistir(lista, "LinhaListaSemRegistro", "N??o foram encontrados registros para Linha", "Lista de Linha");
		
		criaSeNaoExistir(lista, "CampanhaFormularioId", "C??digo", "Formul??rio de Campanha");
		criaSeNaoExistir(lista, "CampanhaListaId", "C??digo", "Lista de Campanha");
		criaSeNaoExistir(lista, "CampanhaFormularioNome", "Nome", "Formul??rio de Campanha");
		criaSeNaoExistir(lista, "CampanhaFormularioOrdem", "Ordem", "Formul??rio de Campanha");
		criaSeNaoExistir(lista, "CampanhaListaNome", "Nome", "Lista de Campanha");
		criaSeNaoExistir(lista, "CampanhaListaOrdem", "Ordem", "Lista de Campanha");
		criaSeNaoExistir(lista, "CampanhaFormularioProduto", "Produto", "Formul??rio de Campanha");
		criaSeNaoExistir(lista, "CampanhaListaProduto", "Produto", "Lista de Campanha");
		criaSeNaoExistir(lista, "CampanhaFormularioDataIncl", "Data Inclus??o", "Formul??rio de Campanha");
		criaSeNaoExistir(lista, "CampanhaListaDataIncl", "Data Inclus??o", "Lista de Campanha");
		criaSeNaoExistir(lista, "CampanhaFormularioDataAlt", "Data Altera????o", "Formul??rio de Campanha");
		criaSeNaoExistir(lista, "CampanhaListaDataAlt", "Data Altera????o", "Lista de Campanha");
		criaSeNaoExistir(lista, "CampanhaMenuPrincipal", "Campanha", "Menu");
		criaSeNaoExistir(lista, "CampanhaMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "CampanhaMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "CampanhaFormularioBotaoInsere", "Insere", "Formul??rio de Campanha");
		criaSeNaoExistir(lista, "CampanhaFormularioBotaoAltera", "Altera", "Formul??rio de Campanha");
		criaSeNaoExistir(lista, "CampanhaFormularioTituloDaPagina", "Cadastro de Campanha", "Formul??rio de Campanha");
		criaSeNaoExistir(lista, "CampanhaListaTituloDaPagina", "Lista de Campanha", "Lista de Campanha");
		criaSeNaoExistir(lista, "CampanhaListaAcoes", "A????es", "Lista de Campanha");
		criaSeNaoExistir(lista, "CampanhaListaSemRegistro", "N??o foram encontrados registros para Campanha", "Lista de Campanha");		
		
		criaSeNaoExistir(lista, "ModeloFormularioId", "C??digo", "Formul??rio de Modelo");
		criaSeNaoExistir(lista, "ModeloListaId", "C??digo", "Lista de Modelo");
		criaSeNaoExistir(lista, "ModeloFormularioDescricao", "Descri????o", "Formul??rio de Modelo");
		criaSeNaoExistir(lista, "ModeloListaDescricao", "Descri????o", "Lista de Modelo");
		criaSeNaoExistir(lista, "ModeloFormularioTitulo", "T??tulo do Certificado", "Formul??rio de Modelo");
		criaSeNaoExistir(lista, "ModeloListaTitulo", "T??tulo do Certificado", "Lista de Modelo");
		criaSeNaoExistir(lista, "ModeloFormularioCorTitulo", "Cor do T??tulo", "Formul??rio de Modelo");
		criaSeNaoExistir(lista, "ModeloListaCorTitulo", "Cor do T??tulo", "Lista de Modelo");
		criaSeNaoExistir(lista, "ModeloFormularioImagemUnica", "Imagem", "Formul??rio de Modelo");
		criaSeNaoExistir(lista, "ModeloListaImagemUnica", "Imagem", "Lista de Modelo");
		criaSeNaoExistir(lista, "ModeloFormularioImagemCabecalho", "Imagem do Cabecalho", "Formul??rio de Modelo");
		criaSeNaoExistir(lista, "ModeloListaImagemCabecalho", "Imagem do Cabecalho", "Lista de Modelo");
		criaSeNaoExistir(lista, "ModeloFormularioImagemRodape", "Imagem do Rodap??", "Formul??rio de Modelo");
		criaSeNaoExistir(lista, "ModeloListaImagemRodape", "Imagem do Rodap??", "Lista de Modelo");
		criaSeNaoExistir(lista, "ModeloFormularioAtivo", "Ativo", "Formul??rio de Modelo");
		criaSeNaoExistir(lista, "ModeloListaAtivo", "Ativo", "Lista de Modelo");
		criaSeNaoExistir(lista, "ModeloFormularioAtivoLigado", "Sim", "Formul??rio de Modelo");
		criaSeNaoExistir(lista, "ModeloListaAtivoLigado", "Sim", "Lista de Modelo");
		criaSeNaoExistir(lista, "ModeloFormularioAtivoDesligado", "N??o", "Formul??rio de Modelo");
		criaSeNaoExistir(lista, "ModeloListaAtivoDesligado", "N??o", "Lista de Modelo");
		criaSeNaoExistir(lista, "ModeloMenuPrincipal", "Modelo", "Menu");
		criaSeNaoExistir(lista, "ModeloMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "ModeloMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "ModeloFormularioBotaoInsere", "Insere", "Formul??rio de Modelo");
		criaSeNaoExistir(lista, "ModeloFormularioBotaoAltera", "Altera", "Formul??rio de Modelo");
		criaSeNaoExistir(lista, "ModeloFormularioTituloDaPagina", "Cadastro de Modelo", "Formul??rio de Modelo");
		criaSeNaoExistir(lista, "ModeloListaTituloDaPagina", "Lista de Modelo", "Lista de Modelo");
		criaSeNaoExistir(lista, "ModeloListaAcoes", "A????es", "Lista de Modelo");
		criaSeNaoExistir(lista, "ModeloListaSemRegistro", "N??o foram encontrados registros para Modelo", "Lista de Modelo");
		criaSeNaoExistir(lista, "OrcamentoFormularioId", "C??digo", "Formul??rio de Orcamento");
		criaSeNaoExistir(lista, "OrcamentoListaId", "C??digo", "Lista de Orcamento");
		criaSeNaoExistir(lista, "OrcamentoFormularioQuantidade", "Quantidade", "Formul??rio de Orcamento");
		criaSeNaoExistir(lista, "OrcamentoFormularioNome", "Nome", "Formul??rio de Orcamento");
		criaSeNaoExistir(lista, "OrcamentoFormularioEmail", "Email", "Formul??rio de Orcamento");
		criaSeNaoExistir(lista, "OrcamentoFormularioDDD", "DDD", "Formul??rio de Orcamento");
		criaSeNaoExistir(lista, "OrcamentoFormularioCelular", "Celular", "Formul??rio de Orcamento");
		criaSeNaoExistir(lista, "OrcamentoFormularioBotaoEnviar", "Enviar Or??amento", "Formul??rio de Orcamento");
		criaSeNaoExistir(lista, "OrcamentoListaQuantidade", "Quantidade", "Lista de Orcamento");
		criaSeNaoExistir(lista, "OrcamentoFormularioDataIncl", "Data Inclus??o", "Formul??rio de Orcamento");
		criaSeNaoExistir(lista, "OrcamentoListaDataIncl", "Data Inclus??o", "Lista de Orcamento");
		criaSeNaoExistir(lista, "OrcamentoFormularioDataAlt", "Data Altera????o", "Formul??rio de Orcamento");
		criaSeNaoExistir(lista, "OrcamentoListaDataAlt", "Data Altera????o", "Lista de Orcamento");
		criaSeNaoExistir(lista, "OrcamentoFormularioProduto", "Produto", "Formul??rio de Orcamento");
		criaSeNaoExistir(lista, "OrcamentoListaProduto", "Produto", "Lista de Orcamento");
		criaSeNaoExistir(lista, "OrcamentoFormularioAnunciante", "Anunciante", "Formul??rio de Orcamento");
		criaSeNaoExistir(lista, "OrcamentoListaAnunciante", "Anunciante", "Lista de Orcamento");
		criaSeNaoExistir(lista, "OrcamentoMenuPrincipal", "Orcamento", "Menu");
		criaSeNaoExistir(lista, "OrcamentoMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "OrcamentoMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "OrcamentoFormularioBotaoInsere", "Insere", "Formul??rio de Orcamento");
		criaSeNaoExistir(lista, "OrcamentoFormularioBotaoAltera", "Altera", "Formul??rio de Orcamento");
		criaSeNaoExistir(lista, "OrcamentoFormularioTituloDaPagina", "Cadastro de Orcamento", "Formul??rio de Orcamento");
		criaSeNaoExistir(lista, "OrcamentoListaTituloDaPagina", "Lista de Orcamento", "Lista de Orcamento");
		criaSeNaoExistir(lista, "OrcamentoListaAcoes", "A????es", "Lista de Orcamento");
		criaSeNaoExistir(lista, "OrcamentoListaSemRegistro", "N??o foram encontrados registros para Orcamento", "Lista de Orcamento");
		criaSeNaoExistir(lista, "ProdutoFormularioId", "ID", "Formul??rio de Produto");
		criaSeNaoExistir(lista, "ProdutoListaId", "ID", "Lista de Produto");
		criaSeNaoExistir(lista, "ProdutoFormularioCodigo", "C??digo", "Formul??rio de Produto");
		criaSeNaoExistir(lista, "ProdutoListaCodigo", "C??digo", "Lista de Produto");
		criaSeNaoExistir(lista, "ProdutoFormularioTitulo", "T??tulo", "Formul??rio de Produto");
		criaSeNaoExistir(lista, "ProdutoListaTitulo", "T??tulo", "Lista de Produto");
		criaSeNaoExistir(lista, "ProdutoFormularioBrevedescricao", "Breve Descricao", "Formul??rio de Produto");
		criaSeNaoExistir(lista, "ProdutoListaBrevedescricao", "Breve Descricao", "Lista de Produto");
		criaSeNaoExistir(lista, "ProdutoFormularioDescricaocompleta", "Descricao Completa", "Formul??rio de Produto");
		criaSeNaoExistir(lista, "ProdutoListaDescricaocompleta", "Descricao Completa", "Lista de Produto");
		criaSeNaoExistir(lista, "ProdutoFormularioTag", "Tags (usados na busca)", "Formul??rio de Produto");
		criaSeNaoExistir(lista, "ProdutoListaTag", "Tags", "Lista de Produto");
		criaSeNaoExistir(lista, "ProdutoFormularioQtdademin", "Quantidade M??nima", "Formul??rio de Produto");
		criaSeNaoExistir(lista, "ProdutoListaQtdademin", "Quantidade M??nima", "Lista de Produto");
		criaSeNaoExistir(lista, "ProdutoFormularioEstoque", "Estoque", "Formul??rio de Produto");
		criaSeNaoExistir(lista, "ProdutoListaEstoque", "Estoque", "Lista de Produto");
		criaSeNaoExistir(lista, "ProdutoFormularioPreco", "Pre??o", "Formul??rio de Produto");
		criaSeNaoExistir(lista, "ProdutoFormularioDesconto", "% Desconto", "Formul??rio de Produto");
		criaSeNaoExistir(lista, "ProdutoListaPreco", "Pre??o", "Lista de Produto");
		criaSeNaoExistir(lista, "ProdutoFormularioFaixadepreco", "Faixa de Pre??o", "Formul??rio de Produto");
		criaSeNaoExistir(lista, "ProdutoListaFaixadepreco", "Faixa de Pre??o", "Lista de Produto");
		criaSeNaoExistir(lista, "ProdutoFormularioImagem", "Imagem", "Formul??rio de Produto");
		criaSeNaoExistir(lista, "ProdutoListaImagem", "Imagem", "Lista de Produto");
		criaSeNaoExistir(lista, "ProdutoFormularioUrlanunciante", "URL do Seu Site", "Formul??rio de Produto");
		criaSeNaoExistir(lista, "ProdutoListaUrlanunciante", "URL do Seu Site", "Lista de Produto");
		criaSeNaoExistir(lista, "ProdutoFormularioDataIncl", "Data Inclus??o", "Formul??rio de Produto");
		criaSeNaoExistir(lista, "ProdutoListaDataIncl", "Data Inclus??o", "Lista de Produto");
		criaSeNaoExistir(lista, "ProdutoFormularioDataAlt", "Data Altera????o", "Formul??rio de Produto");
		criaSeNaoExistir(lista, "ProdutoListaDataAlt", "Data Altera????o", "Lista de Produto");
		criaSeNaoExistir(lista, "ProdutoFormularioCategoria", "Categoria", "Formul??rio de Produto");
		criaSeNaoExistir(lista, "ProdutoListaCategoria", "Categoria", "Lista de Produto");
		criaSeNaoExistir(lista, "ProdutoFormularioSubCategoria", "SubCategoria", "Formul??rio de Produto");
		criaSeNaoExistir(lista, "ProdutoListaSubCategoria", "SubCategoria", "Lista de Produto");
		criaSeNaoExistir(lista, "ProdutoFormularioAnunciante", "Anunciante", "Formul??rio de Produto");
		criaSeNaoExistir(lista, "ProdutoListaAnunciante", "Anunciante", "Lista de Produto");
		criaSeNaoExistir(lista, "ProdutoFormularioStatus", "Status", "Formul??rio de Produto");
		criaSeNaoExistir(lista, "ProdutoFormularioLinha", "Linha", "Formul??rio de Produto");
		criaSeNaoExistir(lista, "ProdutoListaStatus", "Status", "Lista de Produto");
		criaSeNaoExistir(lista, "ProdutoMenuPrincipal", "Produto", "Menu");
		criaSeNaoExistir(lista, "ProdutoMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "ProdutoMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "ProdutoFormularioBotaoInsere", "Insere", "Formul??rio de Produto");
		criaSeNaoExistir(lista, "ProdutoFormularioBotaoAltera", "Altera", "Formul??rio de Produto");
		criaSeNaoExistir(lista, "ProdutoFormularioBotaoImagemInsere", "Inserir", "Formul??rio de Produto");
		criaSeNaoExistir(lista, "ProdutoFormularioTituloDaPagina", "Cadastro de Produto", "Formul??rio de Produto");
		criaSeNaoExistir(lista, "ProdutoListaTituloDaPagina", "Lista de Produto", "Lista de Produto");
		criaSeNaoExistir(lista, "ProdutoListaAcoes", "A????es", "Lista de Produto");
		criaSeNaoExistir(lista, "ProdutoListaSemRegistro", "N??o foram encontrados registros para Produto", "Lista de Produto");
		criaSeNaoExistir(lista, "ImagensFormularioTituloDaPagina", "Cadastro de Imagens", "Formul??rio de Imagens");
		criaSeNaoExistir(lista, "ImagemListaId", "ID", "Lista de Imagem");
		criaSeNaoExistir(lista, "ImagemListaNome", "Descri????o", "Lista de Imagem");
		criaSeNaoExistir(lista, "ImagemListaProduto", "Id do Produto", "Lista de Imagem");
		criaSeNaoExistir(lista, "ImagemListaMiniatura", "Miniatura", "Lista de Imagem");
		criaSeNaoExistir(lista, "ImagemListaAcoes", "A????es", "Lista de Imagem");
		criaSeNaoExistir(lista, "StatusFormularioId", "C??digo", "Formul??rio de Status");
		criaSeNaoExistir(lista, "StatusListaId", "C??digo", "Lista de Status");
		criaSeNaoExistir(lista, "StatusFormularioNome", "Nome", "Formul??rio de Status");
		criaSeNaoExistir(lista, "StatusListaNome", "Nome", "Lista de Status");
		criaSeNaoExistir(lista, "StatusFormularioDataIncl", "Data Inclus??o", "Formul??rio de Status");
		criaSeNaoExistir(lista, "StatusListaDataIncl", "Data Inclus??o", "Lista de Status");
		criaSeNaoExistir(lista, "StatusFormularioDataAlt", "Data Altera????o", "Formul??rio de Status");
		criaSeNaoExistir(lista, "StatusListaDataAlt", "Data Altera????o", "Lista de Status");
		criaSeNaoExistir(lista, "StatusMenuPrincipal", "Status", "Menu");
		criaSeNaoExistir(lista, "StatusMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "StatusMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "StatusFormularioBotaoInsere", "Insere", "Formul??rio de Status");
		criaSeNaoExistir(lista, "StatusFormularioBotaoAltera", "Altera", "Formul??rio de Status");
		criaSeNaoExistir(lista, "StatusFormularioTituloDaPagina", "Cadastro de Status", "Formul??rio de Status");
		criaSeNaoExistir(lista, "StatusListaTituloDaPagina", "Lista de Status", "Lista de Status");
		criaSeNaoExistir(lista, "StatusListaAcoes", "A????es", "Lista de Status");
		criaSeNaoExistir(lista, "StatusListaSemRegistro", "N??o foram encontrados registros para Status", "Lista de Status");
		criaSeNaoExistir(lista, "SubCategoriaFormularioId", "C??digo", "Formul??rio de SubCategoria");
		criaSeNaoExistir(lista, "SubCategoriaListaId", "C??digo", "Lista de SubCategoria");
		criaSeNaoExistir(lista, "SubCategoriaFormularioNome", "Nome", "Formul??rio de SubCategoria");
		criaSeNaoExistir(lista, "SubCategoriaListaNome", "Nome", "Lista de SubCategoria");
		criaSeNaoExistir(lista, "SubCategoriaFormularioDataIncl", "Data Inclus??o", "Formul??rio de SubCategoria");
		criaSeNaoExistir(lista, "SubCategoriaListaDataIncl", "Data Inclus??o", "Lista de SubCategoria");
		criaSeNaoExistir(lista, "SubCategoriaFormularioDataAlt", "Data Altera????o", "Formul??rio de SubCategoria");
		criaSeNaoExistir(lista, "SubCategoriaListaDataAlt", "Data Altera????o", "Lista de SubCategoria");
		criaSeNaoExistir(lista, "SubCategoriaFormularioCategoria", "Categoria", "Formul??rio de SubCategoria");
		criaSeNaoExistir(lista, "SubCategoriaListaCategoria", "Categoria", "Lista de SubCategoria");
		criaSeNaoExistir(lista, "SubCategoriaMenuPrincipal", "SubCategoria", "Menu");
		criaSeNaoExistir(lista, "SubCategoriaMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "SubCategoriaMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "SubCategoriaFormularioBotaoInsere", "Insere", "Formul??rio de SubCategoria");
		criaSeNaoExistir(lista, "SubCategoriaFormularioBotaoAltera", "Altera", "Formul??rio de SubCategoria");
		criaSeNaoExistir(lista, "SubCategoriaFormularioTituloDaPagina", "Cadastro de SubCategoria", "Formul??rio de SubCategoria");
		criaSeNaoExistir(lista, "SubCategoriaListaTituloDaPagina", "Lista de SubCategoria", "Lista de SubCategoria");
		criaSeNaoExistir(lista, "SubCategoriaListaAcoes", "A????es", "Lista de SubCategoria");
		criaSeNaoExistir(lista, "SubCategoriaListaSemRegistro", "N??o foram encontrados registros para SubCategoria", "Lista de SubCategoria");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioId", "C??digo", "Formul??rio de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaId", "C??digo", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioRequiredOnly", "required Only", "Formul??rio de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaRequiredOnly", "required Only", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioMaxLengthOnly", "maxLength Only", "Formul??rio de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaMaxLengthOnly", "maxLength Only", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioSomenteNumerosOnly", "somente Numeros Only", "Formul??rio de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaSomenteNumerosOnly", "somente Numeros Only", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioRemoveAcentosOnly", "remove Acentos Only", "Formul??rio de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaRemoveAcentosOnly", "remove Acentos Only", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioTextDefault", "text Default", "Formul??rio de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaTextDefault", "text Default", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioTextSomenteNumero", "text Somente Numero", "Formul??rio de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaTextSomenteNumero", "text Somente Numero", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioTextRemoveAcentos", "text Remove Acentos", "Formul??rio de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaTextRemoveAcentos", "text Remove Acentos", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioTextEmail", "text Email", "Formul??rio de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaTextEmail", "text Email", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioCpfSomenteNumeros", "cpf Somente Numeros", "Formul??rio de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaCpfSomenteNumeros", "cpf Somente Numeros", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioCnpjSomenteNumeros", "cnpj Somente Numeros", "Formul??rio de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaCnpjSomenteNumeros", "cnpj Somente Numeros", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioTelDDD", "tel DDD", "Formul??rio de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaTelDDD", "tel DDD", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioTelNumero", "tel Numero", "Formul??rio de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaTelNumero", "tel Numero", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioCepSomenteNumeros", "cep Somente Numeros", "Formul??rio de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaCepSomenteNumeros", "cep Somente Numeros", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioDataTeste", "data teste", "Formul??rio de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaDataTeste", "data teste", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioDataTime", "data Time", "Formul??rio de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaDataTime", "data Time", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioDoubleTeste", "double Teste", "Formul??rio de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaDoubleTeste", "double Teste", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioIntegerTeste", "integer Teste", "Formul??rio de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaIntegerTeste", "integer Teste", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioLongTeste", "long Teste", "Formul??rio de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaLongTeste", "long Teste", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioTimeStampTeste", "time Stamp Teste", "Formul??rio de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaTimeStampTeste", "time Stamp Teste", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioTextAreaSimples", "text Area Simples", "Formul??rio de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaTextAreaSimples", "text Area Simples", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioTextAreaHtml", "text Area Html", "Formul??rio de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaTextAreaHtml", "text Area Html", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioBooleanTeste", "boolean Teste", "Formul??rio de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaBooleanTeste", "boolean Teste", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioBooleanTesteLigado", "Sim", "Formul??rio de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaBooleanTesteLigado", "Sim", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioBooleanTesteDesligado", "N??o", "Formul??rio de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaBooleanTesteDesligado", "N??o", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioArquivoTeste", "arquivo Teste", "Formul??rio de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaArquivoTeste", "arquivo Teste", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorMenuPrincipal", "MsbcValidator", "Menu");
		criaSeNaoExistir(lista, "MsbcValidatorMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "MsbcValidatorMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioBotaoInsere", "Insere", "Formul??rio de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioBotaoAltera", "Altera", "Formul??rio de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorFormularioTituloDaPagina", "Cadastro de MsbcValidator", "Formul??rio de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaTituloDaPagina", "Lista de MsbcValidator", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaAcoes", "A????es", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "MsbcValidatorListaSemRegistro", "N??o foram encontrados registros para MsbcValidator", "Lista de MsbcValidator");
		criaSeNaoExistir(lista, "GrupoFormularioId", "C??digo", "Formul??rio de Grupo");
		criaSeNaoExistir(lista, "GrupoListaId", "C??digo", "Lista de Grupo");
		criaSeNaoExistir(lista, "GrupoFormularioNome", "Nome", "Formul??rio de Grupo");
		criaSeNaoExistir(lista, "GrupoListaNome", "Nome", "Lista de Grupo");
		criaSeNaoExistir(lista, "GrupoMenuPrincipal", "Grupo", "Menu");
		criaSeNaoExistir(lista, "GrupoMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "GrupoMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "GrupoFormularioBotaoInsere", "Insere", "Formul??rio de Grupo");
		criaSeNaoExistir(lista, "GrupoFormularioBotaoAltera", "Altera", "Formul??rio de Grupo");
		criaSeNaoExistir(lista, "GrupoFormularioTituloDaPagina", "Cadastro de Grupo", "Formul??rio de Grupo");
		criaSeNaoExistir(lista, "GrupoListaTituloDaPagina", "Lista de Grupo", "Lista de Grupo");
		criaSeNaoExistir(lista, "GrupoListaAcoes", "A????es", "Lista de Grupo");
		criaSeNaoExistir(lista, "GrupoListaSemRegistro", "N??o foram encontrados registros para Grupo", "Lista de Grupo");
		criaSeNaoExistir(lista, "UsuarioFormularioId", "C??digo", "Formul??rio de Usu??rio");
		criaSeNaoExistir(lista, "UsuarioListaId", "C??digo", "Lista de Usu??rio");
		criaSeNaoExistir(lista, "UsuarioFormularioLogin", "Login", "Formul??rio de Usu??rio");
		criaSeNaoExistir(lista, "UsuarioListaLogin", "Login", "Lista de Usu??rio");
		criaSeNaoExistir(lista, "UsuarioFormularioNome", "Nome", "Formul??rio de Usu??rio");
		criaSeNaoExistir(lista, "UsuarioListaNome", "Nome", "Lista de Usu??rio");
		criaSeNaoExistir(lista, "UsuarioFormularioApelido", "Apelido", "Formul??rio de Usu??rio");
		criaSeNaoExistir(lista, "UsuarioListaApelido", "Apelido", "Lista de Usu??rio");
		criaSeNaoExistir(lista, "UsuarioFormularioEmail", "Email", "Formul??rio de Usu??rio");
		criaSeNaoExistir(lista, "UsuarioListaEmail", "Email", "Lista de Usu??rio");
		criaSeNaoExistir(lista, "UsuarioFormularioSenha", "Senha", "Formul??rio de Usu??rio");
		criaSeNaoExistir(lista, "UsuarioListaSenha", "Senha", "Lista de Usu??rio");
		criaSeNaoExistir(lista, "UsuarioFormularioGrupo", "Grupo", "Formul??rio de Usu??rio");
		criaSeNaoExistir(lista, "UsuarioListaGrupo", "Grupo", "Lista de Usu??rio");
		criaSeNaoExistir(lista, "UsuarioFormularioAnunciante", "Anunciante", "Formul??rio de Usu??rio");
		criaSeNaoExistir(lista, "UsuarioListaAnunciante", "Anunciante", "Lista de Usu??rio");
		criaSeNaoExistir(lista, "UsuarioMenuPrincipal", "Usu??rio", "Menu");
		criaSeNaoExistir(lista, "UsuarioMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "UsuarioMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "UsuarioFormularioBotaoInsere", "Insere", "Formul??rio de Usu??rio");
		criaSeNaoExistir(lista, "UsuarioFormularioBotaoAltera", "Altera", "Formul??rio de Usu??rio");
		criaSeNaoExistir(lista, "UsuarioFormularioTituloDaPagina", "Cadastro de Usu??rio", "Formul??rio de Usu??rio");
		criaSeNaoExistir(lista, "UsuarioListaTituloDaPagina", "Lista de Usu??rio", "Lista de Usu??rio");
		criaSeNaoExistir(lista, "UsuarioListaAcoes", "A????es", "Lista de Usu??rio");
		criaSeNaoExistir(lista, "UsuarioListaSemRegistro", "N??o foram encontrados registros para Usu??rio", "Lista de Usu??rio");
		criaSeNaoExistir(lista, "HistoricoDoSistemaFormularioId", "C??digo", "Formul??rio de Hist??rico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaListaId", "C??digo", "Lista de Hist??rico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaFormularioLogin", "Login", "Formul??rio de Hist??rico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaListaLogin", "Login", "Lista de Hist??rico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaFormularioNome", "Nome", "Formul??rio de Hist??rico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaListaNome", "Nome", "Lista de Hist??rico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaFormularioDatahora", "Data / Hora", "Formul??rio de Hist??rico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaListaDatahora", "Data / Hora", "Lista de Hist??rico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaFormularioLocal", "Local", "Formul??rio de Hist??rico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaListaLocal", "Local", "Lista de Hist??rico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaFormularioAcao", "A????o", "Formul??rio de Hist??rico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaListaAcao", "A????o", "Lista de Hist??rico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaFormularioDados", "Dados", "Formul??rio de Hist??rico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaListaDados", "Dados", "Lista de Hist??rico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaMenuPrincipal", "Hist??rico do sistema", "Menu");
		criaSeNaoExistir(lista, "HistoricoDoSistemaMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "HistoricoDoSistemaMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "HistoricoDoSistemaFormularioBotaoInsere", "Insere", "Formul??rio de Hist??rico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaFormularioBotaoAltera", "Altera", "Formul??rio de Hist??rico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaFormularioTituloDaPagina", "Cadastro de Hist??rico do sistema", "Formul??rio de Hist??rico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaListaTituloDaPagina", "Lista de Hist??rico do sistema", "Lista de Hist??rico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaListaAcoes", "A????es", "Lista de Hist??rico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaListaSemRegistro", "N??o foram encontrados registros para Hist??rico do sistema", "Lista de Hist??rico do sistema");
		criaSeNaoExistir(lista, "MensagensDoSistemaFormularioPropriedade", "Propriedade", "Formul??rio de Mensagens do sistema");
		criaSeNaoExistir(lista, "MensagensDoSistemaListaPropriedade", "Propriedade", "Lista de Mensagens do sistema");
		criaSeNaoExistir(lista, "MensagensDoSistemaFormularioValor", "Valor", "Formul??rio de Mensagens do sistema");
		criaSeNaoExistir(lista, "MensagensDoSistemaListaValor", "Valor", "Lista de Mensagens do sistema");
		criaSeNaoExistir(lista, "MensagensDoSistemaFormularioTela", "Tela", "Formul??rio de Mensagens do sistema");
		criaSeNaoExistir(lista, "MensagensDoSistemaListaTela", "Tela", "Lista de Mensagens do sistema");
		criaSeNaoExistir(lista, "MensagensDoSistemaMenuPrincipal", "Mensagens do sistema", "Menu");
		criaSeNaoExistir(lista, "MensagensDoSistemaMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "MensagensDoSistemaMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "MensagensDoSistemaFormularioBotaoInsere", "Insere", "Formul??rio de Mensagens do sistema");
		criaSeNaoExistir(lista, "MensagensDoSistemaFormularioBotaoAltera", "Altera", "Formul??rio de Mensagens do sistema");
		criaSeNaoExistir(lista, "MensagensDoSistemaFormularioTituloDaPagina", "Cadastro de Mensagens do sistema", "Formul??rio de Mensagens do sistema");
		criaSeNaoExistir(lista, "MensagensDoSistemaListaTituloDaPagina", "Lista de Mensagens do sistema", "Lista de Mensagens do sistema");
		criaSeNaoExistir(lista, "MensagensDoSistemaListaAcoes", "A????es", "Lista de Mensagens do sistema");
		criaSeNaoExistir(lista, "MensagensDoSistemaListaSemRegistro", "N??o foram encontrados registros para Mensagens do sistema", "Lista de Mensagens do sistema");
		criaSeNaoExistir(lista, "ConfiguracoesDoSistemaFormularioPropriedade", "Propriedade", "Formul??rio de Configura????es do sistema");
		criaSeNaoExistir(lista, "ConfiguracoesDoSistemaListaPropriedade", "Propriedade", "Lista de Configura????es do sistema");
		criaSeNaoExistir(lista, "ConfiguracoesDoSistemaFormularioValor", "Valor", "Formul??rio de Configura????es do sistema");
		criaSeNaoExistir(lista, "ConfiguracoesDoSistemaListaValor", "Valor", "Lista de Configura????es do sistema");
		criaSeNaoExistir(lista, "ConfiguracoesDoSistemaMenuPrincipal", "Configura????es do sistema", "Menu");
		criaSeNaoExistir(lista, "ConfiguracoesDoSistemaMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "ConfiguracoesDoSistemaMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "ConfiguracoesDoSistemaFormularioBotaoInsere", "Insere", "Formul??rio de Configura????es do sistema");
		criaSeNaoExistir(lista, "ConfiguracoesDoSistemaFormularioBotaoAltera", "Altera", "Formul??rio de Configura????es do sistema");
		criaSeNaoExistir(lista, "ConfiguracoesDoSistemaFormularioTituloDaPagina", "Cadastro de Configura????es do sistema", "Formul??rio de Configura????es do sistema");
		criaSeNaoExistir(lista, "ConfiguracoesDoSistemaListaTituloDaPagina", "Lista de Configura????es do sistema", "Lista de Configura????es do sistema");
		criaSeNaoExistir(lista, "ConfiguracoesDoSistemaListaAcoes", "A????es", "Lista de Configura????es do sistema");
		criaSeNaoExistir(lista, "ConfiguracoesDoSistemaListaSemRegistro", "N??o foram encontrados registros para Configura????es do sistema", "Lista de Configura????es do sistema");
		criaSeNaoExistir(lista, "ModalExclusaoTitulo", "Confirmar exclus??o", "Modal de exclus??o");
		criaSeNaoExistir(lista, "ModalExclusaoMensagemInicio", "Deseja mesmo excluir o ", "Modal de exclus??o");
		criaSeNaoExistir(lista, "ModalExclusaoMensagemFim", "? Essa a????o n??o poder?? ser revertida!", "Modal de exclus??o");
		criaSeNaoExistir(lista, "ModalExclusaoBotaoSim", "Sim, excluir!", "Modal de exclus??o");
		criaSeNaoExistir(lista, "ModalExclusaoBotaoNao", "N??o, Fechar!", "Modal de exclus??o");
		criaSeNaoExistir(lista, "MensagemAdicionadoComSucesso", "Inserido com sucesso!", "Todas as telas de inser????o");
		criaSeNaoExistir(lista, "ModalRedefinirSenhaTitulo", "Confirmar redefini????o de senha", "Modal de redefini????o de senha");
		criaSeNaoExistir(lista, "ModalRedefinirSenhaMensagemInicio", "Deseja mesmo redefinir a senha de ", "Modal de redefini????o de senha");
		criaSeNaoExistir(lista, "ModalRedefinirSenhaMensagemFim", "? A nova senha ser?? 123456!", "Modal de redefini????o de senha");
		criaSeNaoExistir(lista, "ModalRedefinirSenhaBotaoSim", "Sim, redefinir!", "Modal de redefini????o de senha");
		criaSeNaoExistir(lista, "ModalRedefinirSenhaBotaoNao", "N??o, Fechar!", "Modal de redefini????o de senha");
		criaSeNaoExistir(lista, "MensagemAlteradoComSucesso", "Alterado com sucesso!", "Todas as telas de altera????o");
		criaSeNaoExistir(lista, "MensagemExcluidoComSucesso", "Exclu??do com sucesso!", "Todas as telas de exclus??o");
		criaSeNaoExistir(lista, "MensagemErroIdNulo", "O id n??o pode ser nulo!", "Rest Controller");
		criaSeNaoExistir(lista, "MensagemErroAlteracaoNaoLocalizadaNoBanco", "N??o foi possivel realizar a altera????o, n??o localizado no banco!", "Rest Controller");
		criaSeNaoExistir(lista, "MensagemErroExclusaoNaoLocalizadaNoBanco", "N??o foi possivel realizar a exclus??o, n??o localizado no banco!", "Rest Controller");
		criaSeNaoExistir(lista, "SenhaAtualNaoConfere", "Senha atual n??o confere!", "Tela de altera????o de senha");
		criaSeNaoExistir(lista, "ValidacaoErroNovaSenhaObrigatoria", "O campo Nova senha deve ter de 6 a 30 caracteres!", "Tela de altera????o de senha");
		criaSeNaoExistir(lista, "ValidacaoErroRepeteSenhaObrigatoria", "O campo Repetir nova senha deve ter de 6 a 30 caracteres!", "Tela de altera????o de senha");
		criaSeNaoExistir(lista, "ValidacaoErroSenhasDiferentes", "O campo Nova senha e Repetir nova senha s??o diferentes!", "Tela de altera????o de senha");
		criaSeNaoExistir(lista, "AlterarSenhaTitulo", "Alterar Senha", "Tela de altera????o de senha");
		criaSeNaoExistir(lista, "AlterarSenhaSenhaAtual", "Senha atual", "Tela de altera????o de senha");
		criaSeNaoExistir(lista, "AlterarSenhaNovaSenha", "Nova senha", "Tela de altera????o de senha");
		criaSeNaoExistir(lista, "AlterarSenhaRepetirNovaSenha", "Repetir nova senha", "Tela de altera????o de senha");
		criaSeNaoExistir(lista, "AlterarSenhaBotao", "Altera", "Tela de altera????o de senha");
		criaSeNaoExistir(lista, "AlterarSenhaUsuariosDaRede", "Usu??rios da rede n??o podem alterar a senha!", "Tela de altera????o de senha");
		criaSeNaoExistir(lista, "MenuBotaoSair", "Sair", "Menu");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioAnuncianteRazaosocial", "O campo Raz??o Social deve ser preenchido!", "Formul??rio de Anunciante");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioAnuncianteFantasia", "O campo Nome Fantasia deve ser preenchido!", "Formul??rio de Anunciante");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioAnuncianteEmail", "O campo E-Mail deve ser preenchido!", "Formul??rio de Anunciante");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioAnuncianteDdd", "O campo DDD deve ser preenchido!", "Formul??rio de Anunciante");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioAnuncianteTelefone", "O campo Telefone deve ser preenchido!", "Formul??rio de Anunciante");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioAnuncianteDataIncl", "O campo Data Inclus??o deve ser preenchido!", "Formul??rio de Anunciante");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioAnuncianteDataAlt", "O campo Data Altera????o deve ser preenchido!", "Formul??rio de Anunciante");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioAnuncianteTipoNome", "O campo Nome deve ser preenchido!", "Formul??rio de AnuncianteTipo");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioAnuncianteTipoDataIncl", "O campo Data Inclus??o deve ser preenchido!", "Formul??rio de AnuncianteTipo");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioAnuncianteTipoDataAlt", "O campo Data Altera????o deve ser preenchido!", "Formul??rio de AnuncianteTipo");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioCategoriaNome", "O campo Nome deve ser preenchido!", "Formul??rio de Categoria");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioCategoriaDataIncl", "O campo Data Inclus??o deve ser preenchido!", "Formul??rio de Categoria");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioCategoriaDataAlt", "O campo Data Altera????o deve ser preenchido!", "Formul??rio de Categoria");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioAtividadeNome", "O campo Nome deve ser preenchido!", "Formul??rio de Atividade");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioAtividadeDataIncl", "O campo Data Inclus??o deve ser preenchido!", "Formul??rio de Atividade");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioAtividadeDataAlt", "O campo Data Altera????o deve ser preenchido!", "Formul??rio de Atividade");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioLinhaNome", "O campo Nome deve ser preenchido!", "Formul??rio de Linha");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioLinhaDataIncl", "O campo Data Inclus??o deve ser preenchido!", "Formul??rio de Linha");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioLinhaDataAlt", "O campo Data Altera????o deve ser preenchido!", "Formul??rio de Linha");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioCampanhaNome", "O campo Nome deve ser preenchido!", "Formul??rio de Campanha");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioCampanhaOrdem", "O campo Ordem deve ser preenchido!", "Formul??rio de Campanha");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioModeloDescricao", "O campo Descri????o deve ser preenchido!", "Formul??rio de Modelo");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioModeloTitulo", "O campo T??tulo do Certificado deve ser preenchido!", "Formul??rio de Modelo");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioModeloCorTitulo", "O campo Cor do T??tulo deve ser preenchido!", "Formul??rio de Modelo");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioModeloAtivo", "O campo Ativo|Sim|N??o deve ser preenchido!", "Formul??rio de Modelo");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioOrcamentoQuantidade", "O campo Quantidade deve ser preenchido!", "Formul??rio de Orcamento");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioOrcamentoDataIncl", "O campo Data Inclus??o deve ser preenchido!", "Formul??rio de Orcamento");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioOrcamentoDataAlt", "O campo Data Altera????o deve ser preenchido!", "Formul??rio de Orcamento");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioOrcamentoNome", "O campo Nome deve ser preenchido!", "Formul??rio de Orcamento");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioOrcamentoEmail", "O campo Email deve ser preenchido!", "Formul??rio de Orcamento");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioProdutoCodigo", "O campo C??digo deve ser preenchido!", "Formul??rio de Produto");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioProdutoTitulo", "O campo T??tulo deve ser preenchido!", "Formul??rio de Produto");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioProdutoBrevedescricao", "O campo Breve Descricao deve ser preenchido!", "Formul??rio de Produto");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioProdutoDescricaocompleta", "O campo Descricao Completa deve ser preenchido!", "Formul??rio de Produto");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioProdutoTag", "O campo Tags deve ser preenchido!", "Formul??rio de Produto");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioProdutoPreco", "O campo Pre??o deve ser preenchido!", "Formul??rio de Produto");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioProdutoFaixadepreco", "O campo Faixa de Pre??o deve ser preenchido!", "Formul??rio de Produto");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioProdutoImagem", "O campo Imagem deve ser preenchido!", "Formul??rio de Produto");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioProdutoUrlanunciante", "O campo URL do Seu Site deve ser preenchido!", "Formul??rio de Produto");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioProdutoDataIncl", "O campo Data Inclus??o deve ser preenchido!", "Formul??rio de Produto");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioProdutoDataAlt", "O campo Data Altera????o deve ser preenchido!", "Formul??rio de Produto");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioStatusNome", "O campo Nome deve ser preenchido!", "Formul??rio de Status");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioStatusDataIncl", "O campo Data Inclus??o deve ser preenchido!", "Formul??rio de Status");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioStatusDataAlt", "O campo Data Altera????o deve ser preenchido!", "Formul??rio de Status");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioSubCategoriaNome", "O campo Nome deve ser preenchido!", "Formul??rio de SubCategoria");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioSubCategoriaDataIncl", "O campo Data Inclus??o deve ser preenchido!", "Formul??rio de SubCategoria");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioSubCategoriaDataAlt", "O campo Data Altera????o deve ser preenchido!", "Formul??rio de SubCategoria");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorRequiredOnly", "O campo required Only deve ser preenchido!", "Formul??rio de MsbcValidator");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorMaxLengthOnly", "O campo maxLength Only deve ser preenchido!", "Formul??rio de MsbcValidator");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorSomenteNumerosOnly", "O campo somente Numeros Only deve ser preenchido!", "Formul??rio de MsbcValidator");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorRemoveAcentosOnly", "O campo remove Acentos Only deve ser preenchido!", "Formul??rio de MsbcValidator");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorTextDefault", "O campo text Default deve ser preenchido!", "Formul??rio de MsbcValidator");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorTextSomenteNumero", "O campo text Somente Numero deve ser preenchido!", "Formul??rio de MsbcValidator");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorTextRemoveAcentos", "O campo text Remove Acentos deve ser preenchido!", "Formul??rio de MsbcValidator");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorTextEmail", "O campo text Email deve ser preenchido!", "Formul??rio de MsbcValidator");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorCpfSomenteNumeros", "O campo cpf Somente Numeros deve ser preenchido!", "Formul??rio de MsbcValidator");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorCnpjSomenteNumeros", "O campo cnpj Somente Numeros deve ser preenchido!", "Formul??rio de MsbcValidator");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorTelDDD", "O campo tel DDD deve ser preenchido!", "Formul??rio de MsbcValidator");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorTelNumero", "O campo tel Numero deve ser preenchido!", "Formul??rio de MsbcValidator");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorCepSomenteNumeros", "O campo cep Somente Numeros deve ser preenchido!", "Formul??rio de MsbcValidator");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorDataTeste", "O campo data teste deve ser preenchido!", "Formul??rio de MsbcValidator");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorDataTime", "O campo data Time deve ser preenchido!", "Formul??rio de MsbcValidator");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorDoubleTeste", "O campo double Teste deve ser preenchido!", "Formul??rio de MsbcValidator");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorIntegerTeste", "O campo integer Teste deve ser preenchido!", "Formul??rio de MsbcValidator");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorLongTeste", "O campo long Teste deve ser preenchido!", "Formul??rio de MsbcValidator");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorTimeStampTeste", "O campo time Stamp Teste deve ser preenchido!", "Formul??rio de MsbcValidator");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorTextAreaSimples", "O campo text Area Simples deve ser preenchido!", "Formul??rio de MsbcValidator");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorTextAreaHtml", "O campo text Area Html deve ser preenchido!", "Formul??rio de MsbcValidator");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorBooleanTeste", "O campo boolean Teste|Sim|N??o deve ser preenchido!", "Formul??rio de MsbcValidator");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMsbcValidatorArquivoTeste", "O campo arquivo Teste deve ser preenchido!", "Formul??rio de MsbcValidator");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioGrupoNome", "O campo Nome deve ser preenchido!", "Formul??rio de Grupo");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioUsuarioLogin", "O campo Login deve ser preenchido!", "Formul??rio de Usuario");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioUsuarioNome", "O campo Nome deve ser preenchido!", "Formul??rio de Usuario");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioUsuarioApelido", "O campo Apelido deve ser preenchido!", "Formul??rio de Usuario");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioUsuarioEmail", "O campo Email deve ser preenchido!", "Formul??rio de Usuario");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioUsuarioSenha", "O campo Senha deve ser preenchido!", "Formul??rio de Usuario");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioHistoricoDoSistemaLogin", "O campo Login deve ser preenchido!", "Formul??rio de HistoricoDoSistema");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioHistoricoDoSistemaNome", "O campo Nome deve ser preenchido!", "Formul??rio de HistoricoDoSistema");
		criaSeNaoExistir(lista, "ValidacaoErroDataObrigatoriaHistoricoDoSistemaDatahora", "O campo Data / Hora deve ser preenchido!", "Formul??rio de HistoricoDoSistema");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioHistoricoDoSistemaLocal", "O campo Local deve ser preenchido!", "Formul??rio de HistoricoDoSistema");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioHistoricoDoSistemaAcao", "O campo A????o deve ser preenchido!", "Formul??rio de HistoricoDoSistema");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioHistoricoDoSistemaDados", "O campo Dados deve ser preenchido!", "Formul??rio de HistoricoDoSistema");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMensagensDoSistemaPropriedade", "O campo Propriedade deve ser preenchido!", "Formul??rio de MensagensDoSistema");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMensagensDoSistemaValor", "O campo Valor deve ser preenchido!", "Formul??rio de MensagensDoSistema");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMensagensDoSistemaTela", "O campo Tela deve ser preenchido!", "Formul??rio de MensagensDoSistema");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioConfiguracoesDoSistemaPropriedade", "O campo Propriedade deve ser preenchido!", "Formul??rio de ConfiguracoesDoSistema");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioConfiguracoesDoSistemaValor", "O campo Valor deve ser preenchido!", "Formul??rio de ConfiguracoesDoSistema");
		criaSeNaoExistir(lista, "RegistroDuplicado", "J?? existe um cadastro com esse nome!", "Formul??rio de Cadastro");
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