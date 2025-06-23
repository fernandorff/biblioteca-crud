package com.pge.sisgal.infraestructure.persistence.adapters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.pge.sisgal.TestUtils;
import com.pge.sisgal.domain.entities.Book;
import com.pge.sisgal.infraestructure.persistence.entities.BookEntity;
import com.pge.sisgal.infraestructure.persistence.mappers.BookEntityMapper;
import com.pge.sisgal.infraestructure.persistence.repositories.JpaBookRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(BookEntityMapper.class)
class BookRepositoryAdapterIntegrationTest {

    @Autowired
    private JpaBookRepository jpaBookRepository;

    @Autowired
    private BookEntityMapper bookEntityMapper;

    @Autowired
    private TestEntityManager entityManager;

    private BookRepositoryAdapter bookRepositoryAdapter;

    @BeforeEach
    void setUp() {
        bookRepositoryAdapter = new BookRepositoryAdapter(jpaBookRepository, bookEntityMapper);
        entityManager.clear();
    }

    @Test
    void save_ShouldPersistBookAndReturnIt() {
        Book book = TestUtils.createBook();
        book.setId(null);

        Book savedBook = bookRepositoryAdapter.save(book);

        entityManager.flush();
        assertTrue(savedBook.getId() > 0);
        assertEquals(book.getTitle(), savedBook.getTitle());
        assertEquals(book.getAuthor(), savedBook.getAuthor());
        assertEquals(book.getIsbn(), savedBook.getIsbn());
        assertEquals(book.getAvailableQuantity(), savedBook.getAvailableQuantity());
    }

    @Test
    void findById_ShouldReturnBook_WhenBookExists() {
        Book book = TestUtils.createBook();
        book.setId(null);
        BookEntity bookEntity = bookEntityMapper.toEntity(book);
        BookEntity savedBookEntity = jpaBookRepository.save(bookEntity);
        entityManager.flush();
        entityManager.clear();

        Optional<Book> result = bookRepositoryAdapter.findById(savedBookEntity.getId());

        assertTrue(result.isPresent());
        assertEquals(savedBookEntity.getId(), result.get().getId());
        assertEquals(book.getTitle(), result.get().getTitle());
    }

    @Test
    void findById_ShouldReturnEmpty_WhenBookDoesNotExist() {
        Optional<Book> result = bookRepositoryAdapter.findById(999L);

        assertFalse(result.isPresent());
    }

    @Test
    void findByIsbn_ShouldReturnBook_WhenBookExists() {
        Book book = TestUtils.createBook();
        book.setId(null);
        BookEntity bookEntity = bookEntityMapper.toEntity(book);
        BookEntity savedBookEntity = jpaBookRepository.save(bookEntity);
        entityManager.flush();
        entityManager.clear();

        Optional<Book> result = bookRepositoryAdapter.findByIsbn(book.getIsbn());

        assertTrue(result.isPresent());
        assertEquals(savedBookEntity.getId(), result.get().getId());
        assertEquals(book.getIsbn(), result.get().getIsbn());
    }

    @Test
    void findByIsbn_ShouldReturnEmpty_WhenBookDoesNotExist() {
        Optional<Book> result = bookRepositoryAdapter.findByIsbn("9999999999");

        assertFalse(result.isPresent());
    }

    @Test
    void existsByIsbn_ShouldReturnTrue_WhenBookExists() {
        Book book = TestUtils.createBook();
        book.setId(null);
        BookEntity bookEntity = bookEntityMapper.toEntity(book);
        jpaBookRepository.save(bookEntity);
        entityManager.flush();
        entityManager.clear();

        Boolean result = bookRepositoryAdapter.existsByIsbn(book.getIsbn());

        assertTrue(result);
    }

    @Test
    void existsByIsbn_ShouldReturnFalse_WhenBookDoesNotExist() {
        Boolean result = bookRepositoryAdapter.existsByIsbn("9999999999");

        assertFalse(result);
    }

    @Test
    void findAllAvailable_ShouldReturnBooks_WhenAvailableBooksExist() {
        Book book1 = TestUtils.createBook();
        book1.setId(null);
        Book book2 = TestUtils.createBook();
        book2.setId(null);
        book2.setIsbn("9876543210");
        BookEntity bookEntity1 = bookEntityMapper.toEntity(book1);
        BookEntity bookEntity2 = bookEntityMapper.toEntity(book2);
        jpaBookRepository.saveAll(List.of(bookEntity1, bookEntity2));
        entityManager.flush();
        entityManager.clear();

        List<Book> result = bookRepositoryAdapter.findAllAvailable();

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(b -> b.getIsbn().equals(book1.getIsbn())));
        assertTrue(result.stream().anyMatch(b -> b.getIsbn().equals(book2.getIsbn())));
    }

    @Test
    void findAllAvailable_ShouldReturnEmptyList_WhenNoAvailableBooksExist() {
        Book book = TestUtils.createBook();
        book.setId(null);
        book.setAvailableQuantity(0);
        BookEntity bookEntity = bookEntityMapper.toEntity(book);
        jpaBookRepository.save(bookEntity);
        entityManager.flush();
        entityManager.clear();

        List<Book> result = bookRepositoryAdapter.findAllAvailable();

        assertTrue(result.isEmpty());
    }
}
