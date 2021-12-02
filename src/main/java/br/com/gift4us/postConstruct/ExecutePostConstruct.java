package br.com.gift4us.postConstruct;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import br.com.gift4us.mensagensdosistema.MensagensDoSistemaDAO;

import br.com.gift4us.grupo.GrupoDAO;
import br.com.gift4us.grupo.GrupoModel;
import br.com.gift4us.usuario.UsuarioDAO;
import br.com.gift4us.usuario.UsuarioModel;
import br.com.gift4us.configuracoesdosistema.ConfiguracoesDoSistemaDAO;

@Component
public class ExecutePostConstruct {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private MensagensDoSistemaDAO mensagensDoSistemaDAO;

	@Autowired
	private ConfiguracoesDoSistemaDAO configuracoesDoSistemaDAO;

	@Autowired
	private UsuarioDAO usuarioDAO;

	@Autowired
	private GrupoDAO grupoDAO;

	@PostConstruct
	public void atualizaBanco() {
		System.out.println("MSG SIST Post Construct");
		mensagensDoSistemaDAO.insertsParaSeremUtilizadosNoPostConstruct();
		System.out.println("Conf Post Construct");
		configuracoesDoSistemaDAO.insertsParaSeremUtilizadosNoPostConstruct();
		System.out.println("insert Post Construct");
		grupoDAO.insertsParaSeremUtilizadosNoPostConstruct();
		System.out.println("cria Post Construct");
		usuarioDAO.criaInsertDeUsuarioAdmin();
		System.out.println("se necess Post Construct");
		inseriGruposSeNecessarioNoUsuarioAdmin();
		System.out.println("final Post Construct");
	}

	@Transactional
	private void inseriGruposSeNecessarioNoUsuarioAdmin() {
		UsuarioModel usuario = usuarioDAO.buscaPorLogin("admin").get(0);
		if (usuario != null) {
			String sql = "SELECT listadegrupo_id FROM USUARIO_GRUPO WHERE usuario_id = %d";
			sql = String.format(sql, usuario.getId());
			@SuppressWarnings("unchecked")
			List<Object> usuarios = manager.createNativeQuery(sql).getResultList();
			for (GrupoModel grupo : grupoDAO.listaTudo()) {
				boolean contem = false;
				for (Object id : usuarios) {
					Long idLong = Long.valueOf(id.toString());
					if (grupo.getId().equals(idLong)) {
						contem = true;
						break;
					}
				}
				if (!contem) {
					grupoDAO.insertsGrupoNoUsuarioPostConstruct(usuario.getId(), grupo.getId());
				}
			}
		}
	}
}