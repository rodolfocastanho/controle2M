package br.com.rodolfocastanho.controle2M.service;

import br.com.rodolfocastanho.controle2M.model.Ordem;
import br.com.rodolfocastanho.controle2M.model.Usuario;
import br.com.rodolfocastanho.controle2M.repository.OrdemRepository;
import org.aspectj.weaver.ast.Or;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdemService {

    @Autowired
    private OrdemRepository ordemRepository;

    public void Save(Ordem ordem){
        ordemRepository.save(ordem);
    }

    public List<Ordem> Search(Ordem ordem){
        return ordemRepository.findAll();
    }

    public List<Ordem> findByStatusOrdemNot(String status){
        return ordemRepository.findByStatusOrdemNot(status);
    }

    public List<Ordem> findByStatusOrdemNotAndNumero(String status, String numero){
        return ordemRepository.findByStatusOrdemNotAndNumero(status, numero);
    }

    public Ordem findByIdAndStatusOrdem(Long id, String status){
        return ordemRepository.findByIdAndStatusOrdem(id, status);
    }

    public List<Ordem> filtroFormPendentes(String os, String pendencia, Usuario responsavel){
        return ordemRepository.filtroFormPendentes(os, pendencia, responsavel);
    }

    public Page<Ordem> filtroFormPendentes(String os, String pendencia, Usuario responsavel, Pageable pageable){
        return ordemRepository.filtroFormPendentes(os, pendencia, responsavel, pageable);
    }

    public Page<Ordem> filtroFormOrdens(String os, String pendencia, Usuario responsavel, Pageable pageable){
        return ordemRepository.filtroFormOrdens(os, pendencia, responsavel, pageable);
    }

}
