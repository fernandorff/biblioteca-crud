package com.pge.sisgal.application.usecases.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pge.sisgal.TestUtils;
import com.pge.sisgal.domain.entities.Book;
import com.pge.sisgal.domain.messages.BookValidationMessages;
import com.pge.sisgal.domain.persistence.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateBookUseCaseTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private CreateBookUseCase createBookUseCase;

    private Book book;

    @BeforeEach
    void setUp() {
        book = TestUtils.createBook();
    }

    @Test
    void execute_ShouldCreateBook_WhenValidParameters() {
        when(bookRepository.existsByIsbn(book.getIsbn())).thenReturn(false);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book result = createBookUseCase.execute(book.getTitle(), book.getAuthor(), book.getIsbn(), book.getAvailableQuantity());

        verify(bookRepository).existsByIsbn(book.getIsbn());
        verify(bookRepository).save(any(Book.class));
        assertEquals(book.getTitle(), result.getTitle());
        assertEquals(book.getAuthor(), result.getAuthor());
        assertEquals(book.getIsbn(), result.getIsbn());
        assertEquals(book.getAvailableQuantity(), result.getAvailableQuantity());
    }

    @Test
    void execute_ShouldThrowException_WhenIsbnAlreadyExists() {
        String title = book.getTitle();
        String author = book.getAuthor();
        String isbn = book.getIsbn();
        Integer availableQuantity = book.getAvailableQuantity();

        when(bookRepository.existsByIsbn(isbn)).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> createBookUseCase.execute(title, author, isbn, availableQuantity));

        verify(bookRepository).existsByIsbn(isbn);
        verify(bookRepository, never()).save(any(Book.class));
        assertEquals(BookValidationMessages.bookAlreadyCreatedWithSameIsbn(isbn), exception.getMessage());
    }
}
