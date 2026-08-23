package org.tiagoliberato.assistente_virtual_jargos.infraestructure.persistent.repository;

import org.springframework.aop.target.LazyInitTargetSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.tiagoliberato.assistente_virtual_jargos.domain.Category;
import org.tiagoliberato.assistente_virtual_jargos.domain.Transaction;
import org.tiagoliberato.assistente_virtual_jargos.domain.TransactionRepository;
import org.tiagoliberato.assistente_virtual_jargos.infraestructure.persistent.entity.TransactionEntity;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<Transaction> findAllCategory(Category category) {
        return transactionEntityRepository.findAllByCategory(category).stream().map(TransactionEntity::toDomain).toList();
    }
}
