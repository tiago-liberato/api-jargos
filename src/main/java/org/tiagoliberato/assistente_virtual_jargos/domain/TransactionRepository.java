package org.tiagoliberato.assistente_virtual_jargos.domain;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository {
    Transaction save(Transaction transaction);
    List<Transaction> findAllCategory(Category category);

}
