package com.sharamikah.Auth_Service.DTO;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyEmailRequest {

    @NotBlank
    private String token;      // email verification token
}
