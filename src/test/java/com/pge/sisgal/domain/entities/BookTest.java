package com.pge.sisgal.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.pge.sisgal.TestUtils;
import com.pge.sisgal.domain.messages.BookValidationMessages;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookTest {

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " "})
    void constructor_ShouldThrowException_WhenTitleIsInvalid(String title) {
        Book validBook = TestUtils.createBook();
        String author = validBook.getAuthor();
        String isbn = validBook.getIsbn();
        Integer availableQuantity = validBook.getAvailableQuantity();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> new Book(title, author, isbn, availableQuantity));

        assertEquals(BookValidationMessages.TITLE_REQUIRED, exception.getMessage());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " "})
    void constructor_ShouldThrowException_WhenAuthorIsInvalid(String author) {
        Book validBook = TestUtils.createBook();
        String title = validBook.getTitle();
        String isbn = validBook.getIsbn();
        Integer availableQuantity = validBook.getAvailableQuantity();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> new Book(title, author, isbn, availableQuantity));

        assertEquals(BookValidationMessages.AUTHOR_REQUIRED, exception.getMessage());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " "})
    void constructor_ShouldThrowException_WhenIsbnIsInvalid(String isbn) {
        Book validBook = TestUtils.createBook();
        String title = validBook.getTitle();
        String author = validBook.getAuthor();
        Integer availableQuantity = validBook.getAvailableQuantity();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> new Book(title, author, isbn, availableQuantity));

        assertEquals(BookValidationMessages.ISBN_REQUIRED, exception.getMessage());
    }

    @Test
    void constructor_ShouldThrowException_WhenAvailableQuantityIsNull() {
        Book validBook = TestUtils.createBook();
        String title = validBook.getTitle();
        String author = validBook.getAuthor();
        String isbn = validBook.getIsbn();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> new Book(title, author, isbn, null));

        assertEquals(BookValidationMessages.AVAILABLE_QUANTITY_REQUIRED, exception.getMessage());
    }

    @Test
    void constructor_ShouldThrowException_WhenAvailableQuantityIsNegative() {
        Book validBook = TestUtils.createBook();
        String title = validBook.getTitle();
        String author = validBook.getAuthor();
        String isbn = validBook.getIsbn();
        Integer negativeQuantity = -1;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> new Book(title, author, isbn, negativeQuantity));

        assertEquals(BookValidationMessages.AVAILABLE_QUANTITY_NEGATIVE, exception.getMessage());
    }

    @Test
    void decreaseQuantity_ShouldDecreaseByOne_WhenQuantityIsPositive() {
        Book book = TestUtils.createBook();
        book.decreaseQuantity();
        assertEquals(TestUtils.createBookResponse().getAvailableQuantity() - 1, book.getAvailableQuantity());
    }

    @Test
    void decreaseQuantity_ShouldThrowException_WhenQuantityIsZero() {
        Book book = TestUtils.createBook();
        book.setAvailableQuantity(0);

        IllegalStateException exception = assertThrows(IllegalStateException.class,
            book::decreaseQuantity);

        assertEquals(BookValidationMessages.noAvailableCopies(TestUtils.createBookResponse().getTitle()), exception.getMessage());
    }

    @Test
    void increaseQuantity_ShouldIncreaseByOne() {
        Book book = TestUtils.createBook();
        book.increaseQuantity();
        assertEquals(TestUtils.createBookResponse().getAvailableQuantity() + 1, book.getAvailableQuantity());
    }
}
