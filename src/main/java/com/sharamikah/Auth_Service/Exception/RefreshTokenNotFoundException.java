package com.sharamikah.Auth_Service.Exception;

public class RefreshTokenNotFoundException extends RuntimeException {
    public RefreshTokenNotFoundException(String token) {
        super("Refresh token not found: " + token);
    }
}