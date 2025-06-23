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
class FindBookByIdUseCaseTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private FindBookByIdUseCase findBookByIdUseCase;

    @Test
    void execute_ShouldReturnBook_WhenBookExists() {
        Book expectedBook = TestUtils.createBook();

        when(bookRepository.findById(expectedBook.getId())).thenReturn(Optional.of(expectedBook));

        Book result = findBookByIdUseCase.execute(expectedBook.getId());

        assertEquals(expectedBook.getId(), result.getId());
        assertEquals(expectedBook.getTitle(), result.getTitle());
    }

    @Test
    void execute_ShouldThrowException_WhenBookNotFound() {
        Long bookId = TestUtils.createBook().getId();
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> findBookByIdUseCase.execute(bookId));

        assertEquals(BookValidationMessages.bookNotFound(bookId), exception.getMessage());
    }
}
