package org.tiagoliberato.assistente_virtual_jargos.domain.model;



import lombok.Data;
import java.time.LocalDate;


@Data
public class User {

    private Long id;
    private String name;
    private String password;
    private LocalDate birthDate;
    private String cpf;

    public User(String name, LocalDate birthDate, String cpf, String password){
        this.name = name;
        this.birthDate = birthDate;
        this.cpf = cpf;
        this.password = password;
    }

    public User(Long id, String name, String password, LocalDate birthDate, String cpf) {
        this(name, birthDate, password, cpf);
        this.id = id;
    }
}
