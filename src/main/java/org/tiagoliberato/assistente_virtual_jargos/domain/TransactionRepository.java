package org.tiagoliberato.assistente_virtual_jargos.domain;

import org.springframework.stereotype.Repository;
import org.tiagoliberato.assistente_virtual_jargos.application.transactions.dto.TransactionQuery;
import org.tiagoliberato.assistente_virtual_jargos.domain.model.Transaction;

import java.util.List;

@Repository
public interface TransactionRepository {
    Transaction save(Transaction transaction);
    List<Transaction> findAll(TransactionQuery query);

}
