package org.tiagoliberato.assistente_virtual_jargos.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Transaction {
    private TransactionId id;
    private String description;
    private long amount;
    private Category category;

    public Transaction(String description, long amount, Category category) {
        this.id = new TransactionId(UUID.randomUUID());
        this.description = description;
        this.amount = amount;
        this.category = category;
    }
}
