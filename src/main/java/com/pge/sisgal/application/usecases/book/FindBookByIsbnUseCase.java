package com.pge.sisgal.application.usecases.book;

import com.pge.sisgal.domain.entities.Book;
import com.pge.sisgal.domain.messages.BookValidationMessages;
import com.pge.sisgal.domain.persistence.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FindBookByIsbnUseCase {
    private final BookRepository bookRepository;

    public Book execute(String isbn) {
        return bookRepository.findByIsbn(isbn)
            .orElseThrow(() -> new IllegalArgumentException(BookValidationMessages.bookNotFoundByIsbn(isbn)));
    }
}
