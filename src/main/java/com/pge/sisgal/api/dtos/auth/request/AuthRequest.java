package com.pge.sisgal.api.dtos.auth.request;

import static com.pge.sisgal.domain.messages.AuthValidationMessages.PASSWORD_REQUIRED;
import static com.pge.sisgal.domain.messages.AuthValidationMessages.REGISTRATION_REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "AuthRequest", description = "DTO para autenticação de usuário")
public class AuthRequest {

    @NotBlank(message = REGISTRATION_REQUIRED)
    @Schema(description = "Matrícula do usuário", example = "12345678")
    private String registration;

    @NotBlank(message = PASSWORD_REQUIRED)
    @Schema(description = "Senha do usuário", example = "password123")
    private String password;
}
