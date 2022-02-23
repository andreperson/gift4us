package br.com.gift4us.campanha;

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

import br.com.gift4us.categoria.CategoriaModel;

import java.util.Map;
import org.springframework.security.core.userdetails.UserDetails;
import com.google.common.annotations.VisibleForTesting;
import org.springframework.stereotype.Repository;

@Repository
public class CampanhaDAO {

	private final String TABELA = CampanhaModel.class.getSimpleName();

	@PersistenceContext
	private EntityManager manager;

	public CampanhaDAO() {
	}

	@VisibleForTesting
	public CampanhaDAO(EntityManager em) {
		this.manager = em;
	}

	
	@Transactional
	public void insere(CampanhaModel campanha) {
		manager.persist(campanha);
		manager.flush();
		manager.detach(campanha);
	}

	@Transactional
	public void altera(CampanhaModel campanha) {
		manager.merge(campanha);
		manager.flush();
		manager.detach(campanha);
	}

	@Transactional
	public void exclui(CampanhaModel campanha) {
		manager.remove(manager.find(CampanhaModel.class, campanha.getId()));
	}

	public List<CampanhaModel> listaTudo() {
		String jpql = "SELECT a FROM " + TABELA + " a ORDER BY a.ordem";
		TypedQuery<CampanhaModel> query = manager.createQuery(jpql, CampanhaModel.class);
		return query.getResultList();
	}
	
	public List<CampanhaModel> buscaPorNomeExato(String nome) {

		String jpql = "SELECT c FROM " + TABELA + " c WHERE c.nome = :nome ";

		TypedQuery<CampanhaModel> query = manager.createQuery(jpql, CampanhaModel.class);

		query.setParameter("nome", nome);

		return query.getResultList();
	}
	
	public CampanhaModel buscaPorOrdem(Integer ordem) {

		String jpql = "SELECT a FROM " + TABELA + " a WHERE a.ordem = :ordem ";

		TypedQuery<CampanhaModel> query = manager.createQuery(jpql, CampanhaModel.class);

		query.setParameter("ordem", ordem);

		List<CampanhaModel> resultado = query.getResultList();

		if(resultado.size() == 0){
			return new CampanhaModel();
		}else{
			return resultado.get(0);
		}
	}

	public CampanhaModel buscaPorId(Long id) {

		String jpql = "SELECT a FROM " + TABELA + " a WHERE a.id = :id ";

		TypedQuery<CampanhaModel> query = manager.createQuery(jpql, CampanhaModel.class);

		query.setParameter("id", id);

		List<CampanhaModel> resultado = query.getResultList();

		if(resultado.size() == 0){
			return new CampanhaModel();
		}else{
			return resultado.get(0);
		}
	}

	public CampanhaModel buscaPorIdClonando(Long id) {
		CampanhaModel encontrado = buscaPorId(id);
		Gson gson = new Gson();
		return gson.fromJson(gson.toJson(encontrado), CampanhaModel.class);
	}

	public List<CampanhaModel> buscaPorNome(String nome) {

		String jpql = "SELECT a FROM " + TABELA + " a WHERE a.nome LIKE  :nome ";

		TypedQuery<CampanhaModel> query = manager.createQuery(jpql, CampanhaModel.class);

		query.setParameter("nome", "%"+nome+"%");

		return query.getResultList();
	}

	public List<CampanhaModel> buscaPorDataIncl(Calendar dataIncl) {

		String jpql = "SELECT a FROM " + TABELA + " a WHERE a.dataIncl =  :dataIncl ";

		TypedQuery<CampanhaModel> query = manager.createQuery(jpql, CampanhaModel.class);

		query.setParameter("dataIncl", dataIncl);

		return query.getResultList();
	}

	public List<CampanhaModel> buscaPorDataAlt(Calendar dataAlt) {

		String jpql = "SELECT a FROM " + TABELA + " a WHERE a.dataAlt =  :dataAlt ";

		TypedQuery<CampanhaModel> query = manager.createQuery(jpql, CampanhaModel.class);

		query.setParameter("dataAlt", dataAlt);

		return query.getResultList();
	}

}