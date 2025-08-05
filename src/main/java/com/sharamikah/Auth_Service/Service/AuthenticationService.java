package com.sharamikah.Auth_Service.Service;


import com.nimbusds.openid.connect.sdk.AuthenticationResponse;
import com.sharamikah.Auth_Service.DTO.LoginRequestDTO;
import com.sharamikah.Auth_Service.DTO.TokenRefreshRequestDTO;
import com.sharamikah.Auth_Service.DTO.TokenRefreshResponse;

public interface AuthenticationService {

    /** Verify credentials, record login, issue tokens */
    AuthenticationResponse login(LoginRequestDTO request);

    /** Invalidate current refresh token + session */
    void logout(String refreshToken);

    /** Exchange valid refresh token for new access/refresh pair */
    TokenRefreshResponse refresh(TokenRefreshRequestDTO request);

    /** Quick validation of an access token’s signature + expiry */
    boolean validateAccessToken(String accessToken);
}

