package org.tiagoliberato.assistente_virtual_jargos.application.users.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.tiagoliberato.assistente_virtual_jargos.domain.UserRepository;
import org.tiagoliberato.assistente_virtual_jargos.domain.exception.UserAlreadyExistsException;
import org.tiagoliberato.assistente_virtual_jargos.domain.model.User;
import org.tiagoliberato.assistente_virtual_jargos.infraestructure.http.dto.UserRegisterRequest;

@Service
public class RegisterUserUseCase {

    @Autowired
    UserRepository repository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public void execute(UserRegisterRequest userRegisterRequest) throws UserAlreadyExistsException {
        User user = from(userRegisterRequest);
        String cpf = user.getCpf().replaceAll("\\D", "");
        user.setCpf(cpf);

        // Usuario existente ou nao com base no cpf
        if(repository.existsByCpf(cpf)){
            throw new UserAlreadyExistsException("Usário existente");
        }else{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            repository.save(user);
        }

    }

    private User from(UserRegisterRequest user){return new User(user.name(), user.birthDate(), user.cpf(), user.password());}

}
