package br.com.EventXplorer.eventX.services;

import br.com.EventXplorer.eventX.models.enums.Categorias;
import br.com.EventXplorer.eventX.models.enums.Status;
import br.com.EventXplorer.eventX.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventoService {
    @Autowired
    private EventoRepository eventoRepository;

    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    public void cadastrarEvento(String nome, String address, Categorias category, Status status, LocalTime startTime, LocalDate startDate){
        eventoRepository.save(nome, address, category, status, startTime, startDate);
    }

    public void listEventos(){
        eventoRepository.findAll();
    }

    public void buscarEventoPorId(long id){
        eventoRepository.findById(id);
    }

    public  void atualizarEvento(long id, String name, String address, Categorias category, Status status, LocalTime startTime, LocalDate startDate){
        eventoRepository.update(id, name, address,category,status,startTime, startDate);
    }

    public void excluirEventoPorId(long id){
        eventoRepository.deleteById(id);
    }
}

