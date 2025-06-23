package com.pge.sisgal.domain.persistence;

import com.pge.sisgal.domain.entities.Loan;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LoanRepository {

    Loan save(Loan loan);

    Optional<Loan> findById(Long id);

    List<Loan> findActiveLoansByUser(Long userId);

    List<Loan> findOverdueLoansByUser(Long userId, LocalDate currentDate);

}
