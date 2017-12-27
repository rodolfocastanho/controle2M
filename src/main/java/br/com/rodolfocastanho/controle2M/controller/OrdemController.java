package br.com.rodolfocastanho.controle2M.controller;


import br.com.rodolfocastanho.controle2M.controller.page.PageWrapper;
import br.com.rodolfocastanho.controle2M.model.*;
import br.com.rodolfocastanho.controle2M.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Controller
@RequestMapping("/ordem")
public class OrdemController {

    private final String INDEX = "CadastroDeOrdem";

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private OrdemService ordemService;

    @Autowired
    private StatusProvisionamentoService provisionamentoService;

    @Autowired
    private StatusComissionamentoService comissionamentoService;

    @Autowired
    private TipoServicoOrdemService tipoServicoOrdemService;

    @Autowired
    private SegmentoService segmentoService;

    /*
    private final Usuario userLogIn =
            usuarioService.validaLogin(
                    SecurityContextHolder.getContext().getAuthentication().getName()
            );
    */
    private Date dataAtual = Date.valueOf(LocalDate.now());
    //private Calendar dataAtual = Calendar.getInstance();


    @RequestMapping(value = "/novo")
    public ModelAndView index(Ordem ordem){
        ModelAndView mv = new ModelAndView(INDEX);
        mv.addObject("listaDeTipos", tipoServicoOrdemService.findAllByOrderByDescricao());
        mv.addObject("listaDeUsuarios", usuarioService.OrderListByName());
        mv.addObject("listaDeSegmento", segmentoService.OrderListByDescricao());
        return mv;
    }

    @RequestMapping(value = "/novo", method = RequestMethod.POST)
    public ModelAndView Save(@Valid Ordem ordem, BindingResult result, RedirectAttributes attributes){

        ordem.setDataEntrada(Calendar.getInstance());

        if (result.hasErrors()){
            System.out.println("Erro no objeto: " + result.getAllErrors().toString());
            return index(ordem);
        }

        //Verificar existência de número de ordem em aberto
        if (ordemService.findByStatusOrdemNotAndNumero("Finalizado", ordem.getNumero()).isEmpty()){

            ordem.setStatusOrdem("Aprovisionamento");
            ordemService.Save(ordem);
            attributes.addFlashAttribute("msg_success", "Ordem gravada com sucesso!!!");
        } else {
            attributes.addFlashAttribute("msg_error", "Já existe ordem pendente com este número!!!");
        }

        return new ModelAndView("redirect:/ordem/novo");
    }

    @GetMapping
    public ModelAndView Search(Ordem ordem, @PageableDefault(size = 8)Pageable pageable, HttpServletRequest httpServletRequest){

        ModelAndView mv = new ModelAndView("PesquisaDeOrdem");

        String os = ordem.getNumero() == null || ordem.getNumero() == "" ? "%" : ordem.getNumero();
        String pendencia = ordem.getStatusOrdem() == null || ordem.getStatusOrdem() == "" ? "%" : ordem.getStatusOrdem();

        if(ordem.getResponsavel() != null && ordem.getResponsavel().getId() == null) ordem.setResponsavel(null);
        if (pendencia.equals("Comissionamento")) ordem.setResponsavel(null);

        mv.addObject("listaDeUsuarios", usuarioService.OrderListByName());
        PageWrapper<Ordem> pageWrapper = new PageWrapper<>(ordemService.filtroFormPendentes(os, pendencia, ordem.getResponsavel(), pageable), httpServletRequest);
        mv.addObject("pagina", pageWrapper);
        return mv;

        /* estilo ModelAndView
        ModelAndView mv = new ModelAndView("PesquisaDeOrdem");

        String os = ordem.getNumero() == null || ordem.getNumero() == "" ? "%" : ordem.getNumero();
        String pendencia = ordem.getStatusOrdem() == null || ordem.getStatusOrdem() == "" ? "%" : ordem.getStatusOrdem();
        //Usuario usuario = ordem.getResponsavel() == null ? null : ordem.getResponsavel();
        if(ordem.getResponsavel() != null && ordem.getResponsavel().getId() == null) ordem.setResponsavel(null);
        if (pendencia.equals("Comissionamento")) ordem.setResponsavel(null);

        mv.addObject("listaDeUsuarios", usuarioService.OrderListByName());
        //mv.addObject("ordens", ordemService.findByStatusOrdemNot("Finalizado"));
        mv.addObject("ordens", ordemService.filtroFormPendentes(os, pendencia, ordem.getResponsavel()));
        return mv;
        */
    }

    @RequestMapping(value = "/aprovisionamento", method = RequestMethod.GET)
    public ModelAndView EditProvisionamento(){
        return new ModelAndView("redirect:/ordem");
    }

    @RequestMapping(value = "/aprovisionamento/{id}", method = RequestMethod.GET)
    public ModelAndView EditProvisionamento(@PathVariable("id") Ordem ordem){

        ModelAndView mv;
        //Verificar se ordem já não está provisionada
        if (ordem == null) {
            mv = new ModelAndView("redirect:/ordem");
        } else {
            if (ordemService.findByIdAndStatusOrdem(ordem.getId(), "Aprovisionamento") == null){
                mv = new ModelAndView("redirect:/ordem");
            } else {
                mv = new ModelAndView("LiberaProvisionamento");
                //mv.addObject("listaStatusProvisionamento", StatusProvisionamento.values());
                List<String> status = new ArrayList<String>(Arrays.asList("ENVOLVE","NAO_ENVOLVE"));
                mv.addObject("listaStatusComissionamento", comissionamentoService.findByIdIn(status));
                mv.addObject("listaStatusProvisionamento", provisionamentoService.OrderListByDescricao());
                mv.addObject(ordem);
            }
        }

        return mv;
    }

    @RequestMapping(value = "/aprovisionamento", method = RequestMethod.POST)
    public ModelAndView SaveProvisionamento(Ordem ordem, RedirectAttributes attributes){

        ModelAndView mv;

        if(//ordem.getDataProvisionamento() == null ||
                ordem.getStatusProvisionamento() == null ||
                ordem.getStatusComissionamento() == null){
            attributes.addFlashAttribute("msg_error", "Preencha todos os campos");
            mv = new ModelAndView("redirect:/ordem/aprovisionamento/" + ordem.getId());
        } else {
            ordem.setDataProvisionamento(Calendar.getInstance());
            ordem.setObservacaoProvisionamento(("["+dataAtual+"] "+ordem.getObservacaoProvisionamento()).toString());
            ordem.setResponsavelProvisionamento(usuarioService.validaLogin(SecurityContextHolder.getContext().getAuthentication().getName()));

            if(!ordem.getStatusProvisionamento().getId().equals("LIBERADO")){
                StatusComissionamento statusComissionamento = new StatusComissionamento();
                statusComissionamento.setId(ordem.getStatusProvisionamento().getId());
                statusComissionamento.setDescricao(ordem.getStatusProvisionamento().getDescricao());

                ordem.setStatusComissionamento(statusComissionamento);
                ordem.setDataComissionamento(ordem.getDataProvisionamento());
                ordem.setResponsavelComissionamento(ordem.getResponsavelProvisionamento());
                //ordem.setResponsavelProvisionamento(userLogIn);
                ordem.setStatusOrdem("Finalizado");
            } else {
                if (ordem.getStatusComissionamento().getId().equals("NAO_ENVOLVE")){
                    //ordem.setStatusComissionamento("NAO_ENVOLVE");
                    ordem.setDataComissionamento(ordem.getDataProvisionamento());
                    ordem.setResponsavelComissionamento(ordem.getResponsavelProvisionamento());
                    //ordem.setResponsavelProvisionamento(userLogIn);
                    ordem.setStatusOrdem("Finalizado");
                } else {
                    ordem.setStatusComissionamento(null);
                    ordem.setDataComissionamento(null);
                    ordem.setResponsavelComissionamento(null);
                    //ordem.setResponsavelProvisionamento(userLogIn);
                    ordem.setStatusOrdem("Comissionamento");
                }
            }

            ordemService.Save(ordem);
            mv = new ModelAndView("redirect:/ordem");
        }
        return mv;
    }

    @RequestMapping(value = "/comissionamento", method = RequestMethod.GET)
    public ModelAndView EditComissionamento(){
        return new ModelAndView("redirect:/ordem");
    }

    @RequestMapping(value = "/comissionamento/{id}", method = RequestMethod.GET)
    public ModelAndView EditComissionamento(@PathVariable("id") Ordem ordem){

        ModelAndView mv;
        //Verificar se ordem já não está comissionada
        if (ordem == null) {
            mv = new ModelAndView("redirect:/ordem");
        } else {
            if (ordemService.findByIdAndStatusOrdem(ordem.getId(), "Comissionamento") == null){
                mv = new ModelAndView("redirect:/ordem");
            } else {
                mv = new ModelAndView("LiberaComissionamento");
                List<String> status = new ArrayList<String>(Arrays.asList("LIBERADO","TA_BAL"));
                mv.addObject("listaStatusComissionamento", comissionamentoService.findByIdIn(status));
                mv.addObject(ordem);
            }
        }

        return mv;
    }

    @RequestMapping(value = "/comissionamento", method = RequestMethod.POST)
    public ModelAndView SaveComissionamento(Ordem ordem, RedirectAttributes attributes){

        ModelAndView mv;

        if(ordem.getStatusComissionamento() == null){
            attributes.addFlashAttribute("msg_error", "Preencha todos os campos");
            mv = new ModelAndView("redirect:/ordem/comissionamento/" + ordem.getId());
        } else {
            ordem.setDataComissionamento(Calendar.getInstance());
            ordem.setObservacaoComissionamento(("["+dataAtual+"] "+ordem.getObservacaoComissionamento()).toString());
            ordem.setResponsavelComissionamento(usuarioService.validaLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
            ordem.setStatusOrdem("Finalizado");

            if(!ordem.getStatusComissionamento().getId().equals("LIBERADO")){
                ordem.setStatusOrdem("Comissionamento");
                ordem.setPendenciaBAL(1);
            }

            ordemService.Save(ordem);
            mv = new ModelAndView("redirect:/ordem");
        }
        return mv;
    }


}
