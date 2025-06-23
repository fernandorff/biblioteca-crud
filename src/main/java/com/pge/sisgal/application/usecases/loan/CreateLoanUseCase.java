package com.pge.sisgal.application.usecases.loan;

import com.pge.sisgal.domain.entities.Book;
import com.pge.sisgal.domain.entities.Loan;
import com.pge.sisgal.domain.entities.User;
import com.pge.sisgal.domain.messages.BookValidationMessages;
import com.pge.sisgal.domain.messages.UserValidationMessages;
import com.pge.sisgal.domain.persistence.BookRepository;
import com.pge.sisgal.domain.persistence.LoanRepository;
import com.pge.sisgal.domain.persistence.UserRepository;
import com.pge.sisgal.domain.services.LoanDomainService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateLoanUseCase {
    private final LoanDomainService loanDomainService;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final LoanRepository loanRepository;

    public Loan execute(Long bookId, Long userId, LocalDate startDate, LocalDate dueDate) {
        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new IllegalArgumentException(BookValidationMessages.bookNotFound(bookId)));

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException(UserValidationMessages.userNotFoundById(userId)));

        List<Loan> userLoans = loanRepository.findActiveLoansByUser(userId);

        Loan loan = loanDomainService.processLoanCreation(book, user, startDate, dueDate, userLoans);

        Loan savedLoan = loanRepository.save(loan);

        bookRepository.save(book);

        return savedLoan;
    }
}
