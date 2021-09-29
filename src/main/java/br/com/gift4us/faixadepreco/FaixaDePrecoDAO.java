package br.com.gift4us.faixadepreco;

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
public class FaixaDePrecoDAO {

	private final String TABELA = FaixaDePrecoModel.class.getSimpleName();

	@PersistenceContext
	private EntityManager manager;

	public FaixaDePrecoDAO() {
	}

	@VisibleForTesting
	public FaixaDePrecoDAO(EntityManager em) {
		this.manager = em;
	}

	
	@Transactional
	public void insere(FaixaDePrecoModel categoria) {
		manager.persist(categoria);
		manager.flush();
		manager.detach(categoria);
	}

	@Transactional
	public void altera(FaixaDePrecoModel categoria) {
		manager.merge(categoria);
		manager.flush();
		manager.detach(categoria);
	}

	@Transactional
	public void exclui(FaixaDePrecoModel categoria) {
		manager.remove(manager.find(FaixaDePrecoModel.class, categoria.getId()));
	}

	public List<FaixaDePrecoModel> listaTudo() {
		String jpql = "SELECT c FROM " + TABELA + " c ORDER BY c.id";
		TypedQuery<FaixaDePrecoModel> query = manager.createQuery(jpql, FaixaDePrecoModel.class);
		return query.getResultList();
	}

	public FaixaDePrecoModel buscaPorId(Long id) {

		String jpql = "SELECT c FROM " + TABELA + " c WHERE c.id = :id ";

		TypedQuery<FaixaDePrecoModel> query = manager.createQuery(jpql, FaixaDePrecoModel.class);

		query.setParameter("id", id);

		List<FaixaDePrecoModel> resultado = query.getResultList();

		if(resultado.size() == 0){
			return new FaixaDePrecoModel();
		}else{
			return resultado.get(0);
		}
	}

	public FaixaDePrecoModel buscaPorIdClonando(Long id) {
		FaixaDePrecoModel encontrado = buscaPorId(id);
		Gson gson = new Gson();
		return gson.fromJson(gson.toJson(encontrado), FaixaDePrecoModel.class);
	}

	public List<FaixaDePrecoModel> buscaPorNome(String nome) {

		String jpql = "SELECT c FROM " + TABELA + " c WHERE c.nome LIKE  :nome ";

		TypedQuery<FaixaDePrecoModel> query = manager.createQuery(jpql, FaixaDePrecoModel.class);

		query.setParameter("nome", "%"+nome+"%");

		return query.getResultList();
	}
	
	
	public List<FaixaDePrecoModel> buscaPorNomeExato(String nome) {

		String jpql = "SELECT c FROM " + TABELA + " c WHERE c.nome = :nome ";

		TypedQuery<FaixaDePrecoModel> query = manager.createQuery(jpql, FaixaDePrecoModel.class);

		query.setParameter("nome", nome);

		return query.getResultList();
	}
	

	public List<FaixaDePrecoModel> buscaPorDataIncl(Calendar dataIncl) {

		String jpql = "SELECT c FROM " + TABELA + " c WHERE c.dataIncl =  :dataIncl ";

		TypedQuery<FaixaDePrecoModel> query = manager.createQuery(jpql, FaixaDePrecoModel.class);

		query.setParameter("dataIncl", dataIncl);

		return query.getResultList();
	}

	public List<FaixaDePrecoModel> buscaPorDataAlt(Calendar dataAlt) {

		String jpql = "SELECT c FROM " + TABELA + " c WHERE c.dataAlt =  :dataAlt ";

		TypedQuery<FaixaDePrecoModel> query = manager.createQuery(jpql, FaixaDePrecoModel.class);

		query.setParameter("dataAlt", dataAlt);

		return query.getResultList();
	}

}