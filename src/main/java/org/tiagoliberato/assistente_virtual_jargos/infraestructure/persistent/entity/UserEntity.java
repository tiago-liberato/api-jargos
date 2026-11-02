package org.tiagoliberato.assistente_virtual_jargos.infraestructure.persistent.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;
import org.tiagoliberato.assistente_virtual_jargos.domain.model.User;

import java.time.LocalDate;

@Entity
@Table(name = "users" )
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(name = "date")
    private LocalDate birthDate;

    @NotBlank(message = "Campo CPF não pode ser vazio")
    @CPF(message = "CPF inválido")
    private String cpf;

    @NotBlank
    private String password;

    public UserEntity(String name, LocalDate birthDate, String cpf, String password){
        this.name = name;
        this.birthDate = birthDate;
        this.cpf = cpf;
        this.password = password;
    }

    public static UserEntity from(User user){
        return new UserEntity(user.getName(), user.getBirthDate(), user.getCpf(), user.getPassword());
    }

    public  User toDomain(){
        return new User(this.id, this.name, this.password, this.birthDate, this.cpf);
    }
}
