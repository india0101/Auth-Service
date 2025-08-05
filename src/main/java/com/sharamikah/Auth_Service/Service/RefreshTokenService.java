package com.sharamikah.Auth_Service.Service;


import com.sharamikah.Auth_Service.domain.entity.AppUser;
import java.util.Optional;

public interface RefreshTokenService {

    /**
     * Create and persist a new refresh token for the given user.
     *
     * @param user the AppUser to link the token to
     * @return a new opaque-refresh-token string
     */
    String createRefreshToken(AppUser user);

    /**
     * Revoke (delete) a refresh token by its raw token value.
     *
     * @param refreshToken the opaque token to revoke
     * @return true if a matching token was found and deleted, false otherwise
     */
    boolean revokeByToken(String refreshToken);

    /**
     * Check that a refresh token exists and is not expired.
     *
     * @param refreshToken the opaque token to validate
     * @return Optional of the user's identifier (e.g. user ID or email) if valid; empty if invalid/expired
     */
    Optional<String> validateRefreshToken(String refreshToken);

    /**
     * Rotate a refresh token: revoke the old one and issue a brand-new one.
     *
     * @param oldToken the token to revoke
     * @param user     the AppUser requesting rotation
     * @return the newly issued opaque refresh token string
     */
    String rotateRefreshToken(String oldToken, AppUser user);
}
