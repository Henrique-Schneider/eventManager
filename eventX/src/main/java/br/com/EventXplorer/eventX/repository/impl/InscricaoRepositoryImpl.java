package br.com.EventXplorer.eventX.repository.impl;

import br.com.EventXplorer.eventX.models.Evento;
import br.com.EventXplorer.eventX.models.Inscricao;
import br.com.EventXplorer.eventX.models.Usuario;
import br.com.EventXplorer.eventX.repository.EventoRepository;
import br.com.EventXplorer.eventX.repository.InscricaoRepository;
import br.com.EventXplorer.eventX.repository.UsuarioRepository;
import br.com.EventXplorer.eventX.utils.LocalDateAdapter;
import br.com.EventXplorer.eventX.utils.LocalDateTimeAdapter;
import br.com.EventXplorer.eventX.utils.LocalTimeAdapter;
import com.google.gson.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;


public class InscricaoRepositoryImpl implements InscricaoRepository {

    private static long proximoId = 1;
    private static final String FILENAME = "src/main/resources/registration.data";
    private static final Logger logger = LogManager.getLogger();
    private final EventoRepository eventoRepository;
    private final UsuarioRepository usuarioRepository;
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    public InscricaoRepositoryImpl(EventoRepository eventoRepository, UsuarioRepository usuarioRepository) {
        this.eventoRepository = eventoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Inscricao findById(long id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Inscricao inscricao = gson.fromJson(line, Inscricao.class);
                if (inscricao != null && inscricao.getId() == id) {
                    System.out.println(inscricao);
                    return inscricao;
                }
            }
        } catch (IOException e) {
            logger.error("Erro ao carregar usuários: {}", e.getMessage());
        }
        return null;
    }

    @Override
    public void save(long idEvento, long idUsuario) {
        // Verifica se já existe um evento com o ID especificado
        Evento evento = eventoRepository.findById(idEvento);
        if (evento == null) {
            logger.info("Evento com o ID {} não encontrado.", idEvento);
            return; // Encerra o método
        }

        // Verifica se já existe um usuário com o ID especificado
        Usuario usuario = usuarioRepository.findById(idUsuario);
        if (usuario == null) {
            logger.info("Usuário com o ID {} não encontrado.", idUsuario);
            return; // Encerra o método
        }

        // Criação do objeto LocalDateTime para a data da inscrição
        LocalDateTime dataInscricao = LocalDateTime.now();

        // Lê todas as inscrições do arquivo
        List<Inscricao> inscricoes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    Inscricao existentInscricao = gson.fromJson(line, Inscricao.class);
                    inscricoes.add(existentInscricao);
                }
            }
        } catch (IOException e) {
            logger.error("Erro ao carregar inscrições: {}", e.getMessage());
        }

        // Verifica se já existe uma inscrição para o evento
        Optional<Inscricao> existingInscricao = inscricoes.stream()
                .filter(i -> i.getIdEvento() == idEvento)
                .findFirst();

        if (existingInscricao.isPresent()) {
            // Se já existe uma inscrição para o evento, verifica se o participante já está na lista
            Inscricao inscricao = existingInscricao.get();
            boolean participanteJaAdicionado = inscricao.getParticipantes().stream()
                    .anyMatch(p -> p.getId() == idUsuario); // <--- Aqui era o problema
            if (!participanteJaAdicionado) {
                // Se o participante ainda não está na lista, adiciona-o
                inscricao.adicionarParticipante(usuario, dataInscricao); // <--- Passando o objeto Usuario
            } else {
                logger.info("Usuário com o ID {} já está inscrito no evento {}.", idUsuario, idEvento);
                return; // Encerra o método
            }
        } else {
            // Se não existe uma inscrição para o evento, cria uma nova
            Inscricao inscricao = new Inscricao(idEvento, idUsuario, dataInscricao);
            inscricao.setId(proximoId++);
            inscricao.adicionarParticipante(usuario, dataInscricao); // <--- Passando o objeto Usuario
            inscricoes.add(inscricao);
        }

        // Salva as inscrições atualizadas no arquivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME))) {
            for (Inscricao inscricao : inscricoes) {
                writer.write(gson.toJson(inscricao));
                writer.newLine();
            }
            logger.info("Inscrição cadastrada com sucesso!");
        } catch (IOException e) {
            logger.error("Erro ao cadastrar inscrição: {}", e.getMessage());
        }
    }


    @Override
    public Inscricao findAll() {
        List<Inscricao> inscricaos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) { // Verifica se a linha não está vazia
                    Inscricao inscricao = gson.fromJson(line, Inscricao.class);
                    inscricaos.add(inscricao);
                    return inscricao;
                }
            }
        } catch (IOException e) {
            logger.error("Erro ao carregar usuários: {}", e.getMessage());
        }
        System.out.println(inscricaos);
      return null;
    }



    @Override
    public void update() {
        // Implementação para atualizar uma inscrição, se necessário
    }

    @Override
    public void deleteById(long id) {
        // Implementação para excluir uma inscrição por ID, se necessário
    }
}