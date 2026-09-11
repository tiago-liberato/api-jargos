package org.tiagoliberato.assistente_virtual_jargos.infraestructure.http.dto;

import org.tiagoliberato.assistente_virtual_jargos.domain.model.Category;

public record TransactionRequest(String description, long amount, Category category) {
}
