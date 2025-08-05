package com.sharamikah.Auth_Service.Configuration;


import com.sharamikah.Auth_Service.Exception.TokenReuseDetectedException;
import com.sharamikah.Auth_Service.Exception.TokenValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(TokenValidationException.class)
    public ResponseEntity<?> handleValidation(TokenValidationException ex) {
        Map<String, Object> body = Map.of(
                "timestamp", Instant.now(),
                "status", HttpStatus.UNAUTHORIZED.value(),
                "error", "Unauthorized",
                "message", ex.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(body);
    }

    @ExceptionHandler(TokenReuseDetectedException.class)
    public ResponseEntity<?> handleReuse(TokenReuseDetectedException ex) {
        Map<String, Object> body = Map.of(
                "timestamp", Instant.now(),
                "status", HttpStatus.FORBIDDEN.value(),
                "error", "Forbidden",
                "message", ex.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(body);
    }
}
