package com.pge.sisgal.api.controllers.user;

import com.pge.sisgal.api.dtos.user.requests.CreateUserRequest;
import com.pge.sisgal.api.dtos.user.responses.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Usuários", description = "Operações relacionadas ao gerenciamento de usuários")
@RequestMapping("/api/users")
public interface UserController {

    @PostMapping
    @Operation(
        summary = "Cria um novo usuário. ACESSO PÚBLICO",
        description = "Cadastra um novo usuário no sistema com os dados fornecidos."
    )
    ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request);

    @GetMapping("/{id}")
    @Operation(
        summary = "Busca usuário pelo ID. ROLES: ADMIN, USER",
        description = "Recupera os detalhes de um usuário específico pelo seu ID."
    )
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    ResponseEntity<UserResponse> getUserById(@PathVariable Long id);

    @GetMapping("/matricula/{registration}")
    @Operation(
        summary = "Busca usuário por matrícula. ROLES: ADMIN, USER",
        description = "Recupera os detalhes de um usuário específico pela sua matrícula."
    )
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    ResponseEntity<UserResponse> getUserByRegistration(@PathVariable String registration);
}
