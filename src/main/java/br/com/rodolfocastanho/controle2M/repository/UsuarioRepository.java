package br.com.rodolfocastanho.controle2M.repository;

import br.com.rodolfocastanho.controle2M.dto.UsuarioDTO;
import br.com.rodolfocastanho.controle2M.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    List<Usuario> findByLogin(String login);

    Usuario findByLoginIgnoreCase(String login);

    List<Usuario> findByNomeContainingIgnoreCaseOrderByIdDesc(String nome);

    Page<Usuario> findByNomeContainingIgnoreCaseOrderByIdDesc(String nome, Pageable pageable);

    List<Usuario> findAllByOrderByNomeAsc();

    @Query("select new br.com.rodolfocastanho.controle2M.dto.UsuarioDTO(id, nome) from Usuario where nome like %?1%")
    List<UsuarioDTO> filtradas(String nome);

}
