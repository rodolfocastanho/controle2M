package br.com.rodolfocastanho.controle2M.model;

public enum Perfil {

    ADMIN("Administrador"),
    OPER("Operador");

    private String tipoPerfil;


    Perfil(String tipoPerfil) {
        this.tipoPerfil = tipoPerfil;
    }

    public String getTipoPerfil() {
        return tipoPerfil;
    }
}
