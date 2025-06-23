package com.pge.sisgal.api.dtos.book.requests;

import com.pge.sisgal.domain.messages.BookValidationMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(name = "CreateBookRequest", description = "DTO para criação de um novo livro")
public class CreateBookRequest {

    @NotBlank(message = BookValidationMessages.TITLE_REQUIRED)
    @Schema(description = "Título do livro", example = "Dom Casmurro")
    private String title;

    @NotBlank(message = BookValidationMessages.AUTHOR_REQUIRED)
    @Schema(description = "Autor do livro", example = "Machado de Assis")
    private String author;

    @NotBlank(message = BookValidationMessages.ISBN_REQUIRED)
    @Schema(description = "ISBN do livro", example = "978-85-7232-238-1")
    private String isbn;

    @NotNull(message = BookValidationMessages.AVAILABLE_QUANTITY_REQUIRED)
    @Schema(description = "Quantidade disponível", example = "5")
    private Integer availableQuantity;

}
