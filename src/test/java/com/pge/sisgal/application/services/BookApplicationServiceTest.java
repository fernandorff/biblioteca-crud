package com.pge.sisgal.application.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pge.sisgal.TestUtils;
import com.pge.sisgal.api.dtos.book.responses.BookResponse;
import com.pge.sisgal.application.mappers.BookResponseMapper;
import com.pge.sisgal.application.services.book.BookApplicationServiceImpl;
import com.pge.sisgal.application.usecases.book.CreateBookUseCase;
import com.pge.sisgal.application.usecases.book.FindAllAvailableBooksUseCase;
import com.pge.sisgal.application.usecases.book.FindBookByIdUseCase;
import com.pge.sisgal.application.usecases.book.FindBookByIsbnUseCase;
import com.pge.sisgal.domain.entities.Book;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookApplicationServiceTest {

    @Mock
    private CreateBookUseCase createBookUseCase;

    @Mock
    private FindBookByIdUseCase findBookByIdUseCase;

    @Mock
    private FindBookByIsbnUseCase findBookByIsbnUseCase;

    @Mock
    private FindAllAvailableBooksUseCase findAllAvailableBooksUseCase;

    @Mock
    private BookResponseMapper bookResponseMapper;

    @InjectMocks
    private BookApplicationServiceImpl bookApplicationService;

    private Book book;
    private BookResponse bookResponse;

    @BeforeEach
    void setUp() {
        book = TestUtils.createBook();
        bookResponse = TestUtils.createBookResponse();
    }

    @Test
    void createBook_ShouldReturnBookResponse_WhenValid() {
        when(createBookUseCase.execute(book.getTitle(), book.getAuthor(), book.getIsbn(), book.getAvailableQuantity())).thenReturn(book);
        when(bookResponseMapper.toResponse(book)).thenReturn(bookResponse);

        BookResponse result = bookApplicationService.createBook(book.getTitle(), book.getAuthor(), book.getIsbn(), book.getAvailableQuantity());

        verify(createBookUseCase).execute(book.getTitle(), book.getAuthor(), book.getIsbn(), book.getAvailableQuantity());
        verify(bookResponseMapper).toResponse(book);
        assertEquals(bookResponse, result);
    }

    @Test
    void getBookById_ShouldReturnBookResponse_WhenBookExists() {
        when(findBookByIdUseCase.execute(book.getId())).thenReturn(book);
        when(bookResponseMapper.toResponse(book)).thenReturn(bookResponse);

        BookResponse result = bookApplicationService.getBookById(book.getId());

        verify(findBookByIdUseCase).execute(book.getId());
        verify(bookResponseMapper).toResponse(book);
        assertEquals(bookResponse, result);
    }

    @Test
    void getBookByIsbn_ShouldReturnBookResponse_WhenBookExists() {
        when(findBookByIsbnUseCase.execute(book.getIsbn())).thenReturn(book);
        when(bookResponseMapper.toResponse(book)).thenReturn(bookResponse);

        BookResponse result = bookApplicationService.getBookByIsbn(book.getIsbn());

        verify(findBookByIsbnUseCase).execute(book.getIsbn());
        verify(bookResponseMapper).toResponse(book);
        assertEquals(bookResponse, result);
    }

    @Test
    void getAllAvailableBooks_ShouldReturnBookResponses_WhenBooksExist() {
        List<Book> books = List.of(book);
        when(findAllAvailableBooksUseCase.execute()).thenReturn(books);
        when(bookResponseMapper.toResponse(book)).thenReturn(bookResponse);

        List<BookResponse> result = bookApplicationService.getAllAvailableBooks();

        verify(findAllAvailableBooksUseCase).execute();
        assertEquals(1, result.size());
        assertEquals(bookResponse, result.getFirst());
    }
}
