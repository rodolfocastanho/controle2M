package br.com.rodolfocastanho.controle2M.controller;

import br.com.rodolfocastanho.controle2M.model.Senha;
import br.com.rodolfocastanho.controle2M.model.Usuario;
import br.com.rodolfocastanho.controle2M.security.UsuarioSistema;
import br.com.rodolfocastanho.controle2M.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class IndexController {

    //TODO: Criar conteúdo da página index.html

    private final String INDEX = "index";

    @Autowired
    private UsuarioService usuarioService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(){
        Usuario usuarioLogado = usuarioService.validaLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        if (usuarioLogado.getPrimeiroAcesso() == (int)1) {
            return "redirect:/senha";
        } else {
            return "redirect:/ordem";
        }

    }

    @RequestMapping(value = "/erro")
    public String error(){
        return "error";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        Usuario usuarioLogado = usuarioService.validaLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        if (usuarioLogado == null){
            return  "login";
        } else {
            if (usuarioLogado.getPrimeiroAcesso() == (int)1) {
                return "redirect:/senha";
            } else {
                return "redirect:/ordem";
            }
        }


    }

    @RequestMapping(value = "/senha", method = RequestMethod.GET)
    public ModelAndView senha(Senha senha){
        ModelAndView mv;

        mv = new ModelAndView("AlteraSenha");
        mv.addObject(senha);

        return mv;
    }

    @RequestMapping(value = "/senha", method = RequestMethod.POST)
    public ModelAndView alteraSenha(@Valid Senha senha, BindingResult result, RedirectAttributes attributes){

        if (result.hasErrors()){
            return senha(senha);
        }

        ModelAndView mv;
        Usuario usuarioLogado = usuarioService.validaLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        //Senha atual != real, senha nova != senha nova valida
        if(!bCryptPasswordEncoder.matches(senha.getSenhaAtual(), usuarioLogado.getSenha()) ||
                !senha.getNovaSenha().matches(senha.getNovaSenhaValida())){
            attributes.addFlashAttribute("msg_error", "Informações divergentes. A senha não foi alterada!!!");
        } else {
            usuarioLogado.setSenha(new BCryptPasswordEncoder().encode(senha.getNovaSenha()));
            usuarioLogado.setPrimeiroAcesso(0);
            usuarioService.Save(usuarioLogado);
            attributes.addFlashAttribute("msg_success", "Senha alterada com sucesso!!!");
        }

        mv = new ModelAndView("redirect:/senha");
        return mv;
    }

}
