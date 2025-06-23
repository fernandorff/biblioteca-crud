package com.pge.sisgal.infraestructure.persistence.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.pge.sisgal.TestUtils;
import com.pge.sisgal.domain.entities.Book;
import com.pge.sisgal.infraestructure.persistence.entities.BookEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookEntityMapperTest {

    private final BookEntityMapper bookEntityMapper = new BookEntityMapper();

    @Test
    void toEntity_ShouldMapBookToBookEntity() {
        Book book = TestUtils.createBook();

        BookEntity result = bookEntityMapper.toEntity(book);

        assertEquals(book.getId(), result.getId());
        assertEquals(book.getTitle(), result.getTitle());
        assertEquals(book.getAuthor(), result.getAuthor());
        assertEquals(book.getIsbn(), result.getIsbn());
        assertEquals(book.getAvailableQuantity(), result.getAvailableQuantity());
    }

    @Test
    void toDomain_ShouldMapBookEntityToBook() {
        Book book = TestUtils.createBook();
        BookEntity bookEntity = BookEntity.builder()
            .id(book.getId())
            .title(book.getTitle())
            .author(book.getAuthor())
            .isbn(book.getIsbn())
            .availableQuantity(book.getAvailableQuantity())
            .build();

        Book result = bookEntityMapper.toDomain(bookEntity);

        assertEquals(bookEntity.getId(), result.getId());
        assertEquals(bookEntity.getTitle(), result.getTitle());
        assertEquals(bookEntity.getAuthor(), result.getAuthor());
        assertEquals(bookEntity.getIsbn(), result.getIsbn());
        assertEquals(bookEntity.getAvailableQuantity(), result.getAvailableQuantity());
    }
}
