package br.com.gift4us.anunciante;

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

import br.com.gift4us.anunciantetipo.AnuncianteTipoModel;
import br.com.gift4us.categoria.CategoriaModel;
import br.com.gift4us.subcategoria.SubCategoriaModel;

import java.util.Map;
import org.springframework.security.core.userdetails.UserDetails;
import com.google.common.annotations.VisibleForTesting;
import org.springframework.stereotype.Repository;

@Repository
public class AnuncianteDAO {

	private final String TABELA = AnuncianteModel.class.getSimpleName();

	@PersistenceContext
	private EntityManager manager;

	public AnuncianteDAO() {
	}

	@VisibleForTesting
	public AnuncianteDAO(EntityManager em) {
		this.manager = em;
	}

	
	@Transactional
	public void insere(AnuncianteModel anunciante) {
		manager.persist(anunciante);
		manager.flush();
		manager.detach(anunciante);
	}

	@Transactional
	public void altera(AnuncianteModel anunciante) {
		manager.merge(anunciante);
		manager.flush();
		manager.detach(anunciante);
	}

	@Transactional
	public void exclui(AnuncianteModel anunciante) {
		manager.remove(manager.find(AnuncianteModel.class, anunciante.getId()));
	}

	public List<AnuncianteModel> listaTudo() {
		String jpql = "SELECT a FROM " + TABELA + " a ORDER BY a.id";
		TypedQuery<AnuncianteModel> query = manager.createQuery(jpql, AnuncianteModel.class);
		return query.getResultList();
	}

	public AnuncianteModel buscaPorId(Long id) {

		String jpql = "SELECT a FROM " + TABELA + " a WHERE a.id = :id ";

		TypedQuery<AnuncianteModel> query = manager.createQuery(jpql, AnuncianteModel.class);

		query.setParameter("id", id);

		List<AnuncianteModel> resultado = query.getResultList();

		if(resultado.size() == 0){
			return new AnuncianteModel();
		}else{
			return resultado.get(0);
		}
	}

	public AnuncianteModel buscaPorIdClonando(Long id) {
		AnuncianteModel encontrado = buscaPorId(id);
		Gson gson = new Gson();
		return gson.fromJson(gson.toJson(encontrado), AnuncianteModel.class);
	}

	public List<AnuncianteModel> buscaPorRazaosocial(String razaosocial) {

		String jpql = "SELECT a FROM " + TABELA + " a WHERE a.razaosocial LIKE  :razaosocial ";

		TypedQuery<AnuncianteModel> query = manager.createQuery(jpql, AnuncianteModel.class);

		query.setParameter("razaosocial", "%"+razaosocial+"%");

		return query.getResultList();
	}
	
	public List<AnuncianteModel> buscaPorRazaosocialExato(String razaosocial) {

		String jpql = "SELECT a FROM " + TABELA + " a WHERE a.razaosocial = :razaosocial";

		TypedQuery<AnuncianteModel> query = manager.createQuery(jpql, AnuncianteModel.class);

		query.setParameter("razaosocial", razaosocial);

		return query.getResultList();
	}
	
	

	public List<AnuncianteModel> buscaPorFantasia(String fantasia) {

		String jpql = "SELECT a FROM " + TABELA + " a WHERE a.fantasia LIKE  :fantasia ";

		TypedQuery<AnuncianteModel> query = manager.createQuery(jpql, AnuncianteModel.class);

		query.setParameter("fantasia", "%"+fantasia+"%");

		return query.getResultList();
	}

	public List<AnuncianteModel> buscaPorEmail(String email) {

		String jpql = "SELECT a FROM " + TABELA + " a WHERE a.email LIKE  :email ";

		TypedQuery<AnuncianteModel> query = manager.createQuery(jpql, AnuncianteModel.class);

		query.setParameter("email", "%"+email+"%");

		return query.getResultList();
	}

	public List<AnuncianteModel> buscaPorDdd(String ddd) {

		String jpql = "SELECT a FROM " + TABELA + " a WHERE a.ddd LIKE  :ddd ";

		TypedQuery<AnuncianteModel> query = manager.createQuery(jpql, AnuncianteModel.class);

		query.setParameter("ddd", "%"+ddd+"%");

		return query.getResultList();
	}

	public List<AnuncianteModel> buscaPorTelefone(String telefone) {

		String jpql = "SELECT a FROM " + TABELA + " a WHERE a.telefone LIKE  :telefone ";

		TypedQuery<AnuncianteModel> query = manager.createQuery(jpql, AnuncianteModel.class);

		query.setParameter("telefone", "%"+telefone+"%");

		return query.getResultList();
	}

	public List<AnuncianteModel> buscaPorDataIncl(Calendar dataIncl) {

		String jpql = "SELECT a FROM " + TABELA + " a WHERE a.dataIncl =  :dataIncl ";

		TypedQuery<AnuncianteModel> query = manager.createQuery(jpql, AnuncianteModel.class);

		query.setParameter("dataIncl", dataIncl);

		return query.getResultList();
	}

	public List<AnuncianteModel> buscaPorDataAlt(Calendar dataAlt) {

		String jpql = "SELECT a FROM " + TABELA + " a WHERE a.dataAlt =  :dataAlt ";

		TypedQuery<AnuncianteModel> query = manager.createQuery(jpql, AnuncianteModel.class);

		query.setParameter("dataAlt", dataAlt);

		return query.getResultList();
	}

}