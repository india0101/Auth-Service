package com.sharamikah.Auth_Service.DTO;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OAuthRequest {

    @NotBlank
    private String provider;       // "GOOGLE" or "APPLE"

    @NotBlank
    private String authorizationCode;

    @NotBlank
    private String redirectUri;
}
