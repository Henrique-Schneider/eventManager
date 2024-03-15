package br.com.EventXplorer.eventX.services;


import br.com.EventXplorer.eventX.models.Inscricao;
import br.com.EventXplorer.eventX.repository.InscricaoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Collections;

public class InscricaoService {


    @Autowired
    private InscricaoRepository inscricaoRepository;

    public InscricaoService(InscricaoRepository inscricaoRepository) {
        this.inscricaoRepository = inscricaoRepository;
    }

    public void realizarIncricaoEvento(long idEvento, long idUsuario){

        inscricaoRepository.save(idEvento, idUsuario);

    }

    public void buscarTodasAsIncricoes(){
        inscricaoRepository.findAll();
    }

    public void buscarInscricaoPorId(long id){
        inscricaoRepository.findById(id);
    }
}
