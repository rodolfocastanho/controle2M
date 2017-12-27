package br.com.rodolfocastanho.controle2M.service;

import br.com.rodolfocastanho.controle2M.model.TipoServicoOrdem;
import br.com.rodolfocastanho.controle2M.repository.TipoServicoOrdemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoServicoOrdemService {

    @Autowired
    private TipoServicoOrdemRepository tipoServicoOrdemRepository;

    public List<TipoServicoOrdem> findAllByOrderByDescricao(){
        return tipoServicoOrdemRepository.findAllByOrderByDescricao();
    }

}
