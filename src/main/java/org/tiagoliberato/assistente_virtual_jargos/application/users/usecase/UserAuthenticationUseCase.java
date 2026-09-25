package org.tiagoliberato.assistente_virtual_jargos.application.users.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.tiagoliberato.assistente_virtual_jargos.domain.UserRepository;
import org.tiagoliberato.assistente_virtual_jargos.domain.model.User;
import org.tiagoliberato.assistente_virtual_jargos.infraestructure.security.UserDetailsImpl;

@Service
public class UserAuthenticationUseCase implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username);

        return UserDetailsImpl.from(user);

    }
}
