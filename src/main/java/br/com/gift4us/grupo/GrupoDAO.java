package br.com.gift4us.grupo;

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
public class GrupoDAO {

	private final String TABELA = GrupoModel.class.getSimpleName();

	@PersistenceContext
	private EntityManager manager;

	public GrupoDAO() {
	}

	@VisibleForTesting
	public GrupoDAO(EntityManager em) {
		this.manager = em;
	}

	
	@Transactional
	public void insere(GrupoModel grupo) {
		manager.persist(grupo);
		manager.flush();
		manager.detach(grupo);
	}

	@Transactional
	public void altera(GrupoModel grupo) {
		manager.merge(grupo);
		manager.flush();
		manager.detach(grupo);
	}

	@Transactional
	public void exclui(GrupoModel grupo) {
		manager.remove(manager.find(GrupoModel.class, grupo.getId()));
	}

	public List<GrupoModel> listaTudo() {
		String jpql = "SELECT g FROM " + TABELA + " g ORDER BY g.id";
		TypedQuery<GrupoModel> query = manager.createQuery(jpql, GrupoModel.class);
		return query.getResultList();
	}

	public GrupoModel buscaPorId(Long id) {

		String jpql = "SELECT g FROM " + TABELA + " g WHERE g.id = :id ";

		TypedQuery<GrupoModel> query = manager.createQuery(jpql, GrupoModel.class);

		query.setParameter("id", id);

		List<GrupoModel> resultado = query.getResultList();

		if(resultado.size() == 0){
			return new GrupoModel();
		}else{
			return resultado.get(0);
		}
	}

	public GrupoModel buscaPorIdClonando(Long id) {
		GrupoModel encontrado = buscaPorId(id);
		Gson gson = new Gson();
		return gson.fromJson(gson.toJson(encontrado), GrupoModel.class);
	}

	public List<GrupoModel> buscaPorNome(String nome) {

		String jpql = "SELECT g FROM " + TABELA + " g WHERE g.nome LIKE  :nome ";

		TypedQuery<GrupoModel> query = manager.createQuery(jpql, GrupoModel.class);

		query.setParameter("nome", "%"+nome+"%");

		return query.getResultList();
	}


	@Transactional
	public void insertsParaSeremUtilizadosNoPostConstruct() {
		List<GrupoModel> lista = listaTudo();
		criaSeNaoExistir(lista, "ROLE_USUARIO_LOGADO");
		criaSeNaoExistir(lista, "ROLE_ADMIN");
		criaSeNaoExistir(lista, "ROLE_ANUNCIANTE");
		criaSeNaoExistir(lista, "ROLE_GERENCIAL");
		criaSeNaoExistir(lista, "ROLE_CONFIGURACOES");
		criaSeNaoExistir(lista, "ROLE_REDEFINIR_SENHA");
	}

	@Transactional
	public void insertsGrupoNoUsuarioPostConstruct(Long usuarioId, Long grupoId) {
		String sql = "INSERT INTO USUARIO_GRUPO (usuario_id, listadegrupo_id) VALUES (%d, %d)";
		sql = String.format(sql, usuarioId, grupoId);
		manager.createNativeQuery(sql).executeUpdate();
	}

	private void criaSeNaoExistir(List<GrupoModel> lista, String role) {
		boolean existe = false;
		for (GrupoModel grupo : lista) {
			if (grupo.getNome().equals(role)) {
				existe = true;
				break;
			}
		}
		if (!existe) {
			GrupoModel grupo = new GrupoModel(null, role);
			manager.persist(grupo);
		}
}

}