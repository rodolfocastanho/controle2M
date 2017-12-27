package br.com.rodolfocastanho.controle2M.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_segmento")
public class Segmento {

    @Id
    @Column(length = 3)
    private String id;

    @Column(length = 10)
    private String descricao;

    @OneToMany(mappedBy = "segmento")
    private List<Ordem> ordens = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Ordem> getOrdens() {
        return ordens;
    }

    public void setOrdens(List<Ordem> ordens) {
        this.ordens = ordens;
    }
}
