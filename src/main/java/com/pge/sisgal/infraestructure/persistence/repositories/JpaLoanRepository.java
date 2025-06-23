package com.pge.sisgal.infraestructure.persistence.repositories;

import com.pge.sisgal.infraestructure.persistence.entities.LoanEntity;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaLoanRepository extends JpaRepository<LoanEntity, Long> {

    List<LoanEntity> findByUserIdAndReturnDateIsNull(Long userId);

    List<LoanEntity> findByUserIdAndReturnDateIsNullAndDueDateBefore(Long userId, LocalDate currentDate);
}
