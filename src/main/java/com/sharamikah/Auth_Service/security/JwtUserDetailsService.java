package com.sharamikah.Auth_Service.security;


import org.springframework.security.core.Authentication;

public interface JwtUserDetailsService {
    /**
     * Validate the token, extract the email, load the user, and
     * build a complete Authentication object (or throw on failure).
     */
    Authentication getAuthenticationFromToken(String token);
}

