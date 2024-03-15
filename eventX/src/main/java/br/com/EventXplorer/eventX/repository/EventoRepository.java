package br.com.EventXplorer.eventX.repository;

import br.com.EventXplorer.eventX.models.Evento;
import br.com.EventXplorer.eventX.models.Usuario;
import br.com.EventXplorer.eventX.models.enums.Categorias;
import br.com.EventXplorer.eventX.models.enums.Status;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface EventoRepository {
    Evento findById(long id);
    Evento findByName(String nome);
    void save(String nome, String  address, Categorias category, Status status, LocalTime startTime, LocalDate startDate);
    void findAll();
    void update(long id, String nome, String  address, Categorias category, Status status, LocalTime startTime, LocalDate startDate);
    void deleteById(long id);

}
