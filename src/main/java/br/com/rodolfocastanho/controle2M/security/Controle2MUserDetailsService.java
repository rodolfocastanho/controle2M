package br.com.rodolfocastanho.controle2M.security;

import br.com.rodolfocastanho.controle2M.model.Usuario;
import br.com.rodolfocastanho.controle2M.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class Controle2MUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.validaLogin(username);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado!");
        }

        Collection<GrantedAuthority> auths = new ArrayList<>();
        auths.add(new SimpleGrantedAuthority("ROLE_" + usuario.getTipoPerfil()));

        return new UsuarioSistema(usuario, auths);
    }
}
