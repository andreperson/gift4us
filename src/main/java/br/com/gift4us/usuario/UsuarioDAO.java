package br.com.gift4us.usuario;

import org.springframework.security.core.userdetails.UserDetailsService;
import java.util.List;

public interface UsuarioDAO extends UserDetailsService {

	public abstract void criaInsertDeUsuarioAdmin();

	public abstract void insere(UsuarioModel usuario);

	public abstract void altera(UsuarioModel usuario);

	public abstract void exclui(UsuarioModel usuario);

	public abstract List<UsuarioModel> listaTudo();

	public abstract UsuarioModel buscaPorId(Long id);

	public abstract UsuarioModel buscaPorIdClonando(Long id);

	public abstract List<UsuarioModel> buscaPorLogin(String login);

	public abstract List<UsuarioModel> buscaPorNome(String nome);
}