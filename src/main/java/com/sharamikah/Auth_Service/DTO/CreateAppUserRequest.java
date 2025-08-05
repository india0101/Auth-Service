package com.sharamikah.Auth_Service.DTO;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


/**
 * Payload for registering a new user.
 */
@Data
public class CreateAppUserRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 8, message = "Password must be 8+ chars")
    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;
}

