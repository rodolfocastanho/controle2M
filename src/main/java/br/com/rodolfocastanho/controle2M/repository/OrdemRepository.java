package br.com.rodolfocastanho.controle2M.repository;

import br.com.rodolfocastanho.controle2M.model.Ordem;
import br.com.rodolfocastanho.controle2M.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdemRepository extends JpaRepository<Ordem,Long> {

    public List<Ordem> findByStatusOrdemNot(String status);

    public List<Ordem> findByStatusOrdemNotAndNumero(String status, String numero);

    public Ordem findByIdAndStatusOrdem(Long id, String status);


    @Query("select o from Ordem o where " +
            "o.numero like %?1% and " +
            "(o.statusOrdem like %?2% and o.statusOrdem <> 'Finalizado') and " +
            "(o.responsavel = ?3 or ?3 is null)")
    public List<Ordem> filtroFormPendentes(String os, String pendencia, Usuario responsavel);

    @Query("select o from Ordem o where " +
            "o.numero like %?1% and " +
            "(o.statusOrdem like %?2% and o.statusOrdem <> 'Finalizado') and " +
            "(o.responsavel = ?3 or ?3 is null)")
    public Page<Ordem> filtroFormPendentes(String os, String pendencia, Usuario responsavel, Pageable pageable);

    @Query("select o from Ordem o where " +
            "o.numero like %?1% and " +
            "o.statusOrdem like %?2% and " +
            "(o.responsavel = ?3 or ?3 is null)")
    public Page<Ordem> filtroFormOrdens(String os, String pendencia, Usuario responsavel, Pageable pageable);


}
