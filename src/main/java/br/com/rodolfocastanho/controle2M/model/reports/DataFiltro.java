package br.com.rodolfocastanho.controle2M.model.reports;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class DataFiltro {

    @NotNull
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date DataInicio;

    @NotNull
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date DataFim;

    private long DataInicioLong;
    private long DataFimLong;

    public DataFiltro fillObjectDataFiltro(DataFiltro dataFiltro){
        dataFiltro.setDataInicioLong(dataFiltro.getDataInicio().toInstant().toEpochMilli());
        dataFiltro.setDataFimLong(dataFiltro.getDataFim().toInstant().toEpochMilli());

        return dataFiltro;
    }

    public Date getDataInicio() {
        return DataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        DataInicio = dataInicio;
    }

    public Date getDataFim() {
        return DataFim;
    }

    public void setDataFim(Date dataFim) {
        DataFim = dataFim;
    }

    public long getDataInicioLong() {
        return DataInicioLong;
    }

    public void setDataInicioLong(long dataInicioLong) {
        DataInicioLong = dataInicioLong;
    }

    public long getDataFimLong() {
        return DataFimLong;
    }

    public void setDataFimLong(long dataFimLong) {
        DataFimLong = dataFimLong;
    }
}
