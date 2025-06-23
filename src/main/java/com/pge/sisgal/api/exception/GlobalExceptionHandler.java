package com.pge.sisgal.api.exception;

import com.pge.sisgal.api.exception.dto.ErrorResponse;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        ErrorResponse response = ErrorResponse.builder()
            .error(ex.getClass().getSimpleName())
            .details(errors)
            .status(HttpStatus.BAD_REQUEST.value())
            .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
        IllegalArgumentException.class,
        IllegalStateException.class,
        BadCredentialsException.class,
        UsernameNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(Exception ex) {
        ErrorResponse response = ErrorResponse.builder()
            .error(ex.getClass().getSimpleName())
            .details(ex.getMessage())
            .status(HttpStatus.BAD_REQUEST.value())
            .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(Exception ex) {
        ErrorResponse response = ErrorResponse.builder()
            .error(ex.getClass().getSimpleName())
            .details("Acesso negado: você não tem permissão para acessar este recurso.")
            .status(HttpStatus.FORBIDDEN.value())
            .build();
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        ErrorResponse response = ErrorResponse.builder()
            .error(ex.getClass().getSimpleName())
            .details("Erro interno do servidor: " + ex.getMessage())
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
