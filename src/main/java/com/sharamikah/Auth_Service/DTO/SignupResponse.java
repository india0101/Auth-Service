package com.sharamikah.Auth_Service.DTO;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupResponse {

    private Long userId;
    private String email;
    private String message;     // e.g. "Verification email sent"
}
