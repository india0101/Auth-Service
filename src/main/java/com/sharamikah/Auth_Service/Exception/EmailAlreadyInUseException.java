package com.sharamikah.Auth_Service.Exception;


public class EmailAlreadyInUseException extends RuntimeException {
    public EmailAlreadyInUseException(String email) {
        super("Email already in use: " + email);
    }
}
