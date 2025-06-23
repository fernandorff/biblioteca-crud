package com.pge.sisgal.application.services.loan;

import com.pge.sisgal.api.dtos.loan.responses.LoanResponse;
import com.pge.sisgal.application.mappers.LoanResponseMapper;
import com.pge.sisgal.application.usecases.loan.CreateLoanUseCase;
import com.pge.sisgal.application.usecases.loan.FindActiveLoansByUserUseCase;
import com.pge.sisgal.application.usecases.loan.FindOverdueLoansByUserUseCase;
import com.pge.sisgal.application.usecases.loan.ReturnLoanUseCase;
import com.pge.sisgal.domain.entities.Loan;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoanApplicationServiceImpl implements LoanApplicationService {

    private final CreateLoanUseCase createLoanUseCase;
    private final ReturnLoanUseCase returnLoanUseCase;
    private final FindActiveLoansByUserUseCase findActiveLoansByUserUseCase;
    private final FindOverdueLoansByUserUseCase findOverdueLoansByUserUseCase;
    private final LoanResponseMapper loanResponseMapper;

    @Override
    public LoanResponse createLoan(Long bookId, Long userId, LocalDate startDate, LocalDate dueDate) {
        Loan loan = createLoanUseCase.execute(bookId, userId, startDate, dueDate);
        return loanResponseMapper.toResponse(loan);
    }

    @Override
    public LoanResponse returnLoan(Long loanId, LocalDate returnDate) {
        Loan loan = returnLoanUseCase.execute(loanId, returnDate);
        return loanResponseMapper.toResponse(loan);
    }

    @Override
    public List<LoanResponse> getActiveLoansByUser(Long userId) {
        List<Loan> loans = findActiveLoansByUserUseCase.execute(userId);
        return loans.stream()
            .map(loanResponseMapper::toResponse)
            .toList();
    }

    @Override
    public List<LoanResponse> getOverdueLoansByUser(Long userId) {
        List<Loan> loans = findOverdueLoansByUserUseCase.execute(userId);
        return loans.stream()
            .map(loanResponseMapper::toResponse)
            .toList();
    }
}
