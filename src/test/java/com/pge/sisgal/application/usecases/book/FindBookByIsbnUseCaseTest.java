package com.pge.sisgal.application.usecases.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.pge.sisgal.TestUtils;
import com.pge.sisgal.domain.entities.Book;
import com.pge.sisgal.domain.messages.BookValidationMessages;
import com.pge.sisgal.domain.persistence.BookRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FindBookByIsbnUseCaseTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private FindBookByIsbnUseCase findBookByIsbnUseCase;

    @Test
    void execute_ShouldReturnBook_WhenBookExists() {
        Book expectedBook = TestUtils.createBook();

        when(bookRepository.findByIsbn(expectedBook.getIsbn())).thenReturn(Optional.of(expectedBook));

        Book result = findBookByIsbnUseCase.execute(expectedBook.getIsbn());

        assertEquals(expectedBook.getId(), result.getId());
        assertEquals(expectedBook.getIsbn(), result.getIsbn());
    }

    @Test
    void execute_ShouldThrowException_WhenBookNotFound() {
        String isbn = TestUtils.createBook().getIsbn();
        when(bookRepository.findByIsbn(isbn)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> findBookByIsbnUseCase.execute(isbn));

        assertEquals(BookValidationMessages.bookNotFoundByIsbn(isbn), exception.getMessage());
    }
}
