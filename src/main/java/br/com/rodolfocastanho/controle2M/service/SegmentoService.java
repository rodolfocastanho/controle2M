package br.com.rodolfocastanho.controle2M.service;

import br.com.rodolfocastanho.controle2M.model.Segmento;
import br.com.rodolfocastanho.controle2M.repository.SegmentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SegmentoService {

    @Autowired
    private SegmentoRepository segmentoRepository;

    public List<Segmento> OrderListByDescricao(){
        return segmentoRepository.findAllByOrderByDescricao();
    }

}
