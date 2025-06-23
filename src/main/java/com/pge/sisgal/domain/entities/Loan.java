package com.pge.sisgal.domain.entities;

import com.pge.sisgal.domain.messages.LoanValidationMessages;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Loan {
    private Long id;
    private Book book;
    private User user;
    private LocalDate startDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    public Loan(Book book, User user, LocalDate startDate, LocalDate dueDate) {
        if (book == null) {
            throw new IllegalArgumentException(LoanValidationMessages.BOOK_REQUIRED);
        }
        if (user == null) {
            throw new IllegalArgumentException(LoanValidationMessages.USER_REQUIRED);
        }
        if (startDate == null) {
            throw new IllegalArgumentException(LoanValidationMessages.START_DATE_REQUIRED);
        }
        if (dueDate == null) {
            throw new IllegalArgumentException(LoanValidationMessages.DUE_DATE_REQUIRED);
        }

        this.book = book;
        this.user = user;
        this.startDate = startDate;
        this.dueDate = dueDate;
    }

    public void returnLoan(LocalDate returnDate) {
        if (returnDate == null) {
            throw new IllegalArgumentException(LoanValidationMessages.RETURN_DATE_REQUIRED);
        }
        if (returnDate.isBefore(startDate)) {
            throw new IllegalArgumentException(LoanValidationMessages.RETURN_DATE_BEFORE_START_DATE);
        }
        this.returnDate = returnDate;
    }
}
