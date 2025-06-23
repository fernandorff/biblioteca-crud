package com.pge.sisgal.api.controllers.book;

import com.pge.sisgal.api.dtos.book.requests.CreateBookRequest;
import com.pge.sisgal.api.dtos.book.responses.BookResponse;
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

@Tag(name = "Livros", description = "Operações relacionadas ao gerenciamento de livros")
@RequestMapping("/api/books")
public interface BookController {

    @PostMapping
    @Operation(
        summary = "Cria um novo livro ROLES: ADMIN",
        description = "Cadastra um novo livro no sistema com os dados fornecidos."
    )
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<BookResponse> createBook(@Valid @RequestBody CreateBookRequest request);

    @GetMapping("/{id}")
    @Operation(
        summary = "Busca livro por ID. ROLES: ADMIN, USER",
        description = "Recupera os detalhes de um livro específico pelo seu ID."
    )
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    ResponseEntity<BookResponse> getBookById(@PathVariable Long id);

    @GetMapping("/isbn/{isbn}")
    @Operation(
        summary = "Busca livro por ISBN. ROLES: ADMIN, USER",
        description = "Recupera os detalhes de um livro específico pelo seu ISBN."
    )
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    ResponseEntity<BookResponse> getBookByIsbn(@PathVariable String isbn);

    @GetMapping("/available")
    @Operation(
        summary = "Lista livros disponíveis. ROLES: ADMIN, USER",
        description = "Retorna uma lista de todos os livros disponíveis para empréstimo."
    )
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    ResponseEntity<List<BookResponse>> getAllAvailableBooks();
}
