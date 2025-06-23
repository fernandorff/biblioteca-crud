package com.pge.sisgal.api.dtos.loan.requests;

import com.pge.sisgal.domain.messages.LoanValidationMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(name = "ReturnLoanRequest", description = "DTO para registro de devolução de empréstimo")
public class ReturnLoanRequest {

    @NotNull(message = LoanValidationMessages.LOAN_ID_REQUIRED)
    @Schema(description = "ID do empréstimo a ser devolvido", example = "1")
    private Long loanId;

    @NotNull(message = LoanValidationMessages.RETURN_DATE_REQUIRED)
    @Schema(description = "Data de devolução (formato: yyyy-MM-dd)", example = "2025-01-01")
    private LocalDate returnDate;

}
