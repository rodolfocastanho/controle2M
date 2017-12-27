package br.com.rodolfocastanho.controle2M.repository;

import br.com.rodolfocastanho.controle2M.model.Ordem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Repository
public interface RelatorioRepository extends JpaRepository<Ordem,Long> {

    /*
    @Query("select o from Ordem o where " +
            "o.dataEntrada between ?1 and ?2")
    */
    public List<Ordem> findBydataEntradaBetween (Calendar dataInicio, Calendar dataFim);

}
