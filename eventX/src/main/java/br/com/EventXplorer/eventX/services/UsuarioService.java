package br.com.EventXplorer.eventX.services;

import br.com.EventXplorer.eventX.repository.UsuarioRepository;
import br.com.EventXplorer.eventX.validations.UsuarioDuplicadoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
@Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void cadastrarUsuario(String nome, String email, String cidade, int idade) {

        usuarioRepository.save(nome, email, cidade, idade);
    }

    public void listarUsuarios() {
        usuarioRepository.findAll();
    }

    public void buscarUsuarioPorId(long id){
        usuarioRepository.findById(id);
    }

    public void atualizarUsuario(long id, String nome, String email, String cidade, int idade){
        usuarioRepository.update(id, nome, email, cidade, idade);
    }

    public void excluirUsuarioPorId(long id){
        usuarioRepository.deleteById(id);
    }


}
