package br.com.gift4us.atividade;

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
public class AtividadeDAO {

	private final String TABELA = AtividadeModel.class.getSimpleName();

	@PersistenceContext
	private EntityManager manager;

	public AtividadeDAO() {
	}

	@VisibleForTesting
	public AtividadeDAO(EntityManager em) {
		this.manager = em;
	}

	
	@Transactional
	public void insere(AtividadeModel atividade) {
		manager.persist(atividade);
		manager.flush();
		manager.detach(atividade);
	}

	@Transactional
	public void altera(AtividadeModel atividade) {
		manager.merge(atividade);
		manager.flush();
		manager.detach(atividade);
	}

	@Transactional
	public void exclui(AtividadeModel atividade) {
		manager.remove(manager.find(AtividadeModel.class, atividade.getId()));
	}

	public List<AtividadeModel> listaTudo() {
		String jpql = "SELECT a FROM " + TABELA + " a ORDER BY a.id";
		TypedQuery<AtividadeModel> query = manager.createQuery(jpql, AtividadeModel.class);
		return query.getResultList();
	}

	public AtividadeModel buscaPorId(Long id) {

		String jpql = "SELECT a FROM " + TABELA + " a WHERE a.id = :id ";

		TypedQuery<AtividadeModel> query = manager.createQuery(jpql, AtividadeModel.class);

		query.setParameter("id", id);

		List<AtividadeModel> resultado = query.getResultList();

		if(resultado.size() == 0){
			return new AtividadeModel();
		}else{
			return resultado.get(0);
		}
	}

	public AtividadeModel buscaPorIdClonando(Long id) {
		AtividadeModel encontrado = buscaPorId(id);
		Gson gson = new Gson();
		return gson.fromJson(gson.toJson(encontrado), AtividadeModel.class);
	}

	public List<AtividadeModel> buscaPorNome(String nome) {

		String jpql = "SELECT a FROM " + TABELA + " a WHERE a.nome LIKE  :nome ";

		TypedQuery<AtividadeModel> query = manager.createQuery(jpql, AtividadeModel.class);

		query.setParameter("nome", "%"+nome+"%");

		return query.getResultList();
	}

	public List<AtividadeModel> buscaPorDataIncl(Calendar dataIncl) {

		String jpql = "SELECT a FROM " + TABELA + " a WHERE a.dataIncl =  :dataIncl ";

		TypedQuery<AtividadeModel> query = manager.createQuery(jpql, AtividadeModel.class);

		query.setParameter("dataIncl", dataIncl);

		return query.getResultList();
	}

	public List<AtividadeModel> buscaPorDataAlt(Calendar dataAlt) {

		String jpql = "SELECT a FROM " + TABELA + " a WHERE a.dataAlt =  :dataAlt ";

		TypedQuery<AtividadeModel> query = manager.createQuery(jpql, AtividadeModel.class);

		query.setParameter("dataAlt", dataAlt);

		return query.getResultList();
	}

}