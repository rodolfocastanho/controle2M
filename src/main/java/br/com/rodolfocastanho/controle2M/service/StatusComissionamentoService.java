package br.com.rodolfocastanho.controle2M.service;


import br.com.rodolfocastanho.controle2M.model.StatusComissionamento;
import br.com.rodolfocastanho.controle2M.repository.StatusComissionamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class StatusComissionamentoService {

    @Autowired
    private StatusComissionamentoRepository statusComissionamentoRepository;

    public List<StatusComissionamento> OrderListByDescricao(){
        return statusComissionamentoRepository.findAllByOrderByDescricao();
    }

    public List<StatusComissionamento> findByIdIn(Collection<String> status){
        return statusComissionamentoRepository.findByIdIn(status);
    }

}
