package com.sharamikah.Auth_Service.Service.Impl;


import com.sharamikah.Auth_Service.DTO.AuthenticationResponseDTO;
import com.sharamikah.Auth_Service.DTO.LoginRequestDTO;
import com.sharamikah.Auth_Service.DTO.TokenRefreshRequestDTO;
import com.sharamikah.Auth_Service.DTO.TokenRefreshResponse;
import com.sharamikah.Auth_Service.domain.entity.AppUser;
import com.sharamikah.Auth_Service.Exception.AuthenticationFailedException;
import com.sharamikah.Auth_Service.Exception.RefreshTokenNotFoundException;
import com.sharamikah.Auth_Service.Exception.TokenValidationException;
import com.sharamikah.Auth_Service.Exception.UserNotFoundException;
import com.sharamikah.Auth_Service.Repository.UserRepository;
import com.sharamikah.Auth_Service.Service.AuthenticationService;
import com.sharamikah.Auth_Service.Service.DeviceSessionService;
import com.sharamikah.Auth_Service.Service.RefreshTokenService;
import com.sharamikah.Auth_Service.util.JwtTokenProvider;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authManager;
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtProvider;
    private final RefreshTokenService refreshTokenSvc;
    private final DeviceSessionService deviceSessionSvc;

    public AuthenticationServiceImpl(
            AuthenticationManager authManager,
            UserRepository userRepo,
            PasswordEncoder passwordEncoder,
            JwtTokenProvider jwtProvider,
            RefreshTokenService refreshTokenSvc,
            DeviceSessionService deviceSessionSvc) {
        this.authManager = authManager;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.refreshTokenSvc = refreshTokenSvc;
        this.deviceSessionSvc = deviceSessionSvc;
    }

    /** Validate credentials, record session, issue JWT + refresh token */
    @Override
    @Transactional
    public AuthenticationResponseDTO login(LoginRequestDTO request) {
        try {
            // Authenticate via Spring Security
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (BadCredentialsException ex) {
            throw new AuthenticationFailedException("Invalid email or password");
        }

        // Load user entity
        AppUser user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException(request.getEmail()));

        // Generate tokens
        String accessToken  = jwtProvider.generateAccessToken(user);
        String refreshToken = refreshTokenSvc.createRefreshToken(user);

        // Start device session (optional: pass IP/UA from request)
        deviceSessionSvc.createSession(
                // you’d map a CreateDeviceSessionRequest here
        );

        return new AuthenticationResponseDTO(
                accessToken,
                refreshToken,
                "Bearer",
                jwtProvider.getAccessTokenExpirySeconds()
        );
    }

    /** Revoke refresh token and related session */
    @Override
    @PreAuthorize("isAuthenticated()")
    @Transactional
    public void logout(String refreshToken) {
        boolean removed = refreshTokenSvc.revokeByToken(refreshToken);
        if (!removed) {
            throw new RefreshTokenNotFoundException(refreshToken);
        }
    }

    /** Exchange a valid refresh token for new tokens */
    @Override
    @Transactional
    public TokenRefreshResponse refresh(TokenRefreshRequestDTO request) {
        // Validate & rotate refresh token
        String userEmail = refreshTokenSvc.validateRefreshToken(request.getRefreshToken())
                .orElseThrow(() -> new TokenValidationException("Invalid or expired refresh token"));

        AppUser user = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException(userEmail));

        // Issue new tokens
        String newAccessToken  = jwtProvider.generateAccessToken(user);
        String newRefreshToken = refreshTokenSvc.rotateRefreshToken(request.getRefreshToken(), user);

        return new TokenRefreshResponse(
                newAccessToken,
                newRefreshToken,
                "Bearer",
                jwtProvider.getAccessTokenExpirySeconds()
        );
    }

    /** Quick check of access token’s signature & expiry */
    @Override
    public boolean validateAccessToken(String accessToken) {
        try {
            return jwtProvider.validateToken(accessToken);
        } catch (Exception ex) {
            return false;
        }
    }


    @Transactional
    public public class AuthenticationResponseDTO {
        refreshAccessToken(TokenRefreshRequestDTO request) {
        String oldToken = request.getRefreshToken();
        String email = RefreshTokenService
                .validateRefreshToken(oldToken)
                .orElseThrow(() -> new TokenValidationException("Invalid or expired refresh token"));

        // rotate: revoke old & create new
        String newRefresh = refreshService.rotateRefreshToken(oldToken, email);
        String newAccess  = jwtProvider.generateAccessToken(email);

        return new public class AuthenticationResponseDTO {
(newAccess, newRefresh);
    }
}

