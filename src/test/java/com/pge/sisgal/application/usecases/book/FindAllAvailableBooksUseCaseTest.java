package com.pge.sisgal.application.usecases.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pge.sisgal.TestUtils;
import com.pge.sisgal.domain.entities.Book;
import com.pge.sisgal.domain.persistence.BookRepository;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FindAllAvailableBooksUseCaseTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private FindAllAvailableBooksUseCase findAllAvailableBooksUseCase;

    @Test
    void execute_ShouldReturnAvailableBooks_WhenBooksAreAvailable() {
        Book book1 = TestUtils.createBook();
        Book book2 = TestUtils.createBook();
        book2.setId(3L);
        List<Book> availableBooks = Arrays.asList(book1, book2);
        when(bookRepository.findAllAvailable()).thenReturn(availableBooks);

        List<Book> result = findAllAvailableBooksUseCase.execute();

        assertEquals(2, result.size());
        assertTrue(result.contains(book1));
        assertTrue(result.contains(book2));
        verify(bookRepository).findAllAvailable();
    }

    @Test
    void execute_ShouldReturnEmptyList_WhenNoBooksAreAvailable() {
        when(bookRepository.findAllAvailable()).thenReturn(Collections.emptyList());

        List<Book> result = findAllAvailableBooksUseCase.execute();

        assertTrue(result.isEmpty());
        verify(bookRepository).findAllAvailable();
    }
}
