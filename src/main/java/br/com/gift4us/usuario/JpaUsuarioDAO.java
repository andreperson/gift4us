package br.com.gift4us.usuario;

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
public class JpaUsuarioDAO implements UsuarioDAO {

	private final String TABELA = UsuarioModel.class.getSimpleName();

	@PersistenceContext
	private EntityManager manager;

	public JpaUsuarioDAO() {
	}

	@VisibleForTesting
	public JpaUsuarioDAO(EntityManager em) {
		this.manager = em;
	}

	@Transactional
	public void criaInsertDeUsuarioAdmin() {
		List<UsuarioModel> buscaPorLogin = this.buscaPorLogin("admin");

		if (buscaPorLogin != null && buscaPorLogin.size() == 0) {
			UsuarioModel usuario = new UsuarioModel();
			usuario.setSenha("admin");
			usuario.setNome("Admin - Deletar quando criar os proprios usuarios");
			usuario.setLogin("admin");
			usuario.setApelido("Admin");
			usuario.setEmail("gift4us@gift4us.com.br");
			manager.persist(usuario);
		}

	}

	@Transactional
	public void insere(UsuarioModel usuario) {
		manager.persist(usuario);
		manager.flush();
		manager.detach(usuario);
	}

	@Transactional
	public void altera(UsuarioModel usuario) {
		manager.merge(usuario);
		manager.flush();
		manager.detach(usuario);
	}

	@Transactional
	public void exclui(UsuarioModel usuario) {
		manager.remove(manager.find(UsuarioModel.class, usuario.getId()));
	}

	public List<UsuarioModel> listaTudo() {
		String jpql = "SELECT u FROM " + TABELA + " u ORDER BY u.id";
		TypedQuery<UsuarioModel> query = manager.createQuery(jpql, UsuarioModel.class);
		return query.getResultList();
	}

	public UsuarioModel buscaPorId(Long id) {

		String jpql = "SELECT u FROM " + TABELA + " u WHERE u.id = :id ";

		TypedQuery<UsuarioModel> query = manager.createQuery(jpql, UsuarioModel.class);

		query.setParameter("id", id);

		List<UsuarioModel> resultado = query.getResultList();

		if (resultado.size() == 0) {
			return new UsuarioModel();
		} else {
			return resultado.get(0);
		}
	}

	public UsuarioModel buscaPorIdClonando(Long id) {
		UsuarioModel encontrado = buscaPorId(id);
		Gson gson = new Gson();
		return gson.fromJson(gson.toJson(encontrado), UsuarioModel.class);
	}

	public List<UsuarioModel> buscaPorLogin(String login) {

		String jpql = "SELECT u FROM " + TABELA + " u WHERE u.login = :login ";

		TypedQuery<UsuarioModel> query = manager.createQuery(jpql, UsuarioModel.class);

		query.setParameter("login", login);

		return query.getResultList();
	}
	
	public List<UsuarioModel> buscaPorEmail(String email) {

		String jpql = "SELECT u FROM " + TABELA + " u WHERE u.email = :email ";

		TypedQuery<UsuarioModel> query = manager.createQuery(jpql, UsuarioModel.class);

		query.setParameter("email", email);

		return query.getResultList();
	}

	public List<UsuarioModel> buscaPorNome(String nome) {

		String jpql = "SELECT u FROM " + TABELA + " u WHERE u.nome LIKE  :nome ";

		TypedQuery<UsuarioModel> query = manager.createQuery(jpql, UsuarioModel.class);

		query.setParameter("nome", "%" + nome + "%");

		return query.getResultList();
	}

	public List<UsuarioModel> buscaPorSenha(String senha) {

		String jpql = "SELECT u FROM " + TABELA + " u WHERE u.senha LIKE  :senha ";

		TypedQuery<UsuarioModel> query = manager.createQuery(jpql, UsuarioModel.class);

		query.setParameter("senha", "%" + senha + "%");

		return query.getResultList();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<UsuarioModel> usuarios = buscaPorLogin(username);
		if (usuarios.size() > 1) {
			throw new UsernameNotFoundException("Multiplos usuário cadastrados com o mesmo login");
		}
		if (usuarios.size() == 1) {
			return usuarios.get(0);
		}
		throw new UsernameNotFoundException("Usuário não encontrado!");
	}
}