package br.com.gift4us.subcategoria;

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
public class SubCategoriaDAO {

	private final String TABELA = SubCategoriaModel.class.getSimpleName();

	@PersistenceContext
	private EntityManager manager;

	public SubCategoriaDAO() {
	}

	@VisibleForTesting
	public SubCategoriaDAO(EntityManager em) {
		this.manager = em;
	}

	
	@Transactional
	public void insere(SubCategoriaModel subcategoria) {
		manager.persist(subcategoria);
		manager.flush();
		manager.detach(subcategoria);
	}

	@Transactional
	public void altera(SubCategoriaModel subcategoria) {
		manager.merge(subcategoria);
		manager.flush();
		manager.detach(subcategoria);
	}

	@Transactional
	public void exclui(SubCategoriaModel subcategoria) {
		manager.remove(manager.find(SubCategoriaModel.class, subcategoria.getId()));
	}

	public List<SubCategoriaModel> listaTudo() {
		String jpql = "SELECT s FROM " + TABELA + " s ORDER BY s.id";
		TypedQuery<SubCategoriaModel> query = manager.createQuery(jpql, SubCategoriaModel.class);
		return query.getResultList();
	}

	public SubCategoriaModel buscaPorId(Long id) {

		String jpql = "SELECT s FROM " + TABELA + " s WHERE s.id = :id ";

		TypedQuery<SubCategoriaModel> query = manager.createQuery(jpql, SubCategoriaModel.class);

		query.setParameter("id", id);

		List<SubCategoriaModel> resultado = query.getResultList();

		if(resultado.size() == 0){
			return new SubCategoriaModel();
		}else{
			return resultado.get(0);
		}
	}

	public SubCategoriaModel buscaPorIdClonando(Long id) {
		SubCategoriaModel encontrado = buscaPorId(id);
		Gson gson = new Gson();
		return gson.fromJson(gson.toJson(encontrado), SubCategoriaModel.class);
	}

	public List<SubCategoriaModel> buscaPorNome(String nome) {

		String jpql = "SELECT s FROM " + TABELA + " s WHERE s.nome LIKE  :nome ";

		TypedQuery<SubCategoriaModel> query = manager.createQuery(jpql, SubCategoriaModel.class);

		query.setParameter("nome", "%"+nome+"%");

		return query.getResultList();
	}

	public List<SubCategoriaModel> buscaPorDataIncl(Calendar dataIncl) {

		String jpql = "SELECT s FROM " + TABELA + " s WHERE s.dataIncl =  :dataIncl ";

		TypedQuery<SubCategoriaModel> query = manager.createQuery(jpql, SubCategoriaModel.class);

		query.setParameter("dataIncl", dataIncl);

		return query.getResultList();
	}

	public List<SubCategoriaModel> buscaPorDataAlt(Calendar dataAlt) {

		String jpql = "SELECT s FROM " + TABELA + " s WHERE s.dataAlt =  :dataAlt ";

		TypedQuery<SubCategoriaModel> query = manager.createQuery(jpql, SubCategoriaModel.class);

		query.setParameter("dataAlt", dataAlt);

		return query.getResultList();
	}

}