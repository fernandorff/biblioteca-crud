package com.pge.sisgal.api.dtos.loan.requests;

import com.pge.sisgal.domain.messages.LoanValidationMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(name = "CreateLoanRequest", description = "DTO para criação de um novo empréstimo")
public class CreateLoanRequest {

    @NotNull(message = LoanValidationMessages.BOOK_ID_REQUIRED)
    @Schema(description = "ID do livro a ser emprestado", example = "1")
    private Long bookId;

    @NotNull(message = LoanValidationMessages.USER_ID_REQUIRED)
    @Schema(description = "ID do usuário que está pegando emprestado", example = "1")
    private Long userId;

    @NotNull(message = LoanValidationMessages.START_DATE_REQUIRED)
    @Schema(description = "Data de início do empréstimo (formato: yyyy-MM-dd)", example = "2025-01-01")
    private LocalDate startDate;

    @NotNull(message = LoanValidationMessages.DUE_DATE_REQUIRED)
    @Schema(description = "Data de vencimento do empréstimo (formato: yyyy-MM-dd)", example = "2025-01-15")
    private LocalDate dueDate;
}
