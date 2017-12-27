package br.com.rodolfocastanho.controle2M.model.reports;

import br.com.rodolfocastanho.controle2M.model.Ordem;
import br.com.rodolfocastanho.controle2M.service.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.plaf.synth.Region;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class ResumoGrafico {

    private int semanaMes = 0;
    private long qtdDias = 0;
    private Date dia;
    private String diaSemana;

    private int OrdemTotal = 0;
    private int OrdemExecutado = 0;
    private int OrdemUmDia = 0;
    private int OrdemMaiorUmDia = 0;
    private int OrdemMaiorTresDias = 0;
    private double OrdemMediaAtendimento = 0;
    private int OrdemPendente = 0;
    private double OrdemExecutadoPct = 0;
    private double OrdemUmDiaPct = 0;
    private double OrdemMaiorUmDiaPct = 0;
    private double OrdemMaiorTresDiasPct = 0;
    private double OrdemPendentePct = 0;

    private int ProvisionamentoExecutado = 0;
    private int ProvisionamentoUmDia = 0;
    private int ProvisionamentoMaiorUmDia = 0;
    private int ProvisionamentoMaiorTresDias = 0;
    private double ProvisionamentoMediaAtendimento = 0;
    private int ProvisionamentoPendente = 0;
    private double ProvisionamentoExecutadoPct = 0;
    private double ProvisionamentoUmDiaPct = 0;
    private double ProvisionamentoMaiorUmDiaPct = 0;
    private double ProvisionamentoMaiorTresDiasPct = 0;
    private double ProvisionamentoPendentePct = 0;

    private int ComissionamentoExecutado = 0;
    private int ComissionamentoUmDia = 0;
    private int ComissionamentoMaiorUmDia = 0;
    private int ComissionamentoMaiorTresDias = 0;
    private double ComissionamentoMediaAtendimento = 0;
    private int ComissionamentoPendente = 0;
    private double ComissionamentoExecutadoPct = 0;
    private double ComissionamentoUmDiaPct = 0;
    private double ComissionamentoMaiorUmDiaPct = 0;
    private double ComissionamentoMaiorTresDiasPct = 0;
    private double ComissionamentoPendentePct = 0;

    private int E1Total = 0;
    private int E1Executado = 0;
    private int E1UmDia = 0;
    private int E1MaiorUmDia = 0;
    private int E1MaiorTresDias = 0;
    private double E1MediaAtendimento = 0;
    private int E1Pendente = 0;
    private double E1ExecutadoPct = 0;
    private double E1UmDiaPct = 0;
    private double E1MaiorUmDiaPct = 0;
    private double E1MaiorTresDiasPct = 0;
    private double E1PendentePct = 0;


    //GERA RELATÓRIO POR TEMPO
    public ResumoGrafico carregaResumo(List<Ordem> ordens){
        ResumoGrafico resumoGrafico = new ResumoGrafico();
        long mediaAtendimentoOrdem = 0;
        long mediaAtendimentoProvisionamento = 0;
        long mediaAtendimentoComissionamento = 0;

        long mediaAtendimentoOrdemQtd = 0;
        long mediaAtendimentoProvisionamentoQtd = 0;
        long mediaAtendimentoComissionamentoQtd = 0;

        //ORDEM E COMISSIONAMENTO
        resumoGrafico.setOrdemTotal(ordens.size());

        for (Ordem ordem: ordens) {
            resumoGrafico.setE1Total(resumoGrafico.getE1Total()+ordem.getE1());
            //Executado
            if (ordem.getStatusOrdem().equalsIgnoreCase("Finalizado")) {
                resumoGrafico.setOrdemExecutado(resumoGrafico.getOrdemExecutado()+1);
                resumoGrafico.setComissionamentoExecutado(resumoGrafico.getComissionamentoExecutado()+1);
                resumoGrafico.setE1Executado(resumoGrafico.getE1Executado()+ordem.getE1());

                mediaAtendimentoOrdemQtd++;
                mediaAtendimentoComissionamentoQtd++;

                mediaAtendimentoOrdem += diferencaDias(ordem.getDataEntrada(),ordem.getDataComissionamento());
                mediaAtendimentoComissionamento += diferencaDias(ordem.getDataEntrada(),ordem.getDataComissionamento());
            }
            //Executado Um dia
            if(ordem.getStatusOrdem().equalsIgnoreCase("Finalizado") &&
                    diferencaDias(ordem.getDataEntrada(),ordem.getDataComissionamento()) == 0){
                resumoGrafico.setOrdemUmDia(resumoGrafico.getOrdemUmDia()+1);
                resumoGrafico.setComissionamentoUmDia(resumoGrafico.getComissionamentoUmDia()+1);
                resumoGrafico.setE1UmDia(resumoGrafico.getE1UmDia()+ordem.getE1());
            }
            //Executado Maior Um Dia
            if(ordem.getStatusOrdem().equalsIgnoreCase("Finalizado") &&
                    (diferencaDias(ordem.getDataEntrada(),ordem.getDataComissionamento()) >= 1 &&
                            diferencaDias(ordem.getDataEntrada(),ordem.getDataComissionamento()) <=3)){
                resumoGrafico.setOrdemMaiorUmDia(resumoGrafico.getOrdemMaiorUmDia()+1);
                resumoGrafico.setComissionamentoMaiorUmDia(resumoGrafico.getComissionamentoMaiorUmDia()+1);
                resumoGrafico.setE1MaiorUmDia(resumoGrafico.getE1MaiorUmDia()+ordem.getE1());
            }
            //Executado Maior Tres Dias
            if(ordem.getStatusOrdem().equalsIgnoreCase("Finalizado") &&
                    diferencaDias(ordem.getDataEntrada(),ordem.getDataComissionamento()) > 3){
                resumoGrafico.setOrdemMaiorTresDias(resumoGrafico.getOrdemMaiorTresDias()+1);
                resumoGrafico.setComissionamentoMaiorTresDias(resumoGrafico.getComissionamentoMaiorTresDias()+1);
                resumoGrafico.setE1MaiorTresDias(resumoGrafico.getE1MaiorTresDias()+ordem.getE1());
            }
            //Pendente
            if(!ordem.getStatusOrdem().equalsIgnoreCase("Finalizado")){
                resumoGrafico.setOrdemPendente(resumoGrafico.getOrdemPendente()+1);
                resumoGrafico.setComissionamentoPendente(resumoGrafico.getComissionamentoPendente()+1);
                resumoGrafico.setE1Pendente(resumoGrafico.getE1Pendente()+ordem.getE1());
            }

            //PROVISIONAMENTO
            //Executado
            if (!ordem.getStatusOrdem().equalsIgnoreCase("Aprovisionamento")) {
                resumoGrafico.setProvisionamentoExecutado(resumoGrafico.getProvisionamentoExecutado()+1);

                mediaAtendimentoProvisionamentoQtd++;

                mediaAtendimentoProvisionamento += diferencaDias(ordem.getDataEntrada(),ordem.getDataProvisionamento());
            }
            //Executado Um dia
            if(!ordem.getStatusOrdem().equalsIgnoreCase("Aprovisionamento") &&
                    diferencaDias(ordem.getDataEntrada(),ordem.getDataProvisionamento()) == 0){
                resumoGrafico.setProvisionamentoUmDia(resumoGrafico.getProvisionamentoUmDia()+1);
            }
            //Executado Maior Um Dia
            if(!ordem.getStatusOrdem().equalsIgnoreCase("Aprovisionamento") &&
                    (diferencaDias(ordem.getDataEntrada(),ordem.getDataProvisionamento()) >= 1 &&
                            diferencaDias(ordem.getDataEntrada(),ordem.getDataProvisionamento()) <=3)){
                resumoGrafico.setProvisionamentoMaiorUmDia(resumoGrafico.getProvisionamentoMaiorUmDia()+1);
            }
            //Executado Maior Tres Dias
            if(!ordem.getStatusOrdem().equalsIgnoreCase("Aprovisionamento") &&
                    diferencaDias(ordem.getDataEntrada(),ordem.getDataProvisionamento()) > 3){
                resumoGrafico.setProvisionamentoMaiorTresDias(resumoGrafico.getProvisionamentoMaiorTresDias()+1);
            }
            //Pendente
            if(ordem.getStatusOrdem().equalsIgnoreCase("Aprovisionamento")){
                resumoGrafico.setProvisionamentoPendente(resumoGrafico.getProvisionamentoPendente()+1);
            }
        }

        //MEDIAS
        if(mediaAtendimentoOrdemQtd > 0) resumoGrafico.setOrdemMediaAtendimento((double)mediaAtendimentoOrdem/mediaAtendimentoOrdemQtd);
        if(mediaAtendimentoProvisionamentoQtd > 0) resumoGrafico.setProvisionamentoMediaAtendimento((double)mediaAtendimentoProvisionamento/mediaAtendimentoProvisionamentoQtd);
        if(mediaAtendimentoComissionamentoQtd > 0) resumoGrafico.setComissionamentoMediaAtendimento((double)mediaAtendimentoComissionamento/mediaAtendimentoComissionamentoQtd);

        //PORCENTAGENS
        resumoGrafico = trataPct(resumoGrafico);

        return resumoGrafico;
    }

    public ResumoGrafico trataPct(ResumoGrafico resumoGrafico){

        if(resumoGrafico.getOrdemTotal() != 0){
            resumoGrafico.setOrdemExecutadoPct(((double)resumoGrafico.getOrdemExecutado()/(double)resumoGrafico.getOrdemTotal()) * 100);
            resumoGrafico.setOrdemUmDiaPct(((double)resumoGrafico.getOrdemUmDia()/(double)resumoGrafico.getOrdemExecutado()) * 100);
            resumoGrafico.setOrdemMaiorUmDiaPct(((double)resumoGrafico.getOrdemMaiorUmDia()/(double)resumoGrafico.getOrdemExecutado()) * 100);
            resumoGrafico.setOrdemMaiorTresDiasPct(((double)resumoGrafico.getOrdemMaiorTresDias()/(double)resumoGrafico.getOrdemExecutado()) * 100);
            resumoGrafico.setOrdemPendentePct(((double)resumoGrafico.getOrdemPendente()/(double)resumoGrafico.getOrdemTotal()) * 100);

            if(resumoGrafico.getProvisionamentoExecutado() != 0){
                resumoGrafico.setProvisionamentoExecutadoPct(((double)resumoGrafico.getProvisionamentoExecutado()/(double)resumoGrafico.getOrdemTotal()) * 100);
                resumoGrafico.setProvisionamentoUmDiaPct(((double)resumoGrafico.getProvisionamentoUmDia()/(double)resumoGrafico.getProvisionamentoExecutado()) * 100);
                resumoGrafico.setProvisionamentoMaiorUmDiaPct(((double)resumoGrafico.getProvisionamentoMaiorUmDia()/(double)resumoGrafico.getProvisionamentoExecutado()) * 100);
                resumoGrafico.setProvisionamentoMaiorTresDiasPct(((double)resumoGrafico.getProvisionamentoMaiorTresDias()/(double)resumoGrafico.getProvisionamentoExecutado()) * 100);
                resumoGrafico.setProvisionamentoPendentePct(((double)resumoGrafico.getProvisionamentoPendente()/(double)resumoGrafico.getOrdemTotal()) * 100);
            }

            if(resumoGrafico.getProvisionamentoExecutado() != 0){
                resumoGrafico.setComissionamentoExecutadoPct(((double)resumoGrafico.getComissionamentoExecutado()/(double)resumoGrafico.getOrdemTotal()) * 100);
                resumoGrafico.setComissionamentoUmDiaPct(((double)resumoGrafico.getComissionamentoUmDia()/(double)resumoGrafico.getComissionamentoExecutado()) * 100);
                resumoGrafico.setComissionamentoMaiorUmDiaPct(((double)resumoGrafico.getComissionamentoMaiorUmDia()/(double)resumoGrafico.getComissionamentoExecutado()) * 100);
                resumoGrafico.setComissionamentoMaiorTresDiasPct(((double)resumoGrafico.getComissionamentoMaiorTresDias()/(double)resumoGrafico.getComissionamentoExecutado()) * 100);
                resumoGrafico.setComissionamentoPendentePct(((double)resumoGrafico.getComissionamentoPendente()/(double)resumoGrafico.getOrdemTotal()) * 100);
            }

            if(resumoGrafico.getE1Executado() != 0){
                resumoGrafico.setE1ExecutadoPct(((double)resumoGrafico.getE1Executado()/(double)resumoGrafico.getE1Total()) * 100);
                resumoGrafico.setE1UmDiaPct(((double)resumoGrafico.getE1UmDia()/(double)resumoGrafico.getE1Executado()) * 100);
                resumoGrafico.setE1MaiorUmDiaPct(((double)resumoGrafico.getE1MaiorUmDia()/(double)resumoGrafico.getE1Executado()) * 100);
                resumoGrafico.setE1MaiorTresDiasPct(((double)resumoGrafico.getE1MaiorTresDias()/(double)resumoGrafico.getE1Executado()) * 100);
                resumoGrafico.setE1PendentePct(((double)resumoGrafico.getE1Pendente()/(double)resumoGrafico.getE1Total()) * 100);

            }
        }
        return resumoGrafico;
    }

    public long diferencaDias(Calendar d1,Calendar d2){
        long dias = ChronoUnit.DAYS.between(d1.getTime().toInstant(),d2.getTime().toInstant());
        return dias;
    }
    //**************************************************************************

    //GERA RELATÓRIO QUANTIDADE

    public Date resolvePrimeiroUltimo(Date data, boolean isPrimeiro)
    {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(data);
        if(isPrimeiro) {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        } else {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        }

        return calendar.getTime();
    }

    public Date resolveUltimoDiaMes(Date data)
    {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(data);
        calendar.add(Calendar.MONTH,1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar.getTime();
    }

    public int resolveQtdSemanas(Date data){
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(data);
        return calendar.get(Calendar.WEEK_OF_MONTH);
    }

    public Date somaDia(Date data){
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(data);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        //calendar.add(Calendar.HOUR_OF_DAY, 24);
        return calendar.getTime();
    }

    public Date setHoras(Date data, int h, int m, int s){
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(data);
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH ),
                h,
                m,
                s);
        return calendar.getTime();
    }

    public long qtdDiasSemana(Date d1,Date d2){
        Calendar d1Calendar = Calendar.getInstance();
        Calendar d2Calendar = Calendar.getInstance();
        d1Calendar.setTime(d1);
        d2Calendar.setTime(d2);
        long dias = ChronoUnit.DAYS.between(d1Calendar.getTime().toInstant(),d2Calendar.getTime().toInstant());
        return dias+1;
    }

    public String diaSemana(Date data){
        String[] getDiaSemanaExtenso = new DateFormatSymbols(new Locale("pt", "BR")).getWeekdays();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        return getDiaSemanaExtenso[calendar.get(Calendar.DAY_OF_WEEK)];
    }

    //**************************************************************************

    //GERA LISTA DE DETALHE

    public List<Ordem> geraListaDetalhe(List<Ordem> ordens, String status){
        List<Ordem> executado = new ArrayList<>();
        List<Ordem> executadoUmDia = new ArrayList<>();
        List<Ordem> executadoMaiorUmDia = new ArrayList<>();
        List<Ordem> executadoMaiorTresDias = new ArrayList<>();
        List<Ordem> pendente = new ArrayList<>();
        List<Ordem> provisionamentoExecutado = new ArrayList<>();


        for(Ordem ordem: ordens){
            //Executado
            if (ordem.getStatusOrdem().equalsIgnoreCase("Finalizado")) {
                executado.add(ordem);
            }
            //Executado Um dia
            if(ordem.getStatusOrdem().equalsIgnoreCase("Finalizado") &&
                    diferencaDias(ordem.getDataEntrada(),ordem.getDataComissionamento()) == 0){
                executadoUmDia.add(ordem);
            }
            //Executado Maior Um Dia
            if(ordem.getStatusOrdem().equalsIgnoreCase("Finalizado") &&
                    (diferencaDias(ordem.getDataEntrada(),ordem.getDataComissionamento()) >= 1 &&
                            diferencaDias(ordem.getDataEntrada(),ordem.getDataComissionamento()) <=3)){
                executadoMaiorUmDia.add(ordem);
            }
            //Executado Maior Tres Dias
            if(ordem.getStatusOrdem().equalsIgnoreCase("Finalizado") &&
                    diferencaDias(ordem.getDataEntrada(),ordem.getDataComissionamento()) > 3){
                executadoMaiorTresDias.add(ordem);
            }
            //Pendente
            if(!ordem.getStatusOrdem().equalsIgnoreCase("Finalizado")){
                pendente.add(ordem);
            }

            //PROVISIONAMENTO Executado
            if (!ordem.getStatusOrdem().equalsIgnoreCase("Aprovisionamento")) {
                provisionamentoExecutado.add(ordem);
            }
        }

        switch (status) {
            case "Total_Executado":
                return executado;
            case "Executado_1_Dia":
                return executadoUmDia;
            case "Executado_Mais_de_1_dia":
                return executadoMaiorUmDia;
            case "Executado_Mais_de_3_dias":
                return executadoMaiorTresDias;
            case "Pendentes":
                return pendente;
            case "Aprovisionamento_Executado":
                return provisionamentoExecutado;
            case "Comissionamento_Executado":
                return executado;
            default:
                return ordens;
        }

    }

    //**************************************************************************


    public int getSemanaMes() {
        return semanaMes;
    }

    public void setSemanaMes(int semanaMes) {
        this.semanaMes = semanaMes;
    }

    public long getQtdDias() {
        return qtdDias;
    }

    public void setQtdDias(long qtdDias) {
        this.qtdDias = qtdDias;
    }

    public Date getDia() {
        return dia;
    }

    public void setDia(Date dia) {
        this.dia = dia;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public int getOrdemTotal() {
        return OrdemTotal;
    }

    public void setOrdemTotal(int ordemTotal) {
        OrdemTotal = ordemTotal;
    }

    public int getOrdemExecutado() {
        return OrdemExecutado;
    }

    public void setOrdemExecutado(int ordemExecutado) {
        OrdemExecutado = ordemExecutado;
    }

    public int getOrdemUmDia() {
        return OrdemUmDia;
    }

    public void setOrdemUmDia(int ordemUmDia) {
        OrdemUmDia = ordemUmDia;
    }

    public int getOrdemMaiorUmDia() {
        return OrdemMaiorUmDia;
    }

    public void setOrdemMaiorUmDia(int ordemMaiorUmDia) {
        OrdemMaiorUmDia = ordemMaiorUmDia;
    }

    public int getOrdemMaiorTresDias() {
        return OrdemMaiorTresDias;
    }

    public void setOrdemMaiorTresDias(int ordemMaiorTresDias) {
        OrdemMaiorTresDias = ordemMaiorTresDias;
    }

    public double getOrdemMediaAtendimento() {
        return OrdemMediaAtendimento;
    }

    public void setOrdemMediaAtendimento(double ordemMediaAtendimento) {
        OrdemMediaAtendimento = ordemMediaAtendimento;
    }

    public int getOrdemPendente() {
        return OrdemPendente;
    }

    public void setOrdemPendente(int ordemPendente) {
        OrdemPendente = ordemPendente;
    }

    public double getOrdemExecutadoPct() {
        return OrdemExecutadoPct;
    }

    public void setOrdemExecutadoPct(double ordemExecutadoPct) {
        OrdemExecutadoPct = ordemExecutadoPct;
    }

    public double getOrdemUmDiaPct() {
        return OrdemUmDiaPct;
    }

    public void setOrdemUmDiaPct(double ordemUmDiaPct) {
        OrdemUmDiaPct = ordemUmDiaPct;
    }

    public double getOrdemMaiorUmDiaPct() {
        return OrdemMaiorUmDiaPct;
    }

    public void setOrdemMaiorUmDiaPct(double ordemMaiorUmDiaPct) {
        OrdemMaiorUmDiaPct = ordemMaiorUmDiaPct;
    }

    public double getOrdemMaiorTresDiasPct() {
        return OrdemMaiorTresDiasPct;
    }

    public void setOrdemMaiorTresDiasPct(double ordemMaiorTresDiasPct) {
        OrdemMaiorTresDiasPct = ordemMaiorTresDiasPct;
    }

    public double getOrdemPendentePct() {
        return OrdemPendentePct;
    }

    public void setOrdemPendentePct(double ordemPendentePct) {
        OrdemPendentePct = ordemPendentePct;
    }


    public int getProvisionamentoExecutado() {
        return ProvisionamentoExecutado;
    }

    public void setProvisionamentoExecutado(int provisionamentoExecutado) {
        ProvisionamentoExecutado = provisionamentoExecutado;
    }

    public int getProvisionamentoUmDia() {
        return ProvisionamentoUmDia;
    }

    public void setProvisionamentoUmDia(int provisionamentoUmDia) {
        ProvisionamentoUmDia = provisionamentoUmDia;
    }

    public int getProvisionamentoMaiorUmDia() {
        return ProvisionamentoMaiorUmDia;
    }

    public void setProvisionamentoMaiorUmDia(int provisionamentoMaiorUmDia) {
        ProvisionamentoMaiorUmDia = provisionamentoMaiorUmDia;
    }

    public int getProvisionamentoMaiorTresDias() {
        return ProvisionamentoMaiorTresDias;
    }

    public void setProvisionamentoMaiorTresDias(int provisionamentoMaiorTresDias) {
        ProvisionamentoMaiorTresDias = provisionamentoMaiorTresDias;
    }

    public double getProvisionamentoMediaAtendimento() {
        return ProvisionamentoMediaAtendimento;
    }

    public void setProvisionamentoMediaAtendimento(double provisionamentoMediaAtendimento) {
        ProvisionamentoMediaAtendimento = provisionamentoMediaAtendimento;
    }

    public int getProvisionamentoPendente() {
        return ProvisionamentoPendente;
    }

    public void setProvisionamentoPendente(int provisionamentoPendente) {
        ProvisionamentoPendente = provisionamentoPendente;
    }

    public double getProvisionamentoExecutadoPct() {
        return ProvisionamentoExecutadoPct;
    }

    public void setProvisionamentoExecutadoPct(double provisionamentoExecutadoPct) {
        ProvisionamentoExecutadoPct = provisionamentoExecutadoPct;
    }

    public double getProvisionamentoUmDiaPct() {
        return ProvisionamentoUmDiaPct;
    }

    public void setProvisionamentoUmDiaPct(double provisionamentoUmDiaPct) {
        ProvisionamentoUmDiaPct = provisionamentoUmDiaPct;
    }

    public double getProvisionamentoMaiorUmDiaPct() {
        return ProvisionamentoMaiorUmDiaPct;
    }

    public void setProvisionamentoMaiorUmDiaPct(double provisionamentoMaiorUmDiaPct) {
        ProvisionamentoMaiorUmDiaPct = provisionamentoMaiorUmDiaPct;
    }

    public double getProvisionamentoMaiorTresDiasPct() {
        return ProvisionamentoMaiorTresDiasPct;
    }

    public void setProvisionamentoMaiorTresDiasPct(double provisionamentoMaiorTresDiasPct) {
        ProvisionamentoMaiorTresDiasPct = provisionamentoMaiorTresDiasPct;
    }

    public double getProvisionamentoPendentePct() {
        return ProvisionamentoPendentePct;
    }

    public void setProvisionamentoPendentePct(double provisionamentoPendentePct) {
        ProvisionamentoPendentePct = provisionamentoPendentePct;
    }

    public int getComissionamentoExecutado() {
        return ComissionamentoExecutado;
    }

    public void setComissionamentoExecutado(int comissionamentoExecutado) {
        ComissionamentoExecutado = comissionamentoExecutado;
    }

    public int getComissionamentoUmDia() {
        return ComissionamentoUmDia;
    }

    public void setComissionamentoUmDia(int comissionamentoUmDia) {
        ComissionamentoUmDia = comissionamentoUmDia;
    }

    public int getComissionamentoMaiorUmDia() {
        return ComissionamentoMaiorUmDia;
    }

    public void setComissionamentoMaiorUmDia(int comissionamentoMaiorUmDia) {
        ComissionamentoMaiorUmDia = comissionamentoMaiorUmDia;
    }

    public int getComissionamentoMaiorTresDias() {
        return ComissionamentoMaiorTresDias;
    }

    public void setComissionamentoMaiorTresDias(int comissionamentoMaiorTresDias) {
        ComissionamentoMaiorTresDias = comissionamentoMaiorTresDias;
    }

    public double getComissionamentoMediaAtendimento() {
        return ComissionamentoMediaAtendimento;
    }

    public void setComissionamentoMediaAtendimento(double comissionamentoMediaAtendimento) {
        ComissionamentoMediaAtendimento = comissionamentoMediaAtendimento;
    }

    public int getComissionamentoPendente() {
        return ComissionamentoPendente;
    }

    public void setComissionamentoPendente(int comissionamentoPendente) {
        ComissionamentoPendente = comissionamentoPendente;
    }

    public double getComissionamentoExecutadoPct() {
        return ComissionamentoExecutadoPct;
    }

    public void setComissionamentoExecutadoPct(double comissionamentoExecutadoPct) {
        ComissionamentoExecutadoPct = comissionamentoExecutadoPct;
    }

    public double getComissionamentoUmDiaPct() {
        return ComissionamentoUmDiaPct;
    }

    public void setComissionamentoUmDiaPct(double comissionamentoUmDiaPct) {
        ComissionamentoUmDiaPct = comissionamentoUmDiaPct;
    }

    public double getComissionamentoMaiorUmDiaPct() {
        return ComissionamentoMaiorUmDiaPct;
    }

    public void setComissionamentoMaiorUmDiaPct(double comissionamentoMaiorUmDiaPct) {
        ComissionamentoMaiorUmDiaPct = comissionamentoMaiorUmDiaPct;
    }

    public double getComissionamentoMaiorTresDiasPct() {
        return ComissionamentoMaiorTresDiasPct;
    }

    public void setComissionamentoMaiorTresDiasPct(double comissionamentoMaiorTresDiasPct) {
        ComissionamentoMaiorTresDiasPct = comissionamentoMaiorTresDiasPct;
    }

    public double getComissionamentoPendentePct() {
        return ComissionamentoPendentePct;
    }

    public void setComissionamentoPendentePct(double comissionamentoPendentePct) {
        ComissionamentoPendentePct = comissionamentoPendentePct;
    }

    public int getE1Total() {
        return E1Total;
    }

    public void setE1Total(int e1Total) {
        E1Total = e1Total;
    }

    public int getE1Executado() {
        return E1Executado;
    }

    public void setE1Executado(int e1Executado) {
        E1Executado = e1Executado;
    }

    public int getE1UmDia() {
        return E1UmDia;
    }

    public void setE1UmDia(int e1UmDia) {
        E1UmDia = e1UmDia;
    }

    public int getE1MaiorUmDia() {
        return E1MaiorUmDia;
    }

    public void setE1MaiorUmDia(int e1MaiorUmDia) {
        E1MaiorUmDia = e1MaiorUmDia;
    }

    public int getE1MaiorTresDias() {
        return E1MaiorTresDias;
    }

    public void setE1MaiorTresDias(int e1MaiorTresDias) {
        E1MaiorTresDias = e1MaiorTresDias;
    }

    public double getE1MediaAtendimento() {
        return E1MediaAtendimento;
    }

    public void setE1MediaAtendimento(double e1MediaAtendimento) {
        E1MediaAtendimento = e1MediaAtendimento;
    }

    public int getE1Pendente() {
        return E1Pendente;
    }

    public void setE1Pendente(int e1Pendente) {
        E1Pendente = e1Pendente;
    }

    public double getE1ExecutadoPct() {
        return E1ExecutadoPct;
    }

    public void setE1ExecutadoPct(double e1ExecutadoPct) {
        E1ExecutadoPct = e1ExecutadoPct;
    }

    public double getE1UmDiaPct() {
        return E1UmDiaPct;
    }

    public void setE1UmDiaPct(double e1UmDiaPct) {
        E1UmDiaPct = e1UmDiaPct;
    }

    public double getE1MaiorUmDiaPct() {
        return E1MaiorUmDiaPct;
    }

    public void setE1MaiorUmDiaPct(double e1MaiorUmDiaPct) {
        E1MaiorUmDiaPct = e1MaiorUmDiaPct;
    }

    public double getE1MaiorTresDiasPct() {
        return E1MaiorTresDiasPct;
    }

    public void setE1MaiorTresDiasPct(double e1MaiorTresDiasPct) {
        E1MaiorTresDiasPct = e1MaiorTresDiasPct;
    }

    public double getE1PendentePct() {
        return E1PendentePct;
    }

    public void setE1PendentePct(double e1PendentePct) {
        E1PendentePct = e1PendentePct;
    }
}
