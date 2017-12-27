package br.com.rodolfocastanho.controle2M.service;

import br.com.rodolfocastanho.controle2M.dto.UsuarioDTO;
import br.com.rodolfocastanho.controle2M.model.Usuario;
import br.com.rodolfocastanho.controle2M.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void Save(Usuario usuario){
        usuarioRepository.save(usuario);
    }

    public Page<Usuario> Search(String nome, Pageable pageable){
        return usuarioRepository.findByNomeContainingIgnoreCaseOrderByIdDesc(nome, pageable);
    }

    public List<Usuario> OrderListByName(){
        return usuarioRepository.findAllByOrderByNomeAsc();
    }

    public List<Usuario> findByLogin(String login){
        return usuarioRepository.findByLogin(login);
    }

    public List<UsuarioDTO> filtradas(String nome){
        return usuarioRepository.filtradas(nome);
    }

    public Usuario validaLogin(String login) {
        return usuarioRepository.findByLoginIgnoreCase(login);
    }


}
