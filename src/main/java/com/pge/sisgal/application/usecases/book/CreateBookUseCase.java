package com.pge.sisgal.application.usecases.book;

import com.pge.sisgal.domain.entities.Book;
import com.pge.sisgal.domain.messages.BookValidationMessages;
import com.pge.sisgal.domain.persistence.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateBookUseCase {
    private final BookRepository bookRepository;

    public Book execute(String title, String author, String isbn, Integer availableQuantity) {
        if (Boolean.TRUE.equals(bookRepository.existsByIsbn(isbn))) {
            throw new IllegalArgumentException(BookValidationMessages.bookAlreadyCreatedWithSameIsbn(isbn));
        }
        Book book = new Book(title, author, isbn, availableQuantity);
        return bookRepository.save(book);
    }
}
