package br.com.gift4us.orcamento;

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

import br.com.gift4us.anunciante.AnuncianteModel;
import br.com.gift4us.produto.ProdutoModel;

import java.util.Map;
import org.springframework.security.core.userdetails.UserDetails;
import com.google.common.annotations.VisibleForTesting;
import org.springframework.stereotype.Repository;

@Repository
public class OrcamentoDAO {

	private final String TABELA = OrcamentoModel.class.getSimpleName();

	@PersistenceContext
	private EntityManager manager;

	public OrcamentoDAO() {
	}

	@VisibleForTesting
	public OrcamentoDAO(EntityManager em) {
		this.manager = em;
	}

	
	@Transactional
	public void insere(OrcamentoModel orcamento) {
		manager.persist(orcamento);
		manager.flush();
		manager.detach(orcamento);
	}

	@Transactional
	public void altera(OrcamentoModel orcamento) {
		manager.merge(orcamento);
		manager.flush();
		manager.detach(orcamento);
	}

	@Transactional
	public void exclui(OrcamentoModel orcamento) {
		manager.remove(manager.find(OrcamentoModel.class, orcamento.getId()));
	}

	public List<OrcamentoModel> listaTudo() {
		String jpql = "SELECT o FROM " + TABELA + " o ORDER BY o.id";
		TypedQuery<OrcamentoModel> query = manager.createQuery(jpql, OrcamentoModel.class);
		return query.getResultList();
	}
	
	public OrcamentoModel buscaPorProdutoeAnunciante(ProdutoModel produto, AnuncianteModel anunciante) {

		String jpql = "SELECT o FROM " + TABELA + " o WHERE o.produto = :produto and o.anunciante = :anunciante";

		TypedQuery<OrcamentoModel> query = manager.createQuery(jpql, OrcamentoModel.class);

		query.setParameter("produto", produto);
		query.setParameter("anunciante", anunciante);

		List<OrcamentoModel> resultado = query.getResultList();

		if(resultado.size() == 0){
			return new OrcamentoModel();
		}else{
			return resultado.get(0);
		}
	}

	public OrcamentoModel buscaPorId(Long id) {

		String jpql = "SELECT o FROM " + TABELA + " o WHERE o.id = :id ";

		TypedQuery<OrcamentoModel> query = manager.createQuery(jpql, OrcamentoModel.class);

		query.setParameter("id", id);

		List<OrcamentoModel> resultado = query.getResultList();

		if(resultado.size() == 0){
			return new OrcamentoModel();
		}else{
			return resultado.get(0);
		}
	}

	public OrcamentoModel buscaPorIdClonando(Long id) {
		OrcamentoModel encontrado = buscaPorId(id);
		Gson gson = new Gson();
		return gson.fromJson(gson.toJson(encontrado), OrcamentoModel.class);
	}

	public List<OrcamentoModel> buscaPorAnunciante(AnuncianteModel anunciante) {

		String jpql = "SELECT o FROM " + TABELA + " o WHERE o.anunciante =  :anunciante ";

		TypedQuery<OrcamentoModel> query = manager.createQuery(jpql, OrcamentoModel.class);

		query.setParameter("anunciante", anunciante);

		return query.getResultList();
	}

	public List<OrcamentoModel> buscaPorDataIncl(Calendar dataIncl) {

		String jpql = "SELECT o FROM " + TABELA + " o WHERE o.dataIncl =  :dataIncl ";

		TypedQuery<OrcamentoModel> query = manager.createQuery(jpql, OrcamentoModel.class);

		query.setParameter("dataIncl", dataIncl);

		return query.getResultList();
	}

	public List<OrcamentoModel> buscaPorDataAlt(Calendar dataAlt) {

		String jpql = "SELECT o FROM " + TABELA + " o WHERE o.dataAlt =  :dataAlt ";

		TypedQuery<OrcamentoModel> query = manager.createQuery(jpql, OrcamentoModel.class);

		query.setParameter("dataAlt", dataAlt);

		return query.getResultList();
	}

}