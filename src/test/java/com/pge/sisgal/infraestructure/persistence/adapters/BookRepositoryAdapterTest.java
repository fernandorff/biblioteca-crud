package com.pge.sisgal.infraestructure.persistence.adapters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pge.sisgal.TestUtils;
import com.pge.sisgal.domain.entities.Book;
import com.pge.sisgal.infraestructure.persistence.entities.BookEntity;
import com.pge.sisgal.infraestructure.persistence.mappers.BookEntityMapper;
import com.pge.sisgal.infraestructure.persistence.repositories.JpaBookRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookRepositoryAdapterTest {

    @Mock
    private JpaBookRepository jpaBookRepository;

    @Mock
    private BookEntityMapper bookEntityMapper;

    @InjectMocks
    private BookRepositoryAdapter bookRepositoryAdapter;

    @Test
    void save_ShouldReturnSavedBook_WhenValidBookWithId() {
        Book book = TestUtils.createBook();
        BookEntity bookEntity = BookEntity.builder()
            .id(book.getId())
            .title(book.getTitle())
            .author(book.getAuthor())
            .isbn(book.getIsbn())
            .availableQuantity(book.getAvailableQuantity())
            .build();

        when(bookEntityMapper.toEntity(book)).thenReturn(bookEntity);
        when(jpaBookRepository.save(bookEntity)).thenReturn(bookEntity);
        when(bookEntityMapper.toDomain(bookEntity)).thenReturn(book);

        Book result = bookRepositoryAdapter.save(book);

        verify(jpaBookRepository).save(bookEntity);
        assertEquals(book.getId(), result.getId());
        assertEquals(book.getTitle(), result.getTitle());
        assertEquals(book.getAuthor(), result.getAuthor());
        assertEquals(book.getIsbn(), result.getIsbn());
        assertEquals(book.getAvailableQuantity(), result.getAvailableQuantity());
    }

    @Test
    void save_ShouldReturnSavedBook_WhenValidBookWithoutId() {
        Book book = TestUtils.createBook();
        book.setId(null);
        Book savedBook = TestUtils.createBook();
        BookEntity bookEntity = BookEntity.builder()
            .title(book.getTitle())
            .author(book.getAuthor())
            .isbn(book.getIsbn())
            .availableQuantity(book.getAvailableQuantity())
            .build();
        BookEntity savedBookEntity = BookEntity.builder()
            .id(savedBook.getId())
            .title(savedBook.getTitle())
            .author(savedBook.getAuthor())
            .isbn(savedBook.getIsbn())
            .availableQuantity(savedBook.getAvailableQuantity())
            .build();

        when(bookEntityMapper.toEntity(book)).thenReturn(bookEntity);
        when(jpaBookRepository.save(bookEntity)).thenReturn(savedBookEntity);
        when(bookEntityMapper.toDomain(savedBookEntity)).thenReturn(savedBook);

        Book result = bookRepositoryAdapter.save(book);

        verify(jpaBookRepository).save(bookEntity);
        assertEquals(savedBook.getId(), result.getId());
        assertEquals(book.getTitle(), result.getTitle());
    }

    @Test
    void findById_ShouldReturnBook_WhenBookExists() {
        Book book = TestUtils.createBook();
        BookEntity bookEntity = BookEntity.builder()
            .id(book.getId())
            .title(book.getTitle())
            .author(book.getAuthor())
            .isbn(book.getIsbn())
            .availableQuantity(book.getAvailableQuantity())
            .build();

        when(jpaBookRepository.findById(book.getId())).thenReturn(Optional.of(bookEntity));
        when(bookEntityMapper.toDomain(bookEntity)).thenReturn(book);

        Optional<Book> result = bookRepositoryAdapter.findById(book.getId());

        assertTrue(result.isPresent());
        assertEquals(book.getId(), result.get().getId());
        assertEquals(book.getTitle(), result.get().getTitle());
    }

    @Test
    void findById_ShouldReturnEmpty_WhenBookDoesNotExist() {
        when(jpaBookRepository.findById(TestUtils.createBook().getId())).thenReturn(Optional.empty());

        Optional<Book> result = bookRepositoryAdapter.findById(TestUtils.createBook().getId());

        assertFalse(result.isPresent());
    }

    @Test
    void findByIsbn_ShouldReturnBook_WhenBookExists() {
        Book book = TestUtils.createBook();
        BookEntity bookEntity = BookEntity.builder()
            .id(book.getId())
            .title(book.getTitle())
            .author(book.getAuthor())
            .isbn(book.getIsbn())
            .availableQuantity(book.getAvailableQuantity())
            .build();

        when(jpaBookRepository.findByIsbn(book.getIsbn())).thenReturn(Optional.of(bookEntity));
        when(bookEntityMapper.toDomain(bookEntity)).thenReturn(book);

        Optional<Book> result = bookRepositoryAdapter.findByIsbn(book.getIsbn());

        assertTrue(result.isPresent());
        assertEquals(book.getIsbn(), result.get().getIsbn());
    }

    @Test
    void findByIsbn_ShouldReturnEmpty_WhenBookDoesNotExist() {
        when(jpaBookRepository.findByIsbn(TestUtils.createBook().getIsbn())).thenReturn(Optional.empty());

        Optional<Book> result = bookRepositoryAdapter.findByIsbn(TestUtils.createBook().getIsbn());

        assertFalse(result.isPresent());
    }

    @Test
    void existsByIsbn_ShouldReturnTrue_WhenBookExists() {
        when(jpaBookRepository.existsByIsbn(TestUtils.createBook().getIsbn())).thenReturn(true);

        Boolean result = bookRepositoryAdapter.existsByIsbn(TestUtils.createBook().getIsbn());

        assertTrue(result);
    }

    @Test
    void existsByIsbn_ShouldReturnFalse_WhenBookDoesNotExist() {
        when(jpaBookRepository.existsByIsbn(TestUtils.createBook().getIsbn())).thenReturn(false);

        Boolean result = bookRepositoryAdapter.existsByIsbn(TestUtils.createBook().getIsbn());

        assertFalse(result);
    }

    @Test
    void findAllAvailable_ShouldReturnBooks_WhenAvailableBooksExist() {
        Book book = TestUtils.createBook();
        BookEntity bookEntity = BookEntity.builder()
            .id(book.getId())
            .title(book.getTitle())
            .author(book.getAuthor())
            .isbn(book.getIsbn())
            .availableQuantity(book.getAvailableQuantity())
            .build();
        List<BookEntity> bookEntities = List.of(bookEntity);

        when(jpaBookRepository.findByAvailableQuantityGreaterThan(0)).thenReturn(bookEntities);
        when(bookEntityMapper.toDomain(any(BookEntity.class))).thenReturn(book);

        List<Book> result = bookRepositoryAdapter.findAllAvailable();

        assertEquals(1, result.size());
        assertEquals(book.getId(), result.getFirst().getId());
        assertEquals(book.getAvailableQuantity(), result.getFirst().getAvailableQuantity());
    }

    @Test
    void findAllAvailable_ShouldReturnEmptyList_WhenNoAvailableBooks() {
        when(jpaBookRepository.findByAvailableQuantityGreaterThan(0)).thenReturn(Collections.emptyList());

        List<Book> result = bookRepositoryAdapter.findAllAvailable();

        assertTrue(result.isEmpty());
    }

    @Test
    void findById_ShouldReturnEmpty_WhenMapperReturnsNull() {
        Book book = TestUtils.createBook();
        BookEntity bookEntity = BookEntity.builder()
            .id(book.getId())
            .title(book.getTitle())
            .author(book.getAuthor())
            .isbn(book.getIsbn())
            .availableQuantity(book.getAvailableQuantity())
            .build();

        when(jpaBookRepository.findById(book.getId())).thenReturn(Optional.of(bookEntity));
        when(bookEntityMapper.toDomain(bookEntity)).thenReturn(null);

        Optional<Book> result = bookRepositoryAdapter.findById(book.getId());

        assertFalse(result.isPresent());
    }
}
