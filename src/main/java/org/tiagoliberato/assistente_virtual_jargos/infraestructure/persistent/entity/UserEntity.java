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

    public UserEntity(String name, LocalDate birthDate, String cpf){
        this.name = name;
        this.birthDate = birthDate;
        this.cpf = cpf;
    }

    public static UserEntity from(User user){
        return new UserEntity(user.getName(), user.getBirthDate(), user.getCpf());
    }

    public  User toDomain(){
        return new User(this.name, this.birthDate, this.cpf);
    }
}
