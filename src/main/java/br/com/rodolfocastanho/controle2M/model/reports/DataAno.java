package br.com.rodolfocastanho.controle2M.model.reports;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DataAno {

    @NotEmpty
    public int ano;


    public DataAno(){
    }

    public DataAno(int ano) {
        this.ano = ano;
    }


    public List<DataAno> criaListaAno(){
        List<DataAno> listaAno = new ArrayList<>();

        int ano = Calendar.getInstance().get(Calendar.YEAR);
        while (ano >= 2016){
            listaAno.add(new DataAno(ano));
            ano--;
        }

        return listaAno;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }
}
