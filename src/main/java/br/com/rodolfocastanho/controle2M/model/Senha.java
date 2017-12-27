package br.com.rodolfocastanho.controle2M.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class Senha {

    @NotEmpty
    private String senhaAtual;

    @NotEmpty
    private String novaSenha;

    @NotEmpty
    private String novaSenhaValida;

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

    public String getNovaSenhaValida() {
        return novaSenhaValida;
    }

    public void setNovaSenhaValida(String novaSenhaValida) {
        this.novaSenhaValida = novaSenhaValida;
    }
}
