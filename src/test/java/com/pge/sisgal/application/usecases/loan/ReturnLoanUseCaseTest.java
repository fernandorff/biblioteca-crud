package com.pge.sisgal.application.usecases.loan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pge.sisgal.TestUtils;
import com.pge.sisgal.domain.entities.Book;
import com.pge.sisgal.domain.entities.Loan;
import com.pge.sisgal.domain.messages.LoanValidationMessages;
import com.pge.sisgal.domain.persistence.BookRepository;
import com.pge.sisgal.domain.persistence.LoanRepository;
import com.pge.sisgal.domain.services.LoanDomainService;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReturnLoanUseCaseTest {

    @Mock
    private LoanDomainService loanDomainService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private ReturnLoanUseCase returnLoanUseCase;

    @Test
    void execute_ShouldReturnLoan_WhenValid() {
        Loan loan = TestUtils.createLoan();
        Book book = TestUtils.createBook();
        LocalDate returnDate = LocalDate.of(2025, 1, 10);
        when(loanRepository.findById(loan.getId())).thenReturn(Optional.of(loan));
        when(bookRepository.save(any())).thenReturn(book);
        when(loanRepository.save(any())).thenReturn(loan);

        Loan result = returnLoanUseCase.execute(loan.getId(), returnDate);

        assertEquals(loan, result);
        verify(loanRepository).findById(loan.getId());
        verify(loanDomainService).processLoanReturn(loan, returnDate);
        verify(loanRepository).save(loan);
    }

    @Test
    void execute_ShouldThrowException_WhenLoanNotFound() {
        LocalDate returnDate = LocalDate.of(2025, 1, 10);
        when(loanRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> returnLoanUseCase.execute(1L, returnDate));

        assertEquals(LoanValidationMessages.loanNotFoundById(1L), exception.getMessage());
        verify(loanRepository).findById(1L);
    }
}
