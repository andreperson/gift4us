package br.com.gift4us.status;

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
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetails;
import com.google.common.annotations.VisibleForTesting;
import org.springframework.stereotype.Repository;

@Repository
public class StatusDAO {

	private final String TABELA = StatusModel.class.getSimpleName();

	@PersistenceContext
	private EntityManager manager;

	public StatusDAO() {
	}

	@VisibleForTesting
	public StatusDAO(EntityManager em) {
		this.manager = em;
	}

	
	@Transactional
	public void insere(StatusModel status) {
		manager.persist(status);
		manager.flush();
		manager.detach(status);
	}

	@Transactional
	public void altera(StatusModel status) {
		manager.merge(status);
		manager.flush();
		manager.detach(status);
	}

	@Transactional
	public void exclui(StatusModel status) {
		manager.remove(manager.find(StatusModel.class, status.getId()));
	}

	public List<StatusModel> listaTudo() {
		String jpql = "SELECT s FROM " + TABELA + " s ORDER BY s.id";
		TypedQuery<StatusModel> query = manager.createQuery(jpql, StatusModel.class);
		return query.getResultList();
	}

	public StatusModel buscaPorId(Long id) {

		String jpql = "SELECT s FROM " + TABELA + " s WHERE s.id = :id ";

		TypedQuery<StatusModel> query = manager.createQuery(jpql, StatusModel.class);

		query.setParameter("id", id);

		List<StatusModel> resultado = query.getResultList();

		if(resultado.size() == 0){
			return new StatusModel();
		}else{
			return resultado.get(0);
		}
	}

	public StatusModel buscaPorIdClonando(Long id) {
		StatusModel encontrado = buscaPorId(id);
		Gson gson = new Gson();
		return gson.fromJson(gson.toJson(encontrado), StatusModel.class);
	}

	public List<StatusModel> buscaPorNome(String nome) {

		String jpql = "SELECT s FROM " + TABELA + " s WHERE s.nome LIKE  :nome ";

		TypedQuery<StatusModel> query = manager.createQuery(jpql, StatusModel.class);

		query.setParameter("nome", "%"+nome+"%");

		return query.getResultList();
	}
	
	public List<StatusModel> buscaPorNomeExato(String nome) {

		String jpql = "SELECT s FROM " + TABELA + " s WHERE s.nome = :nome ";

		TypedQuery<StatusModel> query = manager.createQuery(jpql, StatusModel.class);

		query.setParameter("nome", nome);

		return query.getResultList();
	}

	public List<StatusModel> buscaPorDataIncl(Calendar dataIncl) {

		String jpql = "SELECT s FROM " + TABELA + " s WHERE s.dataIncl =  :dataIncl ";

		TypedQuery<StatusModel> query = manager.createQuery(jpql, StatusModel.class);

		query.setParameter("dataIncl", dataIncl);

		return query.getResultList();
	}

	public List<StatusModel> buscaPorDataAlt(Calendar dataAlt) {

		String jpql = "SELECT s FROM " + TABELA + " s WHERE s.dataAlt =  :dataAlt ";

		TypedQuery<StatusModel> query = manager.createQuery(jpql, StatusModel.class);

		query.setParameter("dataAlt", dataAlt);

		return query.getResultList();
	}

}