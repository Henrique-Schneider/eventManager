package br.com.EventXplorer.eventX.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class Usuario {

    private long id;

    @NotBlank
    private String name;

    @Email
    private String email;

    @NotBlank
    private String city;


    private int age;


    public Usuario(String name, String email, String city, int age) {
        this.name = name;
        this.email = email;
        this.city = city;
        this.age = age;
    }
}
