package org.tiagoliberato.assistente_virtual_jargos.infraestructure.http.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record UserRegisterRequest(@NotBlank String name,
                                  @NotBlank @Size(min = 8) String password,
                                  @NotNull @Past @JsonFormat(pattern = "dd/MM/yyyy") LocalDate birthDate,
                                  @NotBlank @CPF String cpf) {
}
