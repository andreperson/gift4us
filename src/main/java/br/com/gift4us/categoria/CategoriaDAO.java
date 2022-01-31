package br.com.gift4us.categoria;

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

import br.com.gift4us.produto.ProdutoModel;

import java.util.Map;
import org.springframework.security.core.userdetails.UserDetails;
import com.google.common.annotations.VisibleForTesting;
import org.springframework.stereotype.Repository;

@Repository
public class CategoriaDAO {

	private final String TABELA = CategoriaModel.class.getSimpleName();

	@PersistenceContext
	private EntityManager manager;

	public CategoriaDAO() {
	}

	@VisibleForTesting
	public CategoriaDAO(EntityManager em) {
		this.manager = em;
	}

	@Transactional
	public Long saveorupdate(CategoriaModel categoria) {
		manager.persist(categoria);
		manager.flush();
		manager.detach(categoria);
		return categoria.getId();
	}
	
	
	@Transactional
	public void insere(CategoriaModel categoria) {
		manager.persist(categoria);
		manager.flush();
		manager.detach(categoria);
	}

	@Transactional
	public void altera(CategoriaModel categoria) {
		manager.merge(categoria);
		manager.flush();
		manager.detach(categoria);
	}

	@Transactional
	public void exclui(CategoriaModel categoria) {
		manager.remove(manager.find(CategoriaModel.class, categoria.getId()));
	}

	public List<CategoriaModel> listaTudo() {
		String jpql = "SELECT c FROM " + TABELA + " c ORDER BY c.id";
		TypedQuery<CategoriaModel> query = manager.createQuery(jpql, CategoriaModel.class);
		return query.getResultList();
	}
	
	public List<CategoriaModel> listaMaisVendidos() { 
		String jpql = "SELECT c FROM " + TABELA + " c where c.status=2 ORDER BY c.id";
		TypedQuery<CategoriaModel> query = manager.createQuery(jpql, CategoriaModel.class);
		return query.getResultList();
	}

	public CategoriaModel buscaPorId(Long id) {

		String jpql = "SELECT c FROM " + TABELA + " c WHERE c.id = :id ";

		TypedQuery<CategoriaModel> query = manager.createQuery(jpql, CategoriaModel.class);

		query.setParameter("id", id);

		List<CategoriaModel> resultado = query.getResultList();

		if(resultado.size() == 0){
			return new CategoriaModel();
		}else{
			return resultado.get(0);
		}
	}

	public CategoriaModel buscaPorIdClonando(Long id) {
		CategoriaModel encontrado = buscaPorId(id);
		Gson gson = new Gson();
		return gson.fromJson(gson.toJson(encontrado), CategoriaModel.class);
	}

	public List<CategoriaModel> buscaPorNome(String nome) {

		String jpql = "SELECT c FROM " + TABELA + " c WHERE c.nome LIKE  :nome ";

		TypedQuery<CategoriaModel> query = manager.createQuery(jpql, CategoriaModel.class);

		query.setParameter("nome", "%"+nome+"%");

		return query.getResultList();
	}
	
	
	public List<CategoriaModel> buscaPorNomeExato(String nome) {

		String jpql = "SELECT c FROM " + TABELA + " c WHERE c.nome = :nome ";

		TypedQuery<CategoriaModel> query = manager.createQuery(jpql, CategoriaModel.class);

		query.setParameter("nome", nome);

		return query.getResultList();
	}
	

	public List<CategoriaModel> buscaPorDataIncl(Calendar dataIncl) {

		String jpql = "SELECT c FROM " + TABELA + " c WHERE c.dataIncl =  :dataIncl ";

		TypedQuery<CategoriaModel> query = manager.createQuery(jpql, CategoriaModel.class);

		query.setParameter("dataIncl", dataIncl);

		return query.getResultList();
	}

	public List<CategoriaModel> buscaPorDataAlt(Calendar dataAlt) {

		String jpql = "SELECT c FROM " + TABELA + " c WHERE c.dataAlt =  :dataAlt ";

		TypedQuery<CategoriaModel> query = manager.createQuery(jpql, CategoriaModel.class);

		query.setParameter("dataAlt", dataAlt);

		return query.getResultList();
	}

}