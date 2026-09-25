package org.tiagoliberato.assistente_virtual_jargos.domain;

import org.springframework.stereotype.Repository;
import org.tiagoliberato.assistente_virtual_jargos.domain.model.User;

@Repository
public interface UserRepository {
    User save(User user);
    User findByName(String name);

}
