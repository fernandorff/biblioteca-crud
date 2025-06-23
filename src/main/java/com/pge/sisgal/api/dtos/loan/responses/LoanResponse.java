package com.pge.sisgal.api.dtos.loan.responses;

import com.pge.sisgal.api.dtos.book.responses.BookResponse;
import com.pge.sisgal.api.dtos.user.responses.UserResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Schema(name = "LoanResponse", description = "DTO com informações do empréstimo")
public class LoanResponse {

    @Schema(description = "ID do empréstimo", example = "1")
    private Long id;

    @Schema(description = "Informações do livro emprestado")
    private BookResponse book;

    @Schema(description = "Informações do usuário que pegou emprestado")
    private UserResponse user;

    @Schema(description = "Data de início do empréstimo", example = "2023-01-01")
    private LocalDate startDate;

    @Schema(description = "Data de vencimento do empréstimo", example = "2023-01-15")
    private LocalDate dueDate;

    @Schema(description = "Data de devolução do empréstimo", example = "2023-01-10")
    private LocalDate returnDate;

}

