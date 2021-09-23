package br.com.gift4us.login;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.gift4us.grupo.GrupoDAO;
import br.com.gift4us.grupo.GrupoModel;
import br.com.gift4us.usuario.UsuarioDAO;
import br.com.gift4us.usuario.UsuarioModel;

@Service
public class CustomUserService implements UserDetailsService {

	@Autowired
	private UsuarioDAO usuarioDao;

	@Autowired
	private GrupoDAO grupoDao;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails loadUserByUsername = usuarioDao.loadUserByUsername(username);
		return loadUserByUsername;
	}

	public UsuarioModel buscaPorLogin(String username) {
		return usuarioDao.buscaPorLogin(username).get(0);
	}

	public void alteraUsuario(UsuarioModel usuario) {
		usuarioDao.altera(usuario);
	}

	public void insereUsuario(UsuarioModel usuario) {
		usuarioDao.insere(usuario);
	}

	public GrupoModel buscaGrupoPorNomeDeGrupo(String nome) {
		List<GrupoModel> buscaPorNome = grupoDao.buscaPorNome(nome);
		return buscaPorNome.get(0);
	}
}
