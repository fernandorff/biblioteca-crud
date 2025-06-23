package com.pge.sisgal.infraestructure.persistence.adapters;

import com.pge.sisgal.domain.entities.Loan;
import com.pge.sisgal.domain.persistence.LoanRepository;
import com.pge.sisgal.infraestructure.persistence.entities.LoanEntity;
import com.pge.sisgal.infraestructure.persistence.mappers.LoanEntityMapper;
import com.pge.sisgal.infraestructure.persistence.repositories.JpaLoanRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoanRepositoryAdapter implements LoanRepository {

    private final JpaLoanRepository jpaLoanRepository;
    private final LoanEntityMapper loanEntityMapper;

    @Override
    public Loan save(Loan loan) {
        LoanEntity entity = loanEntityMapper.toEntity(loan);
        LoanEntity savedEntity = jpaLoanRepository.save(entity);
        return loanEntityMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Loan> findById(Long id) {
        return jpaLoanRepository.findById(id)
            .map(loanEntityMapper::toDomain);
    }

    @Override
    public List<Loan> findActiveLoansByUser(Long userId) {
        return jpaLoanRepository.findByUserIdAndReturnDateIsNull(userId)
            .stream()
            .map(loanEntityMapper::toDomain)
            .toList();
    }

    @Override
    public List<Loan> findOverdueLoansByUser(Long userId, LocalDate currentDate) {
        return jpaLoanRepository.findByUserIdAndReturnDateIsNullAndDueDateBefore(userId, currentDate)
            .stream()
            .map(loanEntityMapper::toDomain)
            .toList();
    }
}
