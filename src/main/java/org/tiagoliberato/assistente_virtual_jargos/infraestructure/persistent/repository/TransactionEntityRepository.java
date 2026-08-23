package org.tiagoliberato.assistente_virtual_jargos.infraestructure.persistent.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.tiagoliberato.assistente_virtual_jargos.domain.Category;
import org.tiagoliberato.assistente_virtual_jargos.infraestructure.persistent.entity.TransactionEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionEntityRepository extends CrudRepository<TransactionEntity, UUID> {
    public List<TransactionEntity> findAllByCategory(Category category);
}
