package org.tiagoliberato.assistente_virtual_jargos.application.transactions.usecase;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tiagoliberato.assistente_virtual_jargos.application.transactions.dto.TransactionResponse;
import org.tiagoliberato.assistente_virtual_jargos.domain.model.Category;
import org.tiagoliberato.assistente_virtual_jargos.domain.model.Transaction;
import org.tiagoliberato.assistente_virtual_jargos.domain.TransactionRepository;

import java.math.BigDecimal;

@Service
public class PersistTransactionUseCase {
    @Autowired
    TransactionRepository transactionRepository;

    @Tool(name = "Create-new-Transaction", description = "Registra uma nova transação financeira no sistema. Use APENAS quando o usuário afirmar de forma clara e inequívoca que realizou um gasto ou pagamento (ex: 'gastei', 'paguei', 'comprei'). NÃO use se a fala for uma pergunta, hipótese, dúvida, ou se faltar informação suficiente (descrição, valor ou categoria).")
    public TransactionResponse execute(@ToolParam(description = "Descrição da transação realizada" ) String description, @ToolParam(description = "Valor da transação (em centavos)") long amount, @ToolParam(description = "Categoria da transação. Deve corresponder exatamente a uma categoria válida do sistema: GROCERIES, PHARMA, AUTO.") Category category){
        Transaction transaction = new Transaction(description, amount, category);
        Transaction saved = transactionRepository.save(transaction);

        return new TransactionResponse(
                saved.getDescription(),
                BigDecimal.valueOf(saved.getAmount(), 2),
                saved.getCategory(),
                saved.getDate()
        );
    }
}
