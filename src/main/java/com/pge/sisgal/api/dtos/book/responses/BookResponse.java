package com.pge.sisgal.api.dtos.book.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(name = "BookResponse", description = "DTO com informações do livro")
public class BookResponse {

    @Schema(description = "ID do livro", example = "1")
    private Long id;

    @Schema(description = "Título do livro", example = "Dom Casmurro")
    private String title;

    @Schema(description = "Autor do livro", example = "Machado de Assis")
    private String author;

    @Schema(description = "ISBN do livro", example = "978-85-7232-238-1")
    private String isbn;

    @Schema(description = "Quantidade disponível em estoque", example = "3")
    private Integer availableQuantity;

}
