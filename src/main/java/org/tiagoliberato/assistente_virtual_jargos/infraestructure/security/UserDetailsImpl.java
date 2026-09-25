package org.tiagoliberato.assistente_virtual_jargos.infraestructure.security;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.tiagoliberato.assistente_virtual_jargos.domain.model.User;

import java.util.Collection;
import java.util.List;


public class UserDetailsImpl implements UserDetails {

    User user;

    public UserDetailsImpl(User user){
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public @Nullable String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    public static UserDetails from(User user) {
        return new UserDetailsImpl(user);
    }

}
