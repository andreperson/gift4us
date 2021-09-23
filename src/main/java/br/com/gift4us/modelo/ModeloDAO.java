package br.com.gift4us.modelo;

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
public class ModeloDAO {

	private final String TABELA = ModeloModel.class.getSimpleName();

	@PersistenceContext
	private EntityManager manager;

	public ModeloDAO() {
	}

	@VisibleForTesting
	public ModeloDAO(EntityManager em) {
		this.manager = em;
	}

	
	@Transactional
	public void insere(ModeloModel modelo) {
		manager.persist(modelo);
		manager.flush();
		manager.detach(modelo);
	}

	@Transactional
	public void altera(ModeloModel modelo) {
		manager.merge(modelo);
		manager.flush();
		manager.detach(modelo);
	}

	@Transactional
	public void exclui(ModeloModel modelo) {
		manager.remove(manager.find(ModeloModel.class, modelo.getId()));
	}

	public List<ModeloModel> listaTudo() {
		String jpql = "SELECT m FROM " + TABELA + " m ORDER BY m.id";
		TypedQuery<ModeloModel> query = manager.createQuery(jpql, ModeloModel.class);
		return query.getResultList();
	}

	public ModeloModel buscaPorId(Long id) {

		String jpql = "SELECT m FROM " + TABELA + " m WHERE m.id = :id ";

		TypedQuery<ModeloModel> query = manager.createQuery(jpql, ModeloModel.class);

		query.setParameter("id", id);

		List<ModeloModel> resultado = query.getResultList();

		if(resultado.size() == 0){
			return new ModeloModel();
		}else{
			return resultado.get(0);
		}
	}

	public ModeloModel buscaPorIdClonando(Long id) {
		ModeloModel encontrado = buscaPorId(id);
		Gson gson = new Gson();
		return gson.fromJson(gson.toJson(encontrado), ModeloModel.class);
	}

	public List<ModeloModel> buscaPorDescricao(String descricao) {

		String jpql = "SELECT m FROM " + TABELA + " m WHERE m.descricao LIKE  :descricao ";

		TypedQuery<ModeloModel> query = manager.createQuery(jpql, ModeloModel.class);

		query.setParameter("descricao", "%"+descricao+"%");

		return query.getResultList();
	}

	public List<ModeloModel> buscaPorTitulo(String titulo) {

		String jpql = "SELECT m FROM " + TABELA + " m WHERE m.titulo LIKE  :titulo ";

		TypedQuery<ModeloModel> query = manager.createQuery(jpql, ModeloModel.class);

		query.setParameter("titulo", "%"+titulo+"%");

		return query.getResultList();
	}

	public List<ModeloModel> buscaPorCorTitulo(String corTitulo) {

		String jpql = "SELECT m FROM " + TABELA + " m WHERE m.corTitulo LIKE  :corTitulo ";

		TypedQuery<ModeloModel> query = manager.createQuery(jpql, ModeloModel.class);

		query.setParameter("corTitulo", "%"+corTitulo+"%");

		return query.getResultList();
	}

	public List<ModeloModel> buscaPorImagemUnica(String imagemUnica) {

		String jpql = "SELECT m FROM " + TABELA + " m WHERE m.imagemUnica =  :imagemUnica ";

		TypedQuery<ModeloModel> query = manager.createQuery(jpql, ModeloModel.class);

		query.setParameter("imagemUnica", imagemUnica);

		return query.getResultList();
	}

	public List<ModeloModel> buscaPorImagemCabecalho(String imagemCabecalho) {

		String jpql = "SELECT m FROM " + TABELA + " m WHERE m.imagemCabecalho =  :imagemCabecalho ";

		TypedQuery<ModeloModel> query = manager.createQuery(jpql, ModeloModel.class);

		query.setParameter("imagemCabecalho", imagemCabecalho);

		return query.getResultList();
	}

	public List<ModeloModel> buscaPorImagemRodape(String imagemRodape) {

		String jpql = "SELECT m FROM " + TABELA + " m WHERE m.imagemRodape =  :imagemRodape ";

		TypedQuery<ModeloModel> query = manager.createQuery(jpql, ModeloModel.class);

		query.setParameter("imagemRodape", imagemRodape);

		return query.getResultList();
	}

	public List<ModeloModel> buscaPorAtivo(Boolean ativo) {

		String jpql = "SELECT m FROM " + TABELA + " m WHERE m.ativo =  :ativo ";

		TypedQuery<ModeloModel> query = manager.createQuery(jpql, ModeloModel.class);

		query.setParameter("ativo", ativo);

		return query.getResultList();
	}

}