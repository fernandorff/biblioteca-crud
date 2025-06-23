package com.pge.sisgal.infraestructure.persistence.mappers;

import com.pge.sisgal.domain.entities.Book;
import com.pge.sisgal.domain.entities.Loan;
import com.pge.sisgal.domain.entities.User;
import com.pge.sisgal.infraestructure.persistence.entities.LoanEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoanEntityMapper {
    private final BookEntityMapper bookEntityMapper;
    private final UserEntityMapper userEntityMapper;

    public LoanEntity toEntity(Loan loan) {
        return LoanEntity.builder()
            .id(loan.getId())
            .book(bookEntityMapper.toEntity(loan.getBook()))
            .user(userEntityMapper.toEntity(loan.getUser()))
            .startDate(loan.getStartDate())
            .dueDate(loan.getDueDate())
            .returnDate(loan.getReturnDate())
            .build();
    }

    public Loan toDomain(LoanEntity entity) {
        Book book = bookEntityMapper.toDomain(entity.getBook());
        User user = userEntityMapper.toDomain(entity.getUser());

        Loan loan = new Loan(book, user, entity.getStartDate(), entity.getDueDate());
        loan.setId(entity.getId());
        loan.setReturnDate(entity.getReturnDate());

        return loan;
    }
}
