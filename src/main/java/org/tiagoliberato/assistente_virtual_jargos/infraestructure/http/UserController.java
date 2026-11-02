package org.tiagoliberato.assistente_virtual_jargos.infraestructure.http;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.tiagoliberato.assistente_virtual_jargos.application.users.usecase.RegisterUserUseCase;
import org.tiagoliberato.assistente_virtual_jargos.domain.exception.UserAlreadyExistsException;
import org.tiagoliberato.assistente_virtual_jargos.domain.model.User;
import org.tiagoliberato.assistente_virtual_jargos.infraestructure.http.dto.UserLoginRequest;
import org.tiagoliberato.assistente_virtual_jargos.infraestructure.http.dto.UserRegisterRequest;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    RegisterUserUseCase registerUserUseCase;

    @PostMapping("/login")
    public ResponseEntity longin(@RequestParam UserLoginRequest user){
        // "Transforma" as credenciais em um token a ser autenticado com base no nome e na senha
        var authenticationUser = new UsernamePasswordAuthenticationToken(user.name(), user.password());
        //Chama o Authentication manager por meio de injeção de dependencia para autenticar o usuário
        var authenticate = this.authenticationManager.authenticate(authenticationUser);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity cadastro(@RequestBody @Valid UserRegisterRequest user){
        try {
            registerUserUseCase.execute(user);
            return ResponseEntity.ok(HttpStatus.CREATED);
        }catch(UserAlreadyExistsException e){
            return ResponseEntity.ok(HttpStatus.CONFLICT);
        }
    }
}
