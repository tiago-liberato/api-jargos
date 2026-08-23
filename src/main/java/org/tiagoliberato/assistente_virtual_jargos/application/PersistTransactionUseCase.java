package org.tiagoliberato.assistente_virtual_jargos.application;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tiagoliberato.assistente_virtual_jargos.domain.Category;
import org.tiagoliberato.assistente_virtual_jargos.domain.Transaction;
import org.tiagoliberato.assistente_virtual_jargos.domain.TransactionRepository;

@Service
public class PersistTransactionUseCase {
    @Autowired
    TransactionRepository transactionRepository;

    @Tool(name = "Create-new-Transaction", description = "Persiste uma transação financeira")
    public Transaction execute(@ToolParam(description = "Descrição da transação realizada" ) String description, @ToolParam(description = "Valor da transação (em centavos)") long amount, @ToolParam(description = "Categoria da Transação") Category category){
        Transaction transaction = new Transaction(description, amount, category);

        return transactionRepository.save(transaction);
    }
}
