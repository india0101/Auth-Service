package com.sharamikah.Auth_Service.DTO;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordResponse {

    private String message;    // e.g. "Password reset successful"
}

