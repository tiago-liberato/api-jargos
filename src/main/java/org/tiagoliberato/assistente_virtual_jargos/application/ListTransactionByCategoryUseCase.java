package org.tiagoliberato.assistente_virtual_jargos.application;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tiagoliberato.assistente_virtual_jargos.domain.Category;
import org.tiagoliberato.assistente_virtual_jargos.domain.Transaction;
import org.tiagoliberato.assistente_virtual_jargos.domain.TransactionRepository;

import java.util.List;

@Service
public class ListTransactionByCategoryUseCase {

    @Autowired
    TransactionRepository transactionRepository;

    @Tool(name = "List-Transaction-by-category", description = "Lista transações financeiras de acordo coma a categoria")
    public List<Transaction> execute(@ToolParam(description = "categoria das transações") String category){
        return transactionRepository.findAllCategory(Category.valueOf(category));
    }
}
