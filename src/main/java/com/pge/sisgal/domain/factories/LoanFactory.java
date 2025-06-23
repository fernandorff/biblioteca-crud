package com.pge.sisgal.domain.factories;

import com.pge.sisgal.domain.entities.Book;
import com.pge.sisgal.domain.entities.Loan;
import com.pge.sisgal.domain.entities.User;
import com.pge.sisgal.domain.messages.LoanValidationMessages;
import com.pge.sisgal.domain.policies.LoanPolicy;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import org.springframework.stereotype.Component;

@Component
public class LoanFactory {

    public Loan createLoanInstance(Book book, User user, LocalDate startDate, LocalDate dueDate) {
        validateLoanCreationDates(startDate, dueDate);
        return new Loan(book, user, startDate, dueDate);
    }

    private void validateLoanCreationDates(LocalDate startDate, LocalDate dueDate) {
        if (startDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(LoanValidationMessages.START_DATE_AFTER_CURRENT_DATE);
        }
        if (dueDate.isBefore(startDate)) {
            throw new IllegalArgumentException(LoanValidationMessages.DUE_DATE_BEFORE_START_DATE);
        }

        long daysBetween = ChronoUnit.DAYS.between(startDate, dueDate);
        if (daysBetween > LoanPolicy.MAX_LOAN_DAYS) {
            throw new IllegalArgumentException(
                LoanValidationMessages.loanDueDateExceeded(LoanPolicy.MAX_LOAN_DAYS)
            );
        }
    }

}
