package br.com.EventXplorer.eventX.models;

import br.com.EventXplorer.eventX.models.enums.Categorias;
import br.com.EventXplorer.eventX.models.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class Evento {

    private long id;

    @NotBlank
    private String eventName;

    @NotBlank
    private String address;

    @NotNull
    private Categorias category;

    @NotNull
    private Status status;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalDate startDate;

    private List<Usuario> participantes = new ArrayList<>();

    public Evento(String nome, String address, Categorias category, Status status, LocalTime startHour, LocalDate startDate) {
        this.eventName = nome;
        this.address = address;
        this.category = category;
        this.status = status;
        this.startTime = startHour;
        this.startDate = startDate;
    }

    public void addParticipante(Usuario participante) {
        participantes.add(participante);
    }
}
