package br.com.gift4us.anunciantetipo;

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
public class AnuncianteTipoDAO {

	private final String TABELA = AnuncianteTipoModel.class.getSimpleName();

	@PersistenceContext
	private EntityManager manager;

	public AnuncianteTipoDAO() {
	}

	@VisibleForTesting
	public AnuncianteTipoDAO(EntityManager em) {
		this.manager = em;
	}

	
	@Transactional
	public void insere(AnuncianteTipoModel anunciantetipo) {
		manager.persist(anunciantetipo);
		manager.flush();
		manager.detach(anunciantetipo);
	}

	@Transactional
	public void altera(AnuncianteTipoModel anunciantetipo) {
		manager.merge(anunciantetipo);
		manager.flush();
		manager.detach(anunciantetipo);
	}

	@Transactional
	public void exclui(AnuncianteTipoModel anunciantetipo) {
		manager.remove(manager.find(AnuncianteTipoModel.class, anunciantetipo.getId()));
	}

	public List<AnuncianteTipoModel> listaTudo() {
		String jpql = "SELECT a FROM " + TABELA + " a ORDER BY a.id";
		TypedQuery<AnuncianteTipoModel> query = manager.createQuery(jpql, AnuncianteTipoModel.class);
		return query.getResultList();
	}

	public AnuncianteTipoModel buscaPorId(Long id) {

		String jpql = "SELECT a FROM " + TABELA + " a WHERE a.id = :id ";

		TypedQuery<AnuncianteTipoModel> query = manager.createQuery(jpql, AnuncianteTipoModel.class);

		query.setParameter("id", id);

		List<AnuncianteTipoModel> resultado = query.getResultList();

		if(resultado.size() == 0){
			return new AnuncianteTipoModel();
		}else{
			return resultado.get(0);
		}
	}

	public AnuncianteTipoModel buscaPorIdClonando(Long id) {
		AnuncianteTipoModel encontrado = buscaPorId(id);
		Gson gson = new Gson();
		return gson.fromJson(gson.toJson(encontrado), AnuncianteTipoModel.class);
	}

	public List<AnuncianteTipoModel> buscaPorNome(String nome) {

		String jpql = "SELECT a FROM " + TABELA + " a WHERE a.nome LIKE  :nome ";

		TypedQuery<AnuncianteTipoModel> query = manager.createQuery(jpql, AnuncianteTipoModel.class);

		query.setParameter("nome", "%"+nome+"%");

		return query.getResultList();
	}

	
	
	public List<AnuncianteTipoModel> buscaPorNomeExato(String nome) {

		String jpql = "SELECT a FROM " + TABELA + " a WHERE a.nome LIKE  :nome ";


		TypedQuery<AnuncianteTipoModel> query = manager.createQuery(jpql, AnuncianteTipoModel.class);

		query.setParameter("nome", nome);

		return query.getResultList();
	}
	
	
	
	public List<AnuncianteTipoModel> buscaPorDataIncl(Calendar dataIncl) {

		String jpql = "SELECT a FROM " + TABELA + " a WHERE a.dataIncl =  :dataIncl ";

		TypedQuery<AnuncianteTipoModel> query = manager.createQuery(jpql, AnuncianteTipoModel.class);

		query.setParameter("dataIncl", dataIncl);

		return query.getResultList();
	}

	public List<AnuncianteTipoModel> buscaPorDataAlt(Calendar dataAlt) {

		String jpql = "SELECT a FROM " + TABELA + " a WHERE a.dataAlt =  :dataAlt ";

		TypedQuery<AnuncianteTipoModel> query = manager.createQuery(jpql, AnuncianteTipoModel.class);

		query.setParameter("dataAlt", dataAlt);

		return query.getResultList();
	}

}