package com.pge.sisgal.infraestructure.persistence.adapters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.pge.sisgal.TestUtils;
import com.pge.sisgal.domain.entities.Book;
import com.pge.sisgal.domain.entities.Loan;
import com.pge.sisgal.domain.entities.User;
import com.pge.sisgal.infraestructure.persistence.entities.BookEntity;
import com.pge.sisgal.infraestructure.persistence.entities.LoanEntity;
import com.pge.sisgal.infraestructure.persistence.entities.UserEntity;
import com.pge.sisgal.infraestructure.persistence.mappers.BookEntityMapper;
import com.pge.sisgal.infraestructure.persistence.mappers.LoanEntityMapper;
import com.pge.sisgal.infraestructure.persistence.mappers.UserEntityMapper;
import com.pge.sisgal.infraestructure.persistence.repositories.JpaBookRepository;
import com.pge.sisgal.infraestructure.persistence.repositories.JpaLoanRepository;
import com.pge.sisgal.infraestructure.persistence.repositories.JpaUserRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import({LoanEntityMapper.class, BookEntityMapper.class, UserEntityMapper.class})
class LoanRepositoryAdapterIntegrationTest {

    @Autowired
    private JpaLoanRepository jpaLoanRepository;

    @Autowired
    private JpaBookRepository jpaBookRepository;

    @Autowired
    private JpaUserRepository jpaUserRepository;

    @Autowired
    private LoanEntityMapper loanEntityMapper;

    @Autowired
    private BookEntityMapper bookEntityMapper;

    @Autowired
    private UserEntityMapper userEntityMapper;

    @Autowired
    private TestEntityManager entityManager;

    private LoanRepositoryAdapter loanRepositoryAdapter;

    private Book savedBook;
    private User savedUser;

    @BeforeEach
    void setUp() {
        loanRepositoryAdapter = new LoanRepositoryAdapter(jpaLoanRepository, loanEntityMapper);

        Book book = TestUtils.createBook();
        book.setId(null);
        User user = TestUtils.createUser();
        user.setId(null);

        BookEntity bookEntity = jpaBookRepository.save(bookEntityMapper.toEntity(book));
        UserEntity userEntity = jpaUserRepository.save(userEntityMapper.toEntity(user));

        savedBook = bookEntityMapper.toDomain(bookEntity);
        savedUser = userEntityMapper.toDomain(userEntity);

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void save_ShouldPersistLoanAndReturnIt() {
        Loan loan = TestUtils.createLoan();
        loan.setId(null);
        loan.setBook(savedBook);
        loan.setUser(savedUser);

        Loan savedLoan = loanRepositoryAdapter.save(loan);

        assertTrue(savedLoan.getId() > 0);
        assertEquals(loan.getStartDate(), savedLoan.getStartDate());
        assertEquals(loan.getDueDate(), savedLoan.getDueDate());
        assertEquals(savedBook.getId(), savedLoan.getBook().getId());
        assertEquals(savedUser.getId(), savedLoan.getUser().getId());
    }

    @Test
    void findById_ShouldReturnLoan_WhenLoanExists() {
        Loan loan = TestUtils.createLoan();
        loan.setId(null);
        loan.setBook(savedBook);
        loan.setUser(savedUser);
        LoanEntity loanEntity = loanEntityMapper.toEntity(loan);
        LoanEntity savedLoanEntity = jpaLoanRepository.save(loanEntity);

        Optional<Loan> result = loanRepositoryAdapter.findById(savedLoanEntity.getId());

        assertTrue(result.isPresent());
        assertEquals(savedLoanEntity.getId(), result.get().getId());
        assertEquals(loan.getStartDate(), result.get().getStartDate());
    }

    @Test
    void findById_ShouldReturnEmpty_WhenLoanDoesNotExist() {
        Optional<Loan> result = loanRepositoryAdapter.findById(999L);

        assertFalse(result.isPresent());
    }

    @Test
    void findActiveLoansByUser_ShouldReturnLoans_WhenActiveLoansExist() {
        Loan loan = TestUtils.createLoan();
        loan.setId(null);
        loan.setBook(savedBook);
        loan.setUser(savedUser);
        LoanEntity loanEntity = loanEntityMapper.toEntity(loan);
        jpaLoanRepository.save(loanEntity);
        entityManager.flush();
        entityManager.clear();

        List<Loan> result = loanRepositoryAdapter.findActiveLoansByUser(savedUser.getId());

        assertEquals(1, result.size());
        assertEquals(savedUser.getId(), result.getFirst().getUser().getId());
        assertNull(result.getFirst().getReturnDate());
    }

    @Test
    void findActiveLoansByUser_ShouldReturnEmptyList_WhenNoActiveLoansExist() {
        List<Loan> result = loanRepositoryAdapter.findActiveLoansByUser(savedUser.getId());

        assertTrue(result.isEmpty());
    }

    @Test
    void findOverdueLoansByUser_ShouldReturnLoans_WhenOverdueLoansExist() {
        Loan loan = TestUtils.createLoan();
        loan.setId(null);
        loan.setBook(savedBook);
        loan.setUser(savedUser);
        loan.setDueDate(LocalDate.of(2025, 1, 1));
        LoanEntity loanEntity = loanEntityMapper.toEntity(loan);
        jpaLoanRepository.save(loanEntity);
        entityManager.flush();
        entityManager.clear();

        List<Loan> result = loanRepositoryAdapter.findOverdueLoansByUser(savedUser.getId(), LocalDate.of(2025, 6, 22));

        assertEquals(1, result.size());
        assertEquals(savedUser.getId(), result.getFirst().getUser().getId());
        assertEquals(LocalDate.of(2025, 1, 1), result.getFirst().getDueDate());
    }

    @Test
    void findOverdueLoansByUser_ShouldReturnEmptyList_WhenNoOverdueLoansExist() {
        Loan loan = TestUtils.createLoan();
        loan.setId(null);
        loan.setBook(savedBook);
        loan.setUser(savedUser);
        loan.setDueDate(LocalDate.of(2025, 7, 1));
        LoanEntity loanEntity = loanEntityMapper.toEntity(loan);
        jpaLoanRepository.save(loanEntity);
        entityManager.flush();
        entityManager.clear();

        List<Loan> result = loanRepositoryAdapter.findOverdueLoansByUser(savedUser.getId(), LocalDate.of(2025, 6, 22));

        assertTrue(result.isEmpty());
    }
}
