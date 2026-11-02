package org.tiagoliberato.assistente_virtual_jargos.application.users.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.tiagoliberato.assistente_virtual_jargos.domain.model.User;
import org.tiagoliberato.assistente_virtual_jargos.infraestructure.http.dto.UserLoginRequest;
import org.tiagoliberato.assistente_virtual_jargos.infraestructure.security.UserDetailsImpl;

@Service
public class UserAuthenticationUseCase {

    @Autowired
    AuthenticationManager authenticationManager;


    public User execute(UserLoginRequest userRequest){
        var user = new UsernamePasswordAuthenticationToken(userRequest.name(), userRequest.password());

        var authentication = authenticationManager.authenticate(user);
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return userPrincipal.getUser();
    }
}
