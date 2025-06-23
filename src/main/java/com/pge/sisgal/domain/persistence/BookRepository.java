package com.pge.sisgal.domain.persistence;

import com.pge.sisgal.domain.entities.Book;
import java.util.List;
import java.util.Optional;

public interface BookRepository {

    Book save(Book book);

    Optional<Book> findById(Long id);

    Optional<Book> findByIsbn(String isbn);

    Boolean existsByIsbn(String isbn);

    List<Book> findAllAvailable();

}
