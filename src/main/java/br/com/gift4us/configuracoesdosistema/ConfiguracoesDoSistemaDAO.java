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

	public ConfiguracoesDoSistemaModel buscarPeloNomeDaPropriedade(String propriedade) {
		return manager.find(ConfiguracoesDoSistemaModel.class, propriedade);
	}
	
	public ConfiguracoesDoSistemaModel buscaPorPropriedade(String propriedade) {

		if (mapa == null) {
			this.listaTudoComCache();
		}

		ConfiguracoesDoSistemaModel retorno = mapa.get(propriedade);

		if (retorno == null) {
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

		if (resultado.size() == 0) {
			return new ConfiguracoesDoSistemaModel();
		} else {
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
		try {
			ConfiguracoesDoSistemaModel emailenvio = buscaPorPropriedadeSemCache("EMAIL_ENVIO");
			if (emailenvio == null || emailenvio.getPropriedade() == null) {
				ConfiguracoesDoSistemaModel configuracao = new ConfiguracoesDoSistemaModel();
				configuracao.setPropriedade("EMAIL_ENVIO");
				configuracao.setValor("nao-responda@gift4us.com.br");
				manager.persist(configuracao);
			}
			ConfiguracoesDoSistemaModel emailsenha = buscaPorPropriedadeSemCache("EMAIL_SENHA");
			if (emailsenha == null || emailsenha.getPropriedade() == null) {
				ConfiguracoesDoSistemaModel configuracao = new ConfiguracoesDoSistemaModel();
				configuracao.setPropriedade("EMAIL_SENHA");
				configuracao.setValor("Nao*123");
				manager.persist(configuracao);
			}
			ConfiguracoesDoSistemaModel emailassunto = buscaPorPropriedadeSemCache("EMAIL_ASSUNTO");
			if (emailassunto == null || emailassunto.getPropriedade() == null) {
				ConfiguracoesDoSistemaModel configuracao = new ConfiguracoesDoSistemaModel();
				configuracao.setPropriedade("EMAIL_ASSUNTO");
				configuracao.setValor("Cadastro de Usu�rio");
				manager.persist(configuracao);
			}
			ConfiguracoesDoSistemaModel emaildominio = buscaPorPropriedadeSemCache("EMAIL_DOMINIO");
			if (emaildominio == null || emaildominio.getPropriedade() == null) {
				ConfiguracoesDoSistemaModel configuracao = new ConfiguracoesDoSistemaModel();
				configuracao.setPropriedade("EMAIL_DOMINIO");
				configuracao.setValor("gift4us.com.br");
				manager.persist(configuracao);
			}
			ConfiguracoesDoSistemaModel diretorioDosArquivos = buscaPorPropriedadeSemCache("DIRETORIO_DOS_ARQUIVOS");
			if (diretorioDosArquivos == null || diretorioDosArquivos.getPropriedade() == null) {
				ConfiguracoesDoSistemaModel configuracao = new ConfiguracoesDoSistemaModel();
				configuracao.setPropriedade("DIRETORIO_DOS_ARQUIVOS");
				if ("producao".equals(System.getenv("AMBIENTE"))) {
					configuracao.setValor("/docs/arquivos/");
				} else if ("homologacao".equals(System.getenv("AMBIENTE"))) {
					configuracao.setValor("/docs/arquivos/");
				} else if ("desenvolvimento".equals(System.getenv("AMBIENTE"))) {
					configuracao.setValor("C:/_temp/arquivos/");
				}
				File file = new File(configuracao.getValor());
				if (!file.exists()) {
					file.mkdir();
				}
				manager.persist(configuracao);
			}
			ConfiguracoesDoSistemaModel diretorioDosProdutos = buscaPorPropriedadeSemCache("DIRETORIO_DOS_PRODUTOS");
			if (diretorioDosProdutos == null || diretorioDosProdutos.getPropriedade() == null) {
				ConfiguracoesDoSistemaModel configuracao = new ConfiguracoesDoSistemaModel();
				configuracao.setPropriedade("DIRETORIO_DOS_PRODUTOS");
				if ("producao".equals(System.getenv("AMBIENTE"))) {
					configuracao.setValor("/docs/produtos/");
				} else if ("homologacao".equals(System.getenv("AMBIENTE"))) {
					configuracao.setValor("/docs/produtos/");
				} else if ("desenvolvimento".equals(System.getenv("AMBIENTE"))) {
					configuracao.setValor("C:/_temp/produtos/");
				}
				File file = new File(configuracao.getValor());
				if (!file.exists()) {
					file.mkdir();
				}
				manager.persist(configuracao);
			}
			
			ConfiguracoesDoSistemaModel httpRecursos = buscaPorPropriedadeSemCache("HTTP_RECURSOS");
			if (httpRecursos == null || httpRecursos.getPropriedade() == null) {
				ConfiguracoesDoSistemaModel configuracao = new ConfiguracoesDoSistemaModel();
				configuracao.setPropriedade("HTTP_RECURSOS");
				if ("producao".equals(System.getenv("AMBIENTE"))) {
					configuracao.setValor("https://gift4us.com.br/resources/");
				} else if ("homologacao".equals(System.getenv("AMBIENTE"))) {
					configuracao.setValor("http://gift4us.com.br/resources-homologacao/");
				} else if ("desenvolvimento".equals(System.getenv("AMBIENTE"))) {
					configuracao.setValor("http://gift4us.com.br/resources-homologacao/");
				}
				manager.persist(configuracao);
			}
			
			ConfiguracoesDoSistemaModel httpsRecursos = buscaPorPropriedadeSemCache("HTTPS_RECURSOS");
			if (httpsRecursos == null || httpsRecursos.getPropriedade() == null) {
				ConfiguracoesDoSistemaModel configuracao = new ConfiguracoesDoSistemaModel();
				configuracao.setPropriedade("HTTPS_RECURSOS");
				if ("producao".equals(System.getenv("AMBIENTE"))) {
					configuracao.setValor("https://gift4us.com.br/resources/");
				} else if ("homologacao".equals(System.getenv("AMBIENTE"))) {
					configuracao.setValor("https://gift4us.com.br/resources-homologacao/");
				} else if ("desenvolvimento".equals(System.getenv("AMBIENTE"))) {
					configuracao.setValor("https://gift4us.com.br/resources-homologacao/");
				}
				manager.persist(configuracao);
			}
			
			ConfiguracoesDoSistemaModel QualAmbiente = buscaPorPropriedadeSemCache("AMBIENTE");
			if (QualAmbiente == null || QualAmbiente.getPropriedade() == null) {
				ConfiguracoesDoSistemaModel configuracao = new ConfiguracoesDoSistemaModel();
				configuracao.setPropriedade("AMBIENTE");
				configuracao.setValor(System.getenv("AMBIENTE"));
				manager.persist(configuracao);
			}
			
			
			
		} catch (Exception e) {
			System.out.println("insertsParaSeremUtilizadosNoPostConstruct + ConfiguracoesDoSistemaModel: " + e.getMessage());
		}
	}
	
	
}