package com.pge.sisgal.infraestructure.persistence.repositories;

import com.pge.sisgal.infraestructure.persistence.entities.BookEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaBookRepository extends JpaRepository<BookEntity, Long> {

    Optional<BookEntity> findByIsbn(String isbn);

    List<BookEntity> findByAvailableQuantityGreaterThan(int quantity);

    Boolean existsByIsbn(String isbn);
}
