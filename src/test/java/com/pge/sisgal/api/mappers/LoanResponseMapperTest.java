package com.pge.sisgal.api.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.pge.sisgal.TestUtils;
import com.pge.sisgal.api.dtos.loan.responses.LoanResponse;
import com.pge.sisgal.application.mappers.BookResponseMapper;
import com.pge.sisgal.application.mappers.LoanResponseMapper;
import com.pge.sisgal.application.mappers.UserResponseMapper;
import com.pge.sisgal.domain.entities.Loan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LoanResponseMapperTest {

    @Mock
    private BookResponseMapper bookResponseMapper;

    @Mock
    private UserResponseMapper userResponseMapper;

    @InjectMocks
    private LoanResponseMapper loanResponseMapper;

    private Loan loan;
    private LoanResponse loanResponse;

    @BeforeEach
    void setUp() {
        loan = TestUtils.createReturnedLoan();
        loanResponse = TestUtils.createReturnedLoanResponse();
    }

    @Test
    void toResponse_ShouldMapLoanToLoanResponse_Correctly() {
        when(bookResponseMapper.toResponse(loan.getBook())).thenReturn(loanResponse.getBook());
        when(userResponseMapper.toResponse(loan.getUser())).thenReturn(loanResponse.getUser());

        LoanResponse result = loanResponseMapper.toResponse(loan);

        assertEquals(loanResponse.getId(), result.getId());
        assertEquals(loanResponse.getBook(), result.getBook());
        assertEquals(loanResponse.getUser(), result.getUser());
        assertEquals(loanResponse.getStartDate(), result.getStartDate());
        assertEquals(loanResponse.getDueDate(), result.getDueDate());
        assertEquals(loanResponse.getReturnDate(), result.getReturnDate());
    }
}
