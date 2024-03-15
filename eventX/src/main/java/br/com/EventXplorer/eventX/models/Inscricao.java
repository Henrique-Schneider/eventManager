package br.com.EventXplorer.eventX.models;

import lombok.*;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class Inscricao {
    private long id;
    private long idEvento;
    private long idUsuario;
    private Evento evento;
    private List<Usuario> participantes;
    private LocalDateTime dataInscricao;

    // Construtor com lista de participantes
    public Inscricao(long idEvento, long idUsuario, LocalDateTime dataInscricao) {
        this.idEvento = idEvento;
        this.idUsuario = idUsuario;
        this.participantes = new ArrayList<>();
        this.dataInscricao = dataInscricao;
 }

    // Método para adicionar um participante à lista
    public void adicionarParticipante(Usuario participante, LocalDateTime dataInscricao) {

        if (participantes == null) {
            participantes = new ArrayList<>();
        }
        participantes.add(participante);
    }
}

