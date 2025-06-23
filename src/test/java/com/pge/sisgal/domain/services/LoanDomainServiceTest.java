package com.pge.sisgal.domain.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pge.sisgal.TestUtils;
import com.pge.sisgal.domain.entities.Book;
import com.pge.sisgal.domain.entities.Loan;
import com.pge.sisgal.domain.entities.User;
import com.pge.sisgal.domain.factories.LoanFactory;
import com.pge.sisgal.domain.messages.LoanValidationMessages;
import com.pge.sisgal.domain.policies.LoanPolicy;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LoanDomainServiceTest {

    @Mock
    private LoanFactory loanFactory;

    @Mock
    private Book defaultBook;

    @Mock
    private User defaultUser;

    @Mock
    private Loan defaultLoan;

    @InjectMocks
    private LoanDomainService loanDomainService;

    @Test
    void processLoanCreation_ShouldCreateLoan_WhenValid() {
        Loan loan = TestUtils.createLoan();
        List<Loan> userLoans = Collections.emptyList();
        when(loanFactory.createLoanInstance(defaultBook, defaultUser, loan.getStartDate(), loan.getDueDate()))
            .thenReturn(defaultLoan);

        Loan result = loanDomainService.processLoanCreation(defaultBook, defaultUser, loan.getStartDate(), loan.getDueDate(), userLoans);

        verify(defaultBook).decreaseQuantity();
        assertEquals(defaultLoan, result);
    }

    @Test
    void processLoanCreation_ShouldThrowException_WhenLoanLimitExceeded() {
        Loan loan = TestUtils.createLoan();
        Book book = defaultBook;
        User user = defaultUser;
        LocalDate startDate = loan.getStartDate();
        LocalDate dueDate = loan.getDueDate();
        List<Loan> userLoans = List.of(defaultLoan, defaultLoan, defaultLoan, defaultLoan, defaultLoan);

        IllegalStateException exception = assertThrows(IllegalStateException.class,
            () -> loanDomainService.processLoanCreation(book, user, startDate, dueDate, userLoans));

        assertEquals(LoanValidationMessages.loanMaxLimitReached(LoanPolicy.MAX_LOANS_PER_USER), exception.getMessage());
    }

    @Test
    void processLoanReturn_ShouldUpdateLoanAndBook_WhenValid() {
        when(defaultLoan.getBook()).thenReturn(defaultBook);
        when(defaultLoan.getReturnDate()).thenReturn(null);

        loanDomainService.processLoanReturn(defaultLoan, TestUtils.createReturnedLoanResponse().getReturnDate());

        verify(defaultLoan).returnLoan(TestUtils.createReturnedLoanResponse().getReturnDate());
        verify(defaultBook).increaseQuantity();
    }

    @Test
    void processLoanReturn_ShouldThrowException_WhenLoanAlreadyReturned() {
        Loan loan = TestUtils.createReturnedLoan();
        Loan targetLoan = defaultLoan;
        LocalDate returnDate = loan.getReturnDate();

        when(targetLoan.getReturnDate()).thenReturn(returnDate);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> loanDomainService.processLoanReturn(targetLoan, returnDate));

        assertEquals(LoanValidationMessages.LOAN_ALREADY_RETURNED, exception.getMessage());
    }
}
