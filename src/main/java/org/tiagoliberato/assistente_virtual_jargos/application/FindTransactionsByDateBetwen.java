package org.tiagoliberato.assistente_virtual_jargos.application;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tiagoliberato.assistente_virtual_jargos.domain.Transaction;
import org.tiagoliberato.assistente_virtual_jargos.domain.exception.DateInvalidException;
import org.tiagoliberato.assistente_virtual_jargos.infraestructure.persistent.repository.TransactionRepository;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;

@Service
public class FindTransactionsByDateBetwen {

    @Autowired
    TransactionRepository transactionRepository;

    @Tool(name = "Listar-transacao-em-um-periodo", description = "Lista as transações feitas pelo usuário durante um mês informado")
    public List<Transaction> execute(@ToolParam(description = "Ano em que o usuário deseja ver as transções") Month month, @ToolParam( description = "Mês em que o usuário deseja ver as transações")  Year year){
        YearMonth yearMonth = YearMonth.of(year.getValue(), month);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        if(yearMonth.isBefore(YearMonth.now())){
            throw new DateInvalidException("Mês inválido");
        }

        return transactionRepository.findByDateBetwen(startDate, endDate);

    }
}
