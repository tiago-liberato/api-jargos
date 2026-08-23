package org.tiagoliberato.assistente_virtual_jargos.infraestructure.persistent.repository;

import org.springframework.stereotype.Repository;
import org.tiagoliberato.assistente_virtual_jargos.domain.Category;
import org.tiagoliberato.assistente_virtual_jargos.domain.Transaction;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository {
    Transaction save(Transaction transaction);
    List<Transaction> findAllCategory(Category category);
    List<Transaction> findByDateBetwen(LocalDate startDate, LocalDate endDate);

}
