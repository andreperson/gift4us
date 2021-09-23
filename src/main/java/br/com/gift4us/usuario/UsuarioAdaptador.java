package br.com.gift4us.usuario;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UsuarioAdaptador {

        public UsuarioModel obterUsuarioLogado() {

                try {
                        return (UsuarioModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

                } catch (Exception e) {
                        return null;
                }
        }
}