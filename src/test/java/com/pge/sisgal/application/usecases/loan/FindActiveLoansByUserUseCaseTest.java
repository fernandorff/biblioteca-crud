package com.pge.sisgal.application.usecases.loan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.pge.sisgal.TestUtils;
import com.pge.sisgal.domain.entities.Loan;
import com.pge.sisgal.domain.persistence.LoanRepository;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FindActiveLoansByUserUseCaseTest {

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private FindActiveLoansByUserUseCase findActiveLoansByUserUseCase;

    private Loan loan;

    @BeforeEach
    void setUp() {
        loan = TestUtils.createLoan();
    }

    @Test
    void execute_ShouldReturnLoans_WhenActiveLoansExist() {
        List<Loan> loans = List.of(loan);
        when(loanRepository.findActiveLoansByUser(loan.getUser().getId())).thenReturn(loans);

        List<Loan> result = findActiveLoansByUserUseCase.execute(loan.getUser().getId());

        assertEquals(1, result.size());
        assertEquals(loan.getId(), result.getFirst().getId());
        assertEquals(loan.getUser().getId(), result.getFirst().getUser().getId());
        assertEquals(loan.getBook().getId(), result.getFirst().getBook().getId());
        assertNull(result.getFirst().getReturnDate());
    }

    @Test
    void execute_ShouldReturnEmptyList_WhenNoActiveLoansExist() {
        when(loanRepository.findActiveLoansByUser(loan.getUser().getId())).thenReturn(Collections.emptyList());

        List<Loan> result = findActiveLoansByUserUseCase.execute(loan.getUser().getId());

        assertTrue(result.isEmpty());
    }
}
