package com.pge.sisgal.application.usecases.loan;

import com.pge.sisgal.domain.entities.Loan;
import com.pge.sisgal.domain.messages.LoanValidationMessages;
import com.pge.sisgal.domain.persistence.BookRepository;
import com.pge.sisgal.domain.persistence.LoanRepository;
import com.pge.sisgal.domain.services.LoanDomainService;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReturnLoanUseCase {

    private final LoanDomainService loanDomainService;
    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;

    public Loan execute(Long loanId, LocalDate returnDate) {
        Loan loan = loanRepository.findById(loanId)
            .orElseThrow(() -> new IllegalArgumentException(LoanValidationMessages.loanNotFoundById(loanId)));

        loanDomainService.processLoanReturn(loan, returnDate);

        bookRepository.save(loan.getBook());

        return loanRepository.save(loan);
    }
}
