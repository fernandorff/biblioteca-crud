package com.pge.sisgal.infraestructure.persistence.adapters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pge.sisgal.TestUtils;
import com.pge.sisgal.domain.entities.Loan;
import com.pge.sisgal.infraestructure.persistence.entities.BookEntity;
import com.pge.sisgal.infraestructure.persistence.entities.LoanEntity;
import com.pge.sisgal.infraestructure.persistence.entities.UserEntity;
import com.pge.sisgal.infraestructure.persistence.mappers.LoanEntityMapper;
import com.pge.sisgal.infraestructure.persistence.repositories.JpaLoanRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LoanRepositoryAdapterTest {

    @Mock
    private JpaLoanRepository jpaLoanRepository;

    @Mock
    private LoanEntityMapper loanEntityMapper;

    @InjectMocks
    private LoanRepositoryAdapter loanRepositoryAdapter;

    @Test
    void save_ShouldReturnSavedLoan_WhenValidLoan() {
        Loan loan = TestUtils.createLoan();
        BookEntity bookEntity = BookEntity.builder().build();
        UserEntity userEntity = UserEntity.builder().build();
        LoanEntity loanEntity = LoanEntity.builder()
            .id(loan.getId())
            .book(bookEntity)
            .user(userEntity)
            .startDate(loan.getStartDate())
            .dueDate(loan.getDueDate())
            .build();

        when(loanEntityMapper.toEntity(loan)).thenReturn(loanEntity);
        when(jpaLoanRepository.save(loanEntity)).thenReturn(loanEntity);
        when(loanEntityMapper.toDomain(loanEntity)).thenReturn(loan);

        Loan result = loanRepositoryAdapter.save(loan);

        verify(jpaLoanRepository).save(loanEntity);
        assertEquals(loan.getId(), result.getId());
        assertEquals(loan.getStartDate(), result.getStartDate());
    }

    @Test
    void findById_ShouldReturnLoan_WhenLoanExists() {
        Loan loan = TestUtils.createLoan();
        BookEntity bookEntity = BookEntity.builder().build();
        UserEntity userEntity = UserEntity.builder().build();
        LoanEntity loanEntity = LoanEntity.builder()
            .id(loan.getId())
            .book(bookEntity)
            .user(userEntity)
            .startDate(loan.getStartDate())
            .dueDate(loan.getDueDate())
            .build();

        when(jpaLoanRepository.findById(loan.getId())).thenReturn(Optional.of(loanEntity));
        when(loanEntityMapper.toDomain(loanEntity)).thenReturn(loan);

        Optional<Loan> result = loanRepositoryAdapter.findById(loan.getId());

        assertTrue(result.isPresent());
        assertEquals(loan.getId(), result.get().getId());
    }

    @Test
    void findById_ShouldReturnEmpty_WhenLoanDoesNotExist() {
        when(jpaLoanRepository.findById(TestUtils.createLoan().getId())).thenReturn(Optional.empty());

        Optional<Loan> result = loanRepositoryAdapter.findById(TestUtils.createLoan().getId());

        assertFalse(result.isPresent());
    }

    @Test
    void findActiveLoansByUser_ShouldReturnLoans_WhenActiveLoansExist() {
        Loan loan = TestUtils.createLoan();
        BookEntity bookEntity = BookEntity.builder().build();
        UserEntity userEntity = UserEntity.builder().build();
        LoanEntity loanEntity = LoanEntity.builder()
            .id(loan.getId())
            .book(bookEntity)
            .user(userEntity)
            .startDate(loan.getStartDate())
            .dueDate(loan.getDueDate())
            .build();
        List<LoanEntity> loanEntities = List.of(loanEntity);

        when(jpaLoanRepository.findByUserIdAndReturnDateIsNull(loan.getUser().getId())).thenReturn(loanEntities);
        when(loanEntityMapper.toDomain(any(LoanEntity.class))).thenReturn(loan);

        List<Loan> result = loanRepositoryAdapter.findActiveLoansByUser(loan.getUser().getId());

        verify(jpaLoanRepository).findByUserIdAndReturnDateIsNull(loan.getUser().getId());
        assertEquals(1, result.size());
        assertEquals(loan.getId(), result.getFirst().getId());
    }

    @Test
    void findActiveLoansByUser_ShouldReturnEmptyList_WhenNoActiveLoansExist() {
        when(jpaLoanRepository.findByUserIdAndReturnDateIsNull(TestUtils.createLoan().getUser().getId())).thenReturn(List.of());

        List<Loan> result = loanRepositoryAdapter.findActiveLoansByUser(TestUtils.createLoan().getUser().getId());

        verify(jpaLoanRepository).findByUserIdAndReturnDateIsNull(TestUtils.createLoan().getUser().getId());
        assertTrue(result.isEmpty());
    }

    @Test
    void findOverdueLoansByUser_ShouldReturnLoans_WhenOverdueLoansExist() {
        Loan loan = TestUtils.createLoan();
        BookEntity bookEntity = BookEntity.builder().build();
        UserEntity userEntity = UserEntity.builder().build();
        LoanEntity loanEntity = LoanEntity.builder()
            .id(loan.getId())
            .book(bookEntity)
            .user(userEntity)
            .startDate(loan.getStartDate())
            .dueDate(loan.getDueDate())
            .build();
        List<LoanEntity> loanEntities = List.of(loanEntity);
        LocalDate currentDate = LocalDate.of(2025, 1, 20);

        when(jpaLoanRepository.findByUserIdAndReturnDateIsNullAndDueDateBefore(loan.getUser().getId(), currentDate)).thenReturn(loanEntities);
        when(loanEntityMapper.toDomain(any(LoanEntity.class))).thenReturn(loan);

        List<Loan> result = loanRepositoryAdapter.findOverdueLoansByUser(loan.getUser().getId(), currentDate);

        verify(jpaLoanRepository).findByUserIdAndReturnDateIsNullAndDueDateBefore(loan.getUser().getId(), currentDate);
        assertEquals(1, result.size());
        assertEquals(loan.getId(), result.getFirst().getId());
    }

    @Test
    void findOverdueLoansByUser_ShouldReturnEmptyList_WhenNoOverdueLoansExist() {
        LocalDate currentDate = LocalDate.of(2025, 1, 20);
        when(jpaLoanRepository.findByUserIdAndReturnDateIsNullAndDueDateBefore(TestUtils.createLoan().getUser().getId(), currentDate)).thenReturn(List.of());

        List<Loan> result = loanRepositoryAdapter.findOverdueLoansByUser(TestUtils.createLoan().getUser().getId(), currentDate);

        verify(jpaLoanRepository).findByUserIdAndReturnDateIsNullAndDueDateBefore(TestUtils.createLoan().getUser().getId(), currentDate);
        assertTrue(result.isEmpty());
    }
}
