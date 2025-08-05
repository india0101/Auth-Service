package com.sharamikah.Auth_Service.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Returned by the login endpoint upon successful authentication.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponseDTO {

    /**
     * JWT access token (RFC 7519).
     */
    private String accessToken;

    /**
     * Opaque refresh token.
     */
    private String refreshToken;

    /**
     * Token type, typically “Bearer”.
     */
    private String tokenType;

    /**
     * Seconds until the access token expires.
     */
    private Long expiresIn;
}

