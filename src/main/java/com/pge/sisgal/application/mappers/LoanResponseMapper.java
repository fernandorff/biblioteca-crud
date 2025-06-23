package com.pge.sisgal.application.mappers;

import com.pge.sisgal.api.dtos.loan.responses.LoanResponse;
import com.pge.sisgal.domain.entities.Loan;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoanResponseMapper {

    private final BookResponseMapper bookResponseMapper;
    private final UserResponseMapper userResponseMapper;

    public LoanResponse toResponse(Loan loan) {
        return LoanResponse.builder()
            .id(loan.getId())
            .book(bookResponseMapper.toResponse(loan.getBook()))
            .user(userResponseMapper.toResponse(loan.getUser()))
            .startDate(loan.getStartDate())
            .dueDate(loan.getDueDate())
            .returnDate(loan.getReturnDate())
            .build();
    }

}
