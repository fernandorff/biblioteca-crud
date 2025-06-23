package com.pge.sisgal.application.services.book;

import com.pge.sisgal.api.dtos.book.responses.BookResponse;
import java.util.List;

public interface BookApplicationService {
    BookResponse createBook(String title, String author, String isbn, Integer availableQuantity);
    BookResponse getBookById(Long id);
    BookResponse getBookByIsbn(String isbn);
    List<BookResponse> getAllAvailableBooks();
}
