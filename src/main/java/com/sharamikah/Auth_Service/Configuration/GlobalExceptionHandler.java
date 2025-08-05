package com.sharamikah.Auth_Service.Configuration;


import com.sharamikah.Auth_Service.Exception.TokenReuseDetectedException;
import com.sharamikah.Auth_Service.Exception.TokenValidationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
            errors.put(fe.getField(), fe.getDefaultMessage());
        }
        Map<String, Object> body = Map.of(
                "timestamp", Instant.now(),
                "status", HttpStatus.BAD_REQUEST.value(),
                "errors", errors
        );
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler({ TokenValidationException.class, JwtException.class })
    public ResponseEntity<?> handleUnauthorized(RuntimeException ex) {
        Map<String, Object> body = Map.of(
                "timestamp", Instant.now(),
                "status", HttpStatus.UNAUTHORIZED.value(),
                "error", "Unauthorized",
                "message", ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    @ExceptionHandler(TokenReuseDetectedException.class)
    public ResponseEntity<?> handleForbidden(TokenReuseDetectedException ex) {
        Map<String, Object> body = Map.of(
                "timestamp", Instant.now(),
                "status", HttpStatus.FORBIDDEN.value(),
                "error", "Forbidden",
                "message", ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneric(Exception ex) {
        Map<String, Object> body = Map.of(
                "timestamp", Instant.now(),
                "status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "error", "Internal Server Error",
                "message", "An unexpected error occurred"
        );
        return new ResponseEntity<>(body, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

