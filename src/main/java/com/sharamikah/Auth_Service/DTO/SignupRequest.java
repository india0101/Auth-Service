package com.sharamikah.Auth_Service.DTO;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.AssertTrue;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @AssertTrue(message = "Terms must be accepted")
    private boolean termsAccepted;

    @AssertTrue(message = "Privacy policy must be accepted")
    private boolean privacyPolicyAccepted;
}
