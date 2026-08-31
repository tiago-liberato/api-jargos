package org.tiagoliberato.assistente_virtual_jargos.infraestructure.http.request;

import org.tiagoliberato.assistente_virtual_jargos.domain.model.Category;

public record TransactionRequest(String description, long amount, Category category) {
}
