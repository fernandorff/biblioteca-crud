package com.pge.sisgal.application.services.book;

import com.pge.sisgal.api.dtos.book.responses.BookResponse;
import com.pge.sisgal.application.mappers.BookResponseMapper;
import com.pge.sisgal.application.usecases.book.CreateBookUseCase;
import com.pge.sisgal.application.usecases.book.FindAllAvailableBooksUseCase;
import com.pge.sisgal.application.usecases.book.FindBookByIdUseCase;
import com.pge.sisgal.application.usecases.book.FindBookByIsbnUseCase;
import com.pge.sisgal.domain.entities.Book;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookApplicationServiceImpl implements BookApplicationService {

    private final CreateBookUseCase createBookUseCase;
    private final FindBookByIdUseCase findBookByIdUseCase;
    private final FindBookByIsbnUseCase findBookByIsbnUseCase;
    private final FindAllAvailableBooksUseCase findAllAvailableBooksUseCase;
    private final BookResponseMapper bookResponseMapper;

    @Override
    public BookResponse createBook(String title, String author, String isbn, Integer availableQuantity) {
        Book book = createBookUseCase.execute(title, author, isbn, availableQuantity);
        return bookResponseMapper.toResponse(book);
    }

    @Override
    public BookResponse getBookById(Long id) {
        Book book = findBookByIdUseCase.execute(id);
        return bookResponseMapper.toResponse(book);
    }

    @Override
    public BookResponse getBookByIsbn(String isbn) {
        Book book = findBookByIsbnUseCase.execute(isbn);
        return bookResponseMapper.toResponse(book);
    }

    @Override
    public List<BookResponse> getAllAvailableBooks() {
        List<Book> books = findAllAvailableBooksUseCase.execute();
        return books.stream()
            .map(bookResponseMapper::toResponse)
            .toList();
    }
}
