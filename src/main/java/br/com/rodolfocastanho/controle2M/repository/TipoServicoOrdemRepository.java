package br.com.rodolfocastanho.controle2M.repository;

import br.com.rodolfocastanho.controle2M.model.TipoServicoOrdem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoServicoOrdemRepository extends JpaRepository<TipoServicoOrdem, String> {

    List<TipoServicoOrdem> findAllByOrderByDescricao();

}
