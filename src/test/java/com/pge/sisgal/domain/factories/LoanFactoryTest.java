package com.pge.sisgal.domain.factories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.pge.sisgal.domain.entities.Book;
import com.pge.sisgal.domain.entities.Loan;
import com.pge.sisgal.domain.entities.User;
import com.pge.sisgal.domain.messages.LoanValidationMessages;
import com.pge.sisgal.domain.policies.LoanPolicy;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LoanFactoryTest {

    private static final LocalDate DEFAULT_START_DATE = LocalDate.now();
    private static final LocalDate DEFAULT_DUE_DATE = DEFAULT_START_DATE.plusDays(7);

    @Mock
    private Book defaultBook;

    @Mock
    private User defaultUser;

    private final LoanFactory loanFactory = new LoanFactory();

    @Test
    void createLoanInstance_ShouldCreateLoan_WhenValidDates() {
        Loan loan = loanFactory.createLoanInstance(defaultBook, defaultUser, DEFAULT_START_DATE, DEFAULT_DUE_DATE);
        assertEquals(defaultBook, loan.getBook());
        assertEquals(defaultUser, loan.getUser());
        assertEquals(DEFAULT_START_DATE, loan.getStartDate());
        assertEquals(DEFAULT_DUE_DATE, loan.getDueDate());
        assertNull(loan.getReturnDate());
    }

    @Test
    void createLoanInstance_ShouldThrowException_WhenStartDateIsFuture() {
        LocalDate futureStartDate = LocalDate.now().plusDays(1);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> loanFactory.createLoanInstance(defaultBook, defaultUser, futureStartDate, DEFAULT_DUE_DATE));
        assertEquals(LoanValidationMessages.START_DATE_AFTER_CURRENT_DATE, exception.getMessage());
    }

    @Test
    void createLoanInstance_ShouldThrowException_WhenDueDateIsBeforeStartDate() {
        LocalDate invalidDueDate = DEFAULT_START_DATE.minusDays(1);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> loanFactory.createLoanInstance(defaultBook, defaultUser, DEFAULT_START_DATE, invalidDueDate));
        assertEquals(LoanValidationMessages.DUE_DATE_BEFORE_START_DATE, exception.getMessage());
    }

    @Test
    void createLoanInstance_ShouldThrowException_WhenLoanDurationExceedsMaxDays() {
        LocalDate excessiveDueDate = DEFAULT_START_DATE.plusDays(LoanPolicy.MAX_LOAN_DAYS + 1);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> loanFactory.createLoanInstance(defaultBook, defaultUser, DEFAULT_START_DATE, excessiveDueDate));
        assertEquals(LoanValidationMessages.loanDueDateExceeded(LoanPolicy.MAX_LOAN_DAYS), exception.getMessage());
    }
}
