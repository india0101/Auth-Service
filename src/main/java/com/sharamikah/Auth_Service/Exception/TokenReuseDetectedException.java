package com.sharamikah.Auth_Service.Exception;

// Thrown when a valid token is reused
public class TokenReuseDetectedException extends RuntimeException {
    public TokenReuseDetectedException(String token) {
        super("Refresh token reuse detected: " + token);
    }
}
