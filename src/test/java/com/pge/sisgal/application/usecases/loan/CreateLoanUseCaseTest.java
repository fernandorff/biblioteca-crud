package com.pge.sisgal.application.usecases.loan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pge.sisgal.TestUtils;
import com.pge.sisgal.domain.entities.Book;
import com.pge.sisgal.domain.entities.Loan;
import com.pge.sisgal.domain.entities.User;
import com.pge.sisgal.domain.messages.BookValidationMessages;
import com.pge.sisgal.domain.messages.UserValidationMessages;
import com.pge.sisgal.domain.persistence.BookRepository;
import com.pge.sisgal.domain.persistence.LoanRepository;
import com.pge.sisgal.domain.persistence.UserRepository;
import com.pge.sisgal.domain.services.LoanDomainService;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateLoanUseCaseTest {

    @Mock
    private LoanDomainService loanDomainService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private CreateLoanUseCase createLoanUseCase;

    @Test
    void execute_ShouldCreateLoan_WhenValid() {
        Book book = TestUtils.createBook();
        User user = TestUtils.createUser();
        Loan loan = TestUtils.createLoan();
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        LocalDate dueDate = LocalDate.of(2025, 1, 15);
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(loanRepository.findActiveLoansByUser(user.getId())).thenReturn(Collections.emptyList());
        when(loanDomainService.processLoanCreation(book, user, startDate, dueDate, Collections.emptyList())).thenReturn(loan);
        when(loanRepository.save(loan)).thenReturn(loan);
        when(bookRepository.save(book)).thenReturn(book);

        Loan result = createLoanUseCase.execute(book.getId(), user.getId(), startDate, dueDate);

        assertEquals(loan, result);
        verify(bookRepository).findById(book.getId());
        verify(userRepository).findById(user.getId());
        verify(loanRepository).findActiveLoansByUser(user.getId());
        verify(loanDomainService).processLoanCreation(book, user, startDate, dueDate, Collections.emptyList());
        verify(loanRepository).save(loan);
        verify(bookRepository).save(book);
    }

    @Test
    void execute_ShouldThrowException_WhenBookNotFound() {
        User user = TestUtils.createUser();
        Long bookId = 2L;
        Long userId = user.getId();
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        LocalDate dueDate = LocalDate.of(2025, 1, 15);
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> createLoanUseCase.execute(bookId, userId, startDate, dueDate));

        assertEquals(BookValidationMessages.bookNotFound(bookId), exception.getMessage());
        verify(bookRepository).findById(bookId);
    }

    @Test
    void execute_ShouldThrowException_WhenUserNotFound() {
        Book book = TestUtils.createBook();
        Long bookId = book.getId();
        Long userId = 3L;
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        LocalDate dueDate = LocalDate.of(2025, 1, 15);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> createLoanUseCase.execute(bookId, userId, startDate, dueDate));

        assertEquals(UserValidationMessages.userNotFoundById(userId), exception.getMessage());
        verify(bookRepository).findById(bookId);
        verify(userRepository).findById(userId);
    }
}
