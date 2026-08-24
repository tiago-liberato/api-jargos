package org.tiagoliberato.assistente_virtual_jargos.infraestructure.persistent.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.tiagoliberato.assistente_virtual_jargos.domain.Category;
import org.tiagoliberato.assistente_virtual_jargos.domain.Transaction;
import org.tiagoliberato.assistente_virtual_jargos.domain.TransactionId;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@Table(name = "transaction")
@AllArgsConstructor
@NoArgsConstructor
public class TransactionEntity {
    @Id
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    private String description;
    private long amount;

    @Enumerated(EnumType.STRING)
    private Category category;
    private LocalDate date;

    public static  TransactionEntity from(Transaction transaction){
        return new TransactionEntity(transaction.getId().uuid(),  transaction.getDescription(), transaction.getAmount(), transaction.getCategory(), transaction.getDate());
    }

    public  Transaction toDomain(){
        return new Transaction(new TransactionId(id), description, amount, category, date);
    }
}
