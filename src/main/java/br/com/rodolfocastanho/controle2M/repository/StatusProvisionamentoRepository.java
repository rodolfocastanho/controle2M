package br.com.rodolfocastanho.controle2M.repository;

import br.com.rodolfocastanho.controle2M.model.StatusProvisionamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusProvisionamentoRepository extends JpaRepository<StatusProvisionamento, String> {

    List<StatusProvisionamento> findAllByOrderByDescricao();

}
