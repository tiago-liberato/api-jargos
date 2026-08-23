package org.tiagoliberato.assistente_virtual_jargos.infraestructure.http.request;

import org.tiagoliberato.assistente_virtual_jargos.domain.Category;

public record TransactionRequest(String description, long amount, Category category) {
}
