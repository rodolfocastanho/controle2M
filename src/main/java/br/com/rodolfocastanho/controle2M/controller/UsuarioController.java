package br.com.rodolfocastanho.controle2M.controller;

import br.com.rodolfocastanho.controle2M.controller.page.PageWrapper;
import br.com.rodolfocastanho.controle2M.dto.UsuarioDTO;
import br.com.rodolfocastanho.controle2M.model.Perfil;
import br.com.rodolfocastanho.controle2M.model.Usuario;
import br.com.rodolfocastanho.controle2M.repository.UsuarioRepository;
import br.com.rodolfocastanho.controle2M.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private final String INDEX = "CadastroDeUsuario";

    @Autowired
    private UsuarioService usuarioService;

    @RequestMapping(value = "/novo")
    public ModelAndView index(Usuario usuario){
        ModelAndView mv = new ModelAndView(INDEX);
        mv.addObject("listaPerfil", Perfil.values());
        return mv;
    }


    @RequestMapping(value = "/novo", method = RequestMethod.POST)
    public ModelAndView Save(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes){

        if (result.hasErrors()){
            System.out.println("Erro no objeto: " + result.getAllErrors().toString());
            return index(usuario);
        }

        ModelAndView mv;

        if (usuario.getId() == null){
            //Verifica existência de usuário
            if (usuarioService.findByLogin(usuario.getLogin()).isEmpty()){
                attributes.addFlashAttribute("msg_success", "Usuario salvo com sucesso!!!");
                usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getLogin()));
                usuario.setPrimeiroAcesso(1);
                usuarioService.Save(usuario);
            } else {
                attributes.addFlashAttribute("msg_error", "Login já existente!!!");
            }
            mv = new ModelAndView("redirect:/usuario/novo");
        } else {
            if (usuario.getResetaSenha() == true){
                usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getLogin()));
                usuario.setPrimeiroAcesso(1);
            }
            usuarioService.Save(usuario);
            mv = new ModelAndView("redirect:/usuario?nome=" + usuario.getNome().toString());
        }

        return mv;
    }

    @RequestMapping
    public ModelAndView Search(Usuario usuario, @PageableDefault(size = 8)Pageable pageable, HttpServletRequest httpServletRequest){
        String nome = usuario.getNome() == null ? "%" : usuario.getNome();
        //String nome = usuario.getNome();

        ModelAndView mv = new ModelAndView("PesquisaDeUsuario");
        PageWrapper<Usuario> pageWrapper = new PageWrapper<>(usuarioService.Search(nome, pageable), httpServletRequest);
        mv.addObject("pagina", pageWrapper);
        return mv;
    }
    /* Estilo ModelAndView
    public ModelAndView Search(Usuario usuario, Model model){
        String nome = usuario.getNome() == null ? "%" : usuario.getNome();

        ModelAndView mv = new ModelAndView("PesquisaDeUsuario");
        mv.addObject("usuarios", usuarioService.Search(nome));
        return mv;
    }
    */

    @RequestMapping("/{id}")
    public ModelAndView Edit(@PathVariable("id") Usuario usuario){
        ModelAndView mv = new ModelAndView(INDEX);
        mv.addObject("listaPerfil", Perfil.values());
        mv.addObject(usuario);
        return mv;
    }

    @RequestMapping("/filtro")
    public @ResponseBody List<UsuarioDTO> Filtradas (String nome){
        return usuarioService.filtradas(nome);
    }

}
