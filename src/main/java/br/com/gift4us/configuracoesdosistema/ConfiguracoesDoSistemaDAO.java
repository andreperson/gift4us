package br.com.gift4us.configuracoesdosistema;

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
public class ConfiguracoesDoSistemaDAO {

	private final String TABELA = ConfiguracoesDoSistemaModel.class.getSimpleName();

	private Map<String, ConfiguracoesDoSistemaModel> mapa;

	@PersistenceContext
	private EntityManager manager;

	public ConfiguracoesDoSistemaDAO() {
	}

	@VisibleForTesting
	public ConfiguracoesDoSistemaDAO(EntityManager em) {
		this.manager = em;
	}

	
	@Transactional
	public void insere(ConfiguracoesDoSistemaModel configuracoesdosistema) {
		manager.persist(configuracoesdosistema);
		manager.flush();
		manager.detach(configuracoesdosistema);
	}

	@Transactional
	public void altera(ConfiguracoesDoSistemaModel configuracoesdosistema) {
		manager.merge(configuracoesdosistema);
		manager.flush();
		manager.detach(configuracoesdosistema);
	}

	@Transactional
	public void exclui(ConfiguracoesDoSistemaModel configuracoesdosistema) {
		manager.remove(manager.find(ConfiguracoesDoSistemaModel.class, configuracoesdosistema.getPropriedade()));
	}

	public ConfiguracoesDoSistemaModel buscaPorPropriedade(String propriedade) {

		if (mapa == null) {
			this.listaTudoComCache();
		}

		ConfiguracoesDoSistemaModel retorno = mapa.get(propriedade);

		if(retorno == null){
			retorno = new ConfiguracoesDoSistemaModel();
			retorno.setPropriedade("");
			retorno.setValor("");
		}

		return retorno;
	}
	@Cacheable(value = "cacheConfiguracoesDoSistema")
	public Map<String, ConfiguracoesDoSistemaModel> listaTudoComCache() {
		String jpql = "SELECT c FROM " + TABELA + " c ORDER BY c.id";
		TypedQuery<ConfiguracoesDoSistemaModel> query = manager.createQuery(jpql, ConfiguracoesDoSistemaModel.class);

		mapa = new HashMap<String, ConfiguracoesDoSistemaModel>();

		for (ConfiguracoesDoSistemaModel dados : query.getResultList()) {
			mapa.put(dados.getPropriedade(), dados);
		}
		return mapa;
	}

	@CacheEvict(value = "cacheConfiguracoesDoSistema")
	public void limpaCache() {
		mapa = null;
	}

	public List<ConfiguracoesDoSistemaModel> listaTudoSemCache() {
		String jpql = "SELECT c FROM " + TABELA + " c ORDER BY c.id";
		TypedQuery<ConfiguracoesDoSistemaModel> query = manager.createQuery(jpql, ConfiguracoesDoSistemaModel.class);
		return query.getResultList();
	}
	public ConfiguracoesDoSistemaModel buscaPorPropriedadeSemCache(String id) {

		String jpql = "SELECT c FROM " + TABELA + " c WHERE c.id = :id ";

		TypedQuery<ConfiguracoesDoSistemaModel> query = manager.createQuery(jpql, ConfiguracoesDoSistemaModel.class);

		query.setParameter("id", id);

		List<ConfiguracoesDoSistemaModel> resultado = query.getResultList();

		if(resultado.size() == 0){
			return new ConfiguracoesDoSistemaModel();
		}else{
			return resultado.get(0);
		}
	}

	public ConfiguracoesDoSistemaModel buscaPorPropriedadeClonando(String id) {
		ConfiguracoesDoSistemaModel encontrado = buscaPorPropriedadeSemCache(id);
		Gson gson = new Gson();
		return gson.fromJson(gson.toJson(encontrado), ConfiguracoesDoSistemaModel.class);
	}

	@Transactional
	public void insertsParaSeremUtilizadosNoPostConstruct() {
		ConfiguracoesDoSistemaModel diretorioDosArquivos = buscaPorPropriedadeSemCache("DIRETORIO_DOS_ARQUIVOS");
		if (diretorioDosArquivos == null || diretorioDosArquivos.getPropriedade() == null) {
			ConfiguracoesDoSistemaModel configuracao = new ConfiguracoesDoSistemaModel();
			configuracao.setPropriedade("DIRETORIO_DOS_ARQUIVOS");
			if ("producao".equals(System.getenv("AMBIENTE"))) {
				configuracao.setValor("/dados/docs/GIFT4US/");
			} else if ("homologacao".equals(System.getenv("AMBIENTE"))) {
				configuracao.setValor("/dados/docs/GIFT4US/");
			} else if ("desenvolvimento".equals(System.getenv("AMBIENTE"))) {
				configuracao.setValor("W:/projetos/GIFT4US/");
			}
			File file = new File(configuracao.getValor());
			if (!file.exists()) {
				file.mkdir();
			}
			manager.persist(configuracao);
		}
	}
}