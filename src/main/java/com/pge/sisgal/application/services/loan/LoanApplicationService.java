package com.pge.sisgal.application.services.loan;

import com.pge.sisgal.api.dtos.loan.responses.LoanResponse;
import java.time.LocalDate;
import java.util.List;

public interface LoanApplicationService {
    LoanResponse createLoan(Long bookId, Long userId, LocalDate startDate, LocalDate dueDate);
    LoanResponse returnLoan(Long loanId, LocalDate returnDate);
    List<LoanResponse> getActiveLoansByUser(Long userId);
    List<LoanResponse> getOverdueLoansByUser(Long userId);
}
