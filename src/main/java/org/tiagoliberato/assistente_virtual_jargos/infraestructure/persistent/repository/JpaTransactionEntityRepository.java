package org.tiagoliberato.assistente_virtual_jargos.infraestructure.persistent.repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.tiagoliberato.assistente_virtual_jargos.application.transactions.dto.TransactionQuery;
import org.tiagoliberato.assistente_virtual_jargos.domain.model.Transaction;
import org.tiagoliberato.assistente_virtual_jargos.domain.TransactionRepository;
import org.tiagoliberato.assistente_virtual_jargos.infraestructure.persistent.entity.TransactionEntity;
import org.tiagoliberato.assistente_virtual_jargos.infraestructure.persistent.specification.TransactionSpecification;

import java.util.List;

@Repository
public class JpaTransactionEntityRepository implements TransactionRepository {

    @Autowired
    TransactionEntityRepository transactionEntityRepository;

    @Override
    public Transaction save(Transaction transaction){
        var entity = TransactionEntity.from(transaction);
        return  transactionEntityRepository.save(entity).toDomain();
    }

    @Override
    public List<Transaction> findAll(TransactionQuery query){
        Specification<TransactionEntity> esp = TransactionSpecification.from(query);
        List<TransactionEntity> list = transactionEntityRepository.findAll(esp);

        return list.stream().map(TransactionEntity::toDomain).toList();
    }

}
