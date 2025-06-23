package com.pge.sisgal.api.controllers.auth;

import com.pge.sisgal.api.dtos.auth.request.AuthRequest;
import com.pge.sisgal.api.dtos.auth.response.AuthResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Autenticação", description = "Operações relacionadas à autenticação de usuários")
@RequestMapping("/api/auth")
public interface AuthController {

    @PostMapping("/login")
    @Operation(
        summary = "Autenticar usuário. ACESSO PÚBLICO",
        description = "Autentica um usuário com matrícula e senha, retornando um token JWT."
    )
    ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request);
}
