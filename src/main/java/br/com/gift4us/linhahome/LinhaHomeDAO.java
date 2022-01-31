package br.com.gift4us.linhahome;

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
public class LinhaHomeDAO {

	private final String TABELA = LinhaHomeModel.class.getSimpleName();

	@PersistenceContext
	private EntityManager manager;

	public LinhaHomeDAO() {
	}

	@VisibleForTesting
	public LinhaHomeDAO(EntityManager em) {
		this.manager = em;
	}

	
	@Transactional
	public void insere(LinhaHomeModel linhahome) {
		manager.persist(linhahome);
		manager.flush();
		manager.detach(linhahome);
	}

	@Transactional
	public void altera(LinhaHomeModel linhahome) {
		manager.merge(linhahome);
		manager.flush();
		manager.detach(linhahome);
	}

	@Transactional
	public void exclui(LinhaHomeModel linha) {
		manager.remove(manager.find(LinhaHomeModel.class, linha.getId()));
	}

	public List<LinhaHomeModel> listaTudo() {
		String jpql = "SELECT a FROM " + TABELA + " a ORDER BY a.id";
		TypedQuery<LinhaHomeModel> query = manager.createQuery(jpql, LinhaHomeModel.class);
		return query.getResultList();
	}
	
	public List<LinhaHomeModel> buscaPorNomeExato(String nome) {

		String jpql = "SELECT c FROM " + TABELA + " c WHERE c.nome = :nome ";

		TypedQuery<LinhaHomeModel> query = manager.createQuery(jpql, LinhaHomeModel.class);

		query.setParameter("nome", nome);

		return query.getResultList();
	}
	

	public LinhaHomeModel buscaPorId(Long id) {

		String jpql = "SELECT a FROM " + TABELA + " a WHERE a.id = :id ";

		TypedQuery<LinhaHomeModel> query = manager.createQuery(jpql, LinhaHomeModel.class);

		query.setParameter("id", id);

		List<LinhaHomeModel> resultado = query.getResultList();

		if(resultado.size() == 0){
			return new LinhaHomeModel();
		}else{
			return resultado.get(0);
		}
	}

	public LinhaHomeModel buscaPorIdClonando(Long id) {
		LinhaHomeModel encontrado = buscaPorId(id);
		Gson gson = new Gson();
		return gson.fromJson(gson.toJson(encontrado), LinhaHomeModel.class);
	}

	public List<LinhaHomeModel> buscaPorNome(String nome) {

		String jpql = "SELECT a FROM " + TABELA + " a WHERE a.nome LIKE  :nome ";

		TypedQuery<LinhaHomeModel> query = manager.createQuery(jpql, LinhaHomeModel.class);

		query.setParameter("nome", "%"+nome+"%");

		return query.getResultList();
	}

	public List<LinhaHomeModel> buscaPorDataIncl(Calendar dataIncl) {

		String jpql = "SELECT a FROM " + TABELA + " a WHERE a.dataIncl =  :dataIncl ";

		TypedQuery<LinhaHomeModel> query = manager.createQuery(jpql, LinhaHomeModel.class);

		query.setParameter("dataIncl", dataIncl);

		return query.getResultList();
	}

	public List<LinhaHomeModel> buscaPorDataAlt(Calendar dataAlt) {

		String jpql = "SELECT a FROM " + TABELA + " a WHERE a.dataAlt =  :dataAlt ";

		TypedQuery<LinhaHomeModel> query = manager.createQuery(jpql, LinhaHomeModel.class);

		query.setParameter("dataAlt", dataAlt);

		return query.getResultList();
	}

}