package com.pge.sisgal.infraestructure.persistence.mappers;

import com.pge.sisgal.domain.entities.Book;
import com.pge.sisgal.infraestructure.persistence.entities.BookEntity;
import org.springframework.stereotype.Component;

@Component
public class BookEntityMapper {

    public BookEntity toEntity(Book book) {
        return BookEntity.builder()
            .id(book.getId())
            .title(book.getTitle())
            .author(book.getAuthor())
            .isbn(book.getIsbn())
            .availableQuantity(book.getAvailableQuantity())
            .build();
    }

    public Book toDomain(BookEntity entity) {
        Book book = new Book(
            entity.getTitle(),
            entity.getAuthor(),
            entity.getIsbn(),
            entity.getAvailableQuantity()
        );
        book.setId(entity.getId());
        return book;
    }
}
