package com.pge.sisgal.infraestructure.persistence.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.pge.sisgal.TestUtils;
import com.pge.sisgal.domain.entities.Book;
import com.pge.sisgal.domain.entities.Loan;
import com.pge.sisgal.domain.entities.User;
import com.pge.sisgal.infraestructure.persistence.entities.BookEntity;
import com.pge.sisgal.infraestructure.persistence.entities.LoanEntity;
import com.pge.sisgal.infraestructure.persistence.entities.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LoanEntityMapperTest {

    @Mock
    private BookEntityMapper bookEntityMapper;

    @Mock
    private UserEntityMapper userEntityMapper;

    @Mock
    private Book defaultBook;

    @Mock
    private User defaultUser;

    @Mock
    private BookEntity defaultBookEntity;

    @Mock
    private UserEntity defaultUserEntity;

    @InjectMocks
    private LoanEntityMapper loanEntityMapper;

    @Test
    void toEntity_ShouldMapLoanToLoanEntity() {
        Loan loan = TestUtils.createReturnedLoan();
        when(bookEntityMapper.toEntity(any())).thenReturn(defaultBookEntity);
        when(userEntityMapper.toEntity(any())).thenReturn(defaultUserEntity);

        LoanEntity result = loanEntityMapper.toEntity(loan);

        assertEquals(loan.getId(), result.getId());
        assertEquals(defaultBookEntity, result.getBook());
        assertEquals(defaultUserEntity, result.getUser());
        assertEquals(loan.getStartDate(), result.getStartDate());
        assertEquals(loan.getDueDate(), result.getDueDate());
        assertEquals(loan.getReturnDate(), result.getReturnDate());
    }

    @Test
    void toDomain_ShouldMapLoanEntityToLoan() {
        Loan loan = TestUtils.createReturnedLoan();
        LoanEntity loanEntity = LoanEntity.builder()
            .id(loan.getId())
            .book(defaultBookEntity)
            .user(defaultUserEntity)
            .startDate(loan.getStartDate())
            .dueDate(loan.getDueDate())
            .returnDate(loan.getReturnDate())
            .build();

        when(bookEntityMapper.toDomain(defaultBookEntity)).thenReturn(defaultBook);
        when(userEntityMapper.toDomain(defaultUserEntity)).thenReturn(defaultUser);

        Loan result = loanEntityMapper.toDomain(loanEntity);

        assertEquals(loanEntity.getId(), result.getId());
        assertEquals(defaultBook, result.getBook());
        assertEquals(defaultUser, result.getUser());
        assertEquals(loanEntity.getStartDate(), result.getStartDate());
        assertEquals(loanEntity.getDueDate(), result.getDueDate());
        assertEquals(loanEntity.getReturnDate(), result.getReturnDate());
    }
}
