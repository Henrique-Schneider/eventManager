package br.com.EventXplorer.eventX.repository.impl;

import br.com.EventXplorer.eventX.models.Evento;

import br.com.EventXplorer.eventX.models.enums.Categorias;
import br.com.EventXplorer.eventX.models.enums.Status;
import br.com.EventXplorer.eventX.repository.EventoRepository;
import br.com.EventXplorer.eventX.utils.LocalDateAdapter;
import br.com.EventXplorer.eventX.utils.LocalTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


public class EventoRepositoryImpl implements EventoRepository {

    private static long proximoId = 1;
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();
    private static final String FILENAME = "src/main/resources/events.data";

    private static final Logger logger = LogManager.getLogger();


    @Override
    public Evento findById(long id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Evento evento = gson.fromJson(line, Evento.class);
                if (evento != null && evento.getId() == id) {
                    System.out.println(evento);
                    return evento;
                }
            }
        } catch (IOException e) {
            logger.error("Erro ao carregar usuários: {}", e.getMessage());
        }
        return null;
    }

    @Override
    public Evento findByName(String nome) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Evento evento = gson.fromJson(line, Evento.class);
                if (evento != null && evento.getEventName().equals(nome)) {
                    return evento; // Retorna o evento se o nome for encontrado
                }
            }
        } catch (IOException e) {
            logger.error("Erro ao carregar eventos: {}", e.getMessage());
        }
        return null; // Retorna null se o evento não for encontrado
    }

    @Override
    public void save(String nome, String address, Categorias category, Status status, LocalTime startTime, LocalDate startDate) {
        // Verifica se já existe um evento com id especificado
        if (findByName(nome) != null) {
            logger.info("Evento com o Nome {} já cadastrado.", nome);
            return; // Não salva o usuário e encerra o método
        }

        // Lê todos os eventos do arquivo para determinar o próximo ID disponível
        List<Evento> eventos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    Evento evento = gson.fromJson(line, Evento.class);
                    eventos.add(evento);
                }
            }
        } catch (IOException e) {
            logger.error("Erro ao carregar eventos: {}", e.getMessage());
        }

        // Determina o próximo ID disponível com base nos eventos existentes
        proximoId = eventos.isEmpty() ? 1 : eventos.stream().mapToLong(Evento::getId).max().orElse(0) + 1;

        // Cria e salva o novo evento
        Evento evento = new Evento(nome, address, category, status, startTime, startDate);
        evento.setId(proximoId++);

        String eventoJson = gson.toJson(evento);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME, true))) {
            writer.write(eventoJson);
            writer.newLine();
            logger.info("Evento cadastrado com sucesso!");

        } catch (IOException e) {
            logger.error("Erro ao cadastrar evento: {}", e.getMessage());
        }
    }

    @Override
    public void findAll() {
        List<Evento> eventos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) { // Verifica se a linha não está vazia
                    Evento evento = gson.fromJson(line, Evento.class);
                    eventos.add(evento);
                }
            }
        } catch (IOException e) {
            logger.error("Erro ao carregar eventos: {}", e.getMessage());
        }

        System.out.println(eventos);
    }

    @Override
    public void update(long id, String name, String address, Categorias category, Status status, LocalTime startTime, LocalDate startDate) {

        List<Evento> eventos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Evento evento = gson.fromJson(line, Evento.class);
                if (!line.trim().isEmpty() && evento.getId() == id) {
                    // Atualiza as informações do usuário encontrado
                    evento.setEventName(name);
                    evento.setAddress(address);
                    evento.setCategory(category);
                    evento.setStatus(status);
                    evento.setStartTime(startTime);
                    evento.setStartDate(startDate);
                }
                if (evento != null) { // Verifica se o usuário não é nulo antes de adicioná-lo
                    eventos.add(evento);
                }
            }
        } catch (IOException e) {
            logger.error("Erro ao carregar eventos: {}", e.getMessage());
        }

        // Salva as alterações no arquivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME))) {
            for (Evento evento : eventos) {
                String eventoJson = gson.toJson(evento);
                writer.write(eventoJson);
                writer.newLine();

            }
        } catch (IOException e) {
            logger.error("Erro ao atualizar evento: {}", e.getMessage());
        }

    }

    @Override
    public void deleteById(long id) {
        List<Evento> eventos = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Evento evento = gson.fromJson(line, Evento.class);
                if (evento != null && evento.getId() != id) {
                    eventos.add(evento); // Adiciona todos os usuários exceto o que será excluído
                }
            }
        } catch (IOException e) {
            logger.error("Erro ao carregar eventos: {}", e.getMessage());
        }

        // Escreve a lista atualizada de usuários de volta no arquivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME))) {
            for (Evento evento : eventos) {
                String eventoJson = gson.toJson(evento);
                writer.write(eventoJson);
                writer.newLine();
            }
        } catch (IOException e) {
            logger.error("Erro ao salvar eventos: {}", e.getMessage());
        }
    }

}
