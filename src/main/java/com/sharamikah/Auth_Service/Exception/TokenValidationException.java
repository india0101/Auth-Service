package com.sharamikah.Auth_Service.Exception;

public class TokenValidationException extends RuntimeException {
    public TokenValidationException(String msg) { super(msg); }
}