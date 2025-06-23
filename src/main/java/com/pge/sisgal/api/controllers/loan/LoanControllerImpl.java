package com.pge.sisgal.api.controllers.loan;

import com.pge.sisgal.api.dtos.loan.requests.CreateLoanRequest;
import com.pge.sisgal.api.dtos.loan.requests.ReturnLoanRequest;
import com.pge.sisgal.api.dtos.loan.responses.LoanResponse;
import com.pge.sisgal.application.services.loan.LoanApplicationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoanControllerImpl implements LoanController {

    private final LoanApplicationService loanApplicationService;

    @Override
    public ResponseEntity<LoanResponse> createLoan(CreateLoanRequest request) {
        LoanResponse response = loanApplicationService.createLoan(
            request.getBookId(),
            request.getUserId(),
            request.getStartDate(),
            request.getDueDate()
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<LoanResponse> returnLoan(ReturnLoanRequest request) {
        LoanResponse response = loanApplicationService.returnLoan(
            request.getLoanId(),
            request.getReturnDate()
        );
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<LoanResponse>> getActiveLoansByUser(Long userId) {
        List<LoanResponse> responses = loanApplicationService.getActiveLoansByUser(userId);
        return ResponseEntity.ok(responses);
    }

    @Override
    public ResponseEntity<List<LoanResponse>> getOverdueLoansByUser(Long userId) {
        List<LoanResponse> responses = loanApplicationService.getOverdueLoansByUser(userId);
        return ResponseEntity.ok(responses);
    }
}
