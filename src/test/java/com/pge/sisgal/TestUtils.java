package com.pge.sisgal;

import com.pge.sisgal.api.dtos.book.responses.BookResponse;
import com.pge.sisgal.api.dtos.loan.responses.LoanResponse;
import com.pge.sisgal.api.dtos.user.responses.UserResponse;
import com.pge.sisgal.domain.entities.Book;
import com.pge.sisgal.domain.entities.Loan;
import com.pge.sisgal.domain.entities.Role;
import com.pge.sisgal.domain.entities.User;
import java.time.LocalDate;

public final class TestUtils {

    public static final Long DEFAULT_BOOK_ID = 2L;
    public static final String DEFAULT_BOOK_TITLE = "Default Title";
    public static final String DEFAULT_BOOK_AUTHOR = "Default Author";
    public static final String DEFAULT_BOOK_ISBN = "1234567890";
    public static final int DEFAULT_BOOK_QUANTITY = 5;
    public static final Long DEFAULT_USER_ID = 3L;
    public static final String DEFAULT_USER_NAME = "Default Name";
    public static final String DEFAULT_USER_REGISTRATION = "12345678";
    public static final String DEFAULT_USER_EMAIL = "default@email.com";
    public static final String DEFAULT_USER_PASSWORD = "password";
    public static final Role DEFAULT_USER_ROLE = Role.USER;
    public static final Long DEFAULT_LOAN_ID = 1L;
    public static final LocalDate DEFAULT_START_DATE = LocalDate.of(2025, 1, 1);
    public static final LocalDate DEFAULT_DUE_DATE = LocalDate.of(2025, 1, 15);
    public static final LocalDate DEFAULT_RETURN_DATE = LocalDate.of(2025, 1, 10);

    private TestUtils() {
    }

    public static Book createBook() {
        Book book = new Book(DEFAULT_BOOK_TITLE, DEFAULT_BOOK_AUTHOR, DEFAULT_BOOK_ISBN, DEFAULT_BOOK_QUANTITY);
        book.setId(DEFAULT_BOOK_ID);
        return book;
    }

    public static User createUser() {
        User user = new User(DEFAULT_USER_NAME, DEFAULT_USER_REGISTRATION, DEFAULT_USER_EMAIL, DEFAULT_USER_PASSWORD, DEFAULT_USER_ROLE);
        user.setId(DEFAULT_USER_ID);
        return user;
    }

    public static Loan createLoan() {
        Loan loan = new Loan(createBook(), createUser(), DEFAULT_START_DATE, DEFAULT_DUE_DATE);
        loan.setId(DEFAULT_LOAN_ID);
        return loan;
    }

    public static Loan createReturnedLoan() {
        Loan loan = new Loan(createBook(), createUser(), DEFAULT_START_DATE, DEFAULT_DUE_DATE);
        loan.setId(DEFAULT_LOAN_ID);
        loan.setReturnDate(DEFAULT_RETURN_DATE);
        return loan;
    }

    public static BookResponse createBookResponse() {
        return BookResponse.builder()
            .id(DEFAULT_BOOK_ID)
            .title(DEFAULT_BOOK_TITLE)
            .author(DEFAULT_BOOK_AUTHOR)
            .isbn(DEFAULT_BOOK_ISBN)
            .availableQuantity(DEFAULT_BOOK_QUANTITY)
            .build();
    }

    public static UserResponse createUserResponse() {
        return UserResponse.builder()
            .id(DEFAULT_USER_ID)
            .name(DEFAULT_USER_NAME)
            .registration(DEFAULT_USER_REGISTRATION)
            .email(DEFAULT_USER_EMAIL)
            .role(DEFAULT_USER_ROLE.name())
            .build();
    }

    public static LoanResponse createLoanResponse() {
        return LoanResponse.builder()
            .id(DEFAULT_LOAN_ID)
            .book(createBookResponse())
            .user(createUserResponse())
            .startDate(DEFAULT_START_DATE)
            .dueDate(DEFAULT_DUE_DATE)
            .build();
    }

    public static LoanResponse createReturnedLoanResponse() {
        return LoanResponse.builder()
            .id(DEFAULT_LOAN_ID)
            .book(createBookResponse())
            .user(createUserResponse())
            .startDate(DEFAULT_START_DATE)
            .dueDate(DEFAULT_DUE_DATE)
            .returnDate(DEFAULT_RETURN_DATE)
            .build();
    }
}
