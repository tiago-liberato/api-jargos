package org.tiagoliberato.assistente_virtual_jargos.infraestructure.http.dto;

import java.time.LocalDate;

public record UserResponse(String name, LocalDate birthday) {
}
