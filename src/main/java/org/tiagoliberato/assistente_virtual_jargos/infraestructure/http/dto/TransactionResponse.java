package org.tiagoliberato.assistente_virtual_jargos.infraestructure.http.dto;

import org.tiagoliberato.assistente_virtual_jargos.domain.model.Category;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionResponse(String description, BigDecimal amount, Category category, LocalDate date) {
}
