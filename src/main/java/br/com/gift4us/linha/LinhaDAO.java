package br.com.gift4us.linha;

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

import br.com.gift4us.campanha.CampanhaModel;
import br.com.gift4us.categoria.CategoriaModel;
import br.com.gift4us.produto.ProdutoModel;

import java.util.Map;
import org.springframework.security.core.userdetails.UserDetails;
import com.google.common.annotations.VisibleForTesting;
import org.springframework.stereotype.Repository;

@Repository
public class LinhaDAO {

	private final String TABELA = LinhaModel.class.getSimpleName();

	@PersistenceContext
	private EntityManager manager;

	public LinhaDAO() {
	}

	@VisibleForTesting
	public LinhaDAO(EntityManager em) {
		this.manager = em;
	}

	
	@Transactional
	public void insere(LinhaModel linha) {
		manager.persist(linha);
		manager.flush();
		manager.detach(linha);
	}

	@Transactional
	public void altera(LinhaModel linha) {
		manager.merge(linha);
		manager.flush();
		manager.detach(linha);
	}

	@Transactional
	public void exclui(LinhaModel linha) {
		manager.remove(manager.find(LinhaModel.class, linha.getId()));
	}

	public List<LinhaModel> listaTudo() {
		String jpql = "SELECT a FROM " + TABELA + " a ORDER BY a.id";
		TypedQuery<LinhaModel> query = manager.createQuery(jpql, LinhaModel.class);
		return query.getResultList();
	}
	
	public List<LinhaModel> buscaPorNomeExato(String nome) {

		String jpql = "SELECT c FROM " + TABELA + " c WHERE c.nome = :nome ";

		TypedQuery<LinhaModel> query = manager.createQuery(jpql, LinhaModel.class);

		query.setParameter("nome", nome);

		return query.getResultList();
	}

	public List<LinhaModel> buscaPorCampanha(CampanhaModel campanha) {

		String jpql = "SELECT p FROM " + TABELA + " p WHERE p.campanha = :campanha ";

		TypedQuery<LinhaModel> query = manager.createQuery(jpql, LinhaModel.class);

		query.setParameter("campanha", campanha);

		return query.getResultList();
	}
	
	public LinhaModel buscaPorId(Long id) {

		String jpql = "SELECT a FROM " + TABELA + " a WHERE a.id = :id ";

		TypedQuery<LinhaModel> query = manager.createQuery(jpql, LinhaModel.class);

		query.setParameter("id", id);

		List<LinhaModel> resultado = query.getResultList();

		if(resultado.size() == 0){
			return new LinhaModel();
		}else{
			return resultado.get(0);
		}
	}

	public LinhaModel buscaPorIdClonando(Long id) {
		LinhaModel encontrado = buscaPorId(id);
		Gson gson = new Gson();
		return gson.fromJson(gson.toJson(encontrado), LinhaModel.class);
	}

	public List<LinhaModel> buscaPorNome(String nome) {

		String jpql = "SELECT a FROM " + TABELA + " a WHERE a.nome LIKE  :nome ";

		TypedQuery<LinhaModel> query = manager.createQuery(jpql, LinhaModel.class);

		query.setParameter("nome", "%"+nome+"%");

		return query.getResultList();
	}

	public List<LinhaModel> buscaPorDataIncl(Calendar dataIncl) {

		String jpql = "SELECT a FROM " + TABELA + " a WHERE a.dataIncl =  :dataIncl ";

		TypedQuery<LinhaModel> query = manager.createQuery(jpql, LinhaModel.class);

		query.setParameter("dataIncl", dataIncl);

		return query.getResultList();
	}

	public List<LinhaModel> buscaPorDataAlt(Calendar dataAlt) {

		String jpql = "SELECT a FROM " + TABELA + " a WHERE a.dataAlt =  :dataAlt ";

		TypedQuery<LinhaModel> query = manager.createQuery(jpql, LinhaModel.class);

		query.setParameter("dataAlt", dataAlt);

		return query.getResultList();
	}

}