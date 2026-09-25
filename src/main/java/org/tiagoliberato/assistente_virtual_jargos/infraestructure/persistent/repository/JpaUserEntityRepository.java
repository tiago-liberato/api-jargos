package org.tiagoliberato.assistente_virtual_jargos.infraestructure.persistent.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.tiagoliberato.assistente_virtual_jargos.domain.UserRepository;
import org.tiagoliberato.assistente_virtual_jargos.domain.model.User;
import org.tiagoliberato.assistente_virtual_jargos.infraestructure.persistent.entity.UserEntity;

@Repository
public class JpaUserEntityRepository implements UserRepository {

    @Autowired
    UserEntityRepository userEntityRepository;

    @Override
    public User save(User user) {
       UserEntity entity = UserEntity.from(user);

       return userEntityRepository.save(entity).toDomain();
    }

    @Override
    public User findByName(String name) {
        return userEntityRepository.findByName(name).map(UserEntity::toDomain)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }
}
