package com.pge.sisgal.api.exception.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(name = "ErrorResponse", description = "DTO para respostas de erro da API")
public class ErrorResponse {

    @Schema(description = "Nome da exceção lançada", example = "IllegalArgumentException")
    private final String error;

    @Schema(description = "Detalhes do erro", example = "Livro com ISBN 1234567890 já foi cadastrado")
    private final Object details;

    @Schema(description = "Código de status HTTP", example = "400")
    private final int status;
}
