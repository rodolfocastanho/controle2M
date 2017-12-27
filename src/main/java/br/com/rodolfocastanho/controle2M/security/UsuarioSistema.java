package br.com.rodolfocastanho.controle2M.security;

import br.com.rodolfocastanho.controle2M.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.ManagedBean;
import java.io.Serializable;
import java.util.Collection;

@ManagedBean("usuarioLogado")
@SessionScope
public class UsuarioSistema extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Usuario usuario;

    public UsuarioSistema(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
        super(usuario.getLogin(), usuario.getSenha(), authorities);
        this.usuario = usuario;


    }

    public Usuario getUsuario() {
        return usuario;
    }

    /*
    private Long id;
    private String nome;

    public UsuarioSistema(Long id, String nome, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.nome = nome;
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Long getId() {
        return id;
    }
    */
}
