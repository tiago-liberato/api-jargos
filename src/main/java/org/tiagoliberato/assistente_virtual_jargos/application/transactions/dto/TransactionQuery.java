package org.tiagoliberato.assistente_virtual_jargos.application.transactions.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionQuery(LocalDate startDate, LocalDate endDate, String Category, BigDecimal minAmount, BigDecimal maxAmount) {
}
