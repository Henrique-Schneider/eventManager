package br.com.EventXplorer.eventX.repository;

import br.com.EventXplorer.eventX.models.Inscricao;

public interface InscricaoRepository {
    Inscricao findById(long id);
    void save(long idEvento, long idUsuario); // Método para salvar uma inscrição
   Inscricao findAll(); // Método para recuperar todas as inscrições
    void update();
    void deleteById(long id);
}
