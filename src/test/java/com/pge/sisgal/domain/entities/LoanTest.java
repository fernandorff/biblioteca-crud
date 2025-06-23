package com.pge.sisgal.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.pge.sisgal.TestUtils;
import com.pge.sisgal.domain.messages.LoanValidationMessages;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LoanTest {

    @Mock
    private Book defaultBook;

    @Mock
    private User defaultUser;

    @Test
    void constructor_ShouldThrowException_WhenBookIsNull() {
        User user = defaultUser;
        LocalDate startDate = TestUtils.createLoanResponse().getStartDate();
        LocalDate dueDate = TestUtils.createLoanResponse().getDueDate();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> new Loan(null, user, startDate, dueDate));

        assertEquals(LoanValidationMessages.BOOK_REQUIRED, exception.getMessage());
    }

    @Test
    void constructor_ShouldThrowException_WhenUserIsNull() {
        Book book = defaultBook;
        LocalDate startDate = TestUtils.createLoanResponse().getStartDate();
        LocalDate dueDate = TestUtils.createLoanResponse().getDueDate();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> new Loan(book, null, startDate, dueDate));

        assertEquals(LoanValidationMessages.USER_REQUIRED, exception.getMessage());
    }

    @Test
    void constructor_ShouldThrowException_WhenStartDateIsNull() {
        Book book = defaultBook;
        User user = defaultUser;
        LocalDate dueDate = TestUtils.createLoanResponse().getDueDate();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> new Loan(book, user, null, dueDate));

        assertEquals(LoanValidationMessages.START_DATE_REQUIRED, exception.getMessage());
    }

    @Test
    void constructor_ShouldThrowException_WhenDueDateIsNull() {
        Book book = defaultBook;
        User user = defaultUser;
        LocalDate startDate = TestUtils.createLoanResponse().getStartDate();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> new Loan(book, user, startDate, null));

        assertEquals(LoanValidationMessages.DUE_DATE_REQUIRED, exception.getMessage());
    }

    @Test
    void returnLoan_ShouldSetReturnDate_WhenValid() {
        Loan loan = TestUtils.createLoan();
        loan.returnLoan(TestUtils.createReturnedLoanResponse().getReturnDate());
        assertEquals(TestUtils.createReturnedLoanResponse().getReturnDate(), loan.getReturnDate());
    }

    @Test
    void returnLoan_ShouldThrowException_WhenReturnDateIsNull() {
        Loan loan = TestUtils.createLoan();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> loan.returnLoan(null));

        assertEquals(LoanValidationMessages.RETURN_DATE_REQUIRED, exception.getMessage());
    }

    @Test
    void returnLoan_ShouldThrowException_WhenReturnDateIsBeforeStartDate() {
        Loan loan = TestUtils.createLoan();
        LocalDate invalidReturnDate = TestUtils.createLoanResponse().getStartDate().minusDays(1);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> loan.returnLoan(invalidReturnDate));

        assertEquals(LoanValidationMessages.RETURN_DATE_BEFORE_START_DATE, exception.getMessage());
    }
}
