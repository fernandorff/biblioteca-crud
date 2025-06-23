package com.pge.sisgal.application.usecases.loan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.pge.sisgal.TestUtils;
import com.pge.sisgal.domain.entities.Loan;
import com.pge.sisgal.domain.persistence.LoanRepository;
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
class FindOverdueLoansByUserUseCaseTest {

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private FindOverdueLoansByUserUseCase findOverdueLoansByUserUseCase;

    private Loan loan;

    @BeforeEach
    void setUp() {
        loan = TestUtils.createLoan();
        loan.setDueDate(LocalDate.of(2025, 1, 1));
    }

    @Test
    void execute_ShouldReturnLoans_WhenOverdueLoansExist() {
        List<Loan> loans = List.of(loan);
        when(loanRepository.findOverdueLoansByUser(eq(loan.getUser().getId()), any(LocalDate.class))).thenReturn(loans);

        List<Loan> result = findOverdueLoansByUserUseCase.execute(loan.getUser().getId());

        assertEquals(1, result.size());
        assertEquals(loan.getId(), result.getFirst().getId());
        assertEquals(loan.getUser().getId(), result.getFirst().getUser().getId());
        assertEquals(loan.getBook().getId(), result.getFirst().getBook().getId());
        assertEquals(LocalDate.of(2025, 1, 1), result.getFirst().getDueDate());
        assertNull(result.getFirst().getReturnDate());
    }

    @Test
    void execute_ShouldReturnEmptyList_WhenNoOverdueLoansExist() {
        when(loanRepository.findOverdueLoansByUser(eq(loan.getUser().getId()), any(LocalDate.class))).thenReturn(Collections.emptyList());

        List<Loan> result = findOverdueLoansByUserUseCase.execute(loan.getUser().getId());

        assertTrue(result.isEmpty());
    }
}
