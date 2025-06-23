package com.pge.sisgal.application.usecases.loan;

import com.pge.sisgal.domain.entities.Loan;
import com.pge.sisgal.domain.persistence.LoanRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FindOverdueLoansByUserUseCase {
    private final LoanRepository loanRepository;

    public List<Loan> execute(Long userId) {
        LocalDate currentDate = LocalDate.now();
        return loanRepository.findOverdueLoansByUser(userId, currentDate);
    }
}
