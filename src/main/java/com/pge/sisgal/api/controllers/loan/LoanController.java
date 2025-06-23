package com.pge.sisgal.api.controllers.loan;

import com.pge.sisgal.api.dtos.loan.requests.CreateLoanRequest;
import com.pge.sisgal.api.dtos.loan.requests.ReturnLoanRequest;
import com.pge.sisgal.api.dtos.loan.responses.LoanResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Empréstimos", description = "Operações relacionadas ao gerenciamento de empréstimos")
@RequestMapping("/api/loans")
public interface LoanController {

    @PostMapping
    @Operation(
        summary = "Cria um novo empréstimo. ROLES: ADMIN, USER",
        description = "Registra um novo empréstimo de livro para um usuário."
    )
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    ResponseEntity<LoanResponse> createLoan(@Valid @RequestBody CreateLoanRequest request);

    @PostMapping("/return")
    @Operation(
        summary = "Registra devolução de empréstimo. ROLES: ADMIN, USER",
        description = "Registra a devolução de um livro emprestado."
    )
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    ResponseEntity<LoanResponse> returnLoan(@Valid @RequestBody ReturnLoanRequest request);

    @GetMapping("/user/{userId}/active")
    @Operation(
        summary = "Lista empréstimos ativos por usuário. ROLES: ADMIN, USER",
        description = "Retorna todos os empréstimos ativos de um usuário específico pelo ID."
    )
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    ResponseEntity<List<LoanResponse>> getActiveLoansByUser(@PathVariable Long userId);

    @GetMapping("/user/{userId}/overdue")
    @Operation(
        summary = "Lista empréstimos atrasados por usuário. ROLES: ADMIN, USER",
        description = "Retorna todos os empréstimos em atraso de um usuário específico pelo ID."
    )
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    ResponseEntity<List<LoanResponse>> getOverdueLoansByUser(@PathVariable Long userId);
}
