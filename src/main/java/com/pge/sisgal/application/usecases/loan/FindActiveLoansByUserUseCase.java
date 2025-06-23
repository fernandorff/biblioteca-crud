package com.pge.sisgal.application.usecases.loan;

import com.pge.sisgal.domain.entities.Loan;
import com.pge.sisgal.domain.persistence.LoanRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FindActiveLoansByUserUseCase {
    private final LoanRepository loanRepository;

    public List<Loan> execute(Long userId) {
        return loanRepository.findActiveLoansByUser(userId);
    }
}
