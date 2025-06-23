package com.pge.sisgal.application.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.pge.sisgal.TestUtils;
import com.pge.sisgal.api.dtos.loan.responses.LoanResponse;
import com.pge.sisgal.application.mappers.LoanResponseMapper;
import com.pge.sisgal.application.services.loan.LoanApplicationServiceImpl;
import com.pge.sisgal.application.usecases.loan.CreateLoanUseCase;
import com.pge.sisgal.application.usecases.loan.FindActiveLoansByUserUseCase;
import com.pge.sisgal.application.usecases.loan.FindOverdueLoansByUserUseCase;
import com.pge.sisgal.application.usecases.loan.ReturnLoanUseCase;
import com.pge.sisgal.domain.entities.Loan;
import com.pge.sisgal.domain.messages.BookValidationMessages;
import com.pge.sisgal.domain.messages.LoanValidationMessages;
import com.pge.sisgal.domain.messages.UserValidationMessages;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LoanApplicationServiceTest {

    @Mock
    private CreateLoanUseCase createLoanUseCase;

    @Mock
    private ReturnLoanUseCase returnLoanUseCase;

    @Mock
    private FindActiveLoansByUserUseCase findActiveLoansByUserUseCase;

    @Mock
    private FindOverdueLoansByUserUseCase findOverdueLoansByUserUseCase;

    @Mock
    private LoanResponseMapper loanResponseMapper;

    @InjectMocks
    private LoanApplicationServiceImpl loanApplicationService;

    private Loan loan;
    private LoanResponse loanResponse;

    @BeforeEach
    void setUp() {
        loan = TestUtils.createLoan();
        loanResponse = TestUtils.createLoanResponse();
    }

    @Test
    void createLoan_ShouldReturnLoanResponse_WhenValidParameters() {
        when(createLoanUseCase.execute(loan.getBook().getId(), loan.getUser().getId(), loan.getStartDate(), loan.getDueDate())).thenReturn(loan);
        when(loanResponseMapper.toResponse(loan)).thenReturn(loanResponse);

        LoanResponse result = loanApplicationService.createLoan(loan.getBook().getId(), loan.getUser().getId(), loan.getStartDate(), loan.getDueDate());

        assertEquals(loanResponse.getId(), result.getId());
        assertEquals(loanResponse.getStartDate(), result.getStartDate());
        assertEquals(loanResponse.getDueDate(), result.getDueDate());
    }

    @Test
    void createLoan_ShouldThrowException_WhenBookNotFound() {
        Long bookId = loan.getBook().getId();
        Long userId = loan.getUser().getId();
        LocalDate startDate = loan.getStartDate();
        LocalDate dueDate = loan.getDueDate();

        when(createLoanUseCase.execute(bookId, userId, startDate, dueDate))
            .thenThrow(new IllegalArgumentException(BookValidationMessages.bookNotFound(bookId)));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> loanApplicationService.createLoan(bookId, userId, startDate, dueDate));

        assertEquals(BookValidationMessages.bookNotFound(bookId), exception.getMessage());
    }

    @Test
    void createLoan_ShouldThrowException_WhenUserNotFound() {
        Long bookId = loan.getBook().getId();
        Long userId = loan.getUser().getId();
        LocalDate startDate = loan.getStartDate();
        LocalDate dueDate = loan.getDueDate();

        when(createLoanUseCase.execute(bookId, userId, startDate, dueDate))
            .thenThrow(new IllegalArgumentException(UserValidationMessages.userNotFoundById(userId)));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> loanApplicationService.createLoan(bookId, userId, startDate, dueDate));

        assertEquals(UserValidationMessages.userNotFoundById(userId), exception.getMessage());
    }

    @Test
    void returnLoan_ShouldReturnLoanResponse_WhenValidParameters() {
        Loan returnedLoan = TestUtils.createReturnedLoan();
        LoanResponse returnedLoanResponse = TestUtils.createReturnedLoanResponse();

        when(returnLoanUseCase.execute(loan.getId(), returnedLoan.getReturnDate())).thenReturn(returnedLoan);
        when(loanResponseMapper.toResponse(returnedLoan)).thenReturn(returnedLoanResponse);

        LoanResponse result = loanApplicationService.returnLoan(loan.getId(), returnedLoan.getReturnDate());

        assertEquals(returnedLoanResponse.getId(), result.getId());
        assertEquals(returnedLoanResponse.getReturnDate(), result.getReturnDate());
    }

    @Test
    void returnLoan_ShouldThrowException_WhenLoanNotFound() {
        Long loanId = loan.getId();
        LocalDate returnDate = loan.getDueDate();

        when(returnLoanUseCase.execute(loanId, returnDate))
            .thenThrow(new IllegalArgumentException(LoanValidationMessages.loanNotFoundById(loanId)));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> loanApplicationService.returnLoan(loanId, returnDate));

        assertEquals(LoanValidationMessages.loanNotFoundById(loanId), exception.getMessage());
    }

    @Test
    void getActiveLoansByUser_ShouldReturnLoanResponses_WhenActiveLoansExist() {
        List<Loan> loans = List.of(loan);

        when(findActiveLoansByUserUseCase.execute(loan.getUser().getId())).thenReturn(loans);
        when(loanResponseMapper.toResponse(loan)).thenReturn(loanResponse);

        List<LoanResponse> result = loanApplicationService.getActiveLoansByUser(loan.getUser().getId());

        assertEquals(1, result.size());
        assertEquals(loanResponse.getId(), result.getFirst().getId());
    }

    @Test
    void getActiveLoansByUser_ShouldReturnEmptyList_WhenNoActiveLoans() {
        when(findActiveLoansByUserUseCase.execute(loan.getUser().getId())).thenReturn(Collections.emptyList());

        List<LoanResponse> result = loanApplicationService.getActiveLoansByUser(loan.getUser().getId());

        assertTrue(result.isEmpty());
    }

    @Test
    void getOverdueLoansByUser_ShouldReturnLoanResponses_WhenOverdueLoansExist() {
        List<Loan> loans = List.of(loan);

        when(findOverdueLoansByUserUseCase.execute(loan.getUser().getId())).thenReturn(loans);
        when(loanResponseMapper.toResponse(loan)).thenReturn(loanResponse);

        List<LoanResponse> result = loanApplicationService.getOverdueLoansByUser(loan.getUser().getId());

        assertEquals(1, result.size());
        assertEquals(loanResponse.getId(), result.getFirst().getId());
    }

    @Test
    void getOverdueLoansByUser_ShouldReturnEmptyList_WhenNoOverdueLoans() {
        when(findOverdueLoansByUserUseCase.execute(loan.getUser().getId())).thenReturn(Collections.emptyList());

        List<LoanResponse> result = loanApplicationService.getOverdueLoansByUser(loan.getUser().getId());

        assertTrue(result.isEmpty());
    }
}
