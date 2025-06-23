package com.pge.sisgal.application.mappers;

import com.pge.sisgal.api.dtos.book.responses.BookResponse;
import com.pge.sisgal.domain.entities.Book;
import org.springframework.stereotype.Component;

@Component
public class BookResponseMapper {

    public BookResponse toResponse(Book book) {
        return BookResponse.builder()
            .id(book.getId())
            .title(book.getTitle())
            .author(book.getAuthor())
            .isbn(book.getIsbn())
            .availableQuantity(book.getAvailableQuantity())
            .build();
    }

}
