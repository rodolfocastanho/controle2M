package br.com.rodolfocastanho.controle2M.service;

import br.com.rodolfocastanho.controle2M.model.Ordem;
import br.com.rodolfocastanho.controle2M.repository.RelatorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class RelatorioService {

    @Autowired
    private RelatorioRepository relatorioRepository;

    public List<Ordem> findBydataEntradaBetween (Calendar dataInicio, Calendar dataFim){
        return relatorioRepository.findBydataEntradaBetween(dataInicio, dataFim);
    }

}
