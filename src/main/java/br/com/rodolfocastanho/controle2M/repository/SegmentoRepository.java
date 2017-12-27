package br.com.rodolfocastanho.controle2M.repository;

import br.com.rodolfocastanho.controle2M.model.Segmento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SegmentoRepository extends JpaRepository<Segmento, String> {

    List<Segmento> findAllByOrderByDescricao();

}
