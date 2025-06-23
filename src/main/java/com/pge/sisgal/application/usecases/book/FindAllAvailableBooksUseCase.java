package com.pge.sisgal.application.usecases.book;

import com.pge.sisgal.domain.entities.Book;
import com.pge.sisgal.domain.persistence.BookRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FindAllAvailableBooksUseCase {
    private final BookRepository bookRepository;

    public List<Book> execute() {
        return bookRepository.findAllAvailable();
    }
}
