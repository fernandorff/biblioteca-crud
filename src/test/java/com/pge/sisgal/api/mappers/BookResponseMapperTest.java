package com.pge.sisgal.api.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.pge.sisgal.TestUtils;
import com.pge.sisgal.api.dtos.book.responses.BookResponse;
import com.pge.sisgal.application.mappers.BookResponseMapper;
import com.pge.sisgal.domain.entities.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BookResponseMapperTest {

    private BookResponseMapper bookResponseMapper;
    private Book book;

    @BeforeEach
    void setUp() {
        bookResponseMapper = new BookResponseMapper();
        book = TestUtils.createBook();
    }

    @Test
    void toResponse_ShouldMapBookToBookResponse_Correctly() {
        BookResponse result = bookResponseMapper.toResponse(book);

        assertEquals(book.getId(), result.getId());
        assertEquals(book.getTitle(), result.getTitle());
        assertEquals(book.getAuthor(), result.getAuthor());
        assertEquals(book.getIsbn(), result.getIsbn());
        assertEquals(book.getAvailableQuantity(), result.getAvailableQuantity());
    }
}
