package com.pge.sisgal.domain.services;

import com.pge.sisgal.domain.entities.Book;
import com.pge.sisgal.domain.entities.Loan;
import com.pge.sisgal.domain.entities.User;
import com.pge.sisgal.domain.factories.LoanFactory;
import com.pge.sisgal.domain.messages.LoanValidationMessages;
import com.pge.sisgal.domain.policies.LoanPolicy;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoanDomainService {

    private final LoanFactory loanFactory;

    public Loan processLoanCreation(Book book, User user, LocalDate startDate, LocalDate dueDate, List<Loan> userLoans) {
        validateUserLoanLimit(userLoans);
        book.decreaseQuantity();
        return loanFactory.createLoanInstance(book, user, startDate, dueDate);
    }

    public void processLoanReturn(Loan loan, LocalDate returnDate) {
        if (loan.getReturnDate() != null) {
            throw new IllegalArgumentException(LoanValidationMessages.LOAN_ALREADY_RETURNED);
        }
        loan.returnLoan(returnDate);
        loan.getBook().increaseQuantity();
    }

    private void validateUserLoanLimit(List<Loan> userLoans) {
        if (userLoans.size() >= LoanPolicy.MAX_LOANS_PER_USER) {
            throw new IllegalStateException(
                LoanValidationMessages.loanMaxLimitReached(LoanPolicy.MAX_LOANS_PER_USER)
            );
        }
    }

}
