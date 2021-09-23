package br.com.gift4us.login;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.gift4us.configuracoesdosistema.ConfiguracoesDoSistemaDAO;
import br.com.gift4us.usuario.UsuarioModel;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private CustomUserService userService;

	@Autowired
	private ConfiguracoesDoSistemaDAO configuracoesDao;

	@Override
	public Authentication authenticate(Authentication authentication) {
		String username = authentication.getName().toLowerCase();
		String password = (String) authentication.getCredentials();

		UsuarioModel usuarioDoBanco = null;
		try {
			usuarioDoBanco = userService.buscaPorLogin(username);
		} catch (Exception e) {
			usuarioDoBanco = null;
		}

		if (usuarioDoBanco == null) {
			throw new BadCredentialsException("Usuário não localizado!");
		} else if (!new BCryptPasswordEncoder().matches(password, usuarioDoBanco.getSenha())) {
			throw new BadCredentialsException("Usuário não localizado!");
		}

		UserDetails user = userService.loadUserByUsername(username);

		Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

		return new UsernamePasswordAuthenticationToken(user, password, authorities);
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

}
