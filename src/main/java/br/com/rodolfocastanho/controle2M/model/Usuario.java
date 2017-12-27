package br.com.rodolfocastanho.controle2M.model;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(length = 10)
    private String login;

    @NotBlank
    @Column(length = 100)
    private String nome;

    @Column(length = 200)
    private String senha;

    @NotBlank
    @Column(length = 100)
    private String email;

    //@Column(length = 10)
    //@Enumerated(EnumType.STRING)
    //private Perfil perfil;

    @NotNull
    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "tipoPerfil")
    private Perfil tipoPerfil;

    @OneToMany(mappedBy = "responsavel")
    private List<Ordem> ordens = new ArrayList<>();

    @Transient
    private boolean resetaSenha;

    @Column
    private int primeiroAcesso;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Perfil getTipoPerfil() {
        return tipoPerfil;
    }

    public void setTipoPerfil(Perfil tipoPerfil) {
        this.tipoPerfil = tipoPerfil;
    }

    public List<Ordem> getOrdens() {
        return ordens;
    }

    public void setOrdens(List<Ordem> ordens) {
        this.ordens = ordens;
    }

    public boolean getResetaSenha() {
        return resetaSenha;
    }

    public void setResetaSenha(boolean resetaSenha) {
        this.resetaSenha = resetaSenha;
    }

    public int getPrimeiroAcesso() {
        return primeiroAcesso;
    }

    public void setPrimeiroAcesso(int primeiroAcesso) {
        this.primeiroAcesso = primeiroAcesso;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", nome='" + nome + '\'' +
                ", senha='" + senha + '\'' +
                ", email='" + email + '\'' +
                ", perfil=" + tipoPerfil +
                '}';
    }
}



