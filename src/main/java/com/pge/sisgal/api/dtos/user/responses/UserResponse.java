package com.pge.sisgal.api.dtos.user.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(name = "UserResponse", description = "DTO com informações do usuário")
public class UserResponse {
    @Schema(description = "ID do usuário", example = "1")
    private Long id;

    @Schema(description = "Nome completo", example = "João da Silva")
    private String name;

    @Schema(description = "Código de matrícula", example = "12345678")
    private String registration;

    @Schema(description = "Endereço de e-mail", example = "joao.silva@example.com")
    private String email;

    @Schema(description = "Papel do usuário", example = "USER")
    private String role;
}
