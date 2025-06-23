package com.pge.sisgal.api.dtos.user.requests;

import com.pge.sisgal.domain.messages.UserValidationMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(name = "CreateUserRequest", description = "DTO para criação de um novo usuário")
public class CreateUserRequest {

    @NotBlank(message = UserValidationMessages.NAME_REQUIRED)
    @Schema(description = "Nome completo do usuário", example = "João da Silva")
    private String name;

    @NotBlank(message = UserValidationMessages.REGISTRATION_REQUIRED)
    @Schema(description = "Código de matrícula do usuário", example = "12345678")
    private String registration;

    @NotBlank(message = UserValidationMessages.EMAIL_REQUIRED)
    @Email(message = UserValidationMessages.EMAIL_INVALID)
    @Schema(description = "Endereço de e-mail válido", example = "joao.silva@example.com")
    private String email;

    @NotBlank(message = UserValidationMessages.PASSWORD_REQUIRED)
    @Schema(description = "Senha do usuário", example = "password123")
    private String password;

    @Schema(description = "Papel do usuário", example = "USER / ADMIN", allowableValues = {"USER", "ADMIN"})
    private String role;
}
