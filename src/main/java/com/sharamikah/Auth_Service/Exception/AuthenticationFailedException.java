package com.sharamikah.Auth_Service.Exception;

public class AuthenticationFailedException extends RuntimeException {
    public AuthenticationFailedException(String msg) { super(msg); }
}