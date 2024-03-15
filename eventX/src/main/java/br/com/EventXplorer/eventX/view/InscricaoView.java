package br.com.EventXplorer.eventX.view;

import br.com.EventXplorer.eventX.models.Inscricao;
import br.com.EventXplorer.eventX.models.Usuario;
import br.com.EventXplorer.eventX.models.Evento;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class InscricaoView {


        public static void mostrarDetalhesInscricao (Inscricao inscricao){
            System.out.println("Detalhes da Inscrição:");
            System.out.println("ID da Inscrição: " + inscricao.getId());
            System.out.println("ID do Evento: " + inscricao.getIdEvento());
            System.out.println("ID do Usuário: " + inscricao.getIdUsuario());
            System.out.println("Participantes:");

            List<Usuario> participantes = inscricao.getParticipantes();
            for (Usuario participante : participantes) {
                System.out.println("  - ID: " + participante.getId());
                System.out.println("    Nome: " + participante.getName());
                System.out.println("    Email: " + participante.getEmail());
                System.out.println("    Cidade: " + participante.getCity());
                System.out.println("    Idade: " + participante.getAge());
            }

            System.out.println("Data da Inscrição: " + inscricao.getDataInscricao().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
    }

