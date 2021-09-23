package br.com.gift4us.historicodosistema;

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
public class HistoricoDoSistemaDAO {

	private final String TABELA = HistoricoDoSistemaModel.class.getSimpleName();

	@PersistenceContext
	private EntityManager manager;

	public HistoricoDoSistemaDAO() {
	}

	@VisibleForTesting
	public HistoricoDoSistemaDAO(EntityManager em) {
		this.manager = em;
	}

	
	@Transactional
	public void insere(HistoricoDoSistemaModel historicodosistema) {
		manager.persist(historicodosistema);
		manager.flush();
		manager.detach(historicodosistema);
	}

	public List<HistoricoDoSistemaModel> listaTudo() {
		String jpql = "SELECT h FROM " + TABELA + " h ORDER BY h.id desc";
		TypedQuery<HistoricoDoSistemaModel> query = manager.createQuery(jpql, HistoricoDoSistemaModel.class);
		return query.getResultList();
	}

	public HistoricoDoSistemaModel buscaPorId(Long id) {

		String jpql = "SELECT h FROM " + TABELA + " h WHERE h.id = :id ";

		TypedQuery<HistoricoDoSistemaModel> query = manager.createQuery(jpql, HistoricoDoSistemaModel.class);

		query.setParameter("id", id);

		List<HistoricoDoSistemaModel> resultado = query.getResultList();

		if(resultado.size() == 0){
			return new HistoricoDoSistemaModel();
		}else{
			return resultado.get(0);
		}
	}

	public HistoricoDoSistemaModel buscaPorIdClonando(Long id) {
		HistoricoDoSistemaModel encontrado = buscaPorId(id);
		Gson gson = new Gson();
		return gson.fromJson(gson.toJson(encontrado), HistoricoDoSistemaModel.class);
	}

	public List<HistoricoDoSistemaModel> buscaPorLogin(String login) {

		String jpql = "SELECT h FROM " + TABELA + " h WHERE h.login LIKE  :login ";

		TypedQuery<HistoricoDoSistemaModel> query = manager.createQuery(jpql, HistoricoDoSistemaModel.class);

		query.setParameter("login", login);

		return query.getResultList();
	}

	public List<HistoricoDoSistemaModel> buscaPorNome(String nome) {

		String jpql = "SELECT h FROM " + TABELA + " h WHERE h.nome LIKE  :nome ";

		TypedQuery<HistoricoDoSistemaModel> query = manager.createQuery(jpql, HistoricoDoSistemaModel.class);

		query.setParameter("nome", "%"+nome+"%");

		return query.getResultList();
	}

	public List<HistoricoDoSistemaModel> buscaPorDatahora(Calendar datahora) {

		String jpql = "SELECT h FROM " + TABELA + " h WHERE h.datahora =  :datahora ";

		TypedQuery<HistoricoDoSistemaModel> query = manager.createQuery(jpql, HistoricoDoSistemaModel.class);

		query.setParameter("datahora", datahora);

		return query.getResultList();
	}

	public List<HistoricoDoSistemaModel> buscaPorLocal(String local) {

		String jpql = "SELECT h FROM " + TABELA + " h WHERE h.local LIKE  :local ";

		TypedQuery<HistoricoDoSistemaModel> query = manager.createQuery(jpql, HistoricoDoSistemaModel.class);

		query.setParameter("local", "%"+local+"%");

		return query.getResultList();
	}

	public List<HistoricoDoSistemaModel> buscaPorAcao(String acao) {

		String jpql = "SELECT h FROM " + TABELA + " h WHERE h.acao LIKE  :acao ";

		TypedQuery<HistoricoDoSistemaModel> query = manager.createQuery(jpql, HistoricoDoSistemaModel.class);

		query.setParameter("acao", "%"+acao+"%");

		return query.getResultList();
	}

	public List<HistoricoDoSistemaModel> buscaPorDados(String dados) {

		String jpql = "SELECT h FROM " + TABELA + " h WHERE h.dados LIKE  :dados ";

		TypedQuery<HistoricoDoSistemaModel> query = manager.createQuery(jpql, HistoricoDoSistemaModel.class);

		query.setParameter("dados", "%"+dados+"%");

		return query.getResultList();
	}

}