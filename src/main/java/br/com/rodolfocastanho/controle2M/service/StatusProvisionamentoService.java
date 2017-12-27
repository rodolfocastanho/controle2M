package br.com.rodolfocastanho.controle2M.service;

import br.com.rodolfocastanho.controle2M.model.StatusProvisionamento;
import br.com.rodolfocastanho.controle2M.repository.StatusProvisionamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusProvisionamentoService {

    @Autowired
    private StatusProvisionamentoRepository statusProvisionamentoRepository;

    public List<StatusProvisionamento> OrderListByDescricao(){
        return statusProvisionamentoRepository.findAllByOrderByDescricao();
    }

}
