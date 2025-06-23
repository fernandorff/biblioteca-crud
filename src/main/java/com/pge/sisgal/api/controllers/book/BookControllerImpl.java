package com.pge.sisgal.api.controllers.book;

import com.pge.sisgal.api.dtos.book.requests.CreateBookRequest;
import com.pge.sisgal.api.dtos.book.responses.BookResponse;
import com.pge.sisgal.application.services.book.BookApplicationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookControllerImpl implements BookController {

    private final BookApplicationService bookApplicationService;

    @Override
    public ResponseEntity<BookResponse> createBook(CreateBookRequest request) {
        BookResponse response = bookApplicationService.createBook(
            request.getTitle(),
            request.getAuthor(),
            request.getIsbn(),
            request.getAvailableQuantity()
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<BookResponse> getBookById(Long id) {
        BookResponse response = bookApplicationService.getBookById(id);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<BookResponse> getBookByIsbn(String isbn) {
        BookResponse response = bookApplicationService.getBookByIsbn(isbn);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<BookResponse>> getAllAvailableBooks() {
        List<BookResponse> responses = bookApplicationService.getAllAvailableBooks();
        return ResponseEntity.ok(responses);
    }
}
