package br.com.EventXplorer.eventX.repository.impl;

import br.com.EventXplorer.eventX.models.Usuario;
import br.com.EventXplorer.eventX.repository.UsuarioRepository;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioRepositoryImpl implements UsuarioRepository {

    private static long proximoId = 1;
    private static final Gson gson = new Gson();
    private static final String FILENAME = "src/main/resources/user.data";

    private static final Logger logger = LogManager.getLogger();


    @Override
    public Usuario findById(long id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Usuario usuario = gson.fromJson(line, Usuario.class);
                if (usuario != null && usuario.getId() == id) {
                    System.out.println(usuario);
                    return usuario;
                }
            }
        } catch (IOException e) {
            logger.error("Erro ao carregar usuários: {}", e.getMessage());
        }
        return null;
    }

    @Override
    public Usuario findByEmail(String email) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Usuario usuario = gson.fromJson(line, Usuario.class);
                if (usuario != null && usuario.getEmail().equals(email)) {
                    return usuario; // Retorna o usuário se o e-mail for encontrado
                }
            }
        } catch (IOException e) {
            logger.error("Erro ao carregar usuários: {}", e.getMessage());
        }
        return null; // Retorna null se o e-mail não for encontrado ou ocorrer um erro
    }


    @Override
    public void save(String nome, String email, String cidade, int idade) {

        // Verifica se já existe um usuário com o e-mail especificado
        if (findByEmail(email) != null) {
            logger.info("Usuário com o e-mail {} já cadastrado.", email);
            return; // Não salva o usuário e encerra o método
        }

        // Lê todos os eventos do arquivo para determinar o próximo ID disponível
        List<Usuario> usuarios = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    Usuario usuario = gson.fromJson(line, Usuario.class);
                    usuarios.add(usuario);
                }
            }
        } catch (IOException e) {
            logger.error("Erro ao carregar eventos: {}", e.getMessage());
        }

        // Determina o próximo ID disponível com base nos eventos existentes
        proximoId = usuarios.isEmpty() ? 1 : usuarios.stream().mapToLong(Usuario::getId).max().orElse(0) + 1;
        // Cria e salva o novo usuário
        Usuario usuario = new Usuario(nome, email, cidade, idade);
        usuario.setId(proximoId++);

        String usuarioJson = gson.toJson(usuario);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME, true))) {
            writer.write(usuarioJson);
            writer.newLine();
            logger.info("Usuário cadastrado com sucesso!");

        } catch (IOException e) {
            logger.error("Erro ao cadastrar usuário: {}", e.getMessage());
        }
    }



    @Override
    public void findAll() {
        List<Usuario> usuarios = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) { // Verifica se a linha não está vazia
                    Usuario usuario = gson.fromJson(line, Usuario.class);
                    usuarios.add(usuario);
                }
            }
        } catch (IOException e) {
            logger.error("Erro ao carregar usuários: {}", e.getMessage());
        }
        System.out.println(usuarios);
    }

    @Override
    public void update(long id, String nome, String email, String cidade, int idade) {
        List<Usuario> usuarios = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Usuario usuario = gson.fromJson(line, Usuario.class);
                if (!line.trim().isEmpty() && usuario.getId() == id) {
                    // Atualiza as informações do usuário encontrado
                    usuario.setName(nome);
                    usuario.setEmail(email);
                    usuario.setCity(cidade);
                    usuario.setAge(idade);
                }
                if (usuario != null) { // Verifica se o usuário não é nulo antes de adicioná-lo
                    usuarios.add(usuario);
                }
            }
        } catch (IOException e) {
            logger.error("Erro ao carregar usuários: {}", e.getMessage());
        }

        // Salva as alterações no arquivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME))) {
            for (Usuario usuario : usuarios) {
                String usuarioJson = gson.toJson(usuario);
                writer.write(usuarioJson);
                writer.newLine();

            }
        } catch (IOException e) {
            logger.error("Erro ao atualizar usuário: {}", e.getMessage());
        }
    }

    @Override
    public void deleteById(long id) {
        List<Usuario> usuarios = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Usuario usuario = gson.fromJson(line, Usuario.class);
                if (usuario != null && usuario.getId() != id) {
                    usuarios.add(usuario); // Adiciona todos os usuários exceto o que será excluído
                }
            }
        } catch (IOException e) {
            logger.error("Erro ao carregar usuários: {}", e.getMessage());
        }

        // Escreve a lista atualizada de usuários de volta no arquivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME))) {
            for (Usuario usuario : usuarios) {
                String usuarioJson = gson.toJson(usuario);
                writer.write(usuarioJson);
                writer.newLine();
            }
        } catch (IOException e) {
            logger.error("Erro ao salvar usuários: {}", e.getMessage());
        }
    }
}

