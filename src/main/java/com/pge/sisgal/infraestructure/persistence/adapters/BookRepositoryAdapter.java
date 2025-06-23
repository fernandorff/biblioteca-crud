package com.pge.sisgal.infraestructure.persistence.adapters;

import com.pge.sisgal.domain.entities.Book;
import com.pge.sisgal.domain.persistence.BookRepository;
import com.pge.sisgal.infraestructure.persistence.entities.BookEntity;
import com.pge.sisgal.infraestructure.persistence.mappers.BookEntityMapper;
import com.pge.sisgal.infraestructure.persistence.repositories.JpaBookRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookRepositoryAdapter implements BookRepository {

    private final JpaBookRepository jpaBookRepository;
    private final BookEntityMapper bookEntityMapper;

    @Override
    public Book save(Book book) {
        BookEntity entity = bookEntityMapper.toEntity(book);
        BookEntity savedEntity = jpaBookRepository.save(entity);
        return bookEntityMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Book> findById(Long id) {
        return jpaBookRepository.findById(id)
            .map(bookEntityMapper::toDomain);
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return jpaBookRepository.findByIsbn(isbn)
            .map(bookEntityMapper::toDomain);
    }

    @Override
    public Boolean existsByIsbn(String isbn) {
        return jpaBookRepository.existsByIsbn(isbn);
    }

    @Override
    public List<Book> findAllAvailable() {
        return jpaBookRepository.findByAvailableQuantityGreaterThan(0)
            .stream()
            .map(bookEntityMapper::toDomain)
            .toList();
    }
}
