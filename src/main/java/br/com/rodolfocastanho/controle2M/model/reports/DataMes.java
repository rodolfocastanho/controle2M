package br.com.rodolfocastanho.controle2M.model.reports;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class DataMes {

    public int Mes;

    @NotBlank
    public String MesNome;


    public DataMes() {
    }

    public DataMes(int mes, String mesNome) {
        Mes = mes;
        MesNome = mesNome;
    }


    public List<DataMes> criaListaMes (){
        List<DataMes> listaMes = new ArrayList<>();

        listaMes.add(new DataMes(1, "Janeiro"));
        listaMes.add(new DataMes(2, "Fevereiro"));
        listaMes.add(new DataMes(3, "Março"));
        listaMes.add(new DataMes(4, "Abril"));
        listaMes.add(new DataMes(5, "Maio"));
        listaMes.add(new DataMes(6, "Junho"));
        listaMes.add(new DataMes(7, "Julho"));
        listaMes.add(new DataMes(8, "Agosto"));
        listaMes.add(new DataMes(9, "Setembro"));
        listaMes.add(new DataMes(10, "Outubro"));
        listaMes.add(new DataMes(11, "Novembro"));
        listaMes.add(new DataMes(12, "Dezembro"));

        return listaMes;
    }

    public int getMes() {
        return Mes;
    }

    public void setMes(int mes) {
        Mes = mes;
    }

    public String getMesNome() {
        return MesNome;
    }

    public void setMesNome(String mesNome) {
        MesNome = mesNome;
    }
}
