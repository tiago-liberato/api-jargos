package org.tiagoliberato.assistente_virtual_jargos.application.transactions.usecase;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tiagoliberato.assistente_virtual_jargos.application.transactions.dto.TransactionQuery;
import org.tiagoliberato.assistente_virtual_jargos.application.transactions.dto.TransactionResponse;
import org.tiagoliberato.assistente_virtual_jargos.domain.model.Transaction;
import org.tiagoliberato.assistente_virtual_jargos.domain.TransactionRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ConsultTransactions {

    @Autowired
    TransactionRepository transactionRepository;

    @Tool(description = "Consulta transações financeiras do usuário aplicando filtros opcionais de data, categoria e valor. Use esta ferramenta sempre que o usuário pedir para listar, buscar ou consultar suas transações ou gastos")
    public List<TransactionResponse> executeQuery(@ToolParam(description = "Objeto populado com os atributos de filtragem, os vazios devem ser null") TransactionQuery query){
        List<Transaction> transactions = transactionRepository.findAll(query);
        return transactions.stream().map(this::from).toList();
    }

    private TransactionResponse from(Transaction transaction){
        return new TransactionResponse(transaction.getDescription(), BigDecimal.valueOf(transaction.getAmount(), 2), transaction.getCategory(), transaction.getDate());
    }}
