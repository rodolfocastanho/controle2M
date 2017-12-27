package br.com.rodolfocastanho.controle2M.controller;

import br.com.rodolfocastanho.controle2M.controller.page.PageWrapper;
import br.com.rodolfocastanho.controle2M.model.Ordem;
import br.com.rodolfocastanho.controle2M.model.reports.DataAno;
import br.com.rodolfocastanho.controle2M.model.reports.DataFiltro;
import br.com.rodolfocastanho.controle2M.model.reports.DataMes;
import br.com.rodolfocastanho.controle2M.model.reports.ResumoGrafico;
import br.com.rodolfocastanho.controle2M.service.OrdemService;
import br.com.rodolfocastanho.controle2M.service.RelatorioService;
import br.com.rodolfocastanho.controle2M.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.swing.*;
import javax.validation.Valid;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping("/relatorio")
public class RelatorioController {

    private final String INDEX = "reports/index";

    private Date dataAtual = Date.valueOf(LocalDate.now());
    private List<DataMes> listaMes = new DataMes().criaListaMes();
    private List<DataAno> listaAno = new DataAno().criaListaAno();

    @Autowired
    private OrdemService ordemService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RelatorioService relatorioService;

    @RequestMapping()
    public String index(){
        return INDEX;
    }

    @RequestMapping(value = "/ordem", method = RequestMethod.GET)
    public ModelAndView Search(Ordem ordem, @PageableDefault(size = 8)Pageable pageable, HttpServletRequest httpServletRequest){

        ModelAndView mv = new ModelAndView("reports/ConsultaOrdem");

        String os = ordem.getNumero() == null || ordem.getNumero() == "" ? "%" : ordem.getNumero();
        String pendencia = ordem.getStatusOrdem() == null || ordem.getStatusOrdem() == "" ? "%" : ordem.getStatusOrdem();

        if(ordem.getResponsavel() != null && ordem.getResponsavel().getId() == null) ordem.setResponsavel(null);
        if (pendencia.equals("Comissionamento")) ordem.setResponsavel(null);

        mv.addObject("listaDeUsuarios", usuarioService.OrderListByName());
        PageWrapper<Ordem> pageWrapper = new PageWrapper<>(ordemService.filtroFormOrdens(os, pendencia, ordem.getResponsavel(), pageable), httpServletRequest);
        mv.addObject("pagina", pageWrapper);
        return mv;
    }

    /*
    @RequestMapping(value = "/teste", method = RequestMethod.GET)
    public ModelAndView TesteChart() {

        //double[] dadosChart = new double[]{3.0, 3.0, 3.0, 3.0, 2.0, 3.0};
        String dadosChart = "3.0, 3.0, 3.0, 3.0, 2.0, 3.0";
        ModelAndView mv;
        mv = new ModelAndView("reports/TesteChart");
        mv.addObject("grafico",dadosChart);

        return mv;
    }
    */

    @RequestMapping(value = "/grafico/tempo", method = RequestMethod.GET)
    public ModelAndView ConsolidadoTempo(DataFiltro periodo) {

        ModelAndView mv;
        mv = new ModelAndView("reports/GraficoTempo");
        mv.addObject("periodo",periodo);
        //mv.addObject("grafico",dadosChart);

        return mv;
    }

    @RequestMapping(value = "/grafico/tempo", method = RequestMethod.POST)
    public ModelAndView DoingConsolidadoTempo(@Valid DataFiltro periodo, BindingResult result, RedirectAttributes attributes) {

        if (result.hasErrors()){
            return ConsolidadoTempo(periodo);
        }

        ModelAndView mv;

        if(periodo.getDataInicio() == null ||
                periodo.getDataFim() == null ||
                periodo.getDataInicio().compareTo(periodo.getDataFim()) == 1){
            attributes.addFlashAttribute("msg_error", "Erro: Data Fim MENOR QUE Data Início!!!");
            periodo.setDataInicio(null);
            periodo.setDataFim(null);
            mv = new ModelAndView("redirect:/relatorio/grafico/tempo");
        } else {
            ResumoGrafico resumoGrafico = new ResumoGrafico();
            periodo = periodo.fillObjectDataFiltro(periodo);

            Calendar dataInicio = Calendar.getInstance();
            Calendar dataFim = Calendar.getInstance();
            dataInicio.setTime(resumoGrafico.setHoras(periodo.getDataInicio(),0,0,0));
            dataFim.setTime(resumoGrafico.setHoras(periodo.getDataFim(),23,59,59));
            List<Ordem> ordens = relatorioService.findBydataEntradaBetween(dataInicio, dataFim);
            ResumoGrafico resumoGraficoTempo = new ResumoGrafico().carregaResumo(ordens);


            //String dadosChart = "3.0, 3.0, 3.0, 3.0, 2.0, 1.0";
            String GrafAtividadeAprovisionamento = resumoGraficoTempo.getOrdemTotal() + ", " +
                                                    resumoGraficoTempo.getProvisionamentoExecutado() + ", " +
                                                    resumoGraficoTempo.getProvisionamentoUmDia() + ", " +
                                                    resumoGraficoTempo.getProvisionamentoMaiorUmDia() + ", " +
                                                    resumoGraficoTempo.getProvisionamentoMaiorTresDias() + ", " +
                                                    resumoGraficoTempo.getProvisionamentoPendente();

            String GrafAtividadeComissionamento = resumoGraficoTempo.getOrdemTotal() + ", " +
                                                    resumoGraficoTempo.getComissionamentoExecutado() + ", " +
                                                    resumoGraficoTempo.getComissionamentoUmDia() + ", " +
                                                    resumoGraficoTempo.getComissionamentoMaiorUmDia() + ", " +
                                                    resumoGraficoTempo.getComissionamentoMaiorTresDias() + ", " +
                                                    resumoGraficoTempo.getComissionamentoPendente();

            String GrafGeral = resumoGraficoTempo.getOrdemTotal() + ", " +
                                resumoGraficoTempo.getOrdemExecutado();

            String GrafTempoIdeal = "1, " + (int)resumoGraficoTempo.getOrdemMediaAtendimento();

            int GrafKpi = (int)resumoGraficoTempo.getOrdemUmDiaPct();



            mv = new ModelAndView("reports/GraficoTempo");
            mv.addObject("periodo", periodo);
            mv.addObject("resumo", resumoGraficoTempo);
            mv.addObject("GrafAtividadeAprovisionamento", GrafAtividadeAprovisionamento);
            mv.addObject("GrafAtividadeComissionamento", GrafAtividadeComissionamento);
            mv.addObject("GrafGeral", GrafGeral);
            mv.addObject("GrafTempoIdeal", GrafTempoIdeal);
            mv.addObject("GrafKpi", GrafKpi);
        }

        return mv;
    }

    @RequestMapping(value = "/grafico/tempo/detalhe", method = RequestMethod.GET)
    public ModelAndView ConsolidadoTempoDetalhe(@RequestParam String atividade,
                                                @RequestParam String status,
                                                @RequestParam long dtInicio,
                                                @RequestParam long dtFim) {

        ResumoGrafico resumoGrafico = new ResumoGrafico();
        DataFiltro dataFiltro = new DataFiltro();
        dataFiltro.setDataInicio(java.util.Date.from(Instant.ofEpochMilli(dtInicio)));
        dataFiltro.setDataFim(java.util.Date.from(Instant.ofEpochMilli(dtFim)));

        Calendar dataInicio = Calendar.getInstance();
        Calendar dataFim = Calendar.getInstance();
        dataInicio.setTime(resumoGrafico.setHoras(dataFiltro.getDataInicio(),0,0,0));
        dataFim.setTime(resumoGrafico.setHoras(dataFiltro.getDataFim(),23,59,59));

        List<Ordem> ordens = relatorioService.findBydataEntradaBetween(dataInicio, dataFim);
        List<Ordem> listaDetalhe = new ArrayList<>();

        if(status == "Total") {
            listaDetalhe = ordens;
        } else {
            listaDetalhe = resumoGrafico.geraListaDetalhe(ordens, status);
        }

        ModelAndView mv;
        mv = new ModelAndView("reports/TempoDetalhe");
        mv.addObject("listaDetalhe", listaDetalhe);
        mv.addObject("status", status.replace("_", " "));
        mv.addObject("d1", dataFiltro.getDataInicio());
        mv.addObject("d2", dataFiltro.getDataFim());

        return mv;
    }




    @RequestMapping(value = "/grafico/quantitativo", method = RequestMethod.GET)
    public ModelAndView ConsolidadoQuantitativo(DataFiltro periodo) {
        ModelAndView mv;
        mv = new ModelAndView("reports/GraficoQuantitativo");
        mv.addObject("periodo", periodo);
        //mv.addObject("listaMes", listaMes);
        //mv.addObject("listaAno", listaAno);

        return mv;
    }

    @RequestMapping(value = "/grafico/quantitativo", method = RequestMethod.POST)
    public ModelAndView DoingConsolidadoQuantitativo(DataFiltro periodo, BindingResult result, RedirectAttributes attributes) {

        ModelAndView mv;

        if(periodo.getDataInicio() == null){
            attributes.addFlashAttribute("msg_error", "Erro: Preencha a data inicial!!!");
            periodo.setDataInicio(null);
            periodo.setDataFim(null);
            mv = new ModelAndView("redirect:/relatorio/grafico/quantitativo");
        } else {
            ResumoGrafico resumoGraficoQuantitativo = new ResumoGrafico();

            List<ResumoGrafico> resumoSemanal = new ArrayList<>();
            ResumoGrafico resumoSemanalTotal = new ResumoGrafico();
            List<ResumoGrafico> resumoDiario = new ArrayList<>();

            String grafLabel = "";
            String grafEntrada = "";
            String grafAprov = "";
            String grafComis = "";
            String grafPendente = "";

            java.util.Date firstDayOfMonth = periodo.getDataInicio();
            java.util.Date lastDayOfMonth = resumoGraficoQuantitativo.resolveUltimoDiaMes(periodo.getDataInicio());
            int lastWeekOfMonth = resumoGraficoQuantitativo.resolveQtdSemanas(lastDayOfMonth);

            periodo.setDataFim(lastDayOfMonth);
            periodo = periodo.fillObjectDataFiltro(periodo);

            int count = 1;
            java.util.Date countDateInicio = firstDayOfMonth;
            java.util.Date countDateFim = firstDayOfMonth;
            while (count <= lastWeekOfMonth){

                java.util.Date prim = resumoGraficoQuantitativo.resolvePrimeiroUltimo(countDateInicio, true);
                java.util.Date ult = resumoGraficoQuantitativo.resolvePrimeiroUltimo(countDateInicio, false);
                Calendar d1Calendar = Calendar.getInstance();
                Calendar d2Calendar = Calendar.getInstance();

                if(count == 1) prim = countDateInicio;
                if(count == lastWeekOfMonth) ult = lastDayOfMonth;
                d1Calendar.setTime(resumoGraficoQuantitativo.setHoras(prim,0,0,0));
                d2Calendar.setTime(resumoGraficoQuantitativo.setHoras(ult,23,59,59));

                List<Ordem> listaOrdem = relatorioService.findBydataEntradaBetween(d1Calendar, d2Calendar);
                ResumoGrafico resumoGrafico = resumoGraficoQuantitativo.carregaResumo(listaOrdem);
                resumoGrafico.setSemanaMes(count);
                resumoGrafico.setQtdDias(resumoGraficoQuantitativo.qtdDiasSemana(prim, ult));
                resumoSemanal.add(resumoGrafico);

                resumoSemanalTotal.setOrdemTotal(resumoSemanalTotal.getOrdemTotal()+resumoGrafico.getOrdemTotal());
                resumoSemanalTotal.setProvisionamentoExecutado(resumoSemanalTotal.getProvisionamentoExecutado()+resumoGrafico.getProvisionamentoExecutado());
                resumoSemanalTotal.setProvisionamentoExecutadoPct(resumoSemanalTotal.getProvisionamentoExecutadoPct()+resumoGrafico.getProvisionamentoExecutadoPct());
                resumoSemanalTotal.setComissionamentoExecutado(resumoSemanalTotal.getComissionamentoExecutado()+resumoGrafico.getComissionamentoExecutado());
                resumoSemanalTotal.setComissionamentoExecutadoPct(resumoSemanalTotal.getComissionamentoExecutadoPct()+resumoGrafico.getComissionamentoExecutadoPct());
                resumoSemanalTotal.setOrdemPendente(resumoSemanalTotal.getOrdemPendente()+resumoGrafico.getOrdemPendente());

                grafLabel += "Semana " + count + ",";
                grafEntrada += resumoGrafico.getOrdemTotal() + ",";;
                grafAprov += resumoGrafico.getProvisionamentoExecutado() + ",";;
                grafComis += resumoGrafico.getComissionamentoExecutado() + ",";;
                grafPendente += resumoGrafico.getOrdemPendente() + ",";;

                count++;
                countDateInicio = resumoGraficoQuantitativo.somaDia(ult);
            }

            String GrafKpi = "100, " +
                    (int)resumoSemanalTotal.getProvisionamentoExecutadoPct()/lastWeekOfMonth + ", " +
                    (int)resumoSemanalTotal.getComissionamentoExecutadoPct()/lastWeekOfMonth;


            countDateInicio = firstDayOfMonth;
            countDateFim = resumoGraficoQuantitativo.somaDia(lastDayOfMonth);
            while (countDateInicio.before(countDateFim)){
                Calendar d1Calendar = Calendar.getInstance();
                Calendar d2Calendar = Calendar.getInstance();

                d1Calendar.setTime(resumoGraficoQuantitativo.setHoras(countDateInicio,0,0,0));
                d2Calendar.setTime(resumoGraficoQuantitativo.setHoras(countDateInicio,23,59,59));

                List<Ordem> listaOrdem = relatorioService.findBydataEntradaBetween(d1Calendar, d2Calendar);
                ResumoGrafico resumoGrafico = resumoGraficoQuantitativo.carregaResumo(listaOrdem);
                resumoGrafico.setDia(countDateInicio);
                resumoGrafico.setDiaSemana(resumoGraficoQuantitativo.diaSemana(countDateInicio));
                resumoDiario.add(resumoGrafico);

                countDateInicio = resumoGraficoQuantitativo.somaDia(countDateInicio);
            }

            mv = new ModelAndView("reports/GraficoQuantitativo");

            mv.addObject("periodo", periodo);
            mv.addObject("listaMes", listaMes);
            mv.addObject("listaAno", listaAno);

            mv.addObject("resumoSemanal",resumoSemanal);
            mv.addObject("resumoSemanalTotal",resumoSemanalTotal);
            mv.addObject("resumoDiario", resumoDiario);

            mv.addObject("GrafKpi", GrafKpi);
            mv.addObject("grafLabel", grafLabel.substring(0, grafLabel.length()-1));
            mv.addObject("grafEntrada", grafEntrada.substring(0, grafEntrada.length()-1));
            mv.addObject("grafAprov", grafAprov.substring(0, grafAprov.length()-1));
            mv.addObject("grafComis", grafComis.substring(0, grafComis.length()-1));
            mv.addObject("grafPendente", grafPendente.substring(0, grafPendente.length()-1));

        }

        return mv;
    }

}
