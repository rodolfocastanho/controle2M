package br.com.rodolfocastanho.controle2M.repository;

import br.com.rodolfocastanho.controle2M.model.StatusComissionamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface StatusComissionamentoRepository extends JpaRepository<StatusComissionamento, String> {

    List<StatusComissionamento> findAllByOrderByDescricao();

    List<StatusComissionamento> findByIdIn(Collection<String> status);

}
