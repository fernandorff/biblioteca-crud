package com.pge.sisgal.domain.entities;

import com.pge.sisgal.domain.messages.BookValidationMessages;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Book {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private Integer availableQuantity;

    public Book(String title, String author, String isbn, Integer availableQuantity) {
        if (title == null || title.trim().isEmpty()) throw new IllegalArgumentException(BookValidationMessages.TITLE_REQUIRED);
        if (author == null || author.trim().isEmpty()) throw new IllegalArgumentException(BookValidationMessages.AUTHOR_REQUIRED);
        if (isbn == null || isbn.trim().isEmpty()) throw new IllegalArgumentException(BookValidationMessages.ISBN_REQUIRED);
        if (availableQuantity == null) throw new IllegalArgumentException(BookValidationMessages.AVAILABLE_QUANTITY_REQUIRED);
        if (availableQuantity < 0) throw new IllegalArgumentException(BookValidationMessages.AVAILABLE_QUANTITY_NEGATIVE);
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.availableQuantity = availableQuantity;
    }

    public void decreaseQuantity() {
        if (availableQuantity <= 0) throw new IllegalStateException(BookValidationMessages.noAvailableCopies(title));
        availableQuantity--;
    }

    public void increaseQuantity() {
        availableQuantity++;
    }
}
