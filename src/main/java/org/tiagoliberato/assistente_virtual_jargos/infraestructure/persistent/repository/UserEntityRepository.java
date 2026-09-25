package org.tiagoliberato.assistente_virtual_jargos.infraestructure.persistent.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.tiagoliberato.assistente_virtual_jargos.infraestructure.persistent.entity.UserEntity;

import java.util.Optional;

@Repository
public interface UserEntityRepository extends CrudRepository<UserEntity, Long> {

    Optional<UserEntity> findByName(String name);

}
