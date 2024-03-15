package br.com.EventXplorer.eventX.repository;

import br.com.EventXplorer.eventX.models.Usuario;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository {
    Usuario findById(long id);
    Usuario findByEmail(String  email);
    void save(String nome, String email, String cidade, int idade);
    void findAll();
    void update(long id, String nome, String email, String cidade, int idade);
    void deleteById(long id);
}
