package org.tiagoliberato.assistente_virtual_jargos.domain.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class User {

    private String name;
    private String password;
    private LocalDate birthDate;
    private String cpf;

    public User(String name, LocalDate birthDate, String cpf){
        this.name = name;
        this.birthDate = birthDate;
        this.cpf = cpf;
    }
}
